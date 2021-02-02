package simpleshop.device;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int line) throws SQLException {
        DeviceSetExtractor extractor = new DeviceSetExtractor();
        return extractor.extractData(rs);
    }
}