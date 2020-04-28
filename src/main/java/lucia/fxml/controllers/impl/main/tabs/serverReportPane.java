package main.java.lucia.fxml.controllers.impl.main.tabs;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.map.NodeMap;

/**
 * The controller that controls the manager pane
 *
 * @author zach and matt
 */
public class serverReportPane implements Initializable, Controller {


  @FXML
  private Pane managerPane;

  @FXML
  private JFXButton forward9;

  @FXML
  private JFXButton back9;

  @FXML
  private Pane loginPane;

  @FXML
  private JFXButton loginEmployee;

  @FXML
  private JFXPasswordField enterPasswordEmployee;

  @FXML
  private JFXTextField enterUsernameEmployee;

  @FXML
  private Pane driverReport;

  @FXML
  private Label cashSalesLabel;

  @FXML
  private Label floatLabel;

  @FXML
  private Label tippoolLabel;

  @FXML
  private Label delChargeLabel;

  @FXML
  private Label oversLabel;

  @FXML
  private Label errandsLabel;

  @FXML
  private Label tipsLabel;

  @FXML
  private Label wagesLabel;

  @FXML
  private Label hoursLabel;

  @FXML
  private Label finalOweLabel;

  @FXML
  private Label miscLabel;

  @FXML
  private GridPane paymentInfoGrid;

  @FXML
  private GridPane ordersGrid;

  @FXML
  private GridPane totalsGrid;

  @FXML
  private Pane changePaymentMethodPane;

  @FXML
  private Pane transferOrderPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ControllerMap.addController(ControllerType.SERVER_REPORT_PANE_CONTROLLER, this);
  }

  @Override
  public ControllerType getType() {
    return ControllerType.SERVER_REPORT_PANE_CONTROLLER;
  }

  @FXML
  void cashoutFinalize(ActionEvent event) {

  }

  @FXML
  void changePayment(ActionEvent event) {
    changePaymentMethodPane.setVisible(true);
  }

  @FXML
  void acceptChangePayment(ActionEvent event) {
    changePaymentMethodPane.setVisible(false);
  }

  @FXML
  void transferOrder(ActionEvent event) {
    transferOrderPane.setVisible(true);
  }

  @FXML
  void acceptTransfer(ActionEvent event) {
    transferOrderPane.setVisible(false);
  }

}
