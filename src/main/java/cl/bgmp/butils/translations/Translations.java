package cl.bgmp.butils.translations;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Represents a strings translator, which can be fed with a template .properties file containing all
 * the base strings, and multiple other .properties files for each locale.
 *
 * <p>This class relies on careful handling at the moment of naming localisation files, as the
 * locale codes must remain consistent throughout the project.
 *
 * <p>{@see resources/i18n}
 */
public abstract class Translations {
  private static final String TEMPLATE_LOCALE = "en_us";

  private Properties templateFile;
  private Map<String, Properties> translationFilesMap;
  private Map<String, Map<String, String>> translationsMap = new HashMap<>();

  public Translations(Properties templateFile, Map<String, Properties> translationFilesMap) {
    this.templateFile = templateFile;
    this.translationFilesMap = translationFilesMap;

    this.loadTranslations();
  }

  private void loadTranslations() {
    for (String locale : this.translationFilesMap.keySet()) {
      final Properties localeFile = this.translationFilesMap.get(locale);
      this.loadLocale(locale, localeFile);
    }

    this.loadLocale(TEMPLATE_LOCALE, templateFile);
  }

  private void loadLocale(String locale, Properties translationsFile) {
    final Enumeration<?> keys = translationsFile.propertyNames();
    final Map<String, String> translations = new HashMap<>();

    while (keys.hasMoreElements()) {
      String key = keys.nextElement().toString();
      translations.put(key, translationsFile.getProperty(key));
    }

    this.translationsMap.put(locale, translations);
  }

  /**
   * Gets the translation for a string key, or the base string related to the key if any problem
   * arises whilst retrieving the translation.
   *
   * @param key The key of the string
   * @param locale The locale code of the language to translate to
   * @param args All of the arguments to be replaced within the string
   * @return The translation, or {@code null} if not found.
   */
  public String get(String key, String locale, Object... args) {
    String translated = null;
    final Map<String, String> translations = this.translationsMap.get(locale);

    if (translations != null) {
      final String translation = translations.get(key);
      if (translation != null) {
        translated = translation;
      }
    }

    if (translated == null) {
      final Map<String, String> templates = this.translationsMap.get(TEMPLATE_LOCALE);
      translated = templates.get(key);
    }

    if (args != null && translated != null) {
      return this.replaceArgs(translated, locale, args);
    }

    return translated;
  }

  public String get(Translatable translatable, String locale) {
    return this.get(translatable.getKey(), locale, translatable.getArgs());
  }

  public String get(String key, Object sender, Object... args) {
    return this.get(key, getLocale(sender), args);
  }

  public String get(Translatable translatable, Object sender) {
    return this.get(translatable, getLocale(sender));
  }

  /**
   * Replaces the string value of all the passed {@link Object}s into the translated string.
   *
   * <p>These variables are to be represented in the {0} format. i.e: test.key = Stats: {0} kills
   * {1} deaths and {2} assists.
   *
   * <p>All variables are then to be replaced by the string value of any {@link Object} arguments
   * passed.
   *
   * @param translated The translated string.
   * @param locale The locale instance.
   * @param args The object arguments.
   * @return The final translated string, with all of its arguments in place.
   */
  private String replaceArgs(String translated, String locale, Object... args) {
    String replacement;
    for (int i = 0; i < args.length; i++) {
      if (args[i] instanceof Translatable) {
        replacement = this.get((Translatable) args[i], locale);
      } else {
        replacement = String.valueOf(args[i]);
      }

      translated = translated.replace("{" + i + "}", replacement);
    }

    return translated;
  }

  /**
   * Methods to be overridden by children classes, as different projects may have different ways of
   * getting/setting the locale of players.
   */
  public abstract String getLocale(Object o);

  public abstract void setLocale(Object o, String locale);
}
