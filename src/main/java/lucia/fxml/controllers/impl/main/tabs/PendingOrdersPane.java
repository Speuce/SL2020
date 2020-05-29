package main.java.lucia.fxml.controllers.impl.main.tabs;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.Utils.GridHighlighter;
import main.java.lucia.fxml.controllers.impl.main.tabs.payment.PaymentPaneController;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 * The controller which handles the pending orders pane
 *
 * @author Matt Kwiatkowski
 * @author Zachery Unrau
 */
public class PendingOrdersPane implements Controller {

    @FXML
    private GridPane orderGridPane;

    @FXML
    private Pane paymentPane;

    @FXML
    private PaymentPaneController paymentPaneController;

    /**
     * Utlity to highlight and select rows
     */
    private GridHighlighter gridHighlighter;

    NumberFormat formatter = new DecimalFormat("#0.00");
    public OrderManager orderManager = OrderManager.INSTANCE;
    ArrayList<JFXButton> checkList = new ArrayList<>();

    /**
     * The highlighter for the view orders grid
     */
    private GridHighlighter highlighter;

    /**
     * a variable to store the font that will be used
     */
    public Font pt20Font, pt25Font;


    @FXML
    public void initialize() {
        gridHighlighter = new GridHighlighter(orderGridPane, "#D3D3D3");
        gridHighlighter.addSelectListener(r -> paymentPane(r));
        pt20Font = new Font("Calibri", 20);
        pt25Font = new Font("Calibri", 25);
        paymentPaneController.setParent(this);
        ControllerMap.addController(ControllerType.PENDING_ORDERS_PANE_CONTROLLER, this);
        Future<?> r = AsynchronousTaskService.scheduleRepeating(() -> {
            Platform.runLater(() -> refresh());
        }, 30000L);
    }

    /**
     * opens/closes the paymentpane
     *
     * @param b
     */
    private void paymentPane(boolean b) {
        if (b) {
            paymentPaneController.open();
            paymentPane.setVisible(true);
        } else {
            paymentPane.setVisible(false);
        }
    }

    @Override
    public ControllerType getType() {
        return ControllerType.PENDING_ORDERS_PANE_CONTROLLER;
    }

    public void addToOrderManager(Order order) {
//    EnterNumberPane controller = (EnterNumberPane) ControllerMap
//            .getController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER);
        //order.setCustomerDetails(controller.getCustomerDetails(), false);
        orderManager.registerOrder(order);
        System.out.println("phone: " + order.getCustomerDetails().getPhoneNumber());
        System.out.println("pending size: " + OrderManager.INSTANCE.getPendingOrders().size());
        for (Order o : OrderManager.INSTANCE.getPendingOrders()) {
            System.out.println("order: " + o.getOrderNumber() + "::" + o.getCustomerDetails().getPhoneNumber());
        }
        reset();
        update();
    }


    /**
     * Changes the visibiity of the payment pane
     *
     * @param set
     */
    public void setPaymentPaneVisible(boolean set) {
        paymentPane.setVisible(set);
    }

    /**
     * Marks the given order as complete, when it is signed out under an employee.
     * Moves the order to view orders pane
     *
     * @param o
     */
    public void completeOrder(Order o) {
        orderManager.completeOrder(o);
        reset();
        update();
        DispatchPane controller = (DispatchPane) ControllerMap.getController(ControllerType.DISPATCH_PANE_CONTROLLER);
        controller.load();
        o.setPendingOrder(false);
        //TODO move to view orders pane
    }


    public void update() {
        setCount();
        setName();
        setTime();
        setSince();
        setTotal();
    }

    public void open() {
        System.out.println("----------- Opened pending Orders ------------");
        reset();
        update();
    }

    public void refresh() {
        orderGridPane.getChildren().removeIf(n -> GridPane.getColumnIndex(n) == 3);
        setSince();
    }

    public void setCount() {
        int x = 0;
        for (Order o: orderManager.getPreordersToday()) {
            Label label = new Label(" " + o.getOrderNumber());
            label.setPrefSize(105, 50);
            label.setFont(pt20Font);
            orderGridPane.add(label, 0, x);
            registerNode(label);
            x++;
        }
    }

    public void setName() {
        int x = 0;
        for (Order o: orderManager.getPendingOrders()) {
            CustomerDetails c = o.getCustomerDetails();
            String phone = c.getPhoneNumber();
            String name = c.getName();
            Label label = new Label(" [" + phone + "] " + name);
            label.setPrefSize(590, 50);
            label.setFont(pt20Font);
            orderGridPane.add(label, 1, x);
            registerNode(label);
            x++;
        }
    }

    public void setTime() {
        int x = 0;
        for (Order o: orderManager.getPendingOrders()) {
            Label time = new Label(" " + o.getFormattedSet24Time());
            time.setPrefSize(175, 50);
            time.setFont(pt20Font);
            orderGridPane.add(time, 2, x);
            registerNode(time);
            x++;
        }
    }

    public void setSince() {
        int x = 0;
        for (Order o: orderManager.getPendingOrders()) {
            Label since = new Label(" " + o.timeSince());
            since.setPrefSize(165, 50);
            since.setFont(pt20Font);
            registerNode(since);
            if (x == gridHighlighter.getHighLightedRow()) {
                since.setStyle("-fx-background-color:#f0e381;-fx-border-color: black;-fx-border-width: 0 0 1 1;");
            }
            orderGridPane.add(since, 3, x);
            x++;
        }
    }

    public void setTotal() {
        int x = 0;
        for (Order o: orderManager.getPendingOrders()) {
            long total = o.getGrandTotal();
            Label label = new Label(NumberFormat.getCurrencyInstance().format(total / 100.0));
            label.setPrefSize(265, 50);
            label.setFont(pt20Font);
            orderGridPane.add(label, 4, x);
            registerNode(label);
            x++;
        }
    }

    public Order getHighlightedOrder() {
        return orderManager.getPendingOrders().getOrder(gridHighlighter.getHighLightedRow());
    }

    public void reset() {
        paymentPane.setVisible(false);
        gridHighlighter.reset();
        orderGridPane.getChildren().clear();
    }


    private void registerNode(Node n) {
        gridHighlighter.registerNode(n);
    }

    private boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }


}