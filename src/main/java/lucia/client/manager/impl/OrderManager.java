package main.java.lucia.client.manager.impl;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.net.packet.event.PacketHandler;
import main.java.lucia.net.packet.event.PacketListenerManager;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

import java.util.ArrayList;
import java.util.List;

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
   * The list of orders this store currently has which
   * are pending
   */
  private ArrayList<Order> pendingOrders = new ArrayList<>();

  private ArrayList<Order> pendingOrdersDelivery = new ArrayList<>();

  public ArrayList<Order> getPendingOrdersMade() {
    return pendingOrdersMade;
  }

  private ArrayList<Order> pendingOrdersMade = new ArrayList<>();

  public ArrayList<Order> getAllPendingOrders() {
    return allPendingOrders;
  }

  private ArrayList<Order> allPendingOrders = new ArrayList<>();

  private ArrayList<Order> allPreorders = new ArrayList<>();

  /**
   * The list of orders this store currently has which are
   * not pending (therefore they are completed)
   */
  private Order[] allOrders = new Order[500];

  public OrderManager() {
    PacketListenerManager.get.registerListener(this);
  }


  /**
   * Registers an order and automatically sorts
   * it into it's appropriate list
   *
   * @param order The given order to register
   */
  public void registerOrder(Order order) {
    if(!order.isFuturePreorder()){
      System.out.println("not a future preorder");
      if(order.isPreOrder()){
        allPreorders.add(order);
        System.out.println("registered preorder: " + order.getOrderTime());
      }
      if(!order.isDelivery()) {
        System.out.println("updated pending orders.");
        pendingOrders.add(order);
        System.out.println("pending size: " + pendingOrders.size());
      } else
        pendingOrdersDelivery.add(order);
      allPendingOrders.add(order);
      allOrders[order.getOrderNumber()] = order;
    }
    System.out.println("registered order: " + order.getCustomerDetails().getPhoneNumber());
    submitOrder(order);
  }

  public Order[] getAllOrders(){
    return allOrders;
  }

  public Order getFromOrderNumber(int id){
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
    if(order.isDelivery())
      if(pendingOrdersDelivery.contains(order))
        pendingOrdersDelivery.remove(order);
    else
      if(pendingOrders.contains(order));
      pendingOrders.remove(order);
    if(allPendingOrders.contains(order))
      allPendingOrders.remove(order);
    if(pendingOrdersMade.contains(order))
      pendingOrdersMade.remove(order);
  }

  public void orderMade(Order order) {
    pendingOrdersMade.add(order);
    //order.setIsMade(true);
  }

  /**
   * Gets all of the pending orders which are
   * the orders which have not yet been assigned to
   * an employee
   *
   * @return The pending orders list
   */
  public ArrayList<Order> getPendingOrders() {
    return pendingOrders;
  }

  public ArrayList<Order> getPendingOrdersDelivery() {
    return pendingOrdersDelivery;
  }

  /**
   * The current order number. Should be reset daily
   * Used for assigning order numbers to orders
   */
  private static int currentOrderNumber = 0;

  /**
   * Gets the next available order number
   * @return the next order number
   */
  public static int nextOrderNumber(){
    currentOrderNumber++;
    return currentOrderNumber;
  }

  /**
   * ONLY CALLED BY SERVER
   * DO NOT USE THIS.
   * Adds the order to the current days orders,
   * taking orderNum from the order
   * @param o the Order to add to the current day's order
   */
  public void setOrder(Order o){
//    if(TestMaker.testingOrders){
//      TestMaker.testOrderIntercepted(o);
//    }else{
      if(o.isPreOrder()){
       setPreorder(o);
      }
      allOrders[o.getOrderNumber()] = o;
      if(o.isPendingOrder()){
        if(!o.isDelivery()) {
          System.out.println("adding to pending orders");
          searchAddOrderNum(o, pendingOrders);
        } else
          searchAddOrderNum(o, pendingOrdersDelivery);
        allPendingOrders.add(o);
      }
    //}
  }

  public void setPreorder(Order o){
    System.out.println("setting preorder: " + o.getCustomerDetails().getPhoneNumber() + ":" + o.getOrderTime());
    searchAddRowNumOrOther(o, allPreorders);
    //allPreorders.add(o);
  }

  /**
   * Removes all instances of O from list
   * @param o
   * @param list
   */
  private void searchAddOrderNum(Order o, List<Order> list){
    list.removeIf(order -> order.getOrderNumber()==o.getOrderNumber());
    list.add(o);
  }

  /**
   * Removes all instances of O from preorderlist
   * @param o
   * @param list
   */
  private void searchAddRowNumOrOther(Order o, List<Order> list){
//    list.removeIf(order -> (order.getRowNum()==o.getRowNum()) ||
//        (o.getCustomerDetails().getPhoneNumber().equals(order.getCustomerDetails().getPhoneNumber()) && o.getGrandTotalLong() == order.getGrandTotalLong()));
//    list.add(o);
  }

  public static int getCurrentOrderNumber(){
    return currentOrderNumber;
  }

  /**
   * Resets the order counter to 0
   */
  public static void resetOrderNumber(){
    currentOrderNumber = 0;
  }

  /**
   * Requests the days orders from the server.
   */
  public static void loadAllOrders(){
    OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(OpcodeConstants.DAY_ORDERS_LIST_OPCODE);
    out.setJsonRequest("hey");
    PacketSender.getCurrentPacketSender().sendMessage(out);
  }

  public static void loadAllPreOrders(){
    OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(OpcodeConstants.GET_PREORDERS_OPCODE);
    out.setJsonRequest("hey");
    PacketSender.getCurrentPacketSender().sendMessage(out);
  }

  public static void setCurrentOrderNumber(int set){
    currentOrderNumber = set;
  }

  /**
   * Submits an order to the server
   * @param o the {@link Order} to submit
   */
  public static void submitOrder(Order o){
    OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(OpcodeConstants.SUBMIT_ORDER_OPCODE);
    out.setJsonRequest(GsonTypeFactory.ORDER_GSON.toJson(o));
    PacketSender.getCurrentPacketSender().sendMessage(out);
  }

  public List<Order> getAllPreorders(){
    return this.allPreorders;
  }
}