package dev.smootheez.smoothiezapi.keymapping;

import com.google.common.collect.*;
import it.unimi.dsi.fastutil.objects.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public final class KeyMappingRegistryImpl {
    private static final List<KeyMapping> CUSTOM_KEY_MAPPINGS = new ReferenceArrayList<>();

    private KeyMappingRegistryImpl() {}

    public static KeyMapping register(KeyMapping keyMapping) {
        for (KeyMapping existingKeyMapping : CUSTOM_KEY_MAPPINGS) {
            if (existingKeyMapping == keyMapping) {
                throw new IllegalArgumentException("Key mapping is already registered");
            } else if (existingKeyMapping.getName().equals(keyMapping.getName())) {
                throw new IllegalArgumentException("Key mapping with name '" + keyMapping.getName() + "' is already registered!");          }
        }

        CUSTOM_KEY_MAPPINGS.add(keyMapping);
        return keyMapping;
    }

    public static KeyMapping[] process(KeyMapping[] keyMappings) {
        List<KeyMapping> allNewKeys = Lists.newArrayList(keyMappings);
        allNewKeys.removeAll(CUSTOM_KEY_MAPPINGS);
        allNewKeys.addAll(CUSTOM_KEY_MAPPINGS);
        return allNewKeys.toArray(KeyMapping[]::new);
    }
}
