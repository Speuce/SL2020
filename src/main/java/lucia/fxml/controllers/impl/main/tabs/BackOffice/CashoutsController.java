package main.java.lucia.fxml.controllers.impl.main.tabs.BackOffice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CashoutsController {

    @FXML
    private Pane cashoutsPane;

    @FXML
    private JFXButton cashoutServerButton;

    @FXML
    private JFXButton cashoutDriverButton;

    @FXML
    private JFXButton paidinoutButton;

    @FXML
    private JFXButton paystaffButton;

    @FXML
    private JFXButton assignfloatsButton;

    @FXML
    private Pane paidInOut;

    @FXML
    private JFXComboBox<?> serverList;

    @FXML
    private JFXComboBox<?> reasonList;

    @FXML
    private GridPane cashinoutSummaryGrid;

    @FXML
    private Pane payStaff;

    @FXML
    private JFXComboBox<?> serverListPay;

    @FXML
    private JFXTextField currentTotalHours;

    @FXML
    private JFXTextField newTotalHours;

    @FXML
    private Pane cashoutDriver;

    @FXML
    private Pane driverCashout;

    @FXML
    private GridPane viewOrdersGrid;

    @FXML
    private GridPane paymentInfoGrid;

    @FXML
    private GridPane totalsGrid;

    @FXML
    private Pane viewTipsDriver;

    @FXML
    private GridPane viewTipsGrid;

    @FXML
    private JFXTextField currentTipAmount;

    @FXML
    private JFXTextField newTipAmount;

    @FXML
    private Pane changePaymentMethodDriver;

    @FXML
    private JFXTextField currentPaymentTextField;

    @FXML
    private JFXComboBox<?> newMethodPayment;

    @FXML
    private Pane cashoutServer;

    @FXML
    private Pane driverCashout1;

    @FXML
    private GridPane viewTipsServerGrid;

    @FXML
    private JFXTextField currentTipAmountServer;

    @FXML
    private JFXTextField newTipAmountServer;

    @FXML
    private Pane changePaymentMethodServer;

    @FXML
    private JFXTextField currentPaymentMethodServer;

    @FXML
    private JFXComboBox<?> newPaymentMethodServer;

    @FXML
    private Pane assignFloats;

    @FXML
    private JFXComboBox<?> serverFloatList;

    @FXML
    private JFXComboBox<?> serverAssignFloat;

    @FXML
    private JFXButton assignFloatServer;

    @FXML
    private JFXComboBox<?> driverFloatList;

    @FXML
    private JFXButton assignFloatDriverButton;

    @FXML
    void acceptChangePayment(ActionEvent event) {

    }

    @FXML
    void acceptPaymentMethodServer(ActionEvent event) {

    }

    @FXML
    void calcNewTip(ActionEvent event) {

    }

    @FXML
    void calcNewTipServer(ActionEvent event) {

    }

    @FXML
    void cashIn(ActionEvent event) {

    }

    @FXML
    void cashOut(ActionEvent event) {

    }

    @FXML
    void changePane(Event event) {
        JFXButton source = (JFXButton) event.getSource();
        if (source.equals(paidinoutButton))
            paidInOut.toFront();
        else if (source.equals(cashoutDriverButton))
            cashoutDriver.toFront();
        else if (source.equals(cashoutServerButton))
            cashoutDriverButton.toFront();
        else if (source.equals(paystaffButton))
            payStaff.toFront();
        else if (source.equals(assignfloatsButton))
            assignFloats.toFront();
    }

    @FXML
    void changePayment(ActionEvent event) {

    }

    @FXML
    void changePaymentServer(ActionEvent event) {

    }

    @FXML
    void confirmPay(ActionEvent event) {

    }

    @FXML
    void confirmPayInOut(ActionEvent event) {

    }

    @FXML
    void confirmTipDriver(ActionEvent event) {

    }

    @FXML
    void confirmTipServer(ActionEvent event) {

    }

    @FXML
    void fixHours(ActionEvent event) {

    }

    @FXML
    void viewTips(ActionEvent event) {

    }

    @FXML
    void viewTipsServer(ActionEvent event) {

    }

}
