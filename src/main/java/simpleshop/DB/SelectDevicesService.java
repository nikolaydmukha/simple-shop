package simpleshop.DB;

import exceptions.UnknownFilterException;
import org.springframework.jdbc.core.JdbcTemplate;
import simpleshop.device.Device;
import simpleshop.device.DeviceMapper;

import java.util.List;
import java.util.Properties;

public class SelectDevicesService {

    private JdbcTemplate jdbcTemplate;
    private String filterType;
    private String filterValue;
    private Properties properties = new DBProperties().getProperties();

    public SelectDevicesService(String filterType, String filterValue, JdbcTemplate jdbcTemplate) {
        this.filterType = filterType;
        this.filterValue = filterValue;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Device> makeSelect() throws UnknownFilterException {
        switch (filterType) {
            case "":
                return selectAllDevices(jdbcTemplate);
            case "brand":
                return selectDeviceFilteredBrand(jdbcTemplate);
//            case "lessPrice":
//                return selectDeviceFilteredPriceLess(jdbcTemplate);
//            case "morePrice":
//                return selectDeviceFilteredPriceMore(jdbcTemplate);
//            case "keyWord":
//                return selectDeviceFilteredKeyWord(jdbcTemplate);
            default:
                throw new UnknownFilterException("Неверно указан фильтр поиска данных...");
        }
    }

//    private List<Device> selectDeviceFilteredKeyWord(JdbcTemplate jdbcTemplate) {
//        return jdbcTemplate.query("select * from " + getTableName() + " where ? like '%?%'",
//                new Object[]{getTableName(), getDescriptionField(), filterValue}, new DeviceMapper());
//    }

    private List<Device> selectAllDevices(JdbcTemplate jdbcTemplate) {
        String sql = "select * from " + getTableName();
        return jdbcTemplate.query(sql, new DeviceMapper());
    }

    private List<Device> selectDeviceFilteredBrand(JdbcTemplate jdbcTemplate) {
        String sql = "select * from " + getTableName() + " where ?=?";
        return jdbcTemplate.query(sql, new Object[]{getBrandField(), filterValue}, new DeviceMapper());
    }

//    private List<Device> selectDeviceFilteredPriceLess(JdbcTemplate jdbcTemplate) {
//    }
//
//    private List<Device> selectDeviceFilteredPriceMore(JdbcTemplate jdbcTemplate) {
//    }

    private String getTableName() {
        return properties.getProperty("device.table");
    }

    private String getDescriptionField() {
        return "description";
    }

    private String getBrandField() {
        return "brand1";
    }

}
