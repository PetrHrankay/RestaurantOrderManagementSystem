package cz.engeto.ja2024;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class FileManager {

    private List<Dish> dishes = Collections.synchronizedList(new ArrayList<>());

    public synchronized void saveDishToFile(String fileName) throws FileManagerException {
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
            throw new FileManagerException("File " + fileName + " not found!\n"
                    + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new FileManagerException("Output error while writing to the file: " + fileName
                    + ":\n" + e.getLocalizedMessage());
        }
    }

    public void saveOrderToFile(String fileName) throws FileManagerException {
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
            throw new FileManagerException("File " + fileName + " not found!\n"
                    + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new FileManagerException("Output error while writing to the file: " + fileName
                    + ":\n" + e.getLocalizedMessage());
        }

    }


    private boolean isCookBookFileEmpty(String fileName) throws FileManagerException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.readLine() == null;
        } catch (FileNotFoundException e) {
            throw new FileManagerException("File " + fileName + " not found!\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new FileManagerException("Output error while reading the file: " + fileName + ":\n" + e.getLocalizedMessage());
        }
    }

    public List<Dish> loadDishFromFile(String fileName) throws FileManagerException {
        List<Dish> loadedDishes = new ArrayList<>();
        int lineCounter = 0;

        if (isCookBookFileEmpty(fileName)) {
            System.out.println("No dishes added yet.");
            return loadedDishes;
        }

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String[] parts = scanner.nextLine().trim().split(";\\s*");

                if (parts.length != 5) {
                    throw new DishException("Incorrect number of items on line " + lineCounter + ": " + Arrays.toString(parts));
                }

                loadedDishes.add(new Dish(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        new BigDecimal(parts[2]),
                        Integer.parseInt(parts[3]),
                        parts[4]
                ));
            }
        } catch (FileNotFoundException e) {
            throw new FileManagerException("File " + fileName + " not found!");
        } catch (DishException e) {
            throw new FileManagerException("Error on line " + lineCounter + ": " + e.getMessage());
        }
        return loadedDishes;
    }

    public boolean isOrdersFileEmpty(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.readLine() == null;
        }
    }

    public List<Order> loadOrdersFromFile(String fileName) throws IOException, FileManagerException {
        List<Order> loadedOrders = new ArrayList<>();
        int lineCounter = 0;

        if (isOrdersFileEmpty(fileName)) {
            System.out.println("No orders added yet.");
            return loadedOrders;
        }

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine().trim();
                String[] parts = line.split(";\\s*");

                if (parts.length != 7) {
                    throw new OrderException("Incorrect number of items on line number: " + lineCounter + ": " + line + "!");
                }

                int tableNumber = Integer.parseInt(parts[0].split(":")[1].trim());
                int dishId = Integer.parseInt(parts[1].split(":")[1].trim());
                int quantityOrdered = Integer.parseInt(parts[2].split(":")[1].trim());
                LocalDateTime orderedTime = LocalDateTime.parse(parts[3]);
                LocalDateTime fulfilmentTime = "null".equals(parts[4]) ? null : LocalDateTime.parse(parts[4]);
                boolean isPaid = Boolean.parseBoolean(parts[5]);

                Order order = new Order(tableNumber, dishId, quantityOrdered, orderedTime, fulfilmentTime, isPaid);
                loadedOrders.add(order);
            }
        } catch (FileNotFoundException | OrderException e) {
            throw new FileManagerException("File " + fileName + " not found!\n" + e.getLocalizedMessage());
        }
        return loadedOrders;
    }
}


