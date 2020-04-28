package main.java.lucia.fxml.controllers.impl.main.tabs.BackOffice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ReportsController {
    @FXML
    private Pane reportsPane;

    @FXML
    private JFXButton salesReportButton;

    @FXML
    private JFXButton gstpstButton;

    @FXML
    private JFXButton foodReportButton;

    @FXML
    private JFXButton paidinoutButton;

    @FXML
    private JFXButton driverButton;

    @FXML
    private JFXButton pickupReportButton;

    @FXML
    private JFXButton deliveryButton;

    @FXML
    private JFXButton discountButton;

    @FXML
    private JFXButton wageButton;

    @FXML
    private JFXButton employeeReportButton;

    @FXML
    private Pane salesReport;

    @FXML
    private JFXDatePicker firstDateSale;

    @FXML
    private JFXDatePicker secondDateSale;

    @FXML
    private JFXComboBox<?> saleTypeList;

    @FXML
    private Pane employeeReport;

    @FXML
    private JFXDatePicker firstDateEmployee;

    @FXML
    private JFXDatePicker secondDateEmployee;

    @FXML
    private JFXComboBox<?> employeeTypeList;

    @FXML
    private Pane wageReport;

    @FXML
    private JFXDatePicker firstDateWage;

    @FXML
    private JFXDatePicker secondDateWage;

    @FXML
    private JFXComboBox<?> wageType;

    @FXML
    private Pane discountReport;

    @FXML
    private JFXDatePicker firstDateDiscount;

    @FXML
    private JFXDatePicker secondDateDiscount;

    @FXML
    private JFXComboBox<?> discountTypeList;

    @FXML
    private Pane deliveryReport;

    @FXML
    private JFXDatePicker firstDateDelivery;

    @FXML
    private JFXDatePicker secondDateDelivery;

    @FXML
    private JFXComboBox<?> deliveryReportType;

    @FXML
    private Pane pickupReport;

    @FXML
    private JFXDatePicker firstDatePickup;

    @FXML
    private JFXDatePicker secondDatePickup;

    @FXML
    private JFXComboBox<?> pickupList;

    @FXML
    private Pane driverReport;

    @FXML
    private JFXDatePicker firstDateDriver;

    @FXML
    private JFXDatePicker secondDateDriver;

    @FXML
    private JFXComboBox<?> driverList;

    @FXML
    private Pane paininoutReport;

    @FXML
    private JFXDatePicker firstDatePaidinout;

    @FXML
    private JFXDatePicker secondDatePaininout;

    @FXML
    private JFXComboBox<?> paidinoutList;

    @FXML
    private Pane foodReport;

    @FXML
    private JFXDatePicker firstDateFood;

    @FXML
    private JFXDatePicker secondDateFood;

    @FXML
    private JFXComboBox<?> foodTypeList;

    @FXML
    private Pane gstpst;

    @FXML
    private JFXDatePicker firstDateGstpst;

    @FXML
    private JFXDatePicker secondDategstpst;

    @FXML
    private JFXComboBox<?> gstpstTypeList;

    @FXML
    void calculateDateEmployee(ActionEvent event) {

    }

    @FXML
    void calculateDelivery(ActionEvent event) {

    }

    @FXML
    void calculateDiscount(ActionEvent event) {

    }

    @FXML
    void calculateDriver(ActionEvent event) {

    }

    @FXML
    void calculateFood(ActionEvent event) {

    }

    @FXML
    void calculatePaidinOut(ActionEvent event) {

    }

    @FXML
    void calculatePickupReport(ActionEvent event) {

    }

    @FXML
    void calculateSale(ActionEvent event) {

    }

    @FXML
    void calculateWage(ActionEvent event) {

    }

    @FXML
    void calculategstpst(ActionEvent event) {

    }

    @FXML
    void changePane(Event event) {
        JFXButton source = (JFXButton)event.getSource();
        if(source.equals(salesReportButton))
            salesReport.toFront();
        else if(source.equals(gstpstButton))
            gstpst.toFront();
        else if(source.equals(foodReportButton))
            foodReport.toFront();
        else if(source.equals(paidinoutButton))
            paininoutReport.toFront();
        else if(source.equals(driverButton))
            deliveryReport.toFront();
        else if(source.equals(pickupReportButton))
            pickupReport.toFront();
        else if(source.equals(deliveryButton))
            deliveryReport.toFront();
        else if(source.equals(discountButton))
            discountReport.toFront();
        else if(source.equals(wageButton))
            wageReport.toFront();
        else if(source.equals(employeeReportButton))
            employeeReport.toFront();
    }

}
