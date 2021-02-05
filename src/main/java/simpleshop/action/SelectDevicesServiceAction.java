package simpleshop.action;

import simpleshop.exceptions.UnknownFilterException;
import org.springframework.jdbc.core.JdbcTemplate;
import simpleshop.DB.DBProperties;
import simpleshop.device.DeviceMapper;

import java.net.ConnectException;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;

public class SelectDevicesServiceAction {

    private JdbcTemplate jdbcTemplate;
    private String filterType;
    private String filterValue;
    private Properties properties = new DBProperties().getProperties();

    public SelectDevicesServiceAction(String filterType, String filterValue, JdbcTemplate jdbcTemplate) {
        this.filterType = filterType;
        this.filterValue = filterValue;
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> List<T> makeSelect() throws UnknownFilterException, ConnectException {
        switch (filterType) {
            case "":
                return selectAllDevices();
            case "brand":
                return selectDeviceFilteredBrand();
            case "lessThanPrice":
                return selectDeviceFilteredPriceLess();
            case "moreThanPrice":
                return selectDeviceFilteredPriceMore();
            case "keyWord":
                return selectDeviceFilteredKeyWord();
            case "getTableColumnsName":
                return getTableColumnsName();
            case "findByRating":
                return selectDeviceFilteredRating();
            case "getTypes":
                return getTypes();
            default:
                throw new UnknownFilterException("Неверно указан фильтр поиска данных...");
        }
    }

    private <T> List<T> getTableColumnsName() {
        Formatter f = new Formatter();
        f.format("select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s';", getDBName(), getTableName());
        List<String> columns = jdbcTemplate.queryForList(f.toString(), String.class);
        return (List<T>) columns;
    }

    private <T> List<T> getTypes() {
        Formatter f = new Formatter();
        f.format("select %s from %s group by %s;", getTypeField(), getTableName(), getTypeField());
        List<String> types = jdbcTemplate.queryForList(f.toString(), String.class);
        return (List<T>) types;
    }

    private <T> List<T> selectDeviceFilteredKeyWord() {
        Formatter f = new Formatter();
        f.format("select * from %s where %s like \'%%%s%%\' and %s!=0;",
                getTableName(), getDescriptionField(), filterValue, getQualityField());
        return jdbcTemplate.query(f.toString(), new DeviceMapper());
    }

    private <T> List<T> selectAllDevices() {
        Formatter f = new Formatter();
        f.format("select * from %s where %s!=0;",
                getTableName(), getQualityField());
        return jdbcTemplate.query(f.toString(), new DeviceMapper());
    }

    private <T> List<T> selectDeviceFilteredBrand() {
        Formatter f = new Formatter();
        f.format("select * from %s where %s='%s' and %s!=0;",
                getTableName(), getBrandField(), filterValue, getQualityField());
        return jdbcTemplate.query(f.toString(), new DeviceMapper());
    }

    private <T> List<T> selectDeviceFilteredPriceLess() {
        Formatter f = new Formatter();
        f.format("select * from %s where %s <= %s and %s!=0;",
                getTableName(), getPriceField(), filterValue, getQualityField());
        return jdbcTemplate.query(f.toString(), new DeviceMapper());
    }

    private <T> List<T> selectDeviceFilteredPriceMore() {
        Formatter f = new Formatter();
        f.format("select * from %s where %s >= %s and %s!=0;",
                getTableName(), getPriceField(), filterValue, getQualityField());
        return jdbcTemplate.query(f.toString(), new DeviceMapper());
    }

    private <T> List<T> selectDeviceFilteredRating() {
        Formatter f = new Formatter();
        f.format("select * from %s where %s='%s' and %s!=0 ORDER BY %s DESC;",
                getTableName(), getTypeField(), filterValue, getQualityField(), getRatingField());
        return jdbcTemplate.query(f.toString(), new DeviceMapper());
    }

    private String getDBName() {
        return properties.getProperty("db.name");
    }

    private String getTableName() {
        return properties.getProperty("device.table");
    }

    private String getDescriptionField() {
        return "description";
    }

    private String getBrandField() {
        return "brand";
    }

    private String getPriceField() {
        return "price";
    }

    private String getTypeField() {
        return "type";
    }

    private String getRatingField() {
        return "rating";
    }

    private String getQualityField() {
        return "quality";
    }
}
