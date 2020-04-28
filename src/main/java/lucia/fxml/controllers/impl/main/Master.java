package main.java.lucia.fxml.controllers.impl.main;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.lucia.fxml.InterfaceBuilder;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;
import main.java.lucia.fxml.controllers.impl.main.tabs.PendingOrdersPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.ViewOrdersPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.ViewPreordersPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
//import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeeLoginPane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * The controller for the main application
 *
 * @author Zachery Unrau
 * @author Brett Downey
 * @author Matthew Kwiatkowski
 */
public class Master implements Initializable, Controller {

    /**
     * The {@link AnchorPane} associated to
     * automatically generate the HashMap.
     */
    @FXML
    private AnchorPane buttonMap;

    /**
     * The {@link AnchorPane} associated to
     * automatically generate the HashMap.
     */
    @FXML
    private AnchorPane paneMap;

    /**
     * The {@link Pane}s associated with certain button
     * clicks.
     * // TODO either delete or use for the backwards forwards key
     */
    @FXML
    private Pane pendingOrdersPane;
    @FXML
    private Pane manageTablesPane;
    @FXML
    private Pane driverFramePane;
    @FXML
    private Pane backOfficePane;
    @FXML
    private Pane serverReportPane;
    @FXML
    private Pane dispatchPane;
    @FXML
    private Pane viewOrdersPane;
    @FXML
    private Pane employeeLoginPane;
    @FXML
    private Pane scheduleManagerPane;
    @FXML
    private Pane customerDirectoryPane;
    @FXML
    private Pane statPane;
    @FXML
    private Pane settingsPane;
    @FXML
    private Pane viewPreordersPane;
    @FXML
    private Pane pickupDeliveryPane;


    @FXML
    private Pane theMainBox;

    /**
     * The HashMap which has been mapped for
     * {@link JFXButton} for the key
     * and gives a {@link Pane} as it's associated value.
     */
    private HashMap<Node, Node> eventMap;


  //  EmployeeLoginPane employeeLoginPaneController;

    /**
     * Initializes the controller
     *
     * @param location  The location of the FXML file for this controller
     * @param resources The resources of the controller
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        /*temporary -Matt*/
//        SortedSet<Shift> set = new TreeSet<Shift>();
//        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
//        System.out.println("=============");
//        try {
//            set.add(new Shift(sd.parse("01/01/2019")));
//            set.add(new Shift(sd.parse("10/01/2019")));
//            set.add(new Shift(sd.parse("8/01/2019")));
//            set.add(new Shift(sd.parse("9/01/2019")));
//            set.add(new Shift(sd.parse("2/01/2019")));
//            set.stream().forEach(r -> System.out.println(sd.format(r.getStarted())));
//
//            set.subSet(new Shift(sd.parse("10/01/2019")), new Shift(sd.parse("07/01/2019")))
//                    .stream().forEach(s -> System.out.println(sd.format(s.getStarted())));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("=============");

        /* Map this controller */
        ControllerMap.addController(ControllerType.MASTER_CONTROLLER, this);

        /* Initialize the button map */
        eventMap = new HashMap<>();
        buttonMap.getChildren().forEach(button -> paneMap.getChildren().forEach(pane -> {
            if (pane.getId().contains(button.getId())) {
                eventMap.put(button, pane);
            }
        }));


        //Apply selected resolution
        if (InterfaceBuilder.getProgramResolution().getWidth() != 1920) {
            theMainBox.setScaleX(InterfaceBuilder.getProgramResolution().getScaleX());
            theMainBox.setScaleY(InterfaceBuilder.getProgramResolution().getScaleY());
            theMainBox.setLayoutX(InterfaceBuilder.getProgramResolution().getOffsetX());
            theMainBox.setLayoutY(InterfaceBuilder.getProgramResolution().getOffSetY());
        }
    }
    private Node currentPane;


    /**
     * Changes the main pane
     *
     * @param event The button associated to the pane that was clicked
     */
    @FXML
    public void changePane(ActionEvent event) {
        final Object source = event.getSource();
        for (Node button : eventMap.keySet()) {
            if (button == source){
                if(eventMap.get(button) == employeeLoginPane){
                    EmployeePane.instance.open();
                }else
                if(eventMap.get(button) == viewOrdersPane){
                    ((ViewOrdersPane)ControllerMap.getController(ControllerType.VIEW_ORDERS_PANE_CONTROLLER)).open();
                }else
                if(eventMap.get(button) == pickupDeliveryPane){
                    ((PickupDeliveryPaneController)ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER)).open();
                }else
                if(eventMap.get(button) == this.pendingOrdersPane){
                    ((PendingOrdersPane)ControllerMap.getController(ControllerType.PENDING_ORDERS_PANE_CONTROLLER)).open();
                }else
                if(eventMap.get(button) == this.viewPreordersPane){
                    ((ViewPreordersPane)ControllerMap.getController(ControllerType.VIEW_PREODERS_PANE_CONTROLLER)).open();
                }

                if(currentPane != eventMap.get(button)){
                    if(currentPane == employeeLoginPane){
                        EmployeePane.instance.close();
                    }
                }
                eventMap.get(button).toFront();
                currentPane = eventMap.get(button);
            }


        }
    }

    private void callCloseIfThere(Object o){
        for(Method m: o.getClass().getDeclaredMethods()){
            if(m.getName().equals("close") && m.getParameterCount() == 0){
                try {
                    m.setAccessible(true);
                    m.invoke(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//
//    public EmployeeLoginPane getEmployeeLoginPaneController() {
//        if(employeeLoginPaneController == null){
//            employeeLoginPaneController =  (EmployeeLoginPane) ControllerMap.getController(ControllerType.EMPLOYEE_LOGIN_PANE_CONTROLLER);
//        }
//        return employeeLoginPaneController;
//    }

    @Override
    public ControllerType getType() {
        return ControllerType.MASTER_CONTROLLER;
    }
}