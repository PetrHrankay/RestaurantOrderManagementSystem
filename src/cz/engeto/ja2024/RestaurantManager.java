package cz.engeto.ja2024;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RestaurantManager {

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void countPendingOrders() {
        int pendingOrders = 0;
        for (Order item : Order.getAllOrdersFromReceivedOrdersList()) {
            if (item.getFulfilmentTime() == null || item.getFulfilmentTime().isAfter(LocalDateTime.now())) {
                pendingOrders++;
            }
        }
        System.out.println("Total number of pending orders is: " + pendingOrders + ".");
    }

    public static void getOrdersSortedByCreationTime() {
        List<Order> orders = Order.getAllOrdersFromReceivedOrdersList();
        orders.sort(Comparator.comparing(Order::getOrderedTime));
        System.out.println("Orders sorted by creation time: ");

        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public static void averageOrderProcessingTimeInMinutes() {
        List<Order> orders = Order.getAllOrdersFromReceivedOrdersList();
        long totalProcessingTime = 0;
        int completedOrdersCount = 0;

        for (Order order : orders) {
            LocalDateTime start = order.getOrderedTime();
            LocalDateTime end = order.getFulfilmentTime();
            if (end == null) {
                continue;
            }
            long processingTime = Duration.between(start, end).toMinutes();
            totalProcessingTime += processingTime;
            completedOrdersCount++;
        }
        if (completedOrdersCount == 0) {
            return;
        }
        long averageTime = totalProcessingTime / completedOrdersCount;
        String result = "The average order processing time is: " + averageTime + " minutes.";
        System.out.println(result);
    }

    public static void getDishesOrderedToday() {
        StringBuilder todayDishes = new StringBuilder("Dishes ordered today: ");
        List<Order> orders = Order.getAllOrdersFromReceivedOrdersList();
        boolean anyDishAdded = false;

        for (Order order : orders) {
            if (order.getOrderedTime().toLocalDate().equals(LocalDate.now())) {
                String dishTitle = order.getDish().getTitle();

                if (!todayDishes.toString().contains(dishTitle)) {
                    if (anyDishAdded) {
                        todayDishes.append(", ");
                    }
                    todayDishes.append(dishTitle);
                    anyDishAdded = true;
                }
            }
        }
        System.out.println(todayDishes);
    }

    public static String formatTime(LocalDateTime time) {
        return time != null ? time.format(timeFormatter) : "Pending";
    }

    public static void getAllOrdersForTable(int tableNumber) {
        List<Order> orders = Order.getAllOrdersFromReceivedOrdersList();
        List<Order> ordersForTable = new ArrayList<>();

        for (Order order : orders) {
            if (order.getTableNumber() == tableNumber) {
                ordersForTable.add(order);
            }
        }
        StringBuilder formattedOrders = new StringBuilder();
        formattedOrders.append("** Objednávky pro stůl č. ").append(String.format("%2d", tableNumber)).append(" **\n");
        formattedOrders.append("****\n");
        int orderCount = 1;
        for (Order order : ordersForTable) {
            String orderedTimeFormatted = formatTime(order.getOrderedTime());
            String fulfilmentTimeFormatted = formatTime(order.getFulfilmentTime());
            String formattedOrder = orderCount + ". " + order.getDish().getTitle()
                    + " " + order.getQuantityOrdered() + "x (" +
                    (order.getDish().getPrice().multiply(BigDecimal.valueOf(order.getQuantityOrdered())))
                    + " Kč):\t" + orderedTimeFormatted + "-" + fulfilmentTimeFormatted + "\t" +
                    (order.isPaid() ? " zaplaceno" : "");
            formattedOrders.append(formattedOrder).append("\n");
            orderCount++;
        }
        formattedOrders.append("******");

        System.out.println(formattedOrders);
    }
}
