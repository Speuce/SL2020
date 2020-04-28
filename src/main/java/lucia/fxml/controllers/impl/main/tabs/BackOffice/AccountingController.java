package main.java.lucia.fxml.controllers.impl.main.tabs.BackOffice;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class AccountingController {

    @FXML
    private Pane accountingPane;

    @FXML
    private JFXButton payrollButton;

    @FXML
    private JFXButton employeeReportButton;

    @FXML
    private JFXButton tippoolButton;

    @FXML
    private JFXButton paidinoutButton;

    @FXML
    private JFXButton gstpstButton;

    @FXML
    private Pane employeeReport;

    @FXML
    private Pane payroll;

    @FXML
    private Pane paidinoutreport;

    @FXML
    private Pane gstpst;

    @FXML
    private Pane tippool;

    @FXML
    void changePane(Event event) {
        JFXButton source = (JFXButton)event.getSource();
        if(source.equals(payrollButton))
            payroll.toFront();
        else if(source.equals(employeeReportButton))
            employeeReport.toFront();
        else if(source.equals(tippoolButton))
            tippool.toFront();
        else if(source.equals(paidinoutButton))
            paidinoutreport.toFront();
        else if(source.equals(gstpstButton))
            gstpst.toFront();
    }

}
