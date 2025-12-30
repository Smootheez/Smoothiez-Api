package io.github.smootheez.smoothiezapi.gui.helper;

public class ConfigTranslationHelper {
    private ConfigTranslationHelper() {}

    public static String getTranslationKey(String configId, String optionKey) {
        return "config.widget." + configId + "." + optionKey;
    }

    public static String getDescriptionTranslationKey(String configId, String optionKey) {
        return getTranslationKey(configId, optionKey) + ".description";
    }

}
