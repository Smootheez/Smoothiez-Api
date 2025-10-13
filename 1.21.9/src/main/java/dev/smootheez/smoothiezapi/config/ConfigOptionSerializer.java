package dev.smootheez.smoothiezapi.config;

import com.google.gson.*;

public interface ConfigOptionSerializer<T> {
    JsonElement serialize(T value);
    T deserialize(JsonElement json);
}

