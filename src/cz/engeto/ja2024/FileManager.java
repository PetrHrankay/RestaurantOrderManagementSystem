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
                writer.println(
                        "Table: " + item.getTableNumber() + delimiter
                                + "Dish number: " + item.getDishId() + delimiter
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

    public List<Dish> loadDishFromFile(String fileName) throws FileManagerException {
        List<Dish> loadedDishes = new ArrayList<>();
        int lineCounter = 0;

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine().trim();
                String[] parts = line.split(";\\s*");

                if (parts.length != 5) {
                    throw new DishException("Incorrect number of items on line number: " + lineCounter + ": " + line + "!");
                }

                int dishId = Integer.parseInt(parts[0]);
                String title = parts[1];
                BigDecimal price = new BigDecimal(parts[2]);
                int preparationTimeInMinutes = Integer.parseInt(parts[3]);
                String image = parts[4];

                Dish dish = new Dish(dishId, title, price, preparationTimeInMinutes, image);
                loadedDishes.add(dish);
            }
        } catch (FileNotFoundException e) {
            throw new FileManagerException("File " + fileName + " not found!\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new FileManagerException("Output error while reading the file: " + fileName + ":\n" + e.getLocalizedMessage());
        } catch (DishException e) {
            throw new FileManagerException("Error processing line " + lineCounter + ": " + e.getLocalizedMessage());
        }
        return loadedDishes;
    }

    public List<Order> loadedOrderFromFile(String fileName) throws FileNotFoundException {
        List<Order> loadedOrders = new ArrayList<>();
        int lineCounter = 0;

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
        } catch (OrderException e) {
            throw new RuntimeException(e);
        }
        return loadedOrders;
    }
}


