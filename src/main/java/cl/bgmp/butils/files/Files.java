package cl.bgmp.butils.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface Files {

  /**
   * Retrieves a file as an {@link InputStream} from the resources folder.
   *
   * @param clazz The class where this method is called at
   * @param path Path to the resource
   * @return The requested file as an InputStream from the resources directory
   */
  static InputStream resourceAsStream(Class<?> clazz, String path) {
    return clazz.getClassLoader().getResourceAsStream(path);
  }

  /**
   * Retrieves a {@link File} from the resources folder.
   *
   * @param clazz The class where this method is called at
   * @param path Path to the resource
   * @throws NullPointerException If no such file exists
   * @return The requested {@link File} from the resources directory
   */
  static File resourceAsFile(Class<?> clazz, String path) {
    final URL resource = clazz.getClassLoader().getResource(path);
    if (resource == null) {
      throw new NullPointerException("No resource could be found at " + path);
    }

    return new File(resource.getFile());
  }

  static File copyInputStreamToFile(InputStream stream, File fileTo) {
    try (FileOutputStream outputStream = new FileOutputStream(fileTo)) {

      int read;
      byte[] bytes = new byte[1024];

      while ((read = stream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return fileTo;
  }
}
