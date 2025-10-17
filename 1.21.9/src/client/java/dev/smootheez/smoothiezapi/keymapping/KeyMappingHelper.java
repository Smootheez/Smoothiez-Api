package dev.smootheez.smoothiezapi.keymapping;

import net.fabricmc.api.*;
import net.minecraft.client.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public interface KeyMappingHelper {
    static KeyMapping register(KeyMapping keyMapping) {
        Objects.requireNonNull(keyMapping, "Key mapping cannot be null!");
        return KeyMappingRegistryImpl.register(keyMapping);
    }
}
