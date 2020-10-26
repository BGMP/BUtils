package cl.bgmp.butils.items;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeadBuilder extends ItemBuilder {

  public PlayerHeadBuilder() {
    super(Material.LEGACY_SKULL_ITEM);
    this.setDamage((short) SkullType.PLAYER.ordinal());
  }

  public PlayerHeadBuilder setOwner(String nick) {
    SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
    skullMeta.setOwner(nick);
    this.itemStack.setItemMeta(skullMeta);
    return this;
  }

  public PlayerHeadBuilder setOwner(OfflinePlayer player) {
    SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
    skullMeta.setOwningPlayer(player);
    this.itemStack.setItemMeta(skullMeta);
    return this;
  }
}
