package cl.bgmp.butils.sound;

import java.util.Collection;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/** Various methods to make playing sounds an easy task */
public interface Sounds {

  static void play(Sound sound, long volume, long pitch, Player player) {
    player.playSound(player.getLocation(), sound, volume, pitch);
  }

  static void play(Sound sound, long volume, long pitch, Player... players) {
    for (Player player : players) {
      play(sound, volume, pitch, player);
    }
  }

  static void play(Sound sound, Player player) {
    play(sound, 1L, 1L, player);
  }

  static void play(Sound sound, Player... players) {
    for (Player player : players) {
      play(sound, player);
    }
  }

  static void play(Sound sound, Collection<? extends Player> players) {
    for (Player player : players) {
      play(sound, player);
    }
  }

  static void play(Sound sound, long volume, long pitch, Collection<? extends Player> players) {
    for (Player player : players) {
      play(sound, volume, pitch, player);
    }
  }
}
