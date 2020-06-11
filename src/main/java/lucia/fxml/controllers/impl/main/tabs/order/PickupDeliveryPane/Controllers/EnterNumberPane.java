package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import main.java.lucia.Client;
import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.protocol.packet.in.customer.PacketInFoundCustomer;
import main.java.lucia.client.protocol.packet.outgoing.customer.PacketOutFindCustomerByPhone;
import main.java.lucia.client.protocol.packet.outgoing.customer.PacketOutSaveCustomer;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.PendingOrdersPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDelivery;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber.*;
import main.java.lucia.net.packet.event.PacketEventHandler;
import main.java.lucia.net.packet.event.PacketHandler;
import main.java.lucia.net.packet.event.PacketListenerManager;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.ParsePosition;


/**
 * The enter number pane which handles creation of new customers and other related operations
 *
 * @author Zachery Unrau
 * @author Brett Downey
 * @author Matthew Kwiatkowski
 */
public class EnterNumberPane implements Controller, ParentController<PickupDelivery> {


    @FXML
    private Pane enterNumberPane;

    @FXML
    private Pane extendedMapPane;

    @FXML
    private Pane orderSummaryPane;

    @FXML
    private OrderSummaryPaneController orderSummaryPaneController;

    @FXML
    private Pane toggleMappane;

    @FXML
    private JFXButton toggleMapButton;

    @FXML
    private Pane mappane;

    @FXML
    private MapPaneController mappaneController;

    @FXML
    private JFXTextField areaCodeField;

    @FXML
    private JFXTextField phoneField;

    @FXML
    private JFXRadioButton delivery;

    @FXML
    private JFXRadioButton pickup;

    @FXML
    private Pane customerInfoPane;

    @FXML
    private CustomerInfoPaneController customerInfoPaneController;

    @FXML
    private Pane discountPane;

    @FXML
    private DiscountPaneController discountPaneController;

//    /**
//     * Customer info pane items
//     */
//    @FXML
//    private Pane customerInfoPane;
//
//    @FXML
//    private Label customerNameLabel;
//
//    @FXML
//    private JFXTextField customerNameField;
//
//    @FXML
//    private Label Address;
//
//    @FXML
//    private JFXTextField customerStreetNumberField;
//
//    @FXML
//    private JFXComboBox<String> customerStreetField;
//
//    @FXML
//    private Label customerAptNoLabel;
//
//    @FXML
//    private JFXTextField customerAptNoField;
//
//    @FXML
//    private Label customerBuzzLabel;
//
//    @FXML
//    private JFXTextField customerBuzzField;
//
//    @FXML
//    private JFXButton customerInfoSubmit;
//
//    @FXML
//    private JFXButton customerInfoCheckAddress;
//
//    @FXML
//    private Label customerInfoView;
//
//    @FXML
//    private Label errorLabelCheckAddress;
//
//    @FXML
//    private JFXButton customerInfoBack;

    @FXML
    private Pane preorderPane;

    @FXML
    private PreorderTimeSelectPaneController preorderPaneController;

    @FXML
    private Pane pastOrders;

//    @FXML
//    private Label takeoutTime;

//    @FXML
//    private Pane discountPane;
//
//    @FXML
//    private Pane staffPane;


    public String[] blackListedStringsPickup = {"", " ", "Walkin", "Walk in", "No Name", "Null"};
    public String[] blackListedStringsDelivery = {"", " ", "675 Harbison Avenue East"};

    /**
     * The max length of a phone number that can be given
     */
    private final int MAX_PHONE_LENGTH = 7;

    /**
     * The max length of an area code that can be given
     */
    private final int MAX_AREA_CODE_LENGTH = 3;



    /**
     * Indicates if the current {@link CustomerDetails} that was loaded is a delivery
     */
    private boolean isDelivery = false;



    /**
     * Whether or not the delivery address has been checked
     */
    private boolean deliveryChecked = false;

    /**
     * The initialize call number,
     */
    private static int initializeCallNumb = 0;
    private boolean promptPreorder;
    private boolean confirm = true;
    private PickupDelivery parent;
    private TextInputControl foundStoreLabel;
    private boolean pizzaFirst = false;

