package main.java.lucia.fxml.controllers.impl.main.tabs.BackOffice;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ViewController {

    @FXML
    private Pane viewPane;

    @FXML
    private Pane viewOrders;

    @FXML
    private Pane viewComplaints;

    @FXML
    private JFXButton viewOrdersButton;

    @FXML
    private JFXButton viewInventoryButton;

    @FXML
    private JFXButton viewComplaintsButton;

    @FXML
    private JFXButton viewVoidsButton;

    @FXML
    private Pane viewVoids;

    @FXML
    private Pane viewInventory;

    @FXML
    void changePane(Event event) {
        JFXButton source = (JFXButton)event.getSource();
        if(source.equals(viewOrdersButton))
            viewOrders.toFront();
        else if(source.equals(viewInventoryButton))
            viewInventory.toFront();
        else if(source.equals(viewComplaintsButton))
            viewComplaints.toFront();
        else if(source.equals(viewVoidsButton))
            viewVoids.toFront();
    }

}
