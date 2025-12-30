package io.github.smootheez.smoothiezapi.config;

import io.github.smootheez.smoothiezapi.api.*;
import io.github.smootheez.smoothiezapi.config.option.*;
import io.github.smootheez.smoothiezapi.util.*;

@Config(name = Constants.MOD_ID, autoGui = true)
public class SmoothiezApiConfig implements ConfigApi {
    private final BooleanOption debugMode = new BooleanOption("debug_mode", false);

    public BooleanOption getDebugMode() {
        return debugMode;
    }
}
