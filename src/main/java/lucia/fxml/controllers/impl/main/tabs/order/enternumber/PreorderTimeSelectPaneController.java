package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
    private JFXComboBox<Integer> comboBoxTimeHour;

    @FXML
    private JFXComboBox<Integer> comboBoxTimeMin;

    @FXML
    private JFXComboBox<String> comboBoxTimeAMPM;

    @FXML
    private JFXComboBox<PaymentType> paymentMethodPreorder;

    @FXML
    private Label errorLabelPreorder;

    /**
     * The return value interface for selections made here.
     */
    private PreorderInterface parent;

    @FXML
    private void initialize(){
        for(int i = 1; i <= 12; i++){
            comboBoxTimeHour.getItems().add(i);
        }
        for(int i = 0; i<=60; i+= 5){
            comboBoxTimeMin.getItems().add(i);
        }
        comboBoxTimeAMPM.getItems().add("AM");
        comboBoxTimeAMPM.getItems().add("PM");

        paymentMethodPreorder.getItems().addAll(PaymentType.values());
    }

    public void setParent(PreorderInterface parent) {
        this.parent = parent;
    }

    /**
     * Resets fields in pane
     */
    public void reset(){
        calendarPreorder.setValue(LocalDate.now());
        comboBoxTimeHour.setValue(null);
        comboBoxTimeAMPM.setValue("PM");
        comboBoxTimeMin.setValue(null);
        paymentMethodPreorder.setValue(null);
    }

    /**
     * Sets the time/payment displayed
     * @param o the order to display
     */
    public void setForOrder(Order o){
        LocalDateTime time = o.getOrderTime().toLocalDate();
        calendarPreorder.setValue(time.toLocalDate());
        comboBoxTimeHour.setValue(time.getHour()%12);
        comboBoxTimeMin.setValue(time.getMinute());
        if(time.getHour() >= 12){
            comboBoxTimeAMPM.setValue("PM");
        }else{
            comboBoxTimeAMPM.setValue("AM");
        }
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
        int hour = comboBoxTimeHour.getValue();
        int min = comboBoxTimeMin.getValue();
        if(comboBoxTimeAMPM.getValue().equals("PM")){
            if(hour != 12){
                hour += 12;
            }
        }else if(hour == 12){
            hour = 0;
        }
        return date.atTime(hour, min);
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
        if(comboBoxTimeHour.getValue() == null){
            BlinkUtils.wrong(comboBoxTimeHour);
            return false;
        }
        if(comboBoxTimeMin.getValue() == null){
            BlinkUtils.wrong(comboBoxTimeMin);
            return false;
        }
        if(comboBoxTimeAMPM.getValue() == null){
            BlinkUtils.wrong(comboBoxTimeAMPM);
            return false;
        }
        if(calendarPreorder.getValue() == null){
            BlinkUtils.wrong(calendarPreorder);
            return false;
        }
        return true;
    }
}
