package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.Client;
import main.java.lucia.Zach.StreetNames;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.protocol.packet.outgoing.customer.PacketOutSaveCustomer;
import main.java.lucia.fxml.controllers.impl.main.Utils.AutoCompleteComboBoxListener;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.EnterNumberPane;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;

/**
 * The controller for the customer information pane
 * @author Matthew Kwiatkowski
 */
public class CustomerInfoPaneController {

    @FXML
    private Pane customerInfoPane;

    @FXML
    private Label customerNameLabel;

    @FXML
    private JFXTextField customerNameField;

    @FXML
    private Label Address;

    @FXML
    private JFXTextField customerStreetNumberField;

    @FXML
    private JFXComboBox<String> customerStreetField;

    @FXML
    private Label customerAptNoLabel;

    @FXML
    private JFXTextField customerAptNoField;

    @FXML
    private Label customerBuzzLabel;

    @FXML
    private JFXTextField customerBuzzField;

    @FXML
    private Label errorLabelCheckAddress;

    @FXML
    private Pane pastOrdersPane;

    @FXML
    private PastOrdersPaneController pastOrdersPaneController;

    @FXML
    private JFXButton customerInfoCheckAddress;

    @FXML
    private JFXButton customerInfoSubmit;

    @FXML
    private Label customerInfoView;

    @FXML
    private JFXButton customerInfoBack;

    /**
     * The currently displayed customer
     */
    private CustomerDetails customer;

    /**
     * The parent controller
     */
    private EnterNumberPane parent;

    /**
     * whether or not the delivery address has been checked
     */
    private boolean deliveryChecked;

    private final String DEFAULT_ERROR_MESSAGE = "The entered address does not exist!";

    /**
     * Performs initialization routines
     */
    @FXML
    public void initialize(){
        StreetNames streetNames = new StreetNames();
        customerStreetField.getItems().addAll(streetNames.getNames());
        //customerStreetField.getItems().add("the one and only ave.");
        new AutoCompleteComboBoxListener<>(customerStreetField);
    }

    /**
     * Called when 'submit' button is hit
     */
    @FXML
    void submit(ActionEvent event) {
        PacketOutSaveCustomer out = new PacketOutSaveCustomer(customer);
        PacketSender.sendPacket(out);
//        if (promptPreorder)
//            preorder();
//        else if (pizzaFirst)
//            loadSummaryPane();
//        else
//            addToCustomerPickupDel();
    }

    @FXML
    void checkAddress(ActionEvent event) {
        deliveryChecked = true;
        AsynchronousTaskService.process(() -> Platform.runLater(() -> {
            if (getSelectedStreet() != null && !customerStreetNumberField.getText().equals("") ) {
                String streetName = getSelectedStreetAddress();
                String combined = streetName + " Manitoba Canada";
                //System.out.println("----------- : " + combined);
                String script = ("findLocation(" + "'" + combined + "'" + ")");
                parent.getEngine().executeScript(script); // TODO Make work for more than just Winnipeg when we need to
            }
            parent.toggleMap(true);
        }));
    }

    @FXML
    void clearErrorLabel(ActionEvent event) {
        //errorLabelSummary.setText(" ");
        errorLabelCheckAddress.setText(" ");
    }

    @FXML
    void openPastOrders(ActionEvent event) {
        pastOrdersPaneController.loadForCustomer(customer, o ->{
            Client.logger.info("TODO: load order into editor");
        });
    }

    @FXML
    void updateAddress(KeyEvent event) {

    }

    /**
     * Loads the customer pane.
     * Fills in default values
     * @param n the customer to fill in details for.
     */
    public void loadCustomer(CustomerDetails n){
        this.customer = n;
        assert(n != null);
        customerInfoPane.setVisible(true);
        customerNameField.setText(n.getName());
        if(n.getAddress() != null){
            customerStreetField.setValue(n.getAddress().getStreet());
            customerStreetNumberField.setText(n.getAddress().getAddressNumber());
            customerAptNoField.setText(n.getAddress().getApartmentNumber());
            customerBuzzField.setText(n.getAddress().getBuzzCode());
        }else{
            customerStreetField.setValue("");
            customerStreetNumberField.setText("");
            customerAptNoField.setText("");
            customerBuzzField.setText("");
        }
    }

    /**
     * Get the currently displayed customer
     */
    public CustomerDetails getCustomer() {
        return customer;
    }

    /**
     * Gets the selected Street name
     */
    private String getSelectedStreet(){
        return customerStreetField.getValue();
    }

    /**
     * Gets the street number + " " + street name
     */
    private String getSelectedStreetAddress(){
        return customerStreetNumberField.getText() + " " + customerStreetField.getValue();
    }

    /**
     * Highlights the pickup fields, greys out delivery fields,
     * hides check address button
     */
    public void highlightPickupFields(){
        resetOpacity();
        greyOutDeliveryFields();
        customerInfoCheckAddress.setVisible(false);
    }

    /**
     * Highlights the delviery fields, greys out pickup fields,
     * shows check address button
     */
    public void highlightDeliveryFields(){
        resetOpacity();
        greyOutPickupFields();
        customerInfoCheckAddress.setVisible(true);
    }

    /**
     * Resets opacity for customer fields
     */
    private void resetOpacity(){
        customerNameLabel.setOpacity(1);
        customerNameField.setOpacity(1);

        Address.setOpacity(1);
        customerStreetNumberField.setOpacity(1);
        customerStreetField.setOpacity(1);
        customerAptNoField.setOpacity(1);
        customerAptNoLabel.setOpacity(1);
        customerBuzzLabel.setOpacity(1);
        customerBuzzField.setOpacity(1);
    }

    /**
     * 'greys' out customer pickup fields (name)
     */
    private void greyOutPickupFields(){
        customerNameLabel.setOpacity(0.5);
        customerNameField.setOpacity(0.5);
    }

    /**
     * 'greys' out customer pickup fields (address,apt no, buzz code)
     */
    private void greyOutDeliveryFields(){
        Address.setOpacity(0.5);
        customerStreetNumberField.setOpacity(0.5);
        customerStreetField.setOpacity(0.5);
        customerAptNoField.setOpacity(0.5);
        customerAptNoLabel.setOpacity(0.5);
        customerBuzzLabel.setOpacity(0.5);
        customerBuzzField.setOpacity(0.5);
    }

    /**
     * Shows geocode error message
     */
    public void enableGeocodeError() {
        Platform.runLater(() -> {
            errorLabelCheckAddress.setVisible(true);
            errorLabelCheckAddress.setText(DEFAULT_ERROR_MESSAGE);
        });
    }

    public void enableGeocodeError(String errorMessage) {
        Platform.runLater(() -> {
            //confirmationPane.setVisible(true);
//            errorLabelConfirmationAddress.setVisible(true);
//            errorLabelConfirmationAddress.setText(errorMessage);
        });
    }

    public void disableGeocodeError() {
        Platform.runLater(() -> errorLabelCheckAddress.setVisible(false));
    }

    /**
     * Sets the parent controller
     */
    public void setParent(EnterNumberPane parent) {
        this.parent = parent;
    }
}
