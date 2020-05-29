package main.java.lucia.fxml.controllers.impl.main.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.payment.paymentmethods.PaymentType;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.Utils.GridHighlighter;
import main.java.lucia.fxml.controllers.impl.main.tabs.employee.EmployeeLoginPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.viewOrder.OrderInfoController;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller which controls the transfer order pane
 *
 * @author Matt Kwiatkowski
 */

/**
 * Controller for the view orders pane
 * @author Matt Kwiatkowski
 */
public class ViewOrdersPane implements Controller {

  @FXML
  private Pane viewOrdersPane;

  @FXML
  private Pane viewOrdersInfoPane;

  @FXML
  private JFXButton viewOrderButton;

  @FXML
  private Label viewOrderInfo1;

  @FXML
  private Label viewOrderInfo2;

  @FXML
  private Label viewOrderInfo3;

  @FXML
  private JFXTextField searchBox;

  @FXML
  private Label searchLabel;

  @FXML
  private JFXButton forward7;

  @FXML
  private JFXButton back7;

  @FXML
  private AnchorPane viewOrdersAnchor;

  @FXML
  private AnchorPane orderInfoAnchor;

  @FXML
  private GridPane viewOrderGridpane;

  @FXML
  private AnchorPane orderDetailsPane;

  @FXML
  private OrderInfoController orderDetailsPaneController;

  /**
   * The highlighter for the view orders grid
   */
  private GridHighlighter highlighter;

  /**
   * a variable to store the font that will be used
   */
  public Font pt20Font, pt25Font;

  @FXML
  private void initialize() {
    highlighter = new GridHighlighter(viewOrderGridpane, "#D3D3D3");
    ControllerMap.addController(ControllerType.VIEW_ORDERS_PANE_CONTROLLER, this);
    highlighter.addSelectListener(r -> {
      if (r)
        orderSelected(OrderManager.INSTANCE.getAllOrders()[highlighter.getHighLightedRow()+1]);
      else
        unselected();
    });
    pt20Font = new Font("Calibri", 20);
    pt25Font = new Font("Calibri", 25);
    orderDetailsPaneController.setParent(this);
    searchBox.setTextFormatter(EmployeeLoginPaneController.buildNumericFormatList(10));

  }

  /**
   * Used for opening this pane.
   */
  public void open(){
    resetConstraints();
    orderInfoAnchor.setVisible(false);
    viewOrdersAnchor.setVisible(true);
    viewOrdersInfoPane.setVisible(false);
    searchBox.setVisible(true);
    searchLabel.setVisible(true);
    drawGrid(OrderManager.INSTANCE.getAllOrders(), OrderManager.getApproxNumOrders()+1);
    searchBox.setText("");
  }

  /**
   * Resets the row constraints for the view order gridpane
   */
  private void resetConstraints(){
    RowConstraints r = viewOrderGridpane.getRowConstraints().get(0);
    viewOrderGridpane.getRowConstraints().clear();
    viewOrderGridpane.getRowConstraints().add(r);
    for(int i = 0; i<= OrderManager.getApproxNumOrders(); i++){
      viewOrderGridpane.getRowConstraints().add(r);
    }
  }

  /**
   * Called when an order is selected
   */
  private void orderSelected(Order r) {
    orderDetailsPaneController.setOrder(r);
    viewOrderInfo2.setVisible(false);
    viewOrderInfo3.setVisible(false);
    viewOrdersInfoPane.setVisible(true);
    viewOrderInfo1.setVisible(true);
    viewOrderButton.setVisible(true);
    if (r.isDelivery()) {
      if (r.isTakenOut()) {
        viewOrderInfo2.setVisible(true);
        viewOrderInfo1.setText("Driver: " + EmployeePane.instance.getEmployeeName(r.getServer()));
        viewOrderInfo2.setText("Time out: " + EmployeePane.getTimeFormat().format(r.getTimeOut()));
      } else {
        viewOrderInfo1.setText("Order not taken out.");
      }
    } else {
      if(r.getServer() > 0){
        viewOrderInfo1.setText("Server: " + EmployeePane.instance.getEmployeeNameShort(r.getServer()));
      }else{
        viewOrderInfo1.setText("Not paid yet.");
      }

    }
  }

