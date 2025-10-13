package dev.smootheez.smoothiezapi.keymapping;

import net.minecraft.client.*;

import java.util.*;

public interface KeyMappingHelper {
    static KeyMapping register(KeyMapping keyMapping) {
        Objects.requireNonNull(keyMapping, "Key mapping cannot be null!");
        return KeyMappingRegistryImpl.register(keyMapping);
    }
}
