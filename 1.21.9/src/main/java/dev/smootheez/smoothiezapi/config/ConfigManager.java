package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.*;
import dev.smootheez.smoothiezapi.api.*;
import dev.smootheez.smoothiezapi.modmenu.*;
import dev.smootheez.smoothiezapi.util.*;

import java.util.*;

/**
 * The {@code ConfigManager} class is the central registry for all configuration
 * instances discovered and managed by the Smoothiez API configuration system.
 * <p>
 * It handles the lifecycle of {@link ConfigApi} implementations — including registration,
 * automatic loading and saving, and optional ModMenu integration for client-side GUI editing.
 *
 * <h2>Automatic Registration via Entrypoints</h2>
 * Configs are not manually registered by mod developers. Instead, they are discovered
 * through Fabric’s entrypoint mechanism:
 * <pre>{@code
 * "entrypoints": {
 *   "smoothiezapi": [
 *     "your.mod.package.ExampleConfig"
 *   ]
 * }
 * }</pre>
 * <p>
 * When the client initializes, {@link SmoothiezApi} automatically locates all
 * entrypoints of type {@code "smoothiezapi"} that implement {@link ConfigApi},
 * instantiates them, and registers them through this manager.
 *
 * <h2>Example Config Setup</h2>
 * <pre>{@code
 * @Config(name = "example_config", autoGui = true)
 * public class ExampleConfig implements ConfigApi {
 *
 *     private final ConfigOption<Boolean> enableParticles =
 *             new ConfigOption<>("enableParticles", true);
 *
 *     public ConfigOption<Boolean> enableParticles() {
 *         return enableParticles;
 *     }
 * }
 * }</pre>
 * <p>
 * Once declared in {@code fabric.mod.json} under {@code "smoothiezapi"},
 * the {@link SmoothiezApi} will automatically call:
 * <pre>{@code
 * ConfigManager.register(new ExampleConfig());
 * }</pre>
 *
 * <h3>Lifecycle Overview</h3>
 * <ul>
 *     <li>Called during {@link SmoothiezApi#onInitialize()}.</li>
 *     <li>Loads existing config data from disk (via {@link ConfigApi#loadConfig()}).</li>
 *     <li>Saves to ensure configuration file consistency (via {@link ConfigApi#saveConfig()}).</li>
 *     <li>If {@link Config#autoGui()} is enabled and ModMenu is present, a config screen is registered.</li>
 * </ul>
 *
 * <h3>Integration Notes</h3>
 * <ul>
 *     <li>Works with both client and dedicated server environments.</li>
 *     <li>Client-specific GUI registration only occurs when {@link ModChecker#isModInstalled(String)} detects ModMenu.</li>
 *     <li>Config files are managed by {@link ConfigWriter} and stored under {@code .minecraft/config/}.</li>
 * </ul>
 *
 * @author Smootheez
 * @see Config
 * @see ConfigApi
 * @see SmoothiezApi
 * @see ConfigWriter
 * @see ModChecker
 * @see ModMenuApiImpl
 */
public final class ConfigManager {

    /**
     * Internal cache that maps configuration classes to their registered instances.
     * <p>
     * This ensures that only one instance of each configuration exists globally.
     */
    private static final Map<Class<? extends ConfigApi>, ConfigApi> configs = new HashMap<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigManager() {
    }

    /**
     * Registers a configuration instance and initializes its lifecycle.
     * <p>
     * This method is typically invoked automatically by {@link SmoothiezApi}
     * during client initialization, when it processes all Fabric entrypoints
     * of type {@code "smoothiezapi"}.
     * <p>
     * On registration:
     * <ul>
     *     <li>The config instance is validated and added to the registry if not already present.</li>
     *     <li>Existing configuration data is loaded from disk (if available).</li>
     *     <li>The configuration file is saved to ensure it is up-to-date.</li>
     *     <li>If ModMenu is installed and {@link Config#autoGui()} is enabled, a GUI screen is registered.</li>
     * </ul>
     *
     * @param configInstance the configuration instance to register
     * @param <T>            the type of configuration implementing {@link ConfigApi}
     * @throws NullPointerException if {@code configInstance} is {@code null}
     */
    public static <T extends ConfigApi> void register(T configInstance) {
        Objects.requireNonNull(configInstance, "Config instance cannot be null");

        Class<? extends ConfigApi> configClass = configInstance.getClass();

        // Only add if not already registered
        configs.putIfAbsent(configClass, configInstance);

        // Load existing configuration data
        configInstance.loadConfig();

        // Save current state to disk
        configInstance.saveConfig();

        // If ModMenu is available, register config screen
        Config config = configInstance.getAnnotation(configClass);
        if (ModChecker.isModInstalled(config.name())
                && ModChecker.isModInstalled("modmenu")
                && config.autoGui()) {
            ModMenuApiImpl.registerConfigScreen(config.name(), configInstance::getScreen);
        }

        Constants.LOGGER.info("Registered config: {}", configClass.getSimpleName());
    }

    /**
     * Retrieves a registered configuration instance by its class type.
     * <p>
     * This method allows access to config instances globally, for example:
     * <pre>{@code
     * ExampleConfig config = ConfigManager.getConfig(ExampleConfig.class);
     * boolean enabled = config.enableParticles().getValue();
     * }</pre>
     *
     * @param configClass the configuration class to look up
     * @param <T>         the type of the configuration implementing {@link ConfigApi}
     * @return the registered configuration instance, or {@code null} if not found
     */
    @SuppressWarnings("unchecked")
    public static <T extends ConfigApi> T getConfig(Class<T> configClass) {
        return (T) configs.get(configClass);
    }

    /**
     * Checks whether a configuration class has been registered.
     * <p>
     * Useful for debugging or verifying initialization order.
     *
     * @param configClass the configuration class to check
     * @return {@code true} if the config is registered, {@code false} otherwise
     */
    public static boolean isRegistered(Class<? extends ConfigApi> configClass) {
        return configs.containsKey(configClass);
    }

    /**
     * Clears all registered configuration instances.
     * <p>
     * This method is mainly intended for hot-reload environments or testing.
     * It should not be used during normal gameplay, as it removes all config references.
     */
    public static void clearConfigs() {
        configs.clear();
    }
}


