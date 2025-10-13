package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.config.serializer.*;

import java.util.*;

public class ConfigOption<T> {
    private final String key;
    private T value;
    private final T minValue;
    private final T maxValue;
    private final T defaultValue;
    private final ConfigOptionSerializer<T> serializer;

    private ConfigOption(String key, T defaultValue, ConfigOptionSerializer<T> serializer) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.minValue = null;
        this.maxValue = null;
        this.serializer = serializer;
    }

    private ConfigOption(String key, T defaultValue, T minValue, T maxValue, ConfigOptionSerializer<T> serializer) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.serializer = serializer;
    }

    public static ConfigOption<Boolean> create(String key, Boolean defaultValue) {
        return new ConfigOption<>(key, defaultValue, new BooleanSerializer());
    }

    public static ConfigOption<Integer> create(String key, Integer defaultValue, Integer minValue, Integer maxValue) {
        return new ConfigOption<>(key, defaultValue, minValue, maxValue, new IntegerSerializer());
    }

    public static ConfigOption<Double> create(String key, Double defaultValue, Double minValue, Double maxValue) {
        return new ConfigOption<>(key, defaultValue, minValue, maxValue, new DoubleSerializer());
    }

    public static ConfigOption<String> create(String key, String defaultValue) {
        return new ConfigOption<>(key, defaultValue, new StringSerializer());
    }

    public static ConfigOption<OptionList> create(String key, String... defaultValue) {
        return new ConfigOption<>(key, new OptionList(Arrays.asList(defaultValue)), new OptionListSerializer());
    }

    public static <E extends Enum<E>> ConfigOption<E> create(String key, E defaultValue) {
        Class<E> enumClass = defaultValue.getDeclaringClass();
        return new ConfigOption<>(key, defaultValue, new EnumSerializer<>(enumClass));
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

    public ConfigOptionSerializer<T> getSerializer() {
        return serializer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConfigOption<?> that = (ConfigOption<?>) o;
        return Objects.equals(getKey(), that.getKey())
                && Objects.equals(getValue(), that.getValue())
                && Objects.equals(getMinValue(), that.getMinValue())
                && Objects.equals(getMaxValue(), that.getMaxValue())
                && Objects.equals(getDefaultValue(), that.getDefaultValue())
                && Objects.equals(getSerializer(), that.getSerializer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue(), getMinValue(), getMaxValue(), getDefaultValue(), getSerializer());
    }

    @Override
    public String toString() {
        return "ConfigOption{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", defaultValue=" + defaultValue +
                ", serializer=" + serializer +
                '}';
    }
}
