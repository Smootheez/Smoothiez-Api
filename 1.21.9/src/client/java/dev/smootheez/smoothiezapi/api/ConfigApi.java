package dev.smootheez.smoothiezapi.api;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.util.*;

import java.lang.reflect.*;
import java.util.*;

public interface ConfigApi {

    /**
     * Returns the config ID (from @Config annotation).
     */
    default String getConfigId() {
        Class<? extends ConfigApi> configClass = this.getClass();
        Config config = configClass.getAnnotation(Config.class);
        if (config == null)
            throw new IllegalStateException("Config class " + configClass.getName() + " is missing @Config annotation");
        return config.name();
    }

    /**
     * Returns all ConfigOption fields from this config class.
     */
    default List<ConfigOption<?>> getAllConfigOptions() {
        List<ConfigOption<?>> options = new ArrayList<>();
        Class<? extends ConfigApi> configClass = this.getClass();

        Arrays.stream(configClass.getMethods())
                .filter(method -> ConfigOption.class.isAssignableFrom(method.getReturnType()))
                .filter(method -> method.getParameterCount() == 0)
                .forEach(method -> invokeConfigOption(method, options));

        return options;
    }

    /**
     * Creates a ConfigWriter instance for this config.
     */
    default ConfigWriter getWriter() {
        ConfigWriter writer = new ConfigWriter(getConfigId());
        getAllConfigOptions().forEach(option -> writer.addOption(option.getKey(), option));
        return writer;
    }

    /**
     * Internal helper for reflection-based extraction.
     */
    private void invokeConfigOption(Method method, List<ConfigOption<?>> options) {
        try {
            Object value = method.invoke(this);
            if (value instanceof ConfigOption<?> option) options.add(option);
        } catch (ReflectiveOperationException e) {
            Constants.LOGGER.error("Failed to get config option from method {}: {}", method.getName(), e.getMessage());
        }
    }

    /**
     * Saves the configuration to the file system.
     */
    default void saveConfig() {
        getWriter().saveConfig();
    }

    /**
     * Loads the configuration from the file system.
     */
    default void loadConfig() {
        getWriter().loadConfig();
    }
}

