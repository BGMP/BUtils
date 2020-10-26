package cl.bgmp.butils.time;

import java.time.Duration;

/** Java {@link Duration}s, simplified. */
public interface SimpleDuration {

  /**
   * Converts a humanised duration string, such as {@code 1m, 2h, 3d, etc.} into a {@link Duration}.
   *
   * @param s The humanised duration string.
   * @return The passed string parsed to a {@link Duration}.
   * @throws java.time.format.DateTimeParseException if the text cannot be parsed to a Duration.
   */
  static Duration fromString(String s) {
    return Duration.parse("PT" + s);
  }
}
