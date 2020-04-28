package main.java.lucia.fxml.controllers.impl.main.tabs.employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.employee.Employee;
import main.java.lucia.client.protocol.message.impl.employee.GetEmployeeMapMessage;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParsePosition;

/**
 * Controller for the login page of the employee section
 * @author Matt Kwiatkowski
 */
public class EmployeeLoginPaneController implements Controller {


    @FXML
    private Pane employeeLoginPane;

    @FXML
    private JFXButton loginEmployee1;

    @FXML
    private JFXPasswordField enterPasswordEmployee1;

    @FXML
    private JFXTextField enterUsernameEmployee1;

    /**
     * the Parent pane controller
     */
    private EmployeePane e;

    /**
     * When trying to login, if the employee id is valid, found employee will be set
     */
    private Employee foundEmployee;



    @FXML
    public void initialize(){
        enterUsernameEmployee1.setTextFormatter(buildNumericFormatList(5));
        enableLoginButton();
    }

    /**
     * Called when confirm login button is clicked
     */
    @FXML
    void confirmLogin(MouseEvent event) {
        //TODO server passwrod check
        if(!loginButton){
            return;
        }

        if(enterUsernameEmployee1.getText().equals("")){
            wrong(enterUsernameEmployee1);
            return;
        }
        Employee testEmployee = e.getTestEmployee();
        if(e.isTesting()){
            if(Integer.parseInt(enterUsernameEmployee1.getText()) == testEmployee.getEmployeeID()){
                testEmployee.testPass( enterPasswordEmployee1.getText(), b ->{
                    if(b){
                        e.setCurrentEmployee(e.getTestEmployee());
                        Platform.runLater(() ->e.accessGranted());
                        enableLoginButton();
                    }else{
                        wrong(enterPasswordEmployee1);
                        System.out.println("Wrong Password!! :(((");
                        enableLoginButton();
                    }
                });
                return;
            }
        }else if(foundEmployee != null && Integer.parseInt(enterUsernameEmployee1.getText()) == foundEmployee.getEmployeeID()){
            disableLoginButton();
            testPass();
            return;
            //Check for valid ID, get employee info if valid
        }else{
            disableLoginButton();
            if(e.getEmployeeMap() != null){
                if(!e.getEmployeeMap().containsKey(Integer.parseInt(enterUsernameEmployee1.getText()))){
                    wrong(enterUsernameEmployee1);
                    return;
                }
            }else{
                GetEmployeeMapMessage.sendMapRequest();
                //GetEmployeeMessage.sendEmployeeRequest(Integer.parseInt(enterUsernameEmployee1.getText()));
                EmployeePane.getEmployee(Integer.parseInt(enterUsernameEmployee1.getText()), this::setFoundEmployee);
                disableLoginButton();
            }
        }
    }


    /**

    /**
     * Builds a formatter to limit text input to a set number of digits (digits only)
     * @param maxLength the maximum # of numbers to have
     * @return a textformatter to limit the # of digits to {@code maxLength}
     */
    public static TextFormatter<Object> buildNumericFormatList(int maxLength) {
        DecimalFormat format = new DecimalFormat("#");
        return new TextFormatter<>(change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(change.getControlNewText(), parsePosition);
            if (object == null || parsePosition.getIndex() < change.getControlNewText().length()) {
                return null;
            } else {
                /* Verify it's not breaking our text format rules */
                if (change.getControlNewText().length() > maxLength) {
                    change.setText(StringUtils.EMPTY);
                }
                return change;
            }
        });
    }

    private void wrong(Node f){ EmployeePane.blink(f, Color.white, Color.decode("#AA0000"));
    }

    /**
     * Clears username and password fields.
     */
    public void clearFields(){
        this.enterPasswordEmployee1.clear();
        this.enterUsernameEmployee1.clear();
    }

    /**
     * Set the parent pane
     * @param e the parent EmployeePane controller
     */
    public void setEmployeePane(EmployeePane e){
        this.e = e;
    }

    public Employee getFoundEmployee() {
        return foundEmployee;
    }

    private void setFoundEmployee(Employee foundEmployee) {
        if(foundEmployee == null){
            wrong(enterUsernameEmployee1);
            enableLoginButton();
        }else{
            this.foundEmployee = foundEmployee;
            System.out.println("found employee!!!! Testing pass...");
            testPass();
        }

    }

    /**
     * Used to prevent spam clicking of the login button
     */
    private boolean loginButton;

    public void enableLoginButton(){
        loginButton = true;
    }
    public void disableLoginButton(){
        loginButton = false;
    }


    private void testPass(){
        foundEmployee.testPass( enterPasswordEmployee1.getText(), b ->{
            if(b){
                e.setCurrentEmployee(foundEmployee);
                delFoundEmployee();
                Platform.runLater(() ->e.accessGranted());
                enableLoginButton();
            }else{
                wrong(enterPasswordEmployee1);
                System.out.println("Wrong Password!! :(((");
                enableLoginButton();
            }
        });
    }
    private void delFoundEmployee(){
        foundEmployee = null;
    }
    public void employeeNotFound(){
        wrong(enterUsernameEmployee1);
    }

    @Override
    public ControllerType getType() {
        return ControllerType.EMPLOYEE_LOGIN_CONTROLLER;
    }
}
