package cz.engeto.ja2024;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileManager {

    private static void handleException(String fileName, String message, Exception e) throws FileManagerException {
        throw new FileManagerException(message + " " + fileName + ":\n" + e.getLocalizedMessage());
    }

    public static synchronized void saveCookBookToFile(String fileName) throws FileManagerException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Dish item : Dish.getAllDishesFromCookBook()) {
                writer.println(
                        item.getDishId() + delimiter
                                + item.getTitle() + delimiter
                                + item.getPrice() + delimiter
                                + item.getPreparationTimeInMinutes() + delimiter
                                + item.getImage());
            }
        } catch (FileNotFoundException e) {
            handleException(fileName, "File not found!", e);
        } catch (IOException e) {
            handleException(fileName, "Output error while writing to the file:", e);
        }
    }

    public static synchronized void saveOrdersToFile(String fileName) throws FileManagerException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Order item : Order.getAllOrdersFromReceivedOrdersList()) {
                int dishId = item.getDish().getDishId();
                writer.println(
                        "Table: " + item.getTableNumber() + delimiter
                                + "Dish number: " + dishId + delimiter
                                + "Quantity: " + item.getQuantityOrdered() + delimiter
                                + item.getOrderedTime() + delimiter
                                + item.getFulfilmentTime() + delimiter
                                + item.isPaid() + delimiter
                                + item.isFulfilledOrNot());
            }
        } catch (FileNotFoundException e) {
            handleException(fileName, "File not found!", e);
        } catch (IOException e) {
            handleException(fileName, "Output error while writing to the file:", e);
        }
    }

    private static boolean isFileEmpty(String fileName) throws FileManagerException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.readLine() == null;
        } catch (FileNotFoundException e) {
            handleException(fileName, "File not found!", e);
            return true; // unreachable code, handleException will throw exception
        } catch (IOException e) {
            handleException(fileName, "Output error while reading the file:", e);
            return true; // unreachable code, handleException will throw exception
        }
    }

    public static void loadAndPrintDishFileContent(String fileName) throws FileManagerException {
        int lineCounter = 0;

        if (isFileEmpty(fileName)) {
            System.out.println("No dishes added yet.");
            return;
        }
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";\\s*");
                if (parts.length != 5) {
                    throw new FileManagerException("Incorrect number of items on line " + lineCounter + ": " + Arrays.toString(parts));
                }
                System.out.println("Dish ID: " + parts[0] +
                        ", Title: " + parts[1] +
                        ", Price: " + parts[2] +
                        ", Preparation Time: " + parts[3] +
                        ", Image: " + parts[4]);
                System.out.println("-------------------------------------------");
            }
        } catch (FileNotFoundException e) {
            handleException(fileName, "File not found!", e);
        } catch (IOException e) {
            handleException(fileName, "Error while reading file:", e);
        }
    }

    public static void loadAndPrintOrderFileContent(String fileName) throws FileManagerException, IOException {
        int lineCounter = 0;

        if (isFileEmpty(fileName)) {
            System.out.println("No orders added yet.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCounter++;
                String[] parts = line.trim().split(";\\s*");
                if (parts.length != 7) {
                    throw new FileManagerException(
                            "Invalid format on line " + lineCounter + ": " + Arrays.toString(parts) +
                                    ". Expected 7 items, but got " + parts.length
                    );
                }
                int tableNumber = Integer.parseInt(parts[0].split(":")[1].trim());
                int dishId = Integer.parseInt(parts[1].split(":")[1].trim());
                int quantityOrdered = Integer.parseInt(parts[2].split(":")[1].trim());
                LocalDateTime orderedTime = LocalDateTime.parse(parts[3]);
                LocalDateTime fulfilmentTime = "null".equals(parts[4]) ? null : LocalDateTime.parse(parts[4]);
                boolean isPaid = Boolean.parseBoolean(parts[5]);
                boolean isFulfilled = fulfilmentTime != null && fulfilmentTime.isBefore(LocalDateTime.now());

                System.out.println("Order for table number: " + tableNumber);
                System.out.println("Dish ID: " + dishId);
                System.out.println("Quantity Ordered: " + quantityOrdered);
                System.out.println("Ordered Time: " + orderedTime);
                System.out.println("Fulfilment Time: " + (fulfilmentTime != null ? fulfilmentTime : "Not fulfilled yet"));
                System.out.println("Is Paid: " + isPaid);
                System.out.println("Is Fulfilled: " + (isFulfilled ? "Yes" : "No"));
                System.out.println("-------------------------------------------");
            }
        } catch (FileNotFoundException e) {
            handleException(fileName, "File not found!", e);
        } catch (IOException e) {
            handleException(fileName, "Error reading file:", e);
        }
    }
}
