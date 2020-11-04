package cl.bgmp.butils.items;

import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeadBuilder extends ItemBuilder {

  public PlayerHeadBuilder(String nick) {
    super(Material.PLAYER_HEAD);
    ((SkullMeta) this.itemMeta).setOwner(nick);
  }
}
