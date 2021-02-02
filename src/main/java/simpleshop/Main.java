package simpleshop;

import exceptions.EmptyResultException;
import exceptions.UnknownFilterException;
import simpleshop.DB.DBConnector;
import simpleshop.DB.SelectDevicesService;
import simpleshop.device.Device;
import simpleshop.printer.ItemsPrinter;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu(scanner);
        }
    }

    private static void menu(Scanner scanner) {
        String filterType = "";
        String filterValue = "";
        while (true) {
            System.out.println(
                    "Выберите действие:\n" +
                            "1. Показать все товары;\n" +
                            "2. Фильтр по цене(>);\n" +
                            "3. Фильтр по цене(<);\n" +
                            "4. Фильтр по производителям;\n" +
                            "5. Фильтр по ключевому слову\n" +
                            "6. Рейтинг товаров;\n" +
                            "0. Выход;");
            String point = scanner.nextLine();
            switch (point) {
                case "0":
                    System.exit(1);
                    break;
                case "1":
                    showItems(filterType, filterValue);
                    break;
                case "2":
                case "3":
                    getPrice(point, scanner);
                    break;
                case "4":
                    getBrand(scanner);
                    break;
            }
        }
    }

    private static void getBrand(Scanner scanner) {
        System.out.println("Введите производителя:");
        String brand = scanner.nextLine();
        showItems("brand", brand);
    }

    private static void getPrice(String point, Scanner scanner) {
        String filterType;
        if (point == "2") {
            System.out.println("Введите минимальную цену:");
            filterType = "moreThanPrice";
        } else {
            System.out.println("Введите максимальную цену:");
            filterType = "lessThanPrice";
        }
        while (true) {
            try {
                int price = scanner.nextInt();
                scanner.nextLine();
                showItems(filterType, String.valueOf(price));
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println();
                continue;
            }
        }
    }



    private static void showItems(String filterType, String filterValue) {
        ItemsPrinter printer = new ItemsPrinter();
        DBConnector dbConnector = new DBConnector();
        SelectDevicesService selectDevicesService = new SelectDevicesService(filterType, filterValue, dbConnector.prepareJDBC());
        try {
            List<Device> devices = selectDevicesService.makeSelect();
            printer.itemsPrinter(devices);
        } catch (EmptyResultException ex) {
            System.err.println(ex.getMessage());
        } catch (UnknownFilterException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
