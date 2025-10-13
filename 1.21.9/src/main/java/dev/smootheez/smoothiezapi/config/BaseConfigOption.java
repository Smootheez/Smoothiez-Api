package dev.smootheez.smoothiezapi.config;

public interface BaseConfigOption {
    String getKey();
    Object getValue();
    Object getDefaultValue();
    Object getMinValue();
    Object getMaxValue();
    BaseConfigOptionSerializer getSerializer();
    void setValueUnsafe(Object value);
}
