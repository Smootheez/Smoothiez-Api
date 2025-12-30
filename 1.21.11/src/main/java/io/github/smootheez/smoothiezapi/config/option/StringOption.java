package io.github.smootheez.smoothiezapi.config.option;

import io.github.smootheez.smoothiezapi.config.*;
import io.github.smootheez.smoothiezapi.config.serializer.*;
import io.github.smootheez.smoothiezapi.gui.widget.entries.handler.*;
import org.jetbrains.annotations.*;

public class StringOption extends ConfigOption<String> {
    public StringOption(String key, String defaultValue) {
        super(key, defaultValue, null, null);
    }

    @Override
    public @NotNull Class<String> getType() {
        return String.class;
    }

    @Override
    public @NotNull WidgetHandler<String> getWidgetHandler() {
        return new WidgetHandler.StringHandler();
    }

    @Override
    public @NotNull ConfigOptionSerializer<String> getSerializer() {
        return new StringSerializer();
    }
}
