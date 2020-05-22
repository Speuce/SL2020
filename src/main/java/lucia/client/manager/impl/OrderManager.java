package main.java.lucia.client.manager.impl;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.structures.OrderTable;
import main.java.lucia.client.manager.TimeManager;
import main.java.lucia.client.protocol.packet.in.order.PacketInSetOrder;
import main.java.lucia.client.protocol.packet.in.order.PacketInSetPreOrder;
import main.java.lucia.client.protocol.packet.outgoing.order.PacketOutDayOrdersList;
import main.java.lucia.client.protocol.packet.outgoing.order.PacketOutGetPreorders;
import main.java.lucia.client.protocol.packet.outgoing.order.PacketOutSubmitOrder;
import main.java.lucia.net.packet.event.ListenerPriority;
import main.java.lucia.net.packet.event.PacketEventHandler;
import main.java.lucia.net.packet.event.PacketHandler;
import main.java.lucia.net.packet.event.PacketListenerManager;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * The order manager, which keeps track of all
 * orders this client is currently maintaining.
 * This class will receive orders from the server
 * and orders that are directly places within the client.
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 * @author Zachery Unrau
 */
public class OrderManager implements PacketHandler {

    /**
     * The instance of this singleton class
     */
    public static final OrderManager INSTANCE = new OrderManager();


    /**
     * The collection of orders this store currently has which
     * are pending (and are pickups)
     */
    private OrderTable pendingOrders = new OrderTable(50);

    /**
     * The collection of pending orders for delivery (not yet signed out by a driver)
     */
    private TreeSet<Order> pendingOrdersDelivery;

    /**
     * The set of ALL preorders. Todays preorders included.
     */
    private TreeSet<Order> allPreorders;

    /**
     * The preorders for today.
     */
    private OrderTable preordersToday = new OrderTable(50);

    /**
     * The list of orders this store currently has which are
     * not pending (therefore they are completed)
     */
    private Order[] allOrders = new Order[500];

    /**
     * The highest order num for today.
     */
    private static int numOrders = 0;

    public OrderManager() {
        PacketListenerManager.get.registerListener(this);
        pendingOrdersDelivery = new TreeSet<>(Comparator.comparing(o -> o.getOrderTime().toLocalDate()));
        allPreorders = new TreeSet<>(Comparator.comparing(o -> o.getOrderTime().toLocalDate()));
    }

    @PacketEventHandler(priority = ListenerPriority.HIGH)
    public void onOrderReceive(PacketInSetOrder pack) {
        Order order = pack.getOrder();
        allOrders[order.getOrderNumber()] = order;

    }

    @PacketEventHandler(priority = ListenerPriority.HIGH)
    public void onPreOrderReceive(PacketInSetPreOrder preorder){
        allPreorders.add(preorder.getOrder());
        sortPreorder(preorder.getOrder());
    }

    /**
     * Automatically re-sorts the order into the appropriate list(s)
     * @param o the order to sort.
     */
    public void sortOrder(Order o){
        numOrders = Math.max(o.getOrderNumber(), numOrders);
        if(o.isPendingOrder()){
            if(o.isDelivery()){
                pendingOrdersDelivery.add(o);
                pendingOrders.remove(o);
            }else{
                pendingOrdersDelivery.remove(o);
                pendingOrders.add(o);
            }
        }else{
            pendingOrdersDelivery.remove(o);
            pendingOrders.remove(o);
        }
    }

    /**
     * Automatically re-sorts the order into the appropriate preorder lists.
     * @param o the order to sort.
     */
    public void sortPreorder(Order o){
        assert(o.isPreOrder());
        allPreorders.add(o);
        if(o.getOrderTime().toLocalDate().isBefore(TimeManager.resetTime)){
            preordersToday.add(o);
            //TODO if supposed to add to dispatch, add
            sortOrder(o);
        }else{
            //TODO see issue #24
        }
    }


