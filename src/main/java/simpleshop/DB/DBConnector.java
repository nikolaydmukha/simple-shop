package simpleshop.DB;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Properties;

public class DBConnector {

    private JdbcTemplate jdbcTemplate() {
        Properties properties = new DBProperties().getProperties();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getProperty("jdbc.driver"));
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUsername(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));
        return new JdbcTemplate(dataSource);
    }

    public JdbcTemplate prepareJDBC() {
        return jdbcTemplate();
    }
}
