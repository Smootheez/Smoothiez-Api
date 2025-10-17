package dev.smootheez.smoothiezapi;

import dev.smootheez.smoothiezapi.util.*;
import net.fabricmc.api.*;

public class SmoothiezApi implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.info("Initializing " + Constants.MOD_NAME + "(" + Constants.MOD_ID + ")...");
    }
}
