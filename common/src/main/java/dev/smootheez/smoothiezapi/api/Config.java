package dev.smootheez.smoothiezapi.api;

import java.lang.annotation.*;

/**
 * The {@code @Config} annotation marks a class as a Fabric mod configuration container.
 * <p>
 * When used with {@link ConfigApi}, this annotation defines metadata such as the configuration
 * file name and whether a GUI should be automatically generated for it.
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * @Config(name = "example_mod", autoGui = true)
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
 *
 * <h3>Behavior</h3>
 * <ul>
 *     <li>The {@link #name()} defines the base file name or ID for the configuration file.</li>
 *     <li>The {@link #autoGui()} flag indicates whether a config screen should be auto-generated,
 *     typically used for ModMenu integration.</li>
 * </ul>
 *
 * <h3>Integration Notes:</h3>
 * <ul>
 *     <li>Required for all classes implementing {@link ConfigApi}.</li>
 *     <li>Detected at runtime via reflection by {@link ConfigApi#getAnnotation(Class)}.</li>
 *     <li>Supports automatic save/load operations when paired with {@link ConfigWriter}.</li>
 * </ul>
 *
 * @see ConfigApi
 * @see ConfigWriter
 * @see ConfigOption
 * @see ConfigScreen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {

    /**
     * The unique name of this configuration.
     * <p>
     * This typically corresponds to the mod ID or subconfig name.
     * The resulting file will usually be stored at:
     * <pre>
     * .minecraft/config/{name}.json
     * </pre>
     *
     * @return the unique configuration identifier
     */
    String name();

    /**
     * Determines whether an in-game configuration GUI should be automatically created.
     * <p>
     * If set to {@code true}, the mod's config will appear in ModMenu or any other
     * integrated Fabric GUI system that supports {@link ConfigApi#getScreen(Screen)}.
     *
     * @return {@code true} to enable automatic GUI generation
     */
    boolean autoGui() default false;
}
