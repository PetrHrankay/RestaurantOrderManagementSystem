package cz.engeto.ja2024;

public class Settings {

    private static final String COOKBOOKFILENAME = "resources/cookbook.txt";
    private static final String ORDERFILENAME = "resources/orders.txt";
    private static final String DELIMITER = "; ";

    public static String getCookBookFileName() {
        return COOKBOOKFILENAME;
    }

    public static String getOrdersFilename() {
        return ORDERFILENAME;
    }

    public static String getDelimiter() {
        return DELIMITER;
    }

}