package cz.engeto.ja2024;

public class Settings {

    private static final String DISHFILENAME = "/Users/P3AN/IdeaProjects/RestaurantOrderManagementSystem/src/cookbook.txt";
    private static final String ORDERFILENAME = "/Users/P3AN/IdeaProjects/RestaurantOrderManagementSystem/src/orders.txt";
    private static final String DELIMITER = "; ";

    public static String getCookBookFileName() {
        return DISHFILENAME;
    }

    public static String getOrdersFilename() {
        return ORDERFILENAME;
    }

    public static String getDelimiter() {
        return DELIMITER;
    }
}