    /**
     * Registers an order and automatically sorts
     * it into it's appropriate list
     *
     * @param order The given order to register
     *
     *
     * @deprecated use {@link #submitOrder(Order)}
     */
    @Deprecated
    public void registerOrder(Order order) {
        if (order.getOrderNumber() > 0) {
            throw new IllegalArgumentException("Got an order with an order number already assigned!");
        }
        submitOrder(order);
//        if (!order.isFuturePreorder()) {
//      System.out.println("not a future preorder");
//      if(order.isPreOrder()){
//        allPreorders.add(order);
//        System.out.println("registered preorder: " + order.getOrderTime());
//      }
//      if(!order.isDelivery()) {
//        System.out.println("updated pending orders.");
//        pendingOrders.add(order);
//        System.out.println("pending size: " + pendingOrders.size());
//      } else
//        pendingOrdersDelivery.add(order);
//      allPendingOrders.add(order);
//      allOrders[order.getOrderNumber()] = order;
//            order.setOrderNumber(-1);
//        } else {
//            order.setOrderNumber(0);
//        }
//        System.out.println("registered order: " + order.getCustomerDetails().getPhoneNumber());
//        submitOrder(order);
    }

    /**
     * Get an order, from its order number.
     * @param id the id of the order to find
     * @return the Order, if found, null otherwise
     */
    public Order getFromOrderNumber(int id) {
        assert(id < allOrders.length);
        return allOrders[id];
    }

    /**
     * Completes an order, which will remove the given
     * order from the pending orders list and add it
     * to the non pending orders list.
     * Note that if the order doesn't
     * exist within the pending orders list, then it is
     * not added into the non pending orders list.
     *
     * @param order The order to switch lists
     */
    public void completeOrder(Order order) {
        order.setCompleted(true);
        submitOrder(order);
    }

    /**
     * Sets the order to 'made' and broadcasts the changes.
     * @param order the order to set to made.
     */
    public void orderMade(Order order) {
        order.setMade(true);
        submitOrder(order);
        //order.setIsMade(true);
    }

    /**
     * Gets all of the pending orders which are
     * the orders which have not yet been assigned to
     * an employee
     *
     * @return The pending orders orderTable
     */
    public OrderTable getPendingOrders() {
        return pendingOrders;
    }

//    /**
//     * ONLY CALLED BY SERVER
//     * DO NOT USE THIS.
//     * Adds the order to the current days orders,
//     * taking orderNum from the order
//     *
//     * @param o the Order to add to the current day's order
//     */
//    public void setOrder(Order o) {
////    if(TestMaker.testingOrders){
////      TestMaker.testOrderIntercepted(o);
////    }else{
//        if (o.isPreOrder()) {
//            setPreorder(o);
//        }
//        allOrders[o.getOrderNumber()] = o;
//        if (o.isPendingOrder()) {
//            if (!o.isDelivery()) {
//                System.out.println("adding to pending orders");
//                searchAddOrderNum(o, pendingOrders);
//            } else
//                searchAddOrderNum(o, pendingOrdersDelivery);
//            allPendingOrders.add(o);
//        }
//        //}
//    }

    /**
     * Gets an APPROXIMATE number of orders.
     * Use this for drawing grids and such of orders
     */
    public static int getApproxNumOrders() {
        return numOrders;
    }

    /**
     * Requests the days orders from the server.
     */
    public static void loadAllOrders() {
        PacketSender.getCurrentPacketSender().sendMessage(new PacketOutDayOrdersList());
    }

    /**
     * Requests the preorders from the server.
     */
    public static void loadAllPreOrders() {
        PacketSender.getCurrentPacketSender().sendMessage(new PacketOutGetPreorders());
    }

    /**
     * Get a set of all preorders
     */
    public Set<Order> getAllPreorders() {
        return allPreorders;
    }

    /**
     * Get a table of all orders from today that are preorders
     */
    public OrderTable getPreordersToday() {
        return preordersToday;
    }

    /**
     * Get the set of orders that are pending for delivery
     */
    public TreeSet<Order> getPendingOrdersDelivery() {
        return pendingOrdersDelivery;
    }

    /**
     * @return an array of all of the orders.
     */
    public Order[] getAllOrders() {
        return allOrders;
    }

    /**
     * Submits an order to the server
     *
     * @param o the {@link Order} to submit
     */
    public static void submitOrder(Order o) {
        PacketSender.getCurrentPacketSender().sendMessage(new PacketOutSubmitOrder(o));
    }



}