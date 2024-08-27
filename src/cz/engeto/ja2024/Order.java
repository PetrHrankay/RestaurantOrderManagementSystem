package cz.engeto.ja2024;

import java.time.LocalDateTime;

public class Order {

    private int tableNumber;
    private Dish orderedDishId;
    private int quantityOrdered;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean isFulfilled;
    private boolean isPaid;

    public Order(int tableNumber, Dish orderedDishId, int quantityOrdered, LocalDateTime orderedTime, LocalDateTime fulfilmentTime, boolean isFulfilled, boolean isPaid) {
        this.tableNumber = tableNumber;
        this.orderedDishId = orderedDishId;
        this.quantityOrdered = quantityOrdered;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
        this.isFulfilled = isFulfilled;
        this.isPaid = isPaid;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Dish getOrderedDishId() {
        return orderedDishId;
    }

    public void setOrderedDishId(Dish orderedDishId) {
        this.orderedDishId = orderedDishId;
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

    public boolean isFulfilled() {
        return isFulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        isFulfilled = fulfilled;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
