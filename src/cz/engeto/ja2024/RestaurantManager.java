package cz.engeto.ja2024;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class RestaurantManager {

    public static int countPendingOrders() {
        int pendingOrders = 0;
        for (Order item : Order.getAllOrdersFromReceivedOrdersList()) {
            if (item.getFulfilmentTime() == null) {
                pendingOrders++;
            }
        }
        return pendingOrders;
    }

    public static List<Order> getOrdersSortedByCreationTime() {
        List<Order> orders = Order.getAllOrdersFromReceivedOrdersList();
        orders.sort(Comparator.comparing(Order::getOrderedTime));
        return orders;
    }

    public static String averageOrderProcessingTimeInMinutes() {
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
            return "No completed orders available";
        }
        long averageTime = totalProcessingTime / completedOrdersCount;
        String result = "The average order processing time is: " + averageTime + " minutes.";
        System.out.println(result);
        return result;
        }
}


