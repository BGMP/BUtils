package cl.bgmp.butils;

import static org.junit.Assert.*;

import cl.bgmp.butils.files.PropertiesUtils;
import java.util.Properties;
import org.junit.Test;

public class PropertiesUtilsTests {

  @Test
  public void testNewProperties() {
    Properties properties =
        PropertiesUtils.newProperties(
            getClass().getClassLoader().getResourceAsStream("i18n/template/strings.properties"));
    assertNotNull(properties);
  }

  @Test
  public void testNewPropertiesResource() {
    Properties templates =
        PropertiesUtils.newPropertiesResource(getClass(), "i18n/template/strings.properties");
    assertNotNull(templates);
  }

  @Test
  public void testPropertiesFromResources() {
    Properties properties = PropertiesUtils.fromResources(this, "i18n/template/strings.properties");
    assertNotNull(properties.get("test.hello"));
  }
}
