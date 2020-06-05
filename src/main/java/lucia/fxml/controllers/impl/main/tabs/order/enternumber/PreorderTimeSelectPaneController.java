package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.payment.paymentmethods.PaymentType;
import main.java.lucia.fxml.controllers.intf.PreorderInterface;
import main.java.lucia.fxml.utils.BlinkUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The controller for the preorder time pane
 * @author Matthew Kwiatkowski
 */
public class PreorderTimeSelectPaneController {

    @FXML
    private Pane preorderPane;

    @FXML
    private JFXDatePicker calendarPreorder;

    @FXML
    private JFXComboBox<PaymentType> paymentMethodPreorder;

    @FXML
    private Label errorLabelPreorder;

    @FXML
    private JFXTimePicker timePicker;


    /**
     * The return value interface for selections made here.
     */
    private PreorderInterface parent;

    @FXML
    private void initialize(){
        paymentMethodPreorder.getItems().addAll(PaymentType.values());
    }

    public void setParent(PreorderInterface parent) {
        this.parent = parent;
    }

    /**
     * Sets the time/payment displayed
     * @param o the order to display
     */
    public void setForOrder(Order o){
        LocalDateTime time = o.getOrderTime().toLocalDate();
        calendarPreorder.setValue(time.toLocalDate());
        timePicker.setValue(time.toLocalTime());
        paymentMethodPreorder.setValue(o.getPaymentType());
    }

    @FXML
    void backPreorder(ActionEvent event) {
        parent.onPreorderBack();
    }

    @FXML
    void confirmPreorder(ActionEvent event) {
        if(!verifyFields())
            return;
        LocalDateTime time = readDateTime();
        PaymentType payment = paymentMethodPreorder.getValue();
        parent.onPreorderSelect(time, payment);
    }

    /**
     * Reads the selected date and time
     */
    private LocalDateTime readDateTime(){
        LocalDate date = calendarPreorder.getValue();
        LocalTime time = timePicker.getValue();
        return date.atTime(time);
    }

    /**
     * Verify if all the fields are filled in
     * @return true if all the fields are filled in successfully, false otherwise.
     */
    private boolean verifyFields(){
        if(paymentMethodPreorder.getValue() == null){
            BlinkUtils.wrong(paymentMethodPreorder);
            return false;
        }
        if(timePicker.getValue() == null){
            BlinkUtils.wrong(timePicker);
            return false;
        }
        if(calendarPreorder.getValue() == null){
            BlinkUtils.wrong(calendarPreorder);
            return false;
        }
        return true;
    }
}
