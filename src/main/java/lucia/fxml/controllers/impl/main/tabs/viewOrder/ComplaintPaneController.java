package main.java.lucia.fxml.controllers.impl.main.tabs.viewOrder;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import main.java.lucia.client.content.customer.Complaint;
import main.java.lucia.client.content.customer.ComplaintAction;
import main.java.lucia.client.content.employee.type.Employee;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;
import main.java.lucia.fxml.controllers.impl.main.tabs.employee.EmployeeLoginPaneController;

import java.awt.*;
import java.util.Arrays;

/**
 * The controller for the complaint pane
 * @author Matt Kwiatkowski
 */
public class ComplaintPaneController {
    @FXML
    private Pane complaintInfoPane;

    @FXML
    private ChoiceBox<String> actionTakenBox;

    @FXML
    private JFXTextField complaintTitle;

    @FXML
    private JFXTextArea complaintTextArea;

    @FXML
    private JFXTextField actionInfoBox;

    @FXML
    private Label employeeNameLabel;

    @FXML
    private Label actionInfoLabel;

    @FXML
    private Pane complaintLoginPane;

    @FXML
    private Pane complaintPane;

    @FXML
    private JFXTextField employeeNumberBox;

    @FXML
    private Label noPermissionMessage;

    /**
     * The current complaint that is being delt with in this pane
     */
    private Complaint currentComplaint;

    private ParentController parent;

