package simpleshop;

import exceptions.UnknownFilterException;
import simpleshop.action.RequestUserInfo;
import simpleshop.printer.ItemsPrinter;

import java.util.Scanner;

public class Main {

    static ItemsPrinter printer = ItemsPrinter.getInstance();
    static RequestUserInfo getUserWish = RequestUserInfo.getInstance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                menu(scanner);
            } catch (UnknownFilterException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private static void menu(Scanner scanner) throws UnknownFilterException {
        String filterType = "";
        String filterValue = "";
        while (true) {
            showMenuActions();
            String point = scanner.nextLine();
            switch (point) {
                case "0":
                    System.exit(1);
                    break;
                case "1":
                    printer.showItems(filterType, filterValue);
                    break;
                case "2":
                case "3":
                    getUserWish.getPrice(point, scanner);
                    break;
                case "4":
                    getUserWish.getBrand(scanner);
                    break;
                case "5":
                    getUserWish.getKeyWord(scanner);
                case "6":
                    getUserWish.sortByRating(scanner);
                    break;
                case "7":
                    getUserWish.getKeyWord(scanner);
                    break;
                default:
                    throw new UnknownFilterException("Нет подходящего пункта меню!");
            }
        }
    }

    private static void showMenuActions() {
        System.out.println(
                "Выберите действие:\n" +
                        "1. Показать все товары;\n" +
                        "2. Фильтр по цене(>);\n" +
                        "3. Фильтр по цене(<);\n" +
                        "4. Фильтр по производителям;\n" +
                        "5. Фильтр по ключевому слову\n" +
                        "6. Рейтинг товаров;\n" +
                        "7. Купить товар;" +
                        "0. Выход;");
    }
}
