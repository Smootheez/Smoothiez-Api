package dev.smootheez.smoothiezapi.config.option;

import dev.smootheez.smoothiezapi.config.*;
import dev.smootheez.smoothiezapi.config.serializer.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.handler.*;
import org.jetbrains.annotations.*;

public class IntegerOption extends ConfigOption<Integer> {
    public IntegerOption(String key, Integer defaultValue, Integer minValue, Integer maxValue) {
        super(key, defaultValue, minValue, maxValue);
        if (minValue > maxValue || defaultValue < minValue || defaultValue > maxValue) trhowIllegalArgumentException();
    }

    @Override
    public @NotNull Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public @NotNull WidgetHandler<Integer> getWidgetHandler() {
        return new WidgetHandler.TextIntegerHandler();
    }

    @Override
    public @NotNull ConfigOptionSerializer<Integer> getSerializer() {
        return new IntegerSerializer();
    }
}
