package dev.smootheez.smoothiezapi.keymapping;

import net.fabricmc.api.*;
import net.minecraft.client.*;

import java.util.*;

/**
 * Utility interface for registering custom {@link KeyMapping} instances
 * into Minecraft’s keybinding system under Fabric.
 * <p>
 * This helper provides a simple one-line registration method for client mods
 * that define their own keybindings.
 * <p>
 * Internally delegates to {@link KeyMappingRegistryImpl} to ensure
 * proper management and conflict safety.
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * public static final KeyMapping TOGGLE_UI = KeyMappingHelper.register(
 *     new KeyMapping("key.examplemod.toggle_ui",
 *         InputConstants.KEY_U,
 *         CATEGORY)
 * );
 * }</pre>
 *
 * <h3>Behavior</h3>
 * <ul>
 *     <li>Throws an exception if the provided {@link KeyMapping} is {@code null}.</li>
 *     <li>Ensures duplicate registrations are prevented by the registry layer.</li>
 *     <li>Client-only; should be registered within a {@code ClientModInitializer} context.</li>
 * </ul>
 *
 * @see KeyMapping
 * @see KeyMappingRegistryImpl
 */
@Environment(EnvType.CLIENT)
public interface KeyMappingHelper {

    /**
     * Registers a {@link KeyMapping} with the Smoothiez API keybinding registry.
     *
     * @param keyMapping the {@link KeyMapping} instance to register
     * @return the registered {@link KeyMapping} instance
     * @throws NullPointerException if {@code keyMapping} is {@code null}
     * @throws IllegalArgumentException if a key with the same name or instance
     *                                  has already been registered
     */
    static KeyMapping register(KeyMapping keyMapping) {
        Objects.requireNonNull(keyMapping, "Key mapping cannot be null!");
        return KeyMappingRegistryImpl.register(keyMapping);
    }
}

