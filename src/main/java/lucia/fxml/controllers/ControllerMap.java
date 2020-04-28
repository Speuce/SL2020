package main.java.lucia.fxml.controllers;

import java.util.HashMap;
import javafx.fxml.Initializable;
import main.java.lucia.fxml.controllers.impl.Controller;

public class ControllerMap {

  private static final HashMap<ControllerType, Controller> CONTROLLERS = new HashMap<>();

  public static Controller getController(ControllerType key) {
    return CONTROLLERS.get(key);
  }

  public static void addController(ControllerType key, Controller controller) {
    CONTROLLERS.put(key, controller);
  }
}