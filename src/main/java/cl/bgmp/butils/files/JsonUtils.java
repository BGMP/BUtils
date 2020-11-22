package cl.bgmp.butils.files;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.annotation.Nullable;

public interface JsonUtils {

  @Nullable
  static JsonObject fromResources(Class<?> clazz, String path) {
    JsonParser jsonParser = new JsonParser();
    File jsonFile = Files.resourceAsFile(clazz, path);
    JsonObject json = null;

    try {
      Object parsed = jsonParser.parse(new FileReader(jsonFile.getPath()));
      json = (JsonObject) parsed;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return json;
  }
}
