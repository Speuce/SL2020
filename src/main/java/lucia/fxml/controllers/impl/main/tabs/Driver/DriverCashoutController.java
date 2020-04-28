package main.java.lucia.fxml.controllers.impl.main.tabs.Driver;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.event.KeyEvent;

public class DriverCashoutController {


    @FXML
    private Pane driverCashout;

    @FXML
    private Label cashSalesCashout;

    @FXML
    private Label floatCashout;

    @FXML
    private Label tippoolCashout;

    @FXML
    private Label delChargeCashout;

    @FXML
    private Label oversCashout;

    @FXML
    private Label errandsCashout;

    @FXML
    private Label tipsCashout;

    @FXML
    private Label wagesCashout;

    @FXML
    private Label hoursCashout;

    @FXML
    private Label finalOweCashout;

    @FXML
    private Label miscCashout;

    @FXML
    private GridPane paymentInfoGridCashout;

    @FXML
    private GridPane ordersGridCashout;

    @FXML
    private GridPane totalsGridCashout;

    @FXML
    private Pane enterTipsDriver;

    @FXML
    private GridPane enterTipGrid;

    @FXML
    private Pane enterTipPane;

    @FXML
    private JFXTextField enterTipAmnt;

    @FXML
    private Pane changePaymentMethodDriver;

    @FXML
    private JFXTextField currentPaymentMethod;

    @FXML
    private JFXComboBox<?> newPaymentMethod;

    @FXML
    private Pane enterErrandsPane;

    @FXML
    private JFXTextField errandDesc;

    @FXML
    private Label totalErrandAmount;

    @FXML
    private Pane enterMiscPane;

    @FXML
    private JFXTextField miscDesc;

    @FXML
    private Label totalMiscAmount;

    @FXML
    private JFXTextField miscPrice;

    @FXML
    void acceptErrand(ActionEvent event) {

        enterErrandsPane.setVisible(false);
    }

    @FXML
    void acceptMisc(ActionEvent event) {

        enterMiscPane.setVisible(false);
    }

    @FXML
    void acceptPaymentMethod(ActionEvent event) {

        changePaymentMethodDriver.setVisible(false);
    }

    @FXML
    void acceptTips(ActionEvent event) {

        enterTipsDriver.setVisible(false);
    }

    @FXML
    void calcErrand(ActionEvent event) {

    }

    @FXML
    void calculateMisc(ActionEvent event) {

    }

    @FXML
    void calcTip(ActionEvent event) {

    }

    @FXML
    void changePayment(ActionEvent event) {

        changePaymentMethodDriver.setVisible(true);
    }

    @FXML
    void enterErrands(ActionEvent event) {

        enterErrandsPane.setVisible(true);
    }

    @FXML
    void enterMisc(ActionEvent event) {

        enterMiscPane.setVisible(true);
    }

    @FXML
    void enterTips(ActionEvent event) {

        enterTipsDriver.setVisible(true);
    }

}

