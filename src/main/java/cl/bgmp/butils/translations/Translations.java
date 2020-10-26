package cl.bgmp.butils.translations;

import java.util.Map;
import java.util.Properties;

/**
 * Represents a strings translator, which can be fed with a template .properties file containing all
 * the base strings, and multiple other .properties files for each locale.
 *
 * <p>This class relies on careful handling at the moment of naming localisation files, as the local
 * codes must remain consistent throughout the project.
 *
 * <p>{@see resources/i18n}
 */
public abstract class Translations {
  private Properties template;
  private Map<String, Properties> translationFiles;

  public Translations(Properties template, Map<String, Properties> translationFiles) {
    this.template = template;
    this.translationFiles = translationFiles;
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
    final Properties translationsFile = translationFiles.get(locale);
    if (translationsFile != null) {
      final String translation = translationsFile.getProperty(key);
      if (translation != null) {
        translated = translation;
      }
    }

    if (translated == null) {
      translated = template.getProperty(key);
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
