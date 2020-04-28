package main.java.lucia.fxml.controllers.impl.main.tabs.Driver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;

public class DriverCredsController {

    @FXML
    private Pane driverCredsFrame;

    @FXML
    private JFXButton loginEmployee1;

    @FXML
    private JFXPasswordField enterPasswordEmployee1;

    @FXML
    private JFXTextField enterUsernameEmployee1;


    @FXML
    public void login(Event event) {
        DriverFramePane controller = (DriverFramePane) ControllerMap.getController(ControllerType.DRIVER_FRAME_PANE_CONTROLLER);
        controller.loggedIn(event);
    }
}
