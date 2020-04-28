package main.java.lucia.fxml.controllers.impl.main.tabs.BackOffice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.employee.Driver;
import main.java.lucia.client.content.employee.Employee;
import main.java.lucia.fxml.controllers.impl.main.tabs.Driver.DriverFramePane;

public class EmployeesController {

    @FXML
    private Pane employeesPane;

    @FXML
    private JFXButton viewEmployeesButton;

    @FXML
    private JFXButton tipsButton;

    @FXML
    private JFXButton scheduleButton;

    @FXML
    private JFXButton statisticsButton;

    @FXML
    private JFXButton dailyReportButton;

    @FXML
    private Pane dailyReport;

    @FXML
    private Pane statistics;

    @FXML
    private Pane schedule;

    @FXML
    private Pane viewEmployees;

    @FXML
    private JFXComboBox<?> employeeTypeList;

    @FXML
    private Pane createEmployeePane;

    @FXML
    private JFXComboBox<?> employeeType;

    @FXML
    private JFXTextField CreateEmployeeName;

    @FXML
    private JFXTextField createEmployeeID;

    @FXML
    private JFXTextField createPassword;

    @FXML
    private Pane tips;

    @FXML
    void changePane(Event event) {
        JFXButton source = (JFXButton)event.getSource();
        DriverFramePane.changePane(source, viewEmployeesButton, viewEmployees, tipsButton, tips, scheduleButton, schedule, statisticsButton, statistics, dailyReportButton, dailyReport);
    }

    @FXML
    public void createEmployee() {
        createEmployeePane.toFront();
    }

    @FXML
    public void finalizeEmployee() {

        createEmployeePane.toBack();
    }

}
