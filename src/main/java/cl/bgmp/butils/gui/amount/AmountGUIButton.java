package cl.bgmp.butils.gui.amount;

import cl.bgmp.butils.gui.GUIButton;
import cl.bgmp.butils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a GUI button used to select an amount of type {@link T}.
 *
 * <p>The amount type can be anything, such as {@link Integer}s, {@link Double}s, or even a {@link
 * java.time.Duration}.
 *
 * @param <T> The type of the quantitative object the button will hold relation to.
 */
public abstract class AmountGUIButton<T> extends GUIButton {
  protected T amount;
  protected ItemStack itemWhenSelected;
  protected boolean selected;

  public AmountGUIButton(ItemStack item, int slot, T amount, boolean selected) {
    super(item, slot);
    this.amount = amount;
    this.selected = selected;
    this.itemWhenSelected =
        new ItemBuilder(Material.LIME_WOOL)
            .setName("&a" + this.getVerboseValue())
            .setLore("&a&lSELECCIONADO")
            .build();
  }

  public AmountGUIButton(ItemStack item, int slot, T amount) {
    this(item, slot, amount, false);
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean isSelected() {
    return selected;
  }

  /**
   * Depending on the kind of button created, a different string representing the value of it may be
   * required, such as "1 minute", "3 something", etc.
   *
   * @return The verbose value, typically in the form a sentence.
   */
  public abstract String getVerboseValue();

  @Override
  public ItemStack getItemStack() {
    return this.isSelected() ? this.itemWhenSelected : this.itemStack;
  }

  @Override
  public abstract void clickBy(Player player);
}
