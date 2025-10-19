package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.config.serializer.*;
import dev.smootheez.smoothiezapi.gui.widget.entries.handler.*;

import java.util.*;

public class ConfigOption<T> {
    private final String key;
    private T value;
    private T oldValue;
    private final T minValue;
    private final T maxValue;
    private final T defaultValue;
    private final ConfigOptionSerializer<T> serializer;
    private final WidgetHandler<T> widgetHandler;
    private final Class<T> type;

    private ConfigOption(String key, T defaultValue, ConfigOptionSerializer<T> serializer, WidgetHandler<T> widgetHandler, Class<T> type) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.widgetHandler = widgetHandler;
        this.type = type;
        this.minValue = null;
        this.maxValue = null;
        this.oldValue = defaultValue;
        this.serializer = serializer;
    }

    private ConfigOption(String key, T defaultValue, T minValue, T maxValue, ConfigOptionSerializer<T> serializer, WidgetHandler<T> widgetHandler, Class<T> type) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.oldValue = defaultValue;
        this.serializer = serializer;
        this.widgetHandler = widgetHandler;
        this.type = type;
    }

    public static ConfigOption<Boolean> create(String key, Boolean defaultValue) {
        return new ConfigOption<>(key, defaultValue, new BooleanSerializer(), new WidgetHandler.BooleanHandler(), Boolean.class);
    }

    public static ConfigOption<Integer> create(String key, Integer defaultValue, Integer minValue, Integer maxValue) {
        return new ConfigOption<>(key, defaultValue, minValue, maxValue, new IntegerSerializer(), new WidgetHandler.IntegerHandler(), Integer.class);
    }

    public static ConfigOption<Double> create(String key, Double defaultValue, Double minValue, Double maxValue) {
        return new ConfigOption<>(key, defaultValue, minValue, maxValue, new DoubleSerializer(), new WidgetHandler.DoubleHandler(), Double.class);
    }

    public static ConfigOption<String> create(String key, String defaultValue) {
        return new ConfigOption<>(key, defaultValue, new StringSerializer(), new WidgetHandler.StringHandler(), String.class);
    }

    public static ConfigOption<OptionList> create(String key, String... defaultValue) {
        return new ConfigOption<>(key, new OptionList(Arrays.asList(defaultValue)), new OptionListSerializer(), new WidgetHandler.OptionListHandler(), OptionList.class);
    }

    public static <E extends Enum<E>> ConfigOption<E> create(String key, E defaultValue) {
        Class<E> enumClass = defaultValue.getDeclaringClass();
        return new ConfigOption<>(key, defaultValue, new EnumSerializer<>(enumClass), new WidgetHandler.EnumHandler<>(), enumClass);
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

    public void commit() {
        this.oldValue = this.value;
    }

    public T getOldValue() {
        return oldValue;
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

    public Class<T> getType() {
        return type;
    }

    public WidgetHandler<T> getWidgetHandler() {
        return widgetHandler;
    }

    public ConfigOptionSerializer<T> getSerializer() {
        return serializer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConfigOption<?> that = (ConfigOption<?>) o;
        return Objects.equals(getKey(), that.getKey()) &&
                Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getOldValue(), that.getOldValue()) &&
                Objects.equals(getMinValue(), that.getMinValue()) &&
                Objects.equals(getMaxValue(), that.getMaxValue()) &&
                Objects.equals(getDefaultValue(), that.getDefaultValue()) &&
                Objects.equals(getSerializer(), that.getSerializer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(),
                getValue(),
                getOldValue(),
                getMinValue(),
                getMaxValue(),
                getDefaultValue(),
                getSerializer());
    }

    @Override
    public String toString() {
        return "ConfigOption{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", oldValue=" + oldValue +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", defaultValue=" + defaultValue +
                ", serializer=" + serializer +
                '}';
    }
}
