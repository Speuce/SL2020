/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.BackOffice;

import java.net.URL;
import java.security.KeyFactory;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.map.NodeMap;

/**
 * The controller which controls the back office
 *
 * @author Brett Downey
 */
public class BackOfficePane implements Controller {
  @FXML
  public Pane backOfficePane;

  @FXML
  public Pane employeeCredsPane;

  @FXML
  public Pane mainMenu;

  @FXML
  public Pane reportsPane;

  @FXML
  public Pane databasesPane;

  @FXML
  public Pane storeSettingsPane;

  @FXML
  public Pane employeesPane;

  @FXML
  public Pane cashoutsPane;

  @FXML
  public Pane accountingPane;

  @FXML
  public Pane viewPane;

  @FXML
  public JFXButton viewButton;

  @FXML
  public JFXButton storeSettingsButton;

  @FXML
  public JFXButton accountingButton;

  @FXML
  public JFXButton reportsButton;

  @FXML
  public JFXButton databasesButton;

  @FXML
  public JFXButton employeesButton;

  @FXML
  public JFXButton cashoutsButton;

  JFXButton[] menuButtons;

  @FXML
  public void initialize() {
    ControllerMap.addController(ControllerType.BACK_OFFICE_PANE_CONTROLLER, this);
    menuButtons = new JFXButton[]{viewButton, storeSettingsButton, accountingButton, reportsButton, databasesButton, employeesButton, cashoutsButton};

    mainMenu.toFront();
    employeeCredsPane.toFront();
  }

  @Override
  public ControllerType getType() {
    return ControllerType.BACK_OFFICE_PANE_CONTROLLER;
  }

  @FXML
  public void changePane(Event event) {
    JFXButton source = (JFXButton)event.getSource();
      if(source.equals(viewButton))
        viewPane.toFront();
      else if(source.equals(storeSettingsButton))
        storeSettingsPane.toFront();
      else if(source.equals(accountingButton))
        accountingPane.toFront();
      else if(source.equals(reportsButton))
        reportsPane.toFront();
      else if(source.equals(databasesButton))
        databasesPane.toFront();
      else if(source.equals(employeesButton))
        employeesPane.toFront();
      else if(source.equals(cashoutsButton))
        cashoutsPane.toFront();
  }

  @FXML
  public void confirmLogin() {
    employeeCredsPane.toBack();
  }
}