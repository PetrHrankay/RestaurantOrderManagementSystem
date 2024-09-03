package cz.engeto.ja2024;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int tableNumber;
    private int dishId;
    private int quantityOrdered;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean isPaid;

    private static List<Order> receivedOrdersList = new ArrayList<>();
    private static FileManager fileManager = new FileManager();

    public Order(int tableNumber, int dishId, int quantityOrdered, LocalDateTime orderedTime, LocalDateTime fulfilmentTime, boolean isPaid) {
        this.tableNumber = tableNumber;
        this.dishId = dishId;
        this.quantityOrdered = quantityOrdered;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
        this.isPaid = isPaid;
        findDishById(dishId);
        receivedOrdersList.add(this);
//        saveReceivedOrdersToFile();
    }
    public Order(int tableNumber, int dishId, int quantityOrdered, boolean isPaid) {
        this.tableNumber = tableNumber;
        this.dishId = dishId;
        this.quantityOrdered = quantityOrdered;
        this.isPaid = isPaid;
        this.orderedTime = LocalDateTime.now();
        findDishById(dishId);
        receivedOrdersList.add(this);
        saveReceivedOrdersToFile();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalDateTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String isFulfilledOrNot() {
        String message;
        if (fulfilmentTime != null) {
            message = "The order was fulfilled at: " + fulfilmentTime;
        } else {
            message = "The order has not been fulfilled yet";
        }
        return message;
    }

    public void setOrderedTimeToNow() {
        this.orderedTime = LocalDateTime.now();
    }

    public void setFulfilmentTimeToNow() throws FileManagerException {
        this.fulfilmentTime = LocalDateTime.now();
        fileManager.saveOrderToFile(Settings.getOrdersFilename());
    }

    private Dish findDishById(int dishId) {
        for (Dish dish : Dish.getAllDishesFromCookBook()) {
            if (dish.getDishId() == dishId) {
                return dish;
            }
        }
        throw new IllegalArgumentException("Dish with ID " + dishId + " not found.");
    }

    public Dish getOrderedDish() {
        return findDishById(dishId);
    }

    public static List<Order> getAllOrdersFromReceivedOrdersList() {
        return receivedOrdersList;
    }

    private void saveReceivedOrdersToFile() {
        try {
            fileManager.saveOrderToFile(Settings.getOrdersFilename());
        } catch (FileManagerException e) {
            throw new RuntimeException(e);  // Ty vyjimky si jeste dores

        }
    }

    public static void displayLoadedOrders() throws FileManagerException, FileNotFoundException {
        List<Order> orders = fileManager.loadedOrderFromFile(Settings.getOrdersFilename());
        for (Order item : orders) {
            System.out.println(item);
        }
    }




    @Override
    public String toString() {
        return "Order{" +
                "tableNumber=" + tableNumber +
                ", orderedDishId=" + dishId +
                ", quantityOrdered=" + quantityOrdered +
                ", orderedTime=" + orderedTime +
                ", fulfilmentTime=" + fulfilmentTime +
                ", isPaid=" + isPaid +
                '}';
    }
}
