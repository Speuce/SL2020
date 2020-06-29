package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.main.tabs.DispatchPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.PendingOrdersPane;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * A place to store all the non-fxml related methods and objects
 * related to the pickup-delivery pane
 *
 * @author Matt Kwiatkowski
 */
public abstract class PickupDelivery {

    //set to true to always print
    private static final boolean PRINT = false;

    public int orderCount = 0;

    /**
     * The currently modified order
     */
    private Order currentOrder;

    /**
     * True if this order is just an edit of an already placed order.
     * If this is true, it will not be added to order manager again, etc
     */
    private boolean isEdit = false;

    private String defaultSelected = "ToppingsSelected";

    public PickupDelivery(){
        //currentOrder = new Order(OrderType.UNSELECTED);
    }




    /**
     * Changes the current order to o
     * @param o the {@link Order} to now modify
     */
    public void changeOrder(Order o){
        currentOrder = o;
        isEdit = false;
        onOrderChange(o);
    }

    /**
     * Called when the order is changed.
     * Should call FXML updates and resets to children panes.
     */
    protected abstract void onOrderChange(Order newOrder);

    public void updateSubtotal() {
        long price = 0;
        if(!currentOrder.isEmpty()) {
            currentOrder.calculateFees();
            for (int x = 0; x < currentOrder.getItems().size(); x++) {
                price += currentOrder.getItems().get(x).getPrice();
            }
        }
        onSubtotalUpdate(price);
    }

    /**
     * Called when the subtotal tags should be updated
     * @param price
     */
    protected abstract void onSubtotalUpdate(long price);

    /**
     * Creates a new order and sets it as the current order
     */
    public void newOrder(){
        changeOrder(new Order(OrderType.UNSELECTED));
    }

    public void activateHoverGreen(MouseEvent event) {
        setOptionPanesVisible(false);
        JFXButton hovered = (JFXButton) event.getSource();
        if(!Style.isEnabled(hovered, "ToppingsSelectedGreen")) {
            hovered.getStyleClass().removeAll("ToppingsDefaultGreen");
            hovered.getStyleClass().add("ToppingsEnableHoverGreen");
        }
    }

      public void setExtraButtons(boolean bool, String type) {
    if (bool) {
      if (type.equals("EZ"))
        defaultSelected = "ToppingsSelectedEZ";
      else if (type.equals("X"))
        defaultSelected = "ToppingsSelectedX";
      else if (type.equals("XX"))
        defaultSelected = "ToppingsSelectedXX";
    } else defaultSelected = "ToppingsSelected";
  }

    // ------ "SELECTED" SERIES ------
    public void selectedGreen(Event event) {
        setOptionPanesVisible(false);
        JFXButton button = (JFXButton) event.getSource();
        if (Style.isEnabled(button, "ToppingsSelectedGreen"))
            button.getStyleClass().removeAll("ToppingsSelectedGreen");
        else button.getStyleClass().add("ToppingsSelectedGreen");
    }

    @FXML
    public void activateHover(MouseEvent event) {
        setOptionPanesVisible(false);
        JFXButton hovered = (JFXButton) event.getSource();
        if(!Style.isEnabled(hovered, "ToppingsSelected")) {
            if (Style.isEnabled(hovered, "ToppingsDefault")) {
                hovered.getStyleClass().removeAll("ToppingsDefault");
                hovered.getStyleClass().add("ToppingsEnableHover");
            }
        }
    }

    /**
     * Sets the options panes (popup panes) visible/not
     * @param visible
     */
    public abstract void setOptionPanesVisible(boolean visible);

    /**
     * Adds the item to order
     * @param i the {@link Item} to add
     */
    public void addToOrder(Item i){
        currentOrder.addItem(i);
        onItemAdd(i);
    }

    /**
     * Called when an item is added to the order
     * Should call FXML updates to show the reflected change
     */
    protected abstract void onItemAdd(Item i);

    /**
     * Gets the current order that is being built
     * @return the current {@link Order}
     */
    public Order getCurrentOrder(){
        if(currentOrder == null)
            return new Order(OrderType.UNSELECTED);
        return currentOrder;
    }

    /**
     * Checks to see if the order is empty or not
     */
    public boolean isOrderEmpty() {
        if(currentOrder == null)
            return true;
        return currentOrder.isEmpty();
    }

    /**
     * Adds the current order to order manager
     */
    private void addToOrderManager() {
        PendingOrdersPane pendingOrdersPane = (PendingOrdersPane) ControllerMap.getController(ControllerType.PENDING_ORDERS_PANE_CONTROLLER);
        pendingOrdersPane.addToOrderManager(currentOrder);
        DispatchPane dispatchPane = (DispatchPane) ControllerMap.getController(ControllerType.DISPATCH_PANE_CONTROLLER);
        dispatchPane.load();
    }

    /**
     * Sets the current order to a delivery order
     */
    public void setDelivery() {
        currentOrder.setDelivery(true);
        currentOrder.setOrderType(OrderType.DELIVERY);
    }

    /**
     * Sets the current order to a pickup order
     */
    public void setPickup() {
        currentOrder.setDelivery(false);
        currentOrder.setOrderType(OrderType.PICKUP);
    }

    /**
     * Finalized the order, adds it to order manager, etc
     */
    public void finalizeOrder(){
      currentOrder.calculateFees();
      if(!currentOrder.isFuturePreorder()){
        if(!isEdit){
          //System.out.println("customer details: " + GsonTypeFactory.BASIC_GSON.toJson(currentOrder.getCustomerDetails()));
          currentOrder.finalizeOrder();
          addToOrderManager();
        }else{
          //its an edit of an exsisting order.
          System.out.println("");
          currentOrder.finalizeOrderEdit();
          OrderManager.submitOrder(currentOrder);
          currentOrder.setReprint(true);
        }
      }else{
        System.out.println("submitted preorder to order manager");
        OrderManager.submitOrder(currentOrder);
      }


        //Print the order
        AsynchronousTaskService.process(() ->{
            if(PRINT){
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(currentOrder);
                try {
                    job.print();
                } catch (PrinterException e1){
                    //ignored
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        });
        onFinalize();
        currentOrder = new Order(OrderType.UNSELECTED);
    }

    /**
     * Called when the order should be finalized
     */
    public abstract void onFinalize();


    /**
     * Sets whether not this order is an edit
     * false if this is a brand new order
     */
    public void setEdit(boolean b){
        this.isEdit = b;
    }

    /**
     * Checks whether or not the current order is a delivery order
     * @return {@code true} if the order is a delivery order
     */
    public boolean isDelivery(){
        return currentOrder.isDelivery();
    }

    public String getDefaultSelected() {
        return defaultSelected;
    }

    public void setDefaultSelected(String defaultSelected) {
        this.defaultSelected = defaultSelected;
    }


}
