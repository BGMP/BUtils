package cl.bgmp.butils.bungee;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public interface BungeeChannel {
  String CHANNEL = "BungeeCord";
  String CONNECT = "Connect";
  String MESSAGE = "Message";
  String PLAYER_LIST = "PlayerList";
  String GET_SERVER = "GetServer";
  String GET_SERVERS = "GetServers";

  void registerOutgoing();

  void registerIncoming(PluginMessageListener listener);

  void sendPlayer(Player player, String serverName);

  void sendMessage(Player to, String message);

  String getThisServer();

  /**
   * @param serverName The target server to get the online players list of, or "ALL" if a response
   *     with all the players connected to the proxy is desired.
   * @return A list of all the players connected to the specified server, or to the whole proxy if
   *     requesting "ALL"
   */
  List<String> getOnlinePlayers(String serverName);

  List<String> getServers();
}
