package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.payment.paymentmethods.PaymentType;

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
    private Label customerName;

    @FXML
    private Label customerNumber;

    @FXML
    private Label customerAddress;

    @FXML
    private Label takeoutMethodLabel;

    @FXML
    private ImageView addressNotConfirmed;

    @FXML
    private Label takeoutTimeLabel;

    @FXML
    private Label takeoutTime;

    @FXML
    private Label orderETA;

    @FXML
    private Label errorLabelSummary;

    @FXML
    private Pane paymentMethodPane;

    @FXML
    private void initialize(){

    }

    @FXML
    private JFXComboBox<PaymentType> paymentMethod;

    @FXML
    void cancelOrder(MouseEvent event) {

    }

    @FXML
    void changeOrder(ActionEvent event) {

    }

    @FXML
    void clearErrorLabel(MouseEvent event) {

    }

    @FXML
    void onConfirmOrder(ActionEvent event) {

    }

    @FXML
    void onOrderCancel(ActionEvent event) {

    }

    @FXML
    void openDiscount(ActionEvent event) {

    }

    @FXML
    void openPreorder(ActionEvent event) {

    }
}
