package dev.smootheez.smoothiezapi.config;

import com.google.gson.*;
import dev.smootheez.smoothiezapi.util.*;
import net.fabricmc.loader.api.*;

import java.io.*;
import java.util.*;

public class ConfigWriter {
    private final Gson gson;
    private final File configFile;
    private final Map<String, ConfigOptionAdpter<?>> options = new TreeMap<>();

    public ConfigWriter(String configId) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.configFile = FabricLoader.getInstance().getConfigDir().resolve(configId + ".json").toFile();
    }

    public <T> void addOption(String key, ConfigOption<T> option) {
        options.put(key, new ConfigOptionAdpter<>(option));
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            saveConfig();
            return;
        }

        try (var reader = new FileReader(configFile)) {
            JsonElement element = gson.fromJson(reader, JsonElement.class);
            fromJson(element);
        } catch (IOException e) {
            Constants.LOGGER.error("Failed to load config file: {}, error: {}", configFile.getAbsoluteFile(), e.getMessage());
        }
    }

    public void saveConfig() {
        try (var writer = new FileWriter(configFile)) {
            JsonElement element = toJson();
            gson.toJson(element, writer);
        } catch (IOException e) {
            Constants.LOGGER.error("Failed to save config file: {}, error: {}", configFile.getAbsoluteFile(), e.getMessage());
        }
    }

    private void fromJson(JsonElement element) {
        options.forEach((key, adpter) -> {
            JsonElement jsonElement = element.getAsJsonObject().get(key);
            if (jsonElement != null) adpter.fromJson(jsonElement);
        });
    }

    private JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        options.forEach((key, adpter) -> jsonObject.add(key, adpter.toJson()));
        return jsonObject;
    }

    record ConfigOptionAdpter<T>(ConfigOption<T> option) {
        void fromJson(JsonElement element) {
            option.setValue(option.getSerializer().deserialize(element));
        }

        JsonElement toJson() {
            return option.getSerializer().serialize(option.getValue());
        }
    }
}
