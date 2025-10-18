package dev.smootheez.smoothiezapi.keymapping;

import com.google.common.collect.*;
import it.unimi.dsi.fastutil.objects.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;

import java.util.*;

/**
 * Internal registry implementation for managing custom keybindings
 * registered via the Smoothiez API.
 * <p>
 * This class maintains an internal list of all registered {@link KeyMapping} instances,
 * provides duplicate detection, and merges them into Minecraft’s existing
 * keybinding list during initialization.
 * <p>
 * It acts as the backend for {@link KeyMappingHelper}, ensuring
 * safe registration and consistent behavior with the vanilla keybinding system.
 *
 * <h2>Overview</h2>
 * <ul>
 *     <li>Prevents duplicate registration of key mappings.</li>
 *     <li>Allows merging of new key mappings into the global list via {@link #process(KeyMapping[])}.</li>
 *     <li>Designed for use within {@code ClientModInitializer.onInitializeClient()}.</li>
 * </ul>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * KeyMapping customKey = new KeyMapping("key.examplemod.activate",
 *     InputConstants.KEY_G,
 *     CATEGORY);
 * KeyMappingRegistryImpl.register(customKey);
 * }</pre>
 *
 * <h3>Integration with Minecraft</h3>
 * The {@link #process(KeyMapping[])} method is typically invoked
 * during the key mapping reload cycle to inject custom mappings
 * into the vanilla registry.
 *
 * @see KeyMapping
 * @see KeyMappingHelper
 */
@Environment(EnvType.CLIENT)
public final class KeyMappingRegistryImpl {

    /**
     * Holds all custom {@link KeyMapping} instances registered by mods.
     * <p>
     * Uses a {@link ReferenceArrayList} for efficient dynamic resizing and iteration.
     */
    private static final List<KeyMapping> CUSTOM_KEY_MAPPINGS = new ReferenceArrayList<>();

    /**
     * Private constructor to prevent instantiation.
     * <p>
     * This class functions purely as a static registry.
     */
    private KeyMappingRegistryImpl() {}

    /**
     * Registers a new {@link KeyMapping}, ensuring that no duplicate name or instance exists.
     *
     * @param keyMapping the key mapping to register
     * @return the same {@link KeyMapping} instance for chaining or assignment
     * @throws IllegalArgumentException if a duplicate name or instance is already registered
     */
    public static KeyMapping register(KeyMapping keyMapping) {
        for (KeyMapping existingKeyMapping : CUSTOM_KEY_MAPPINGS) {
            if (existingKeyMapping == keyMapping) {
                throw new IllegalArgumentException("Key mapping is already registered");
            } else if (existingKeyMapping.getName().equals(keyMapping.getName())) {
                throw new IllegalArgumentException(
                        "Key mapping with name '" + keyMapping.getName() + "' is already registered!"
                );
            }
        }

        CUSTOM_KEY_MAPPINGS.add(keyMapping);
        return keyMapping;
    }

    /**
     * Merges all custom key mappings with Minecraft’s existing key mappings.
     * <p>
     * This is typically called during the keybinding reload or initialization phase.
     *
     * @param keyMappings the current vanilla key mapping array
     * @return a new array containing both the vanilla and custom key mappings
     */
    public static KeyMapping[] process(KeyMapping[] keyMappings) {
        List<KeyMapping> allNewKeys = Lists.newArrayList(keyMappings);
        allNewKeys.removeAll(CUSTOM_KEY_MAPPINGS);
        allNewKeys.addAll(CUSTOM_KEY_MAPPINGS);
        return allNewKeys.toArray(KeyMapping[]::new);
    }
}

