package dev.smootheez.smoothiezapi;

import com.mojang.blaze3d.platform.*;
import dev.smootheez.smoothiezapi.keymapping.*;
import dev.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;

@Environment(EnvType.CLIENT)
public class SmoothiezApiClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOGGER.info("Initializing client " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");

        KeyMappingHelper.register(new KeyMapping("key.smoothiez.custom", InputConstants.UNKNOWN.getValue(), KeyMapCategoryHelper.register(Constants.MOD_ID, "custom")));
    }
}
