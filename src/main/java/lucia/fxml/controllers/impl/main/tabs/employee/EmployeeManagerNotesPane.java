package main.java.lucia.fxml.controllers.impl.main.tabs.employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import main.java.lucia.client.content.employee.type.Employee;
import main.java.lucia.client.content.employee.type.Manager;
import main.java.lucia.client.content.employee.ManagerNote;
import main.java.lucia.client.protocol.message.impl.employee.GetEmployeeMapMessage;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;

import java.awt.*;

/**
 * Controller for the manager notes pane of the employee area
 * @author Matt Kwiatkowski
 */
public class EmployeeManagerNotesPane implements Controller {

    @FXML
    private Pane employeeManagerNotesPane;

    @FXML
    private JFXButton employeeMNotesDeleteButton;

    @FXML
    private ScrollPane employeeManagerNotesScrollPane;

    @FXML
    private GridPane employeeMNotesGridPane;

    @FXML
    private JFXButton employeeMNotesToggleButton;

    @FXML
    private Pane employeeMNotesCreatePane;

    @FXML
    private ChoiceBox<String> employeeMNotesSelectEmployee;

    @FXML
    private JFXButton employeeMNotesSendButton;

    @FXML
    private JFXTextArea employeeMNotesCreateContentArea;

    /**
     * the Parent pane controller
     */
    private EmployeePane e;

    @FXML
    void initialize(){
        employeeMNotesSelectEmployee.setStyle("-fx-font: 20px \"Serif\";");
    }

    @FXML
    void onManagerNotesSendNoteClicked(MouseEvent event) {
        //ToDO send note to mapped employee
        Employee send = e.getCurrentEmployee();
        ManagerNote n = new ManagerNote(this.employeeMNotesCreateContentArea.getText(), e.getCurrentEmployee().getName());
        send.addManagerNote(n);
        employeeMNotesCreateContentArea.clear();
        right(employeeMNotesCreateContentArea);
        Platform.runLater(() -> {
            employeeMNotesCreateContentArea.setCache(false);
            ScrollPane sp = (ScrollPane)employeeMNotesCreateContentArea.getChildrenUnmodifiable().get(0);
            sp.setCache(false);
            for (Node r : sp.getChildrenUnmodifiable()) {
                r.setCache(false);
            }
        });
    }

    private void right(Node f){
        EmployeePane.blink(f, Color.white, Color.decode("#00AA00"), 1, 300L);
    }

    /**
     * Tracks whether the manager notes create button has been toggled or not
     */
    private boolean toggled = false;

    @FXML
    void onManagerNoteToggleButtonClicked(MouseEvent event) {
        if(toggled){
            buttonGreen();
        }else{
            buttonYellow();
        }
    }

    /**
     * Called when the pane is opened
     */
    public void open(){
        this.updateManagernotesArea();
        GetEmployeeMapMessage.sendMapRequest();
        if(e.getCurrentEmployee() instanceof Manager){
            employeeMNotesCreateContentArea.clear();
            employeeMNotesToggleButton.setVisible(true);
            buttonGreen();
        }else{
            employeeMNotesToggleButton.setVisible(false);
            employeeMNotesCreatePane.setVisible(false);
            employeeManagerNotesScrollPane.setVisible(true);
        }
    }

    private void updateEmployeeList(){
        //TODO make server request
        employeeMNotesSelectEmployee.getItems().clear();
        employeeMNotesSelectEmployee.getItems().add(e.getCurrentEmployee().getName());
    }

    private void buttonGreen(){
        employeeMNotesToggleButton.setText("New Note");
        employeeMNotesToggleButton.setStyle("-fx-background-color: #00AA00");
        employeeMNotesCreatePane.setVisible(false);
        employeeManagerNotesScrollPane.setVisible(true);
        this.updateManagernotesArea();
        this.toggled = false;
    }

    private void buttonYellow(){
        employeeMNotesToggleButton.setText("My Notes");
        employeeMNotesToggleButton.setStyle("-fx-background-color: #e8e048");
        employeeMNotesCreatePane.setVisible(true);
        employeeManagerNotesScrollPane.setVisible(false);
        this.updateEmployeeList();
        this.toggled = true;
    }

