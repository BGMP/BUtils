package cl.bgmp.butils.gui.amount;

import cl.bgmp.butils.gui.GUI;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

/**
 * A {@link GUI} designed specifically to hold {@link AmountGUIButton}s, where only one of the
 * buttons is to be selected at once.
 *
 * @param <T> The {@link AmountGUIButton} type.
 */
public abstract class AmountSelectionGUI<T extends AmountGUIButton<?>> extends GUI {
  protected List<AmountGUIButton<T>> buttons = new ArrayList<>();

  public AmountSelectionGUI(Plugin plugin, String title, int size) {
    super(plugin, title, size);
  }

  @Override
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (!this.wasClicked(event)) return;
    event.setCancelled(true);

    int clickedSlot = event.getSlot();
    if (buttons.stream().noneMatch(b -> b.getSlot() == clickedSlot)) return;

    final Player player = (Player) event.getWhoClicked();
    for (AmountGUIButton<T> button : buttons) {
      if (clickedSlot == button.getSlot()) {
        button.clickBy(player);
        button.setSelected(true);
        continue;
      }

      button.setSelected(false);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  @Nonnull
  public abstract List<AmountGUIButton<T>> setUpButtons();
}
