package dev.smootheez.smoothiezapi.config;

import com.google.gson.*;

public interface BaseConfigOptionSerializer {
    JsonElement serializeUnsafe(Object value);
    Object deserialize(JsonElement json);
}
