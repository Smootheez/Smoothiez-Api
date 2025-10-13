package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.util.*;

import java.util.*;

public final class ConfigManager {
    // The map explicitly links config class -> config instance of the same type
    private static final Map<Class<? extends ConfigApi>, ConfigApi> configs = new HashMap<>();

    private ConfigManager() {}

    /**
     * Register a new configuration instance.
     * Automatically loads existing data from disk and saves if necessary.
     */
    public static <T extends ConfigApi> void register(T configInstance) {
        Objects.requireNonNull(configInstance, "Config instance cannot be null");

        Class<? extends ConfigApi> configClass = configInstance.getClass();

        // Only add if not already registered
        configs.putIfAbsent(configClass, configInstance);

        // Load first (so existing values are preserved)
        configInstance.loadConfig();

        // Save to ensure file exists and is up-to-date
        configInstance.saveConfig();

        Constants.LOGGER.info("Registered config: {}", configClass.getSimpleName());
    }

    /**
     * Retrieve a registered configuration instance by its class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends ConfigApi> T getConfig(Class<T> configClass) {
        return (T) configs.get(configClass);
    }

    /**
     * Check whether a configuration class is registered.
     */
    public static boolean isRegistered(Class<? extends ConfigApi> configClass) {
        return configs.containsKey(configClass);
    }

    /**
     * Unregister all configurations (useful for reloads or testing).
     */
    public static void clearConfigs() {
        configs.clear();
    }
}