    // ------------------- for some reason the initialize is called only once now ----------------------

    @FXML
    public void initialize() {
        assert(customerInfoPaneController != null);
        customerInfoPaneController.setParent(this);
        orderSummaryPaneController.setParent(this);
        PacketListenerManager.get.registerListener(new PacketHandler() {
            @PacketEventHandler
            public void onCustomerFound(PacketInFoundCustomer in){
                Client.logger.info("Found customer: " + in.getRequest() + " c: " + in.getCustomer());
                if (in.getRequest().equals(areaCodeField.getText() + phoneField.getText())) {
                    CustomerDetails d;
                    if(in.getCustomer() != null){
                        d = in.getCustomer();
                    }else{
                        d = new CustomerDetails(null,
                                areaCodeField.getText() + phoneField.getText(), null);
                    }
                    parent.getCurrentOrder().setCustomerDetails(d);
                    Platform.runLater(() -> {
                        customerInfoPaneController.loadCustomer(d);
                    });
                }
//                if (parent.getCurrentOrder().getCustomerDetails().getPhoneNumber().equals(in.getCustomer().getPhoneNumber())) {
//                    parent.getCurrentOrder().setCustomerDetails(in.getCustomer());
//                }
            }
        });
        ControllerMap.addController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER, this);
        phoneField.setAlignment(Pos.CENTER_LEFT);
        phoneField.setTextFormatter(buildNumericFormatList(MAX_PHONE_LENGTH));
        phoneField.setOnKeyTyped(keyEvent -> {
            phoneAreaChecks();
        });
        areaCodeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                areaCodeField.setText("");
            } else {
                if (areaCodeField.getText().length() == 0) {
                    areaCodeField.setText("204");
                }
            }
        });
        areaCodeField.setTextFormatter(buildNumericFormatList(MAX_AREA_CODE_LENGTH));
        areaCodeField.setOnKeyTyped(keyEvent -> {
            phoneAreaChecks();
        });
        //areaCodeField.setOnKeyPressed();


        //calendarPreorder.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        calendarPreorder.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue)
//                calendarPreorder.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        });
//        for (int x = 3; x >= 0; x--)
//            comboBoxTimeMin.getItems().add(String.valueOf(15 * x));
//        for (int x = 1; x <= 12; x++)
//            comboBoxTimeHour.getItems().add(String.valueOf(x));
//        comboBoxTimeAMPM.getItems().addAll("AM", "PM");
//        paymentMethodPreorder.getItems().addAll("Debit", "Cash", "Mastercard", "Visa", "Gift", "Cheque");
//        paymentMethod.getItems().addAll("Debit", "Cash", "Mastercard", "Visa", "Gift", "Cheque");

