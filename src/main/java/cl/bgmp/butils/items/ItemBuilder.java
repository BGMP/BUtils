package cl.bgmp.butils.items;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Build {@link ItemStack}s with ease. All methods requiring strings such as {@link
 * this#setName(String)} or {@link this#setLore(String...)} accept legacy contents, taking the "&"
 * char for colouring.
 *
 * <p>TODO: Reduce boilerplate regarding ItemMeta
 */
public class ItemBuilder {
  protected ItemStack itemStack;

  public ItemBuilder(Material material) {
    this.itemStack = new ItemStack(material);
  }

  public ItemBuilder setAmount(int amount) {
    this.itemStack.setAmount(amount);
    return this;
  }

  public ItemBuilder setName(String name) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    this.itemStack.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setLore(String... lines) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    List<String> coloredLines =
        Arrays.stream(lines)
            .map(line -> ChatColor.translateAlternateColorCodes('&', line))
            .collect(Collectors.toList());
    itemMeta.setLore(coloredLines);
    this.itemStack.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setDamage(short damage) {
    this.itemStack.setDurability(damage);
    return this;
  }

  public ItemBuilder enchant(Enchantment enchantment, int level, boolean ignoreLevelRestrictions) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.addEnchant(enchantment, level, ignoreLevelRestrictions);
    this.itemStack.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder addFlags(ItemFlag... itemFlags) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.addItemFlags(itemFlags);
    this.itemStack.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder op() {
    ItemMeta itemMeta = itemStack.getItemMeta();

    for (Enchantment enchantment : Enchantment.values()) {
      itemMeta.addEnchant(enchantment, Integer.MAX_VALUE, true);
    }

    this.itemStack.setItemMeta(itemMeta);
    return this;
  }

  public ItemStack build() {
    return this.itemStack;
  }
}
