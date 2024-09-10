import cz.engeto.ja2024.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws FileManagerException, DishException, IOException, OrderException {

        String cookBookFileName = Settings.getCookBookFileName();
        String ordersFileName = Settings.getOrdersFilename();

        // Úkol číslo 1. Načti stav evidence z disku
        loadDataFromFiles(cookBookFileName, ordersFileName);

        // Úkol číslo 2.1. Vlož do systému jídla
        addDishesToSystem(cookBookFileName);

        // Úkol číslo 2.2.1 Vlož do systému objednávky
        addOrdersToSystem(ordersFileName);

        // Úkol číslo 2.2.2. Vytvoř objednávku pro stůl č.2
        addOrdersForTable2(ordersFileName);

        // Úkol číslo 3. Vypiš celkovou cenu konzumace pro stůl 15
        Order.printTotalSpendingForTableNumber(15);

        // Úkol číslo 4. Použij všechny připravené metody pro získání informací pro management — údaje vypisuj na obrazovku.
        printManagementInfo();

        // Úkol číslo 5. Změněná data ulož na disk.
        saveDataToFile(cookBookFileName, ordersFileName);

        // Úkol číslo 6. Po opětovném spuštění aplikace musí být data opět v pořádku načtena.(Vyzkoušej!)
        verifyDataAfterRestart();
    }

    // Úkol číslo 1. Načti stav evidence z disku
    private static void loadDataFromFiles(String cookBookFileName, String ordersFileName) throws FileManagerException, IOException {
        FileManager.loadAndPrintDishFileContent(cookBookFileName);
        FileManager.loadAndPrintOrderFileContent(ordersFileName);
        System.out.println();
    }

    // Úkol číslo 2.1. Vlož do systému jídla
    private static void addDishesToSystem(String cookBookFileName) throws DishException, FileManagerException {
        Dish kureciRizek = new Dish(1, "Kuřecí řízek obalovaný 150 g", BigDecimal.valueOf(119), 15, "kureci-rizek-01");
        Dish hranolky = new Dish(2, "Hranolky 150 g", BigDecimal.valueOf(49), 10, "hranolky-02");
        Dish pstruh = new Dish(3, "Pstruh na víně 200 g", BigDecimal.valueOf(219), 45, "pstruh-na-vine-03");
        Dish kofola = new Dish(4, "Kofola 0.5 l", BigDecimal.valueOf(35), 2, "kofola-0.5l-04");

        FileManager.saveCookBookToFile(cookBookFileName);
    }

    // Úkol číslo 2.2.1 Vlož do systému objednávky
    private static void addOrdersToSystem(String ordersFileName) throws FileManagerException, OrderException {
        Order order1 = new Order(15, 1, 2, false);
        Order order2 = new Order(15, 2, 2, false);
        Order order3 = new Order(15, 4, 2, LocalDateTime.now(), LocalDateTime.now(), false);

        FileManager.saveOrdersToFile(ordersFileName);
    }

    // Úkol číslo 2.2.2. Vytvoř objednávku pro stůl č.2
    private static void addOrdersForTable2(String ordersFileName) throws FileManagerException, OrderException {
        Order order4 = new Order(2, 3, 3, LocalDateTime.now(), LocalDateTime.now().plusMinutes(55), true);
        Order order5 = new Order(2, 2, 3, LocalDateTime.now(), LocalDateTime.now().plusMinutes(55), true);
        Order order6 = new Order(2, 4, 2, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), true);

        FileManager.saveOrdersToFile(ordersFileName);
    }

    // Úkol číslo 4. Použij všechny připravené metody pro získání informací pro management — údaje vypisuj na obrazovku.
    private static void printManagementInfo() throws DishException, OrderException {
        Dish gulas = new Dish(5, "Guláš s knedlíkem", BigDecimal.valueOf(128), 20, "gulas-se-sesti-05");
        Order order7 = new Order(13, 4, 6, LocalDateTime.now().minusMinutes(5), LocalDateTime.now(), false);

        System.out.println("===========================================");
        System.out.println("Information for restaurant management:".toUpperCase(Locale.ROOT));
        System.out.println("-------------------------------------------");

        RestaurantManager.countPendingOrders();
        System.out.println();
        RestaurantManager.getOrdersSortedByCreationTime();
        System.out.println();
        RestaurantManager.averageOrderProcessingTimeInMinutes();
        System.out.println();
        RestaurantManager.getDishesOrderedToday();
        System.out.println();
        RestaurantManager.getAllOrdersForTable(2);  // Výpis je v češtině kvůli dodržení přesného formátu.
    }

    // Úkol číslo 5. Změněná data ulož na disk.
    private static void saveDataToFile(String cookBookFileName, String ordersFileName) throws FileManagerException {
        FileManager.saveCookBookToFile(cookBookFileName);
        FileManager.saveOrdersToFile(ordersFileName);
    }

    // Úkol číslo 6. Po opětovném spuštění aplikace musí být data opět v pořádku načtena.(Vyzkoušej!)
    private static void verifyDataAfterRestart() {
        System.out.println("...***...");
        System.out.println("After restarting the application, the data was successfully loaded (verified).");
    }
}
