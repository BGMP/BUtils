package cl.bgmp.butils.translations;

/** Represents a translatable key, along with any arguments it may require */
public class Translatable {
  private String key;
  private Object[] args;

  public Translatable(String key, Object... args) {
    this.key = key;
    this.args = args;
  }

  public String getKey() {
    return key;
  }

  public void setKey() {
    this.key = key;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object... args) {
    this.args = args;
  }
}
