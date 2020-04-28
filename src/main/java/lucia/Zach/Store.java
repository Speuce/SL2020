package main.java.lucia.Zach;

import java.util.ArrayList;
import main.java.lucia.client.content.order.Order;

public class Store {

    // TODO Keep track of employees hours for this store and cashout information
    // TODO Keep track of all orders with their timestampts
    // TODO Create a timestamp class
    // TODO Keep track of all noteable eveents (discounts given, refunds, cancelled orders) etc.
    // TODO Keep track of all deliveries and their respective times e.g. if the estimated time was 45 minutes and it was late then FLAG aka flag all LATE ORDERS etc

    /**
     * The total orders associated with this
     * shop for the day.
     */
    private static int totalOrders;


    /**
     * The StoreInformation associated with this
     * Store.
     */
    private String store;

    /**
     * The variable which says if the store is currently opened.
     */
    private ArrayList<Order> orders;

    public Store(String store) {
        // TODO Change to StoreInformation
        this.store = store;
    }
}