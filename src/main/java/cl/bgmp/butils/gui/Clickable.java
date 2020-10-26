package cl.bgmp.butils.gui;

import org.bukkit.entity.Player;

/** Represents an object able to be clicked by a {@link Player} */
@FunctionalInterface
public interface Clickable {

  /**
   * Method to be called when the object is clicked
   *
   * @param clicker The player who clicked
   */
  void clickBy(Player clicker);
}
