package dev.smootheez.example;

import com.mojang.blaze3d.platform.*;
import dev.smootheez.example.util.*;
import dev.smootheez.smoothiezapi.keymapping.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;

public class ExampleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOGGER.info("Initializing Client " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");

        KeyMappingHelper.register(new KeyMapping("key.examplemod.toggle_ui",
            InputConstants.KEY_U,
            KeyMapCategoryHelper.register(Constants.MOD_ID, "controls")));
    }
}
