package dev.smootheez.smoothiezapi.config;

import dev.smootheez.smoothiezapi.example.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {
    @BeforeEach
    void setup() {
        ConfigManager.clearConfigs();
    }

    @Test
    void testRegisterAndRetrieveConfig() {
        ExampleConfig config = new ExampleConfig();
        ConfigManager.register(config);

        ExampleConfig retrieved = ConfigManager.getConfig(ExampleConfig.class);
        assertNotNull(retrieved, "Retrieved config should not be null");
        assertSame(config, retrieved, "Retrieved config should be the same instance as registered");
    }

    @Test
    void testConfigValuePersistence() {
        ExampleConfig config = new ExampleConfig();
        ConfigManager.register(config);

        // Change value via the registered instance
        ConfigManager.getConfig(ExampleConfig.class)
                .getBooleanExample()
                .setValue(false);

        // Retrieve again from registry
        ExampleConfig retrieved = ConfigManager.getConfig(ExampleConfig.class);
        assertFalse(retrieved.getBooleanExample().getValue(), "Updated value should persist in registry");
    }

    @Test
    void testNewInstanceDoesNotAffectRegisteredConfig() {
        ExampleConfig config = new ExampleConfig();
        ConfigManager.register(config);

        // Change value in registered config
        ConfigManager.getConfig(ExampleConfig.class)
                .getBooleanExample()
                .setValue(false);

        // Create a new config instance (not registered)
        ExampleConfig newConfig = new ExampleConfig();

        assertTrue(newConfig.getBooleanExample().getValue(),
                "New config instance should still have default value");
        assertFalse(ConfigManager.getConfig(ExampleConfig.class)
                        .getBooleanExample()
                        .getValue(),
                "Registered config should keep the changed value");
    }

    @Test
    void testIsRegistered() {
        assertFalse(ConfigManager.isRegistered(ExampleConfig.class));

        ConfigManager.register(new ExampleConfig());
        assertTrue(ConfigManager.isRegistered(ExampleConfig.class));
    }

    @Test
    void testClearRegistry() {
        ConfigManager.register(new ExampleConfig());
        assertTrue(ConfigManager.isRegistered(ExampleConfig.class));

        ConfigManager.clearConfigs();
        assertFalse(ConfigManager.isRegistered(ExampleConfig.class),
                "Registry should be empty after clear()");
    }
}