    /**
     * Updates the manager notes area
     */
    private void updateManagernotesArea(){
        try{
            employeeMNotesGridPane.getChildren().clear();
        }catch(Exception ignored){}

        if(e.getCurrentEmployee().getManagerNotes().isEmpty()){
            employeeMNotesGridPane.setVisible(false);
            return;
        }
        employeeMNotesGridPane.setVisible(true);
        employeeMNotesGridPane.setGridLinesVisible(false);
        employeeMNotesGridPane.setVgap(10.0);
        int row = 0;
        employeeMNotesGridPane.getRowConstraints().add(new RowConstraints(120));
        for(ManagerNote f: e.getCurrentEmployee().getManagerNotes()){
            JFXTextArea text = e.getLabel(f.getContent());
            text.setPrefWidth(employeeMNotesGridPane.getWidth()/2);
            // text.setManaged(false);
            employeeMNotesGridPane.getRowConstraints().add(new RowConstraints(120));
            employeeMNotesGridPane.getRowConstraints().set(row, new RowConstraints(120));
            employeeMNotesGridPane.add(e.getLabel(f.getSender()), 0, row);
            employeeMNotesGridPane.add(e.getLabel(EmployeePane.getDateFormat().format(f.getClientTime().toLocalDate())), 1, row);
            employeeMNotesGridPane.add(text, 2, row);
            row+=1;
        }
        for (Node node : employeeMNotesGridPane.getChildren()) {

            node.setOnMouseEntered(e -> employeeMNotesGridPane.getChildren().forEach(c -> {
                Integer targetIndex = GridPane.getRowIndex(node);
                if(targetIndex.equals(highLightedRow)){
                    return;
                }
                if (GridPane.getRowIndex(c) == targetIndex) {
                    c.setStyle("-fx-background-color:#f9f3c5;");
                }
            }));
            node.setOnMouseExited(e -> employeeMNotesGridPane.getChildren().forEach(c -> {
                Integer targetIndex = GridPane.getRowIndex(node);
                if(targetIndex.equals(highLightedRow)){
                    return;
                }
                if (GridPane.getRowIndex(c) == targetIndex) {
                    c.setStyle("-fx-background-color:#ffffff;");
                }
            }));
            node.setOnMouseClicked(e -> {
                int targetIndex = GridPane.getRowIndex(node);
                for(Node n: employeeMNotesGridPane.getChildren()){
                    int index = GridPane.getRowIndex(n);
                    if(targetIndex == highLightedRow && index == targetIndex){
                        n.setStyle("-fx-background-color:#f9f3c5;");
                        continue;
                    }else{
                        if(index == targetIndex){
                            n.setStyle("-fx-background-color:#f0e381;");
                            continue;
                        }else if(index == highLightedRow) {
                            n.setStyle("-fx-background-color:#ffffff;");
                            continue;
                        }
                    }
                }
                if(targetIndex == highLightedRow){
                    setHighLightedRow(-1);
                }else{
                    setHighLightedRow(targetIndex);
                }
            });
        }

    }

    /**
     * This is the row that is currently highlighted
     */
    private int highLightedRow = -1;

    /**
     * Change the highlightedrow number
     * @param x the row highlighted
     * if x>=0, then the delete button shows
     * if x < 0, then the delte button is hidden
     */
    private void setHighLightedRow(int x){
        this.highLightedRow = x;
        if(x == -1){
            employeeMNotesDeleteButton.setVisible(false);
        }else{
            employeeMNotesDeleteButton.setVisible(true);
        }
    }

    @FXML
    void onManagerNotesDeleteClicked(MouseEvent event) {
        if(highLightedRow >= 0){
            int index = 0;
            ManagerNote delete = null;
            for(ManagerNote m: e.getCurrentEmployee().getManagerNotes()) {
                if (index == highLightedRow) {
                    delete = m;
                }
                index++;
            }
            e.getCurrentEmployee().getManagerNotes().remove(delete);
            setHighLightedRow(-1);
            updateManagernotesArea();
        }
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
        return ControllerType.SERVER_REPORT_PANE_CONTROLLER;
    }
}
