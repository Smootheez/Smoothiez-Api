package io.github.smootheez.smoothiezapi.util;

import io.github.smootheez.smoothiezapi.config.*;

public class DebugMode {
    private DebugMode() {}

    public static void sendLogInfo(String message) {
        if (ConfigManager.getConfig(SmoothiezApiConfig.class).getDebugMode().getValue())
            Constants.LOGGER.info(message);
    }
}
