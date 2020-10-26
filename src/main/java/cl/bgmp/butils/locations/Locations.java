package cl.bgmp.butils.locations;

import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public interface Locations {

  /**
   * Splits a string using the passed regex and returns a {@link Location} based on that
   *
   * @param s The original string
   * @param regex The string used to split the original
   * @return The parsed Location
   */
  static Location fromString(@Nonnull String s, @Nonnull String regex) {
    return fromSplit(s.split(regex));
  }

  /**
   * Same as above, but actually parsing the split
   *
   * @param split The split array
   * @return The parsed Location
   */
  static Location fromSplit(@Nonnull String[] split) {
    String world = split[0];

    try {
      double x = Double.parseDouble(split[1]);
      double y = Double.parseDouble(split[2]);
      double z = Double.parseDouble(split[3]);

      if (split.length > 4) {
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
      } else return new Location(Bukkit.getWorld(world), x, y, z);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    return null;
  }
}
