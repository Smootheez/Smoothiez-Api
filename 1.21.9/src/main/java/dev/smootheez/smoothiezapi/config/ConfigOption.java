package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.gui.widget.entries.handler.*;
import org.jetbrains.annotations.*;

public abstract class ConfigOption<T> {
    private final String key;
    private T value;
    private final T minValue;
    private final T maxValue;
    private final T defaultValue;

    protected ConfigOption(String key, T defaultValue, T minValue, T maxValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public abstract @NotNull Class<T> getType();

    public abstract @NotNull WidgetHandler<T> getWidgetHandler();

    public abstract @NotNull ConfigOptionSerializer<T> getSerializer();

    protected static void trhowIllegalArgumentException() {
        throw new IllegalArgumentException("minValue cannot be greater than maxValue");
    }
}
