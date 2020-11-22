package cl.bgmp.butils;

import static org.junit.Assert.*;

import cl.bgmp.butils.files.JsonUtils;
import com.google.gson.JsonObject;
import org.junit.Test;

public class JsonUtilsTests {

  @Test
  public void testJsonFromResources() {
    JsonObject json = JsonUtils.fromResources(this.getClass(), "json/test.json");
    assertNotNull(json);

    String testValue = json.get("test").getAsString();
    assertEquals("Hello, I'm a test", testValue);
  }
}
