package simpleshop.printer;

import dnl.utils.text.table.TextTable;
import simpleshop.exceptions.EmptyResultException;
import simpleshop.exceptions.UnknownFilterException;
import org.apache.commons.lang.WordUtils;
import simpleshop.DB.DBConnector;
import simpleshop.action.SelectDevicesServiceAction;
import simpleshop.device.Device;

import java.net.ConnectException;
import java.util.List;
import java.util.stream.Collectors;

public class ItemsPrinter {

    private static ItemsPrinter instance;
    private static DBConnector dbConnector = DBConnector.getInstance();
    private static ItemsPrinter printer = new ItemsPrinter();

    public static ItemsPrinter getInstance() {
        if (instance == null) {
            instance = new ItemsPrinter();
        }
        return instance;
    }

    public void itemsPrinter(List<Device> allItems) throws EmptyResultException, UnknownFilterException, ConnectException {
        validateSize(allItems);
        List<String> columnsName = prepareTableColumns();
        TextTable tt = new TextTable(columnsName.toArray(new String[columnsName.size()]), filler(allItems, columnsName));
        tt.printTable();
        System.out.println();
    }

    private String[][] filler(List<Device> allItems, List<String> columnsName) {
        String[][] data = new String[allItems.size()][columnsName.size()];
        int i = 0;
        for (Device device : allItems) {
            data[i][0] = String.valueOf(device.getId());
            data[i][1] = device.getType();
            data[i][2] = device.getBrand();
            data[i][3] = device.getModel();
            data[i][4] = device.getDescription();
            data[i][5] = device.getPrice().toString();
            data[i][6] = device.getRating().toString();
            data[i][7] = String.valueOf(device.getQuantity());
            i++;
        }
        return data;
    }

    private List<String> prepareTableColumns() throws UnknownFilterException, ConnectException {
        SelectDevicesServiceAction selectDevicesService = new SelectDevicesServiceAction("getTableColumnsName", null, dbConnector.prepareJDBC());
        List<String> columns = selectDevicesService.makeSelect();
        List<String> upperColumnsName = columns.stream()
//                .filter(item -> !item.equals("id"))
                .map(item -> WordUtils.capitalize(item))
                .collect(Collectors.toList());
        return upperColumnsName;
    }

    private void validateSize(List<Device> allItems) throws EmptyResultException {
        if (allItems.size() == 0) {
            throw new EmptyResultException("Не найдено ни одной записи...");
        }
    }

    public List<Device> showItems(String filterType, String filterValue) {
        List<Device> devices = null;
        SelectDevicesServiceAction selectDevicesService = new SelectDevicesServiceAction(filterType, filterValue, dbConnector.prepareJDBC());
        try {
            devices = selectDevicesService.makeSelect();
            printer.itemsPrinter(devices);
        } catch (EmptyResultException ex) {
            System.err.println(ex.getMessage());
        } catch (UnknownFilterException ex) {
            System.err.println(ex.getMessage());
        } catch (ConnectException ex) {
            System.err.println(ex.getMessage());
        }
        return devices;
    }

}
