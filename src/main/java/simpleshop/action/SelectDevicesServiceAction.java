package simpleshop.action;

import exceptions.UnknownFilterException;
import org.springframework.jdbc.core.JdbcTemplate;
import simpleshop.DB.DBProperties;
import simpleshop.device.DeviceMapper;

import java.net.ConnectException;
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
        String sql = "select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + getDBName() + "' AND TABLE_NAME = '" + getTableName() + "';";
        List<String> columns = jdbcTemplate.queryForList(sql, String.class);
        return (List<T>) columns;
    }

    private <T> List<T> getTypes() {
        String sql = "select " + getTypeField() + " from " + getTableName() + " group by " + getTypeField() + ";";
        List<String> types = jdbcTemplate.queryForList(sql, String.class);
        return (List<T>) types;
    }

    private <T> List<T> selectDeviceFilteredKeyWord() {
        String sql = "select * from " + getTableName() + " where " + getDescriptionField() + " like '%" + filterValue + "%';";
        return jdbcTemplate.query(sql, new DeviceMapper());
    }

    private <T> List<T> selectAllDevices() {
        String sql = "select * from " + getTableName();
        return jdbcTemplate.query(sql, new DeviceMapper());
    }

    private <T> List<T> selectDeviceFilteredBrand() {
        String sql = "select * from " + getTableName() + " where " + getBrandField() + "='" + filterValue + "';";
        return jdbcTemplate.query(sql, new DeviceMapper());
    }

    private <T> List<T> selectDeviceFilteredPriceLess() {
        String sql = "select * from " + getTableName() + " where " + getPriceField() + "<=" + filterValue + ";";
        return jdbcTemplate.query(sql, new DeviceMapper());
    }

    private <T> List<T> selectDeviceFilteredPriceMore() {
        String sql = "select * from " + getTableName() + " where " + getPriceField() + ">=" + filterValue + ";";
        return jdbcTemplate.query(sql, new DeviceMapper());
    }

    private <T> List<T> selectDeviceFilteredRating() {
        String sql = "select * from " + getTableName() + " where " + getTypeField() + "='" + filterValue + "' ORDER BY " + getRatingField() + " DESC;";
        return jdbcTemplate.query(sql, new DeviceMapper());
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
}
