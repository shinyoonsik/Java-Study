package org.example.singleton;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Resources {
    private static Resources instance;
    private Properties properties;

    private Resources() throws IOException {
        properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStreamReader isr = new InputStreamReader(classLoader.getResourceAsStream("config.properties"), StandardCharsets.UTF_8)) {
            properties.load(isr);
        }
    }

    public synchronized static Resources getResources() throws IOException {
        if (instance == null) {
            instance = new Resources();
        }
        return instance;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
