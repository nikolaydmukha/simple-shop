package simpleshop.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {

    public Properties getProperties(String fileName) {
        try (InputStream file = getClass().getClassLoader().getResourceAsStream(fileName)) {
            Properties properties = new Properties();
            properties.load(file);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read " + fileName, e);
        }
    }

}