    @FXML
    private void initialize(){
        employeeNumberBox.setTextFormatter(EmployeeLoginPaneController.buildNumericFormatList(5));
        addTextLimiter(complaintTitle, 30);
        addTextLimiter(actionInfoBox, 50);
        employeeNumberBox.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                checkEmployee();
            }
        });
        addEnterListener(complaintTitle, complaintTextArea);
        Platform.runLater(() -> {
            complaintTextArea.setFont(new Font("Calibri", 20));
            complaintTextArea.setCache(false);
            if(!complaintTextArea.getChildrenUnmodifiable().isEmpty()){
                Region sp = (Region)complaintTextArea.getChildrenUnmodifiable().get(0);
                sp.setCache(false);
                for (Node n : sp.getChildrenUnmodifiable()) {
                    n.setCache(false);
                }
            }else{
                MLogger.logError("Something is fishy in ComplaintPaneCntroller init.");
            }

        });
    }

    /**
     * Opens this pane
     * @param r The {@link Complaint} to view
     * @param bypassEmployee true if this pane should bypass the employee logn part
     */
    public void open(Complaint r, boolean bypassEmployee){
        currentComplaint = r;
        loggedEmployee = null;

        if(bypassEmployee){
            accessGranted();
            return;
        }
        employeeNumberBox.clear();
        employeeNumberBox.requestFocus();
        complaintPane.setVisible(true);
        complaintLoginPane.setVisible(true);
        complaintInfoPane.setVisible(false);
    }

    public void setParent(ParentController p){
        this.parent = p;
    }

    private void checkEmployee(){
        if(employeeNumberBox.getText().equals("")){
            wrong(employeeNumberBox);
            return;
        }
        Integer id = 0;
        try{
            id = Integer.parseInt(employeeNumberBox.getText());
        }catch(NumberFormatException e){
            wrong(employeeNumberBox);
            employeeNumberBox.clear();
            employeeNumberBox.requestFocus();
            return;
        }
        if(!EmployeePane.instance.getEmployeeMap().containsKey(id)){
            wrong(employeeNumberBox);
        }
        EmployeePane.getEmployee(id, c -> {
            if(c == null){
                wrong(employeeNumberBox);
                return;
            }else{
                loggedEmployee = c;
                accessGranted();
            }
        });
    }

    private void saveComplaint(){
        if(complaintTitle.getText().equals("")){
            wrong(complaintTitle);
            complaintTitle.requestFocus();
            return;
        }
        if(complaintTextArea.getText().equals("")){
            complaintTextArea.requestFocus();
            wrong(complaintTextArea);
            return;
        }
        if(actionTakenBox.getValue().equals("")){
            wrong(actionTakenBox);
            return;
        }
        ComplaintAction ac = ComplaintAction.getFromDisplay(actionTakenBox.getValue());
        if(ac.hasAdditionalInfo()){
            if(actionInfoBox.getText().equals("")){
                actionInfoBox.requestFocus();
                wrong(actionInfoBox);
                return;
            }else{
                currentComplaint.setComplaintActionInfo(actionInfoBox.getText());
            }
        }
        currentComplaint.setComplaintTitle(complaintTitle.getText());
        currentComplaint.setComplaintDetails(complaintTextArea.getText());
        currentComplaint.setActionTaken(ac);
       // ((Order)parent.getCurrentItem()).setApplicableComplaint(currentComplaint);
        //TODO update server about complaint
    }

    /**
     * The employee making this complaint
     */
    private Employee loggedEmployee = null;

    private void accessGranted(){
        complaintLoginPane.setVisible(false);
        complaintInfoPane.setVisible(true);
        complaintTitle.clear();
        complaintTextArea.clear();
        actionInfoBox.setVisible(false);
        actionInfoBox.clear();
        actionInfoLabel.setVisible(false);
        complaintTitle.requestFocus();
        if(loggedEmployee != null){
            loggedEmployee.getAllowableAction().stream().forEach(a -> actionTakenBox.getItems().add(a.getDisplay()));
        }else{
            Arrays.asList(ComplaintAction.values()).stream().forEach(a -> actionTakenBox.getItems().add(a.getDisplay()));
        }
        if(currentComplaint.getActionTaken() != null){
            actionTakenBox.setValue(currentComplaint.getActionTaken().getDisplay());
            if(currentComplaint.getActionTaken().hasAdditionalInfo()){
                actionInfoLabel.setVisible(true);
                actionInfoBox.setVisible(true);
                actionInfoBox.setText(currentComplaint.getComplaintActionInfo());
            }
        }
        if(currentComplaint.getComplaintTitle() != null && !currentComplaint.getComplaintTitle().equals("")){
            complaintTitle.setText(currentComplaint.getComplaintTitle());
        }
        if(currentComplaint.getComplaintDetails() != null && !currentComplaint.getComplaintDetails().equals("")){
            complaintTextArea.setText(currentComplaint.getComplaintDetails());
        }
        if(currentComplaint.getEmployeeId() > 0){

            String employeeName = EmployeePane.instance.getEmployeeName(currentComplaint.getEmployeeId());
            if(employeeName != null){
                employeeNameLabel.setText(employeeName);
            }else{
                employeeNameLabel.setText(loggedEmployee.getShorterName());
            }
        }else{
            employeeNameLabel.setText(loggedEmployee.getShorterName());
        }
    }

    /**
     * For opening a new complaint
     * @param orderId the Id of the order that this complaint is about
     */
    public void open(int orderId, int customerRowNum){
        open(new Complaint(orderId, customerRowNum), false);
        OrderManager.INSTANCE.getFromOrderNumber(orderId).setApplicableComplaint(this.currentComplaint);
    }

    @FXML
    void continueButton(MouseEvent event) {
        checkEmployee();
    }

    @FXML
    void save(MouseEvent event) {
        saveComplaint();
        parent.close();
    }

    /**
     * Closes the complaint area.
     */
    private void closeComplaintAre(){

    }


    /**
     * Builds a charachter-limiting text change listener
     * @param tf the textfield to add it to
     * @param maxLength the maximum number of characters in the box
     * retrieved from: https://stackoverflow.com/questions/15159988/javafx-2-2-textfield-maxlength
     */
    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    private void addEnterListener(JFXTextField item, Node next){
        item.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                next.requestFocus();
            }
        });
    }

    private void wrong(Node f){ EmployeePane.blink(f, Color.decode("#FFFFFF"), Color.decode("#AA0000"));
    }

}

