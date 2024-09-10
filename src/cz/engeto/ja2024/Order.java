package cz.engeto.ja2024;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private static int orderCounter = 0;

    private int orderId;
    private int tableNumber;
    private Dish dish;
    private int quantityOrdered;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean isPaid;

    private static List<Order> receivedOrdersList = new ArrayList<>();

    public Order(int tableNumber, int dishId, int quantityOrdered, LocalDateTime orderedTime,
                 LocalDateTime fulfilmentTime, boolean isPaid) throws OrderException {
        this(tableNumber, dishId, quantityOrdered, isPaid);
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
    }

    public Order(int tableNumber, int dishId, int quantityOrdered, boolean isPaid) throws OrderException {
        this.orderId = ++orderCounter;
        this.tableNumber = tableNumber;
        this.dish = findDishById(dishId);
        this.quantityOrdered = quantityOrdered;
        this.isPaid = isPaid;
        this.orderedTime = LocalDateTime.now();
        addToReceivedOrdersList();
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
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
        if (fulfilmentTime != null && fulfilmentTime.isBefore(LocalDateTime.now())) {
            return "The order was fulfilled :)";
        } else {
            return "The order has not been fulfilled yet :(";
        }
    }

    public void setOrderedTimeToNow() {
        this.orderedTime = LocalDateTime.now();
    }

    public void setFulfilmentTimeToNow() throws OrderException {
        try {
            this.fulfilmentTime = LocalDateTime.now();
            FileManager.saveOrdersToFile(Settings.getOrdersFilename());
        } catch (FileManagerException e) {
            throw new OrderException("Failed to save orders to file.", e);
        }
    }

    private void addToReceivedOrdersList() {
        receivedOrdersList.add(this);
    }

    private Dish findDishById(int dishId)throws OrderException{
        for (Dish searchedDish : Dish.getAllDishesFromCookBook()) {
            if (searchedDish.getDishId() == dishId) {
                return searchedDish;
            }
        }
        throw new OrderException("Dish with ID " + dishId + " not found.");
    }

    public static List<Order> getAllOrdersFromReceivedOrdersList() {
        return receivedOrdersList;
    }

    public static void printTotalSpendingForTableNumber(int tableNumber) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Order order : Order.getAllOrdersFromReceivedOrdersList()) {
            if (order.getTableNumber() == tableNumber) {
                BigDecimal orderPrice = order.getDish().getPrice().multiply(BigDecimal.valueOf(order.getQuantityOrdered()));
                totalPrice = totalPrice.add(orderPrice);
            }
        }
        System.out.println("Total price for table number " + tableNumber + " is " + totalPrice + " CZK.");
    }

    @Override
    public String toString() {
        return "orderId: " + orderId +
                ", tableNumber: " + tableNumber +
                 ", " + dish.getTitle() +
                ", quantityOrdered: " + quantityOrdered +
                ", orderedTime=" + orderedTime +
                ", fulfilmentTime=" + fulfilmentTime +
                ", isPaid: " + isPaid +
                '}';
    }
}
