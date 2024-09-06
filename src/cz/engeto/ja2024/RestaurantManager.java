package cz.engeto.ja2024;

import java.util.ArrayList;
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
}
