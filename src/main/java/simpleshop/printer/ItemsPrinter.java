package simpleshop.printer;

import dnl.utils.text.table.TextTable;
import exceptions.EmptyResultException;
import simpleshop.device.Device;

import java.util.*;

public class ItemsPrinter {

    public void itemsPrinter(List<Device> allItems) throws EmptyResultException {
        validateSize(allItems);
        String[] columnsName = {"Тип", "Фирма", "Модель", "Описание", "Цена"};
        String[][] data = new String[allItems.size()][5];
        int i = 0;
        int j = 0;
        for (Device device : allItems) {
            data[i][0] = device.getType();
            data[i][1] = device.getBrand();
            data[i][2] = device.getModel();
            data[i][3] = device.getDescription();
            data[i][4] = device.getPrice().toString();
            i++;
        }
        TextTable tt = new TextTable(columnsName, data);
        tt.printTable();
    }

    private void validateSize(List<Device> allItems) throws EmptyResultException {
        if (allItems.size() == 0) {
            throw new EmptyResultException("Не найдено ни одной записи...");
        }
    }
}
