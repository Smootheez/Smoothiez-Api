package dev.smootheez.smoothiezapi.api;

import com.terraformersmc.modmenu.api.*;
import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.util.*;

import java.lang.reflect.*;
import java.util.*;

public interface ConfigApi {
    default String getConfigId() {
        Class<? extends ConfigApi> configClass = this.getClass();
        Config config = configClass.getAnnotation(Config.class);
        if (config == null)
            throw new IllegalStateException("Config class " + configClass.getName() + " is missing @Config annotation");
        return config.name();
    }

    default ConfigWriter getWriter() {
        List<ConfigOption<?>> options = new ArrayList<>();
        Class<? extends ConfigApi> configClass = this.getClass();
        Arrays.stream(configClass.getMethods())
                .filter(method -> ConfigOption.class.isAssignableFrom(method.getReturnType()))
                .filter(method -> method.getParameterCount() == 0)
                .forEach(method -> invokeConfigOption(method, options));
        ConfigWriter writer = new ConfigWriter(getConfigId());
        options.forEach(option -> writer.addOption(option.getKey(), option));
        if (configClass.getAnnotation(Config.class).autoGui())
            ModMenuUtil.registerConfigScreenFactory(getConfigId(), options);
        return writer;
    }

    private void invokeConfigOption(Method method, List<ConfigOption<?>> options) {
        try {
            Object value = method.invoke(this);
            if (value instanceof ConfigOption) options.add((ConfigOption<?>) value);
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
     * The default implementation uses the {@link ConfigWriter} to load the config.
     */
    default void loadConfig() {
        getWriter().loadConfig();
    }
}
