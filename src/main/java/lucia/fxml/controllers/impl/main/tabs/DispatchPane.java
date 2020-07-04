package main.java.lucia.fxml.controllers.impl.main.tabs;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;

import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 * The controller which controls all dispatch operations
 *
 * @author Brett Downey
 */
public class DispatchPane implements Controller {


    @FXML
    private Pane dispatchPane;

    @FXML
    private JFXButton forward8;

    @FXML
    private JFXButton back8;

    @FXML
    private GridPane inProgressPickupGrid;

    @FXML
    private GridPane inProgressDeliveryGrid;

    @FXML
    private GridPane pickupGrid;

    @FXML
    private GridPane deliveryGrid;

    public Font pt20Font, pt25Font;


    @FXML
    public void initialize() {
        ControllerMap.addController(ControllerType.DISPATCH_PANE_CONTROLLER, this);
        Future<?> r = AsynchronousTaskService.scheduleRepeating(() -> {
            Platform.runLater(() -> load());
        }, 30000L);
    }

    @Override
    public ControllerType getType() {
        return ControllerType.DISPATCH_PANE_CONTROLLER;
    }

    public void load() {
//    Set<Order> driverOrders = OrderManager.INSTANCE.getPendingOrdersDelivery();
//    Set<Order> pickupOrders = OrderManager.INSTANCE.getPendingOrders();
//    Set<Order> ordersMade = OrderManager.INSTANCE.getPendingOrdersMade();
//    inProgressPickupGrid.getChildren().clear();
//    inProgressDeliveryGrid.getChildren().clear();
//    pickupGrid.getChildren().clear();
//    deliveryGrid.getChildren().clear();
//    if (!driverOrders.isEmpty()) {
//      int count = 0;
//      for (int x = 0; x < driverOrders.size(); x++) {
//        if (driverOrders.get(x).isPreOrder())
//          driverOrders.get(x).setPreOrderReadyForDispatch(false);
//        else driverOrders.get(x).setPreOrderReadyForDispatch(true);
//        isPreorderReady(driverOrders, x);
//        if ((!driverOrders.get(x).isMade()) && driverOrders.get(x).isPreOrderForDispatch()) {
//          JFXButton button;
//          if (driverOrders.get(x).isPreOrderPast()) {
//            button = new JFXButton("[PAST| #" + driverOrders.get(x).getOrderNumber()
//                    + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//            button.setId("[PAST| #" + driverOrders.get(x).getOrderNumber()
//                    + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//          } else if (driverOrders.get(x).isPreOrder()) {
//            button = new JFXButton("PO:[" + (60 - driverOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + driverOrders.get(x).getOrderNumber()
//                    + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//            button.setId("PO:[" + (60 - driverOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + driverOrders.get(x).getOrderNumber()
//                    + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//          } else {
//            button = new JFXButton("[" + driverOrders.get(x).timeSince() + " | #" + driverOrders.get(x).getOrderNumber()
//                    + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//            button.setId("[" + driverOrders.get(x).timeSince() + " | #" + driverOrders.get(x).getOrderNumber()
//                    + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//          }
//          button.setPrefSize(350, 50);
//          button.setMinSize(350, 50);
//          button.setMaxSize(350, 50);
//          button.setOnMouseClicked((EventHandler<Event>) this::finished);
//          if (driverOrders.get(x).isPreOrder()) {
//            long timePreorder = 60 - driverOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES);
//            if (timePreorder > 40 || driverOrders.get(x).isPreOrderPast())
//              button.setStyle("-fx-background-color: #8e0001; -fx-font-size: 20; -fx-text-alignment: left; -fx-text-fill: white");
//            else button.setStyle("-fx-font-size: 20; -fx-text-alignment: left");
//          } else {
//            if (driverOrders.get(x).timeSinceLongMinutes() > 40)
//              button.setStyle("-fx-background-color: #8e0001; -fx-font-size: 20; -fx-text-alignment: left; -fx-text-fill: white");
//            else button.setStyle("-fx-font-size: 20; -fx-text-alignment: left");
//          }
//          inProgressPickupGrid.add(button, 0, count);
//          count++;
//        }
//      }
//    }
//    if (!pickupOrders.isEmpty()) {
//      int count = 0;
//      for (int x = 0; x < pickupOrders.size(); x++) {
//        if (pickupOrders.get(x).isPreOrder())
//          pickupOrders.get(x).setPreOrderReadyForDispatch(false);
//        else pickupOrders.get(x).setPreOrderReadyForDispatch(true);
//        isPreorderReady(pickupOrders, x);
//        if ((!pickupOrders.get(x).isMade()) && (pickupOrders.get(x).isPreOrderForDispatch() || pickupOrders.get(x).isPreOrderPast())) {
//          JFXButton button;
//          if (pickupOrders.get(x).isPreOrderPast()) {
//            button = new JFXButton("[PAST| #" + pickupOrders.get(x).getOrderNumber()
//                    + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//            button.setId("[PAST| #" + pickupOrders.get(x).getOrderNumber()
//                    + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//          } else if (pickupOrders.get(x).isPreOrder()) {
//            button = new JFXButton("PO:[" + (60 - pickupOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + pickupOrders.get(x).getOrderNumber()
//                    + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//            button.setId("PO:[" + (60 - pickupOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + pickupOrders.get(x).getOrderNumber()
//                    + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//          } else {
//            button = new JFXButton("[" + pickupOrders.get(x).timeSince() + " | #" + pickupOrders.get(x).getOrderNumber()
//                    + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//            button.setId("[" + pickupOrders.get(x).timeSince() + " | #" + pickupOrders.get(x).getOrderNumber()
//                    + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//          }
//          button.setPrefSize(322, 50);
//          button.setMinSize(322, 50);
//          button.setMaxSize(322, 50);
//          button.setOnMouseClicked((EventHandler<Event>) this::finished);
//          if (pickupOrders.get(x).isPreOrder()) {
//            long timePreorder = 60 - pickupOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES);
//            if (timePreorder > 40 || pickupOrders.get(x).isPreOrderPast())
//              button.setStyle("-fx-background-color: #8e0001; -fx-font-size: 20; -fx-text-alignment: left; -fx-text-fill: white");
//            else button.setStyle("-fx-font-size: 20; -fx-text-alignment: left");
//          } else {
//            if (pickupOrders.get(x).timeSinceLongMinutes() > 30)
//              button.setStyle("-fx-background-color: #8e0001; -fx-font-size: 20; -fx-text-alignment: left; -fx-text-fill: white");
//            else button.setStyle("-fx-font-size: 20; -fx-text-alignment: left");
//          }
//          inProgressPickupGrid.add(button, 0, count);
//          count++;
//        }
//      }
//    }
//    if (!ordersMade.isEmpty()) {
//      int count = 0;
//      for (int x = 0; x < ordersMade.size(); x++) {
//        if (ordersMade.get(x).isDelivery()) {
//          if (ordersMade.get(x).isMade() && ordersMade.get(x).isPreOrderForDispatch()) {
//            Label label;
//            if (driverOrders.get(x).isPreOrderPast()) {
//              label = new Label("[PAST| #" + driverOrders.get(x).getOrderNumber()
//                      + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//              label.setId("[PAST| #" + driverOrders.get(x).getOrderNumber()
//                      + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//            } else if (driverOrders.get(x).isPreOrder()) {
//              label = new Label("PO:[" + (60 - driverOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + driverOrders.get(x).getOrderNumber()
//                      + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//              label.setId("PO:[" + (60 - driverOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + driverOrders.get(x).getOrderNumber()
//                      + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//            } else {
//              label = new Label("[" + driverOrders.get(x).timeSince() + " | #" + driverOrders.get(x).getOrderNumber()
//                      + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//              label.setId("[" + driverOrders.get(x).timeSince() + " | #" + driverOrders.get(x).getOrderNumber()
//                      + "] " + driverOrders.get(x).getCustomerDetails().getNameDelivery());
//            }
//            label.setPrefSize(430, 50);
//            label.setMinSize(430, 50);
//            label.setMaxSize(430, 50);
//            if (driverOrders.get(x).isPreOrder()) {
//              long timePreorder = 60 - driverOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES);
//              if (timePreorder > 40 || driverOrders.get(x).isPreOrderPast())
//                label.setStyle("-fx-background-color: #8e0001; -fx-font-size: 20; -fx-text-alignment: left; -fx-text-fill: white");
//              else label.setStyle("-fx-font-size: 20; -fx-text-alignment: left");
//            } else {
//              if (driverOrders.get(x).timeSinceLongMinutes() > 40)
//                label.setStyle("-fx-background-color: #8e0001; -fx-font-size: 20; -fx-text-alignment: left; -fx-text-fill: white");
//              else label.setStyle("-fx-font-size: 20; -fx-text-alignment: left");
//            }
//            deliveryGrid.add(label, 0, count);
//            count++;
//          }
//        } else {
//          if (ordersMade.get(x).isMade() && ordersMade.get(x).isPreOrderForDispatch()) {
//            Label label;
//            if (pickupOrders.get(x).isPreOrderPast()) {
//              label = new Label("[PAST| #" + pickupOrders.get(x).getOrderNumber()
//                      + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//              label.setId("[PAST| #" + pickupOrders.get(x).getOrderNumber()
//                      + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//            } else if (pickupOrders.get(x).isPreOrder()) {
//              label = new Label("PO:[" + (60 - pickupOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + pickupOrders.get(x).getOrderNumber()
//                      + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//              label.setId("PO:[" + (60 - pickupOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + pickupOrders.get(x).getOrderNumber()
//                      + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//            } else {
//              label = new Label("[" + pickupOrders.get(x).timeSince() + " | #" + pickupOrders.get(x).getOrderNumber()
//                      + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//              label.setId("[" + pickupOrders.get(x).timeSince() + " | #" + pickupOrders.get(x).getOrderNumber()
//                      + "] " + pickupOrders.get(x).getCustomerDetails().getNamePickup());
//            }
//            label.setPrefSize(433, 50);
//            label.setMinSize(433, 50);
//            label.setMaxSize(433, 50);
//            if (pickupOrders.get(x).isPreOrder()) {
//              long timePreorder = 60 - pickupOrders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES);
//              if (timePreorder > 40 || pickupOrders.get(x).isPreOrderPast())
//                label.setStyle("-fx-background-color: #8e0001; -fx-font-size: 20; -fx-text-alignment: left; -fx-text-fill: white");
//              else label.setStyle("-fx-font-size: 20; -fx-text-alignment: left");
//            } else {
//              if (pickupOrders.get(x).timeSinceLongMinutes() > 30)
//                label.setStyle("-fx-background-color: #8e0001; -fx-font-size: 20; -fx-text-alignment: left; -fx-text-fill: white");
//              else label.setStyle("-fx-font-size: 20; -fx-text-alignment: left");
//            }
//            pickupGrid.add(label, 0, count);
//            count++;
//          }
//        }
//      }
//    }
    }

