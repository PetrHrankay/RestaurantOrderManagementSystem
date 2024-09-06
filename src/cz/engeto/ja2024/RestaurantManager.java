package cz.engeto.ja2024;

import java.util.ArrayList;
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
}
