package cl.bgmp.butils.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.annotation.Nonnull;

public interface PropertiesUtils {

  /**
   * Retrieves a regular {@link Properties} file, the root of the provided path being the resources
   * folder.
   *
   * <p>The retrieved {@link Properties} file comes in the {@link StandardCharsets#UTF_8} encoding,
   * but you can directly request it in a determined charset using the variant to this method {@link
   * this#fromResources(Object, String, Charset)}
   *
   * @param thiz The class you're requesting this file from.
   * @param path The path to the properties file, the root directory being the resources folder.
   * @return The {@link Properties} loaded from resources, in its standard encoding, or an unloaded
   *     {@link Properties} file if not found.
   */
  @Nonnull
  static <C> Properties fromResources(C thiz, String path) {
    return fromResources(thiz, path, StandardCharsets.UTF_8);
  }

  @Nonnull
  static <C> Properties fromResources(C thiz, String path, Charset charset) {
    final Properties properties = new Properties();

    final InputStream propertiesStream = thiz.getClass().getClassLoader().getResourceAsStream(path);
    if (propertiesStream == null) return properties;

    try {
      properties.load(new InputStreamReader(propertiesStream, charset));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return properties;
  }

  /**
   * Creates a new {@link Properties} by retrieving a file from resources and loading to it.
   *
   * @param clazz The class where this method is called at
   * @param path Path to the resource
   * @param charset The encoding
   * @return The final Properties file, loaded from the resources directory
   */
  @Deprecated
  static Properties newPropertiesResource(Class<?> clazz, String path, Charset charset) {
    return newProperties(getResource(clazz, path), charset);
  }

  @Deprecated
  static Properties newPropertiesResource(Class<?> clazz, String path) {
    return newPropertiesResource(clazz, path, StandardCharsets.UTF_8);
  }

  /**
   * Creates a new {@link Properties} file and loads input to it
   *
   * @param input The input to be loaded to the properties file
   * @param charset The charset of the input
   * @return The new properties file, loaded from the passed input
   */
  @Deprecated
  static Properties newProperties(InputStream input, Charset charset) {
    final Properties properties = new Properties();

    try {
      properties.load(new InputStreamReader(input, charset));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return properties;
  }

  @Deprecated
  static Properties newProperties(InputStream input) {
    return newProperties(input, StandardCharsets.UTF_8);
  }

  /**
   * Use {@link this#fromResources(Object, String)}
   *
   * <p>Retrieves a file as an {@link InputStream} from the resources folder
   *
   * @param clazz The class where this method is called at
   * @param path Path to the resource
   * @return The requested file as an InputStream from the resources directory
   */
  @Deprecated
  static InputStream getResource(Class<?> clazz, String path) {
    return clazz.getClassLoader().getResourceAsStream(path);
  }
}
