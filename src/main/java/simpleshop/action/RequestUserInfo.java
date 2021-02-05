package simpleshop.action;

import simpleshop.bucket.ItemBucket;
import simpleshop.device.Device;
import simpleshop.exceptions.UnknownFilterException;
import simpleshop.DB.DBConnector;
import simpleshop.printer.ItemsPrinter;

import java.net.ConnectException;
import java.util.List;
import java.util.Scanner;

public class RequestUserInfo {
    private static ItemBucket bucket = ItemBucket.getInstance();
    private static ItemsPrinter printer = ItemsPrinter.getInstance();
    private static DBConnector dbConnector = DBConnector.getInstance();
    private static RequestUserInfo instance;

    public static RequestUserInfo getInstance() {
        if (instance == null) {
            instance = new RequestUserInfo();
        }
        return instance;
    }

    private String getTextFromUser(String message, Scanner scanner) {
        System.out.println(message);
        String input = scanner.nextLine();
        return input;
    }

    private int getNumberFromUser(String message, Scanner scanner) {
        int price;
        System.out.println(message);
        while (true) {
            try {
                price = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println();
                continue;
            }
        }
        return price;
    }

    public void getBrand(Scanner scanner) {
        printer.showItems("brand", getTextFromUser("Введите производителя:", scanner));
    }

    public void getKeyWord(Scanner scanner) {
        printer.showItems("keyWord", getTextFromUser("Введите слово для поиска:", scanner));
    }

    public void getPrice(String point, Scanner scanner) {
        String filterType;
        int price;
        if (point == "2") {
            price = getNumberFromUser("Введите минимальную цену:", scanner);
            filterType = "moreThanPrice";
        } else {
            price = getNumberFromUser("Введите максимальную цену:", scanner);
            filterType = "lessThanPrice";
        }
        printer.showItems(filterType, String.valueOf(price));
    }

    private void categoriesList() throws UnknownFilterException, ConnectException {
        System.out.println("Список доступных категорий:");
        SelectDevicesServiceAction selectTypes = new SelectDevicesServiceAction("getTypes", null, dbConnector.prepareJDBC());
        List<String> types = selectTypes.makeSelect();
        types.stream().forEach(System.out::println);
    }

    public void sortByRating(Scanner scanner) throws UnknownFilterException, ConnectException {
        categoriesList();
        printer.showItems("findByRating", getTextFromUser("Какая категория товаров интересует?", scanner));
    }

    public void prepareBuyDevice(Scanner scanner) throws UnknownFilterException, ConnectException {
        categoriesList();
        String category = getTextFromUser("Выберите категорию", scanner);
        System.out.println("Список доступных товаров");
        List<Device> devices = printer.showItems("findByRating", category);
        int id = getNumberFromUser("Введите id товара, который хотите купить: ", scanner);
        int i;
        for (i = 0; i < devices.size(); i++) {
            if (devices.get(i).getId() == id){
                break;
            }
        }
        bucket.addItemToBucket(devices.get(i));
        System.out.println(bucket.getUsersBucket().get(0).getDescription());
    }
}
