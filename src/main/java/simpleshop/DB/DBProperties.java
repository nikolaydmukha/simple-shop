package simpleshop.DB;

import simpleshop.service.PropertiesService;

import java.util.Properties;

public class DBProperties extends PropertiesService {

    public Properties getProperties() {
        return super.getProperties("database.properties");
    }

}
