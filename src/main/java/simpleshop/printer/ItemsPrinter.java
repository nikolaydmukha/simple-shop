package simpleshop.printer;

import dnl.utils.text.table.TextTable;
import exceptions.EmptyResultException;
import exceptions.UnknownFilterException;
import org.apache.commons.lang.WordUtils;
import simpleshop.DB.DBConnector;
import simpleshop.DB.SelectDevicesService;
import simpleshop.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemsPrinter {


    public void itemsPrinter(List<Device> allItems) throws EmptyResultException {
        validateSize(allItems);
        List<String> columnsName = new ArrayList<>();
        try {
            columnsName = prepareTableColumns();
        } catch (UnknownFilterException ex) {
            System.out.println(ex.getMessage());
        }
        String[][] data = new String[allItems.size()][columnsName.size()];
        int i = 0;
        for (Device device : allItems) {
            data[i][0] = device.getType();
            data[i][1] = device.getBrand();
            data[i][2] = device.getModel();
            data[i][3] = device.getDescription();
            data[i][4] = device.getPrice().toString();
            data[i][5] = device.getRating().toString();
            i++;
        }
        TextTable tt = new TextTable(columnsName.toArray(new String[columnsName.size()]), data);
        tt.printTable();
    }

    private List<String> prepareTableColumns() throws UnknownFilterException {
        DBConnector dbConnector = new DBConnector();
        SelectDevicesService selectDevicesService = new SelectDevicesService("getTableColumnsName", null, dbConnector.prepareJDBC());
        List<String> columns = selectDevicesService.makeSelect();
        List<String> upperColumnsName = columns.stream()
                .filter(item -> !item.equals("id"))
                .map(item -> WordUtils.capitalize(item))
                .collect(Collectors.toList());
        return upperColumnsName;
    }

    private void validateSize(List<Device> allItems) throws EmptyResultException {
        if (allItems.size() == 0) {
            throw new EmptyResultException("Не найдено ни одной записи...");
        }
    }
}
