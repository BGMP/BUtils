package cl.bgmp.butils.time;

import java.time.Duration;

public interface SimpleDuration {

  static Duration fromString(String s) {
    return Duration.parse("PT" + s);
  }
}
