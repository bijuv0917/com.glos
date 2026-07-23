package com.glos.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) PROPERTIES.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load config.properties", exception);
        }
    }

    private Config() { }

    public static String get(String name, String defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            value = System.getenv("GLOS_" + name.toUpperCase().replace('.', '_'));
        }
        if (value == null || value.isBlank()) value = PROPERTIES.getProperty(name);
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }

    public static String required(String name) {
        String value = get(name, "");
        if (value.isBlank()) throw new IllegalStateException(
                "Missing " + name + ". Set -D" + name + " or the GLOS_"
                        + name.toUpperCase().replace('.', '_') + " environment variable.");
        return value;
    }
}
