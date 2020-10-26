package cl.bgmp.butils.bungee;

import cl.bgmp.butils.bungee.event.BukkitServerSwitchEvent;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

public class Bungee implements BungeeChannel, PluginMessageListener, Listener {
  private final Plugin plugin;
  private List<String> playerList = new ArrayList<>();
  private List<String> serverList = new ArrayList<>();
  private String serverName;

  public Bungee(Plugin plugin) {
    this.plugin = plugin;

    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  @SuppressWarnings("UnstableApiUsage")
  private ByteArrayDataOutput getNewDataOutPut() {
    return ByteStreams.newDataOutput();
  }

  @Override
  public void registerOutgoing() {
    this.plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, this.CHANNEL);
  }

  @Override
  public void registerIncoming(PluginMessageListener listener) {
    this.plugin
        .getServer()
        .getMessenger()
        .registerIncomingPluginChannel(this.plugin, this.CHANNEL, listener);
  }

  @Override
  public void sendPlayer(Player player, String serverTo) {
    ByteArrayDataOutput out = this.getNewDataOutPut();

    out.writeUTF(this.CONNECT);
    out.writeUTF(serverTo);

    player.sendPluginMessage(this.plugin, this.CHANNEL, out.toByteArray());

    this.plugin
        .getServer()
        .getPluginManager()
        .callEvent(new BukkitServerSwitchEvent(player, this.getThisServer(), serverTo));
  }

  @Override
  public void sendMessage(Player to, String message) {
    ByteArrayDataOutput out = this.getNewDataOutPut();

    out.writeUTF(this.MESSAGE);
    out.writeUTF(to.getName());
    out.writeUTF(message);

    to.sendPluginMessage(this.plugin, this.CHANNEL, out.toByteArray());
  }

  @Override
  public String getThisServer() {
    return serverName;
  }

  private void getThisServer(Player sender) {
    ByteArrayDataOutput out = this.getNewDataOutPut();

    out.writeUTF(this.GET_SERVER);

    sender.sendPluginMessage(this.plugin, this.CHANNEL, out.toByteArray());
  }

  @Override
  public List<String> getOnlinePlayers(String serverName) {
    return this.getOnlinePlayers(serverName, true, null);
  }

  private List<String> getOnlinePlayers(String serverName, boolean refresh, Player sender) {
    if (sender == null) sender = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
    if (sender == null) return Collections.emptyList();

    if (refresh) {
      final ByteArrayDataOutput out = this.getNewDataOutPut();

      out.writeUTF(this.PLAYER_LIST);
      out.writeUTF(serverName);

      sender.sendPluginMessage(this.plugin, this.CHANNEL, out.toByteArray());

      return this.getOnlinePlayers(serverName, false, sender);
    }

    return this.playerList;
  }

  @Override
  public List<String> getServers() {
    return this.getServers(true, null);
  }

  private List<String> getServers(boolean refresh, Player sender) {
    if (sender == null) sender = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
    if (sender == null) return Collections.emptyList();

    if (refresh) {
      final ByteArrayDataOutput out = this.getNewDataOutPut();
      out.writeUTF(this.GET_SERVERS);
      sender.sendPluginMessage(this.plugin, this.CHANNEL, out.toByteArray());

      return this.getServers(false, sender);
    }

    return this.serverList;
  }

  @Override
  @SuppressWarnings("UnstableApiUsage")
  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    if (!channel.equals(this.CHANNEL)) return;

    ByteArrayDataInput in = ByteStreams.newDataInput(message);
    String subChannel = in.readUTF();

    if (subChannel.equals(this.PLAYER_LIST)) {
      String server = in.readUTF();
      String[] connectedPlayers = in.readUTF().split(",");

      this.playerList = Arrays.asList(connectedPlayers);
      return;
    }

    if (subChannel.equals(this.GET_SERVER)) {
      String server = in.readUTF();
      this.serverName = server;
      return;
    }

    if (subChannel.equals(this.GET_SERVERS)) {
      String[] servers = in.readUTF().split(", ");
      this.serverList = Arrays.asList(servers);
    }
  }

  /** Touch some of the references once when a player joins for the first time */
  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    new BukkitRunnable() {
      @Override
      public void run() {
        Bungee.this.getOnlinePlayers("ALL", true, event.getPlayer());
        Bungee.this.getServers(true, event.getPlayer());
        Bungee.this.getThisServer(event.getPlayer());
        HandlerList.unregisterAll(Bungee.this);
      }
    }.runTaskLater(this.plugin, 0L);
  }
}
