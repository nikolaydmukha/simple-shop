package simpleshop.device;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceSetExtractor implements ResultSetExtractor {

    @Override
    public Object extractData(ResultSet rs) throws SQLException {
        Device device = new Device();
        device.setId(Integer.parseInt(rs.getString(1)));
        device.setType(rs.getString(2));
        device.setBrand(rs.getString(3));
        device.setModel(rs.getString(4));
        device.setDescription(rs.getString(5));
        device.setPrice(Double.parseDouble(rs.getString(6)));
        device.setRating(Double.parseDouble(rs.getString(7)));
        return device;
    }

}
