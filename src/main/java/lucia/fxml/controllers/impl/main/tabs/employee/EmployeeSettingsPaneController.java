package main.java.lucia.fxml.controllers.impl.main.tabs.employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;

import java.awt.*;

/**
 * The controller for the settings pane in the employee pane
 * @author Matt Kwiatkowski
 */
public class EmployeeSettingsPaneController implements Controller {

    /**
     * the Parent pane controller
     */
    private EmployeePane e;

    @FXML
    private JFXPasswordField employeeSettingsCurrentPassword;

    @FXML
    private JFXPasswordField employeeSettingsConfirmPassword;

    @FXML
    private Pane employeeSettingsPane;

    @FXML
    private JFXTextField employeeSettingsAddress;

    @FXML
    private JFXPasswordField employeeSettingsNewPassword;

    @FXML
    private JFXButton employeeSettingsChangeAddress;

    // Called when the pane is opened
    public void open(){
        employeeSettingsAddress.setText(e.getCurrentEmployee().getAddress());
        employeeSettingsAddress.positionCaret(e.getCurrentEmployee().getAddress().length());
    }


    /**
     * Called when change address button is clicked in settings
     */
    @FXML
    void changeAddress(MouseEvent event) {
        if(employeeSettingsAddress.getText().equals("")){
            wrong(employeeSettingsAddress);
            return;
        }
        e.getCurrentEmployee().setAddress(employeeSettingsAddress.getText());
        right(employeeSettingsAddress);
    }

    /**
     * Used to clear pass field when clicked
     *
     */
    @FXML
    void onPassFieldClicked(MouseEvent event) {
        ((PasswordField)event.getSource()).clear();
    }

    /**
     * Called when change password button is clicked in settings
     */
    @FXML
    void changePassword(MouseEvent event) {
        if(!employeeSettingsNewPassword.getText().equals(employeeSettingsConfirmPassword.getText())){
            wrong(employeeSettingsConfirmPassword);
            wrong(employeeSettingsNewPassword);
            clearPassField();
            return;
        }
        e.getCurrentEmployee().testPass(employeeSettingsCurrentPassword.getText(), b ->{
            if(!b){
                wrong(employeeSettingsCurrentPassword);
                clearPassField();
                return;
            }else{
                e.getCurrentEmployee().changePassword(employeeSettingsNewPassword.getText());
                employeeSettingsNewPassword.setStyle("-fx-background-color: #00AA00");
                employeeSettingsCurrentPassword.setStyle("-fx-background-color: #00AA00");
                employeeSettingsConfirmPassword.setStyle("-fx-background-color: #00AA00");
                AsynchronousTaskService.scheduleProcessMils(() ->{
                    employeeSettingsConfirmPassword.setStyle("-fx-background-color: #ffffff");
                    employeeSettingsNewPassword.setStyle("-fx-background-color: #ffffff");
                    employeeSettingsCurrentPassword.setStyle("-fx-background-color: #ffffff");
                    employeeSettingsNewPassword.clear();
                    employeeSettingsConfirmPassword.clear();
                    employeeSettingsConfirmPassword.clear();
                }, 300L);
            }
        });
    }
    private void clearPassField(){
        employeeSettingsCurrentPassword.clear();
    }

    private void wrong(Node f){ EmployeePane.blink(f, Color.white, Color.decode("#AA0000"));
    }

    private void right(Node f){
        EmployeePane.blink(f, Color.white, Color.decode("#00AA00"), 1, 300L);
    }

    /**
     * Set the parent pane
     * @param e the parent EmployeePane controller
     */
    public void setEmployeePane(EmployeePane e){
        this.e = e;
    }

    @Override
    public ControllerType getType() {
        return null;
    }
}
