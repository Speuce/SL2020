package main.java.lucia.fxml.controllers.impl.main.tabs.employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.employee.Employee;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.EnterNumberPane;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

import java.awt.*;
import java.text.DecimalFormat;


public class EmployeeCreateAccountPaneController {

    @FXML
    private Pane employeeCreateAccountPane;

    @FXML
    private JFXTextField employeeCreateID;

    @FXML
    private JFXTextField employeeCreateName;

    @FXML
    private JFXPasswordField employeeCreateConfirm;

    @FXML
    private JFXPasswordField employeeCreatePassword;

    @FXML
    private JFXTextField employeeCreateAddress;

    @FXML
    private JFXButton employeeCreateCreateButton;

    @FXML
    private JFXCheckBox employeeCreateServer;

    /**
     * the Parent pane controller
     */
    private EmployeePane e;

    /**
     * Set the parent pane
     * @param e the parent EmployeePane controller
     */
    public void setEmployeePane(EmployeePane e){
        this.e = e;
    }

    @FXML
    public void initialize(){
        employeeCreateID.setTextFormatter(buildNumericFormatList(5));
    }


    @FXML
    void onPassboxClicked(MouseEvent event) {
        ((JFXPasswordField)event.getSource()).clear();
    }

    @FXML
    void onCreateButtonClicked(MouseEvent event) {
        if(employeeCreateID.getText().equals("") || e.getEmployeeMap().containsKey(Integer.parseInt(employeeCreateID.getText()))){
            wrong(employeeCreateID);
            return;
        }
        if(employeeCreateName.getText().equals("")){
            wrong(employeeCreateName);
            return;
        }
        if(employeeCreateAddress.getText().equals("")){
            wrong(employeeCreateAddress);
            return;
        }
        if(!employeeCreateConfirm.getText().equals(employeeCreatePassword.getText()) || employeeCreateConfirm.getText().equals("")){
            wrong(employeeCreateConfirm);
            wrong(employeeCreatePassword);
            return;
        }
        create();
    }

    /**
     * Sends the employee to the server and saves it in the database
     * @param e the {@link Employee} to save
     */
    public void sendSaveEmployeeMessage(Employee e){
        OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(OpcodeConstants.NEW_EMPLOYEE_OPCODE);
        out.setJsonRequest(GsonTypeFactory.EMPLOYEE_GSON.toJson(e));
        PacketSender.getCurrentPacketSender().sendMessage(out);
    }

    private void create(){
        Employee e = new Employee(employeeCreateName.getText(), Integer.parseInt(employeeCreateID.getText()),
                employeeCreatePassword.getText(),employeeCreateServer.isSelected());
        //TODO send server update
        sendSaveEmployeeMessage(e);
        employeeCreateID.setStyle("-fx-background-color: #00AA00");
        employeeCreateName.setStyle("-fx-background-color: #00AA00");;
        employeeCreateAddress.setStyle("-fx-background-color: #00AA00");
        employeeCreateConfirm.setStyle("-fx-background-color: #00AA00");
        employeeCreatePassword.setStyle("-fx-background-color: #00AA00");
        AsynchronousTaskService.scheduleProcessMils(() -> {
            employeeCreateID.setStyle("-fx-background-color: #FFFFFF");
            employeeCreateName.setStyle("-fx-background-color: #FFFFFF");;
            employeeCreateAddress.setStyle("-fx-background-color: #FFFFFF");
            employeeCreateConfirm.setStyle("-fx-background-color: #FFFFFF");
            employeeCreatePassword.setStyle("-fx-background-color: #FFFFFF");
            employeeCreateID.clear();
            employeeCreateName.clear();
            employeeCreateAddress.clear();
            employeeCreateConfirm.clear();
            employeeCreatePassword.clear();
            employeeCreateServer.setSelected(false);
        }, 300L);
    }

    /**
     * Builds a formatter to limit text input to a set number of digits (digits only)
     * @param maxLength the maximum # of numbers to have
     * @return a textformatter to limit the # of digits to {@code maxLength}
     */
    private TextFormatter<Object> buildNumericFormatList(int maxLength) {
        DecimalFormat format = new DecimalFormat("#");
        return new TextFormatter<>(change -> {
            return EnterNumberPane.getChange(maxLength, format, change);
        });
    }

    private void wrong(Node f){ EmployeePane.blink(f, Color.white, Color.decode("#AA0000"));
    }

    private void right(Node f){
        EmployeePane.blink(f, Color.white, Color.decode("#00AA00"), 1, 300L);
    }

}