//        createDeliveryStreet.getEditor().textProperty().addListener((obs, oldText, newText) -> {
//            clearErrorLabel();
//            resetBackgrounds();
//            updateAddress();
//        });



        //delivery.setTooltip(new Tooltip("test"));


        // orderView.setScaleX(0.75);
        // orderView.setScaleY(0.60);
        //  orderView.setLayoutX(-44);
        //  orderView.setLayoutY(-84);

    }

    public void setParent(PickupDeliveryPaneController p) {
        this.parent = p;
    }

    /**
     * Toggles the driverMap interface
     */
    public void toggleMap() {
        toggleMap(false);
    }

    /**
     * Toggles the driverMap depending on the given variable
     * {@code justOpen}.
     *
     * @param justOpen
     */
    public void toggleMap(boolean justOpen) {
        if(parent.getCurrentOrder().getCustomerDetails() == null || parent.getCurrentOrder().getCustomerDetails().getAddress() == null){
            return;
        }
        if (!justOpen && mappane.isVisible()) {
            extendedMapPane.setVisible(false);
            mappaneController.showAddress(parent.getCurrentOrder().getCustomerDetails().getAddress().getName());
            enterNumberPane.setLayoutX(385);
        } else {
            extendedMapPane.setVisible(true);
            enterNumberPane.setLayoutX(200);
        }
    }

    /**
     * Builds our number format listener used different numeric only text fields
     *
     * @param maxLength The max length the field can hold
     * @return The text formatter
     */
    public static TextFormatter<Object> buildNumericFormatList(int maxLength) {
        DecimalFormat format = new DecimalFormat("#");
        return new TextFormatter<>(change -> {
            //addition.run();
            return getChange(maxLength, format, change);
        });
    }

    public static TextFormatter.Change getChange(int maxLength, DecimalFormat format, TextFormatter.Change change) {
        if (change.getControlNewText().isEmpty()) {
            return change;
        }

        ParsePosition parsePosition = new ParsePosition(0);
        Object object = format.parse(change.getControlNewText(), parsePosition);
        if (object == null || parsePosition.getIndex() < change.getControlNewText().length()) {
            return null;
        } else {
            /* Verify it's not breaking our text format rules */
            if (change.getControlNewText().length() > maxLength || change.getControlText()
                    .contains(".")) {
                change.setText(StringUtils.EMPTY);
            }
            return change;
        }
    }

    /**
     * The additional checks required for the phone area checks
     */
    private void phoneAreaChecks() {

        //boolean pickupOrDel = pickup.isSelected() || delivery.isSelected();
        boolean lengthVerify = areaCodeField.getLength() == MAX_AREA_CODE_LENGTH
                && phoneField.getLength() == MAX_PHONE_LENGTH;
        if (lengthVerify) {
            Client.logger.info("Searching for customer with phone: " + areaCodeField.getText() + phoneField.getText());
            PacketSender.sendPacket(new PacketOutFindCustomerByPhone(Integer.parseInt(areaCodeField.getText() + phoneField.getText())));
        }
        else disableAll();
    }

    @FXML
    private void check() {
        if (!(phoneField.getLength() >= MAX_PHONE_LENGTH))
            disableAll();
    }

    @FXML
    private void checkArea() {
        if (!(areaCodeField.getLength() >= MAX_AREA_CODE_LENGTH))
            disableAll();
    }


    @FXML
    private void checkAddress() {

    }

    @FXML
    private void updateAddress() {
//        String name = StringUtils.capitalize(createDeliveryStreet.getEditor().getText());
//        String num = createDeliveryStreetNum.getText() + " ";
//        String appt = createDeliveryAppt.getText();
//        String buzz = createDeliveryBuzz.getText();
//        if (buzz.equals("") && appt.equals(""))
//            createDeliveryView.setText(num + name);
//        else if (buzz.equals(""))
//            createDeliveryView.setText(appt + " " + name);
//        else createDeliveryView.setText(buzz + "-" + appt + " " + name);
    }

    @FXML
    private void updateName() {
        //createPickupNameView.setText(StringUtils.capitalize(createPickupName.getText()));
    }




