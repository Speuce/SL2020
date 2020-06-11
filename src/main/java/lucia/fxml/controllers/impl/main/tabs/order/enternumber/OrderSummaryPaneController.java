package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.payment.paymentmethods.PaymentType;
import main.java.lucia.client.content.time.TimeFormat;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.EnterNumberPane;
import main.java.lucia.fxml.utils.BlinkUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Controller for the order summary pane
 * @author Matthew Kwiatkowski
 */
public class OrderSummaryPaneController {

    @FXML
    private Pane summaryOrder;

    @FXML
    private Pane orderViewPane;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label customerName;

    @FXML
    private Label customerNumber;

    @FXML
    private Label customerAddressLabel;

    @FXML
    private Label customerAddress;

    @FXML
    private Label takeoutMethodLabel;

    @FXML
    private Label addressConfirmedLabel;

    @FXML
    private ImageView addressConfirmed;

    @FXML
    private ImageView addressNotConfirmed;

    @FXML
    private Label orderTime;

    @FXML
    private Label orderETA;

    @FXML
    private Label errorLabelSummary;

    @FXML
    private Pane paymentMethodPane;

    @FXML
    private JFXComboBox<PaymentType> paymentMethod;

    /**
     * the parent controller pane
     */
    private EnterNumberPane parent;

    @FXML
    private void initialize(){
        paymentMethod.getItems().addAll(PaymentType.values());
    }

    /**
     * Sets the parent pane of this summary pane.
     * @param parent the parent of this pane
     */
    public void setParent(EnterNumberPane parent) {
        this.parent = parent;
    }

    /**
     * Loads the pane and info
     * @param o the order to display info for.
     */
    public void loadPane(Order o){
        clearSummary();
        //TODO show order info
//        this.orderViewPane.getChildren().clear();
//        //this.orderViewPane.getChildren().add(((PickupDeliveryPaneController) parent).getOrderView());
//        this.orderViewPane.setScaleX(0.75);
//        this.orderViewPane.setScaleY(0.60);
//        this.orderViewPane.setLayoutX(-20);
//        this.orderViewPane.setLayoutY(0);
        summaryOrder.setVisible(true);
        if (o.isPreOrder()) {
            if (!o.isFuturePreorder()) {
                orderTime.setText(o.getOrderTime().toString(TimeFormat.FORMATTER_12_HOUR_TIME));
                orderETA.setText(o.getOrderTime().toString(TimeFormat.FORMATTER_12_HOUR_TIME));
            } else {
                orderTime.setText(o.getOrderTime().toString(TimeFormat.FORMATTER_12_HOUR));
                orderETA.setText(o.getOrderTime().toString(TimeFormat.FORMATTER_12_HOUR));
            }
            paymentMethodPane.setVisible(true);
            paymentMethod.setValue(o.getPaymentType());
        }else{
            orderTime.setText("NOW");
            if(!o.isDelivery()) {
                takeoutMethodLabel.setText("PICKUP");
                //TODO adaptive eta's
                orderETA.setText("20 - 25 MIN");
                paymentMethodPane.setVisible(false);
            }else{
                takeoutMethodLabel.setText("DELIVERY");
                //TODO adaptive eta's
                orderETA.setText("45 MIN - 1 HR");
                paymentMethodPane.setVisible(true);
                paymentMethod.setValue(o.getPaymentType());
            }
        }
        confirmGeneralInfo(o);
        loadCustomerInfo(o);
    }

    /**
     * Creates the check/x for address confirmation
     * @param o
     */
    private void confirmGeneralInfo(Order o) {
        //PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
        clearGeneralInfoImages();
        if (o.isDelivery()) {
            addressConfirmedLabel.setVisible(true);
            if (parent.isDeliveryChecked())
                addressConfirmed.setVisible(true);
            else addressNotConfirmed.setVisible(true);
        }else{
            addressConfirmedLabel.setVisible(false);
        }
    }

    /**
     * Loads the customer info into the respective pane
     * @param o the order to load info for.
     */
    private void loadCustomerInfo(Order o) {
        customerNumber.setText(o.getCustomerDetails().getPhoneNumberFormatted());
        if (!o.isDelivery()){
            customerAddressLabel.setVisible(false);
            customerNameLabel.setVisible(true);
            customerName.setText(StringUtils.capitalize(o.getCustomerDetails().getName()));
        }else{
            customerAddressLabel.setVisible(true);
            customerNameLabel.setVisible(false);
            customerName.setText(o.getCustomerDetails().getAddress().toString());
        }
    }

    /**
     * Clears the customer summary portion
     */
    public void clearSummary() {
        customerAddress.setText("");
        customerNumber.setText("");
        customerName.setText("");
    }

    /**
     * Clears the checkmark/'x' for address confirmation
     */
    public void clearGeneralInfoImages() {
        addressConfirmed.setVisible(false);
        addressNotConfirmed.setVisible(false);
    }

    @FXML
    void changeOrder(ActionEvent event) {

    }

    @FXML
    void clearErrorLabel(MouseEvent event) {
        errorLabelSummary.setVisible(false);
    }

    @FXML
    void onConfirmOrder(ActionEvent event) {
        if(parent.getCurrentOrder().isDelivery() && paymentMethod.getValue() == null){
            BlinkUtils.wrong(paymentMethod);
            return;
        }
        parent.confirmOrder();
    }

    @FXML
    void onOrderCancel(ActionEvent event) {

    }

    @FXML
    void openDiscount(ActionEvent event) {
        parent.openDiscountPane();
    }

    @FXML
    void openPreorder(ActionEvent event) {
        parent.openPreorder();
    }
}