  /**
   * Puts everything in the gridpane
   */
  private void drawGrid(Order[] orders, int max){
    int row = 0;
    viewOrderGridpane.getChildren().clear();
    highlighter.reset();
    for(int i = 0; i < max; i++){
      Order r = orders[i];
      if(r == null){
        continue;
      }
      Label orderNum = new Label(" "+ r.getOrderNumber() + "");
      registerItem(orderNum, 0);
      viewOrderGridpane.add(orderNum, 0, row);

      Label pu;
      if(r.isDelivery()){
        pu = new Label(" Delivery");
      }else{
        pu = new Label(" Pickup");
      }
      registerItem(pu, 1);
      viewOrderGridpane.add(pu, 1, row);

      Label nameOrAddress = null;
      if(r.isDelivery()){
        nameOrAddress = new Label(" "+ r.getCustomerDetails().getAddress().toString());
      }else{
        nameOrAddress = new Label(" "+ r.getCustomerDetails().getName());
      }
      registerItem(nameOrAddress, 2);
      viewOrderGridpane.add(nameOrAddress, 2, row);


      Label phoneNum = new Label(" "+ r.getCustomerDetails().getPhoneNumberFormatted());
      registerItem(phoneNum, 3);
      viewOrderGridpane.add(phoneNum, 3, row);

      Label time = new Label(" "+ r.getFormattedSet24Time());
      registerItem(time, 4);
      viewOrderGridpane.add(time, 4, row);


      Label cost = new Label(" "+ NumberFormat.getCurrencyInstance().format(r.getAllTotal()));
      registerItem(cost, 5);
      viewOrderGridpane.add(cost, 5, row);

      if(r.getPaymentType() != PaymentType.UNPAID){
        Label paytype = new Label(" "+ r.getPaymentType().getDisplayCode());
        registerItem(paytype, 6);
        viewOrderGridpane.add(paytype, 6, row);
      }else{
        Label paytype = new Label(" ");
        registerItem(paytype, 6);
        viewOrderGridpane.add(paytype, 6, row);
      }
      row++;
    }
  }

  private void registerItem(Label s, int col){
    double width = viewOrderGridpane.getColumnConstraints().get(col).getPrefWidth();
    double height = viewOrderGridpane.getRowConstraints().get(0).getPrefHeight();
    s.setPrefSize(width, height);
    s.setFont(pt20Font);
    registerNode(s);
  }

  private void unselected() {
    viewOrdersInfoPane.setVisible(false);
  }

  @FXML
  private void viewOrder(MouseEvent event) {
    orderDetailsPane.setVisible(true);
    orderInfoAnchor.setVisible(true);
    viewOrdersInfoPane.setVisible(false);
    viewOrdersAnchor.setVisible(false);
    orderDetailsPaneController.open();
    searchBox.setVisible(false);
    searchLabel.setVisible(false);
  }

  private List<Order> search(String number){
      List<Order> display = new ArrayList<>();
      String searchString = number;
    for(int i = 0; i <= OrderManager.getApproxNumOrders(); i++){
        Order r = OrderManager.INSTANCE.getAllOrders()[i];
        if(r == null){
          continue;
        }
        if(r.getCustomerDetails().getPhoneNumber().contains(searchString)){
          display.add(r);
        }
      }
    return display;
  }
  public int matches(List<String> searches, String search){
    int match = 0;
    for(String s: searches){
      match += (search.contains(s)) ? 1 : 0;
    }
    return match;
  }

  @FXML
  private void searchTyped(KeyEvent e){
      List<Order> find = search(searchBox.getText());
      Order[] o = find.toArray(new Order[find.size()]);
      drawGrid(o, find.size());
  }
  private void registerNode(Node n){
    highlighter.registerNode(n);
  }

  @Override
  public ControllerType getType() {
    return ControllerType.VIEW_ORDERS_PANE_CONTROLLER;
  }

  public void close() {
    open();
  }

  public Object getCurrentItem() {
    return OrderManager.INSTANCE.getFromOrderNumber(highlighter.getHighLightedRow());
  }
}