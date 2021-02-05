package simpleshop.action;

import exceptions.UnknownFilterException;
import simpleshop.DB.DBConnector;
import simpleshop.printer.ItemsPrinter;

import java.net.ConnectException;
import java.util.List;
import java.util.Scanner;

public class RequestUserInfo {

    private static ItemsPrinter printer = ItemsPrinter.getInstance();
    private static DBConnector dbConnector = DBConnector.getInstance();
    private static RequestUserInfo instance;

    public static RequestUserInfo getInstance() {
        if (instance == null) {
            instance = new RequestUserInfo();
        }
        return instance;
    }

    public void getBrand(Scanner scanner) {
        System.out.println("Введите производителя:");
        String brand = scanner.nextLine();
        printer.showItems("brand", brand);
    }

    public void getKeyWord(Scanner scanner) {
        System.out.println("Введите слово для поиска:");
        String keyWord = scanner.nextLine();
        printer.showItems("keyWord", keyWord);
    }

    public void getPrice(String point, Scanner scanner) {
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
                printer.showItems(filterType, String.valueOf(price));
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println();
                continue;
            }
        }
    }

    private String getCategoryName(Scanner scanner) {
        System.out.println("Какая категория товаров интересует?");
        String category = scanner.nextLine();
        return category;
    }

    private void categoriesList() throws UnknownFilterException, ConnectException {
        System.out.println("Список доступных категорий:");
        SelectDevicesServiceAction selectTypes = new SelectDevicesServiceAction("getTypes", null, dbConnector.prepareJDBC());
        List<String> types = selectTypes.makeSelect();
        types.stream().forEach(System.out::println);
    }

    public void sortByRating(Scanner scanner) throws UnknownFilterException, ConnectException {
        categoriesList();
        String category = getCategoryName(scanner);
        printer.showItems("findByRating", category);
    }

    public void prepareByeDevice(Scanner scanner) throws UnknownFilterException, ConnectException {
        categoriesList();
        String category = getCategoryName(scanner);
        System.out.println("Список доступных товаров");
        printer.showItems("findByRating", category);
    }
}
