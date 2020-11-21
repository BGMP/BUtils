package cl.bgmp.butils;

import static org.junit.Assert.*;

import cl.bgmp.butils.files.PropertiesUtils;
import cl.bgmp.butils.translations.Translatable;
import cl.bgmp.butils.translations.Translations;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Properties;
import org.junit.Test;

/** All tests related to translations. */
public class TranslationsTests {
  private final Properties template =
      PropertiesUtils.fromResources(this, "i18n/template/strings.properties");
  private final Map<String, Properties> translationFiles =
      ImmutableMap.<String, Properties>builder()
          .put("es_cl", PropertiesUtils.fromResources(this, "i18n/translations/es_cl.properties"))
          .build();

  private final Translations translations =
      new Translations(template, translationFiles) {
        @Override
        public String getLocale(Object o) {
          return null;
        }

        @Override
        public void setLocale(Object o, String locale) {}
      };

  @Test
  public void testRegularTranslations() {
    String translation = translations.get("test.hello", "es_cl");
    assertEquals("Hola", translation);
  }

  @Test
  public void testTranslationArguments() {
    String translation = translations.get("test.arguments", "es_cl", 2);
    assertEquals("Hay 2 manzanas", translation);
  }

  @Test
  public void testNestedTranslations() {
    String translation =
        translations.get(
            "test.nested.translations",
            "es_cl",
            2,
            new Translatable("test.nested.translations.liters"));
    assertEquals("El volumen es 2 litros", translation);
  }

  @Test
  public void testMissingTranslations() {
    String translation = translations.get("test.missing.translation", "es_cl");
    assertEquals("I am missing in es_cl.properties", translation);
  }

  @Test
  public void testInvalidKey() {
    String translation = translations.get("efknreiwhfbedo.dbfi", "es_cl");
    assertNull(translation);
  }

  @Test
  public void testUTF8() {
    String translation = translations.get("test.utf8.translation", "es_cl");
    assertEquals("canción", translation);
  }
}
