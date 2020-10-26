package cl.bgmp.butils.reflection;

import java.lang.reflect.Field;

public interface ReflectX {

  static Object getValue(Object instance, String name) {
    Object result = null;

    try {
      Field field = instance.getClass().getDeclaredField(name);
      field.setAccessible(true);
      result = field.get(instance);
      field.setAccessible(false);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }

    return result;
  }
}
