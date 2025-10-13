package dev.smootheez.smoothiezapi.config;

import com.google.gson.*;

public interface ConfigOptionSerializer<T> extends BaseConfigOptionSerializer {
    JsonElement serialize(T value);
    T deserialize(JsonElement json);

    @Override
    @SuppressWarnings("unchecked")
    default JsonElement serializeUnsafe(Object value) {
        return serialize((T) value);
    }
}