//    /**
//     * Prin
//     * Loads the delivery pane and unloads all other panes
//     */
//    public void loadDeliveryPane() {
//        PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
//        controller.setDelivery();
//        loadCustomerFields();
//        loadDelivery.setVisible(true);
//        createDelivery.setVisible(false);
//        loadPickup.setVisible(false);
//        createPickup.setVisible(false);
//        isDelivery = true;
//    }
//
//    private void loadCustomerFields() {
//        if (parent.getCurrentOrder().getCustomerDetails() != null) {
//            loadPickupName.setText(parent.getCurrentOrder().getCustomerDetails().getName());
//            if (parent.getCurrentOrder().getCustomerDetails().getAddress() != null) {
//                loadDeliveryAppt.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().getApartmentNumber());
//                loadDeliveryBuzz.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().getBuzzCode());
//                loadDeliveryNum.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().getNumber());
//                loadDeliveryStreet.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().getStreet());
//            }
//        }
//    }
//
//    /**
//     * Loads the create delivery pane and unloads all other panes
//     */
//    public void loadCreateDeliveryPane() {
//        PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
//        controller.setDelivery();
//        loadDelivery.setVisible(false);
//        createDelivery.setVisible(true);
//        loadPickup.setVisible(false);
//        createPickup.setVisible(false);
//        isDelivery = true;
//    }
//
//    /**
//     * Loads the pickup pane and unloads all other panes
//     */
//    public void loadPickupPane() {
//        PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
//        controller.setPickup();
//        //updateName();
//        loadCustomerFields();
//
//        loadDelivery.setVisible(false);
//        createDelivery.setVisible(false);
//        loadPickup.setVisible(true);
//        createPickup.setVisible(false);
//        isDelivery = false;
//    }
//
//    /**
//     * Loads the create pickup pane and unloads all other panes
//     */
//    public void loadCreatePickupPane() {
//        PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
//        controller.setPickup();
//        loadDelivery.setVisible(false);
//        createDelivery.setVisible(false);
//        loadPickup.setVisible(false);
//        createPickup.setVisible(true);
//    }

    /**
     * Sets the phone field and the area code field editable
     */
    @FXML
    public void setEditable() {
        phoneField.setEditable(true);
        areaCodeField.setEditable(true);
    }

    /**
     * The on click function which is used for the {code delivery} and {@code pickup} {@link
     * JFXRadioButton}s
     *
     * @param event The click event
     */
    @FXML
    public void onSelectPickuporDel(ActionEvent event) {
        disableAll();
        Object source = event.getSource();
        if (source.equals(delivery)){
            isDelivery = true;
            parent.getCurrentOrder().setOrderType(OrderType.DELIVERY);
            customerInfoPaneController.highlightDeliveryFields();
        }else if(source.equals(pickup)){
            isDelivery = false;
            parent.getCurrentOrder().setOrderType(OrderType.PICKUP);
            customerInfoPaneController.highlightPickupFields();
        }
    }

    @FXML
    public void resetBackgrounds() {
        //createPickupName.setStyle("-fx-background-color: white");
        //createDeliveryStreet.setStyle("-fx-background-color: white");
    }

    /**
     * Adds the given data to the customer interface. This confirms beforehand which data we need to
     * load, as there are two different areas where customer data is entered.
     */
    @FXML
    public void addToCustomer() {
        checkAddress();
        if (confirm) {
//            if (loadPickup.isVisible()) {
//                parent.getCurrentOrder().getCustomerDetails().setName(loadPickupName.getText());
//                PickupDeliveryPaneController cont = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
//                //cont.getCompletedOrder().
//                parent.getCurrentOrder().getCustomerDetails().setPhoneNumber(areaCodeField.getText() + phoneField.getText());
//            } else if (createPickup.isVisible()) {
//                if (checkValidStringPickup(createPickupName.getText())) {
//                    parent.getCurrentOrder().getCustomerDetails().setName(createPickupName.getText());
//                    parent.getCurrentOrder().getCustomerDetails().setPhoneNumber(areaCodeField.getText() + phoneField.getText());
//                } else {
//                    createPickupName.setStyle("-fx-background-color: #717516");
//                    return;
//                }
//            } else if (createDelivery.isVisible() || loadDelivery.isVisible()) {
//
//                String address = "null";
//                String streetNumber = "null";
//                try {
//                    address = String.valueOf(createDeliveryStreet.getValue());
//                    streetNumber = createDeliveryStreetNum.getText() + " ";
//                } catch (Exception e) {
//                    errorLabelCheckAddress.setText("Invalid Address!");
//                }
//                if (checkValidStringDelivery(streetNumber + address)) {
//                    parent.getCurrentOrder().getCustomerDetails().setPhoneNumber(areaCodeField.getText() + phoneField.getText());
//                    parent.getCurrentOrder().getCustomerDetails().setAddress(
//                            new Address(address, streetNumber, createDeliveryAppt.getText(), createDeliveryBuzz.getText()));
//                    PickupDeliveryPaneController cont = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
//                    cont.getCurrentOrder().setDelivery(true);
//                } else {
//                    createDeliveryStreet.setStyle("-fx-background-color: #717516");
//                    return;
//                }
//            }
            //call create customer
            //CreateCustomerMessage.saveCustomer(parent.getCurrentOrder().getCustomerDetails());
            PacketOutSaveCustomer out = new PacketOutSaveCustomer(parent.getCurrentOrder().getCustomerDetails());
            PacketSender.sendPacket(out);
            if (promptPreorder)
                openPreorder();
            else if (pizzaFirst)
                loadSummaryPane();
            else
                addToCustomerPickupDel();
        }
    }

    /**
     * Returns the current order from the pickupdelpane
     */
    public Order getCurrentOrder(){
        return parent.getCurrentOrder();
    }

    /**
     * Called when the createcustomermessage returns the customer
     *
     * @param i the {@link CustomerDetails} returned
     */
    public void gotCustomer(CustomerDetails i) {

    }

    @FXML
    public void onExit() {
        pizzaFirst = true;
        addToCustomerPickupDel();
    }

    private boolean checkValidStringPickup(String string) {
        for (int x = 0; x < blackListedStringsPickup.length; x++)
            if (string.equalsIgnoreCase(blackListedStringsPickup[x]))
                return false;
        return true;
    }

    private boolean checkValidStringDelivery(String string) {
        if (!string.contains(" "))
            return false;
        else if (!checkNum(string)) {
            return false;
        }
        for (int x = 0; x < blackListedStringsDelivery.length; x++)
            if (string.equalsIgnoreCase(blackListedStringsDelivery[x]))
                return false;
        return true;
    }

    private boolean checkNum(String string) {
        try {
            Integer.parseInt(string.substring(0, string.indexOf(" ")));
        } catch (Exception e) {
            System.out.println("FALSE!");
            return false;
        }
        return true;
    }

    @FXML
    public void addToCustomerPickupDel() {
        disableAll();
        //parent.setChild();
        PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap
                .getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
        controller.addToCustomer();
    }

    public void disableAll() {
//        createPickup.setVisible(false);
//        createDelivery.setVisible(false);
//        loadPickup.setVisible(false);
//        loadDelivery.setVisible(false);
        orderSummaryPane.setVisible(false);
    }

    public void reset() {
        disableAll();
//        Pane[] panes = {createDelivery, createPickup, loadDelivery, createDelivery};
//        for (int x = 0; x < panes.length; x++) {
//            panes[x].getChildren().forEach(theButtons -> {
//                if (theButtons instanceof JFXTextField) {
//                    JFXTextField text = (JFXTextField) theButtons;
//                    text.clear();
//                }
//            });
//        }
//        resetPreorder();
        areaCodeField.setText("204");
        //paymentMethodPane.setVisible(false);
        //confirmationPane.setVisible(false);
        orderSummaryPane.setVisible(false);
        clearErrorLabel();
        updateAddress();
        updateName();
        phoneField.clear();
        pickup.setSelected(false);
        delivery.setSelected(false);
        //takeoutTime.setText("NOW");
        pizzaFirst = false;
        PendingOrdersPane controller = (PendingOrdersPane) ControllerMap.getController(ControllerType.PENDING_ORDERS_PANE_CONTROLLER);
        controller.refresh();
        //runner()
    }

    @FXML
    public void cancelOrder() {
        PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap
                .getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
        controller.cancelOrder();
        //parent.setChild();
        //clearSummary();
        reset();
    }

    public void setStoreName(String name) {
        //System.out.println("WORK");
        Platform.runLater(() -> {
            foundStoreLabel.setVisible(true);
            // TODO Clean loose strings up
            if (name.equals("undefined")) {
                if (confirm) {
                    orderSummaryPane.setVisible(true);
                    orderSummaryPane.setVisible(true);
                    //errorLabelConfirmationAddress.setText("Address is not in the Delivery Area");
                } else {
                    orderSummaryPane.setVisible(true);
//                    errorLabelConfirmationAddress.setText("The given location is not within any store location's delivery area");
//                    errorLabelConfirmationAddress.setStyle("-fx-text-fill: #ff2f2f");
                }
            } else {
                if (confirm) {
                    System.out.println("HERE");
                    orderSummaryPane.setVisible(true);
//                    errorLabelConfirmationAddress.setText("Address is within within " + name + "'s delivery area");
                } else {
                    System.out.println("HERE");
                    orderSummaryPane.setVisible(true);
//                    errorLabelConfirmationAddress.setText("The given location is within " + name + "'s delivery area");
//                    errorLabelConfirmationAddress.setStyle("-fx-text-fill: black");
                }
            }
        });
    }

    @FXML
    public void confirmInvalidAddress() {
        confirm = true;
    }

    @FXML
    public void loadSummaryPane() {

    }

    public void loadCustomerInfo() {
//        if (pickup.isSelected())
//            customerName.setText(StringUtils.capitalize(parent.getCurrentOrder().getCustomerDetails().getName()));
//        customerNumber.setText(parent.getCurrentOrder().getCustomerDetails().getPhoneNumberFormatted());
//        if (delivery.isSelected())
//            customerAddress.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().toString());
    }


    /**
     * Whether or not the delivery address has been checked
     */
    public boolean isDeliveryChecked() {
        return deliveryChecked;
    }

    /**
     * Whether or not the delivery address has been checked
     */
    public void setDeliveryChecked(boolean deliveryChecked) {
        this.deliveryChecked = deliveryChecked;
    }

    /**
     * Opens the preorder pane
     */
    public void openPreorder(){
        preorderPane.setVisible(true);
        preorderPaneController.setForOrder(parent.getCurrentOrder());
    }

    /**
     * Opens the discount pane
     */
    public void openDiscountPane(){
        discountPane.setVisible(true);
        discountPaneController.open(parent.getCurrentOrder());
    }

    /**
     * Confirms the order
     */
    public void confirmOrder() {
        PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
        if (delivery.isSelected()) {
                System.out.println(controller.getCurrentOrder().isDelivery());
                System.out.println(controller.getCurrentOrder().getGrandTotalTax().toString());
                //controller.getCurrentOrder().setPaymentType(PaymentType.valueOf(paymentMethod.getValue().toUpperCase()));
                controller.finalizeOrder();
        } else controller.finalizeOrder();
    }

    @FXML
    public void loadOrder() {
        reset();
        addToCustomerPickupDel();
    }

    @FXML
    public void clearErrorLabel() {
        //errorLabelSummary.setText(" ");
        //errorLabelCheckAddress.setText(" ");
//        errorLabelConfirmationAddress.setText(" ");
    }


    @FXML
    public void changeInfo() {
        //summaryOrder.setVisible(false);
    }

    /**
     * Determines if the current order being placed is for a delivery
     *
     * @return the delivery flag
     */
    public boolean isDelivery() {
        return isDelivery;
    }

    /**
     * Gets the {@link ControllerType}
     *
     * @return The controller type
     */
    @Override
    public ControllerType getType() {
        return ControllerType.ENTER_NUMBER_PANE_CONTROLLER;
    }

    @FXML
    public void openPastOrders() {
        pastOrders.setVisible(true);
    }

    @FXML
    public void preorder() {
        preorderPane.setVisible(true);
    }

    @FXML
    public void discounts() {
//        discountPane.toFront();
//        discountPane.setVisible(true);
    }

    @FXML
    void acceptCAA(ActionEvent event) {
        //discountPane.setVisible(false);
    }

    @FXML
    void acceptStaff(ActionEvent event) {
//    if(staffShiftToggle.isSelected())
//      parent.getCurrentOrder().addStaffDiscount(true);
//    else parent.getCurrentOrder().addStaffDiscount(false);
//        staffPane.setVisible(false);
//        discountPane.setVisible(false);
//        staffShiftToggle.setText("Off Shift");
//        staffShiftToggle.setSelected(false);
    }

    @FXML
    void staffOrder(ActionEvent event) {
        //staffPane.setVisible(true);
    }

    @Override
    public void close() {

    }

    @Override
    public Object getCurrentItem() {
        return null;
    }

    @Override
    public void open() {
        areaCodeField.setText("204");
        phoneField.setText("");
    }

    @Override
    public void setParent(PickupDelivery o) {
        this.parent = o;
    }

    @FXML
    public void shiftToggle() {
//        if (staffShiftToggle.isSelected())
//            staffShiftToggle.setText("On Shift");
//        else staffShiftToggle.setText("Off Shift");
    }
}