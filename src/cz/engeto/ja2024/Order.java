package cz.engeto.ja2024;

import java.time.LocalDateTime;

public class Order {

    private int tableNumber;
    private int dishId;
    private int quantityOrdered;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean isPaid;

    public Order(int tableNumber, int dishId, int quantityOrdered, LocalDateTime orderedTime, LocalDateTime fulfilmentTime, boolean isPaid) {
        this.tableNumber = tableNumber;
        this.dishId = dishId;
        this.quantityOrdered = quantityOrdered;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
        this.isPaid = isPaid;
        findDishById(dishId);
    }
    public Order(int tableNumber, int dishId, int quantityOrdered, boolean isPaid) {
        this.tableNumber = tableNumber;
        this.dishId = dishId;
        this.quantityOrdered = quantityOrdered;
        this.isPaid = isPaid;
        this.orderedTime = LocalDateTime.now();
        findDishById(dishId);
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

    public Dish getOrderedDish() {
        return findDishById(dishId);
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

    public void setFulfilmentTimeToNow() {
        this.fulfilmentTime = LocalDateTime.now();
    }

    private Dish findDishById(int dishId) {
        for (Dish dish : Dish.getAllDishesFromCookBook()) {
            if (dish.getDishId() == dishId) {
                return dish;
            }
        }
        throw new IllegalArgumentException("Dish with ID " + dishId + " not found.");
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