    private void isPreorderReady(ArrayList<Order> orders, int x) {
//        if (orders.get(x).isPreOrder()) {
//            if (!orders.get(x).isFuturePreorder()) {
//                if (orders.get(x).getOrderTime().getThisTime().isBefore(ClientTime.getWinnipegTime()))
//                    orders.get(x).setPreOrderPast(true);
//                if (orders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES) <= 60) {
//                    orders.get(x).setPreOrderReadyForDispatch(true);
//                } else orders.get(x).setPreOrderReadyForDispatch(false);
//            }
//        }
    }

    public void finished(Event event) {
//    JFXButton compare = (JFXButton) event.getSource();
//    ArrayList<Order> orders = OrderManager.INSTANCE.getAllPendingOrders();
//
//    for (int x = 0; x < orders.size(); x++) {
//      if (orders.get(x).isDelivery()) {
//        if (orders.get(x).isPreOrderPast()) {
//          if (compare.getId().equals("[PAST| #" + orders.get(x).getOrderNumber() + "] " + orders.get(x).getCustomerDetails().getNameDelivery()))
//            OrderManager.INSTANCE.orderMade(orders.get(x));
//        } else if (orders.get(x).isPreOrder()) {
//          if (compare.getId().equals("PO:[" + (60 - orders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + orders.get(x).getOrderNumber()
//                  + "] " + orders.get(x).getCustomerDetails().getNameDelivery()))
//            OrderManager.INSTANCE.orderMade(orders.get(x));
//        } else {
//          if (compare.getId().equals("[" + orders.get(x).timeSince() + " | #" + orders.get(x).getOrderNumber()
//                  + "] " + orders.get(x).getCustomerDetails().getNameDelivery()))
//            OrderManager.INSTANCE.orderMade(orders.get(x));
//        }
//      } else {
//        if (orders.get(x).isPreOrderPast()) {
//          if (compare.getId().equals("[PAST| #" + orders.get(x).getOrderNumber() + "] " + orders.get(x).getCustomerDetails().getNamePickup()))
//            OrderManager.INSTANCE.orderMade(orders.get(x));
//        } else if (orders.get(x).isPreOrder()) {
//          if (compare.getId().equals("PO:[" + (60 - orders.get(x).getOrderTime().timeFromNow(ChronoUnit.MINUTES)) + " | #" + orders.get(x).getOrderNumber()
//                  + "] " + orders.get(x).getCustomerDetails().getNamePickup()))
//            OrderManager.INSTANCE.orderMade(orders.get(x));
//        } else {
//          if (compare.getId().equals("[" + orders.get(x).timeSince() + " | #" + orders.get(x).getOrderNumber()
//                  + "] " + orders.get(x).getCustomerDetails().getNamePickup()))
//            OrderManager.INSTANCE.orderMade(orders.get(x));
//        }
//      }
//    }
//    load();
    }
}