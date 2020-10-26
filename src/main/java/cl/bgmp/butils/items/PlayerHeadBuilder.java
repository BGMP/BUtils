package cl.bgmp.butils.items;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeadBuilder extends ItemBuilder {
  private SkullMeta skullMeta;

  public PlayerHeadBuilder(String nick) {
    super(Material.LEGACY_SKULL_ITEM);
    this.skullMeta = (SkullMeta) this.itemMeta;

    this.setDamage((short) SkullType.PLAYER.ordinal());
    this.setOwner(nick);
  }

  /**
   * This legacy constructor will disappear passed the pre-snapshots. Use {@link
   * this#PlayerHeadBuilder(String)} instead.
   */
  @Deprecated
  public PlayerHeadBuilder() {
    super(Material.LEGACY_SKULL_ITEM);
    this.setDamage((short) SkullType.PLAYER.ordinal());
  }

  public PlayerHeadBuilder setOwner(String nick) {
    this.skullMeta.setOwner(nick);
    return this;
  }

  @Override
  public ItemStack build() {
    this.itemStack.setItemMeta(this.skullMeta);
    return this.itemStack;
  }
}
