package main.java.lucia.fxml.controllers.impl.main.tabs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.map.NodeMap;

/**
 * The controller which controls the settings pane
 *
 * @author Brett Downey
 */
public class SettingsPane implements Initializable, Controller {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ControllerMap.addController(ControllerType.SETTINGS_PANE_CONTROLLER, this);
  }

  @Override
  public ControllerType getType() {
    return ControllerType.SETTINGS_PANE_CONTROLLER;
  }
}