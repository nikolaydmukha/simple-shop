package simpleshop.action;

import simpleshop.printer.ItemsPrinter;

import java.util.Scanner;

public class RequestUserInfo {
    private static ItemsPrinter printer = ItemsPrinter.getInstance();
    private static RequestUserInfo instance;

    public static RequestUserInfo getInstance() {
        if (instance == null) {
            instance = new RequestUserInfo();
        }
        return instance;
    }

    public static void getBrand(Scanner scanner) {
        System.out.println("Введите производителя:");
        String brand = scanner.nextLine();
        printer.showItems("brand", brand);
    }

    public static void getKeyWord(Scanner scanner) {
        System.out.println("Введите слово для поиска:");
        String keyWord = scanner.nextLine();
        printer.showItems("keyWord", keyWord);
    }

    public static void getPrice(String point, Scanner scanner) {
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

    public static void sortByRating(Scanner scanner) {
        System.out.println("Какая категория товаров интересует?");
        String category = scanner.nextLine();
        printer.showItems("findByRating", category);
    }
}
