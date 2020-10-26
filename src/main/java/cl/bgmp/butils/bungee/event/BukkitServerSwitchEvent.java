package cl.bgmp.butils.bungee.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BukkitServerSwitchEvent extends Event {
  private static final HandlerList handlerList = new HandlerList();

  private final Player player;
  private final String serverFrom;
  private final String serverTo;

  public BukkitServerSwitchEvent(Player player, String serverFrom, String serverTo) {
    this.player = player;
    this.serverFrom = serverFrom;
    this.serverTo = serverTo;
  }

  public Player getPlayer() {
    return player;
  }

  public String getFrom() {
    return serverFrom;
  }

  public String getTo() {
    return serverTo;
  }

  public static HandlerList getHandlerList() {
    return handlerList;
  }

  @Override
  public HandlerList getHandlers() {
    return handlerList;
  }
}
