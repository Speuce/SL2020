package main.java.lucia.fxml.controllers.impl.main.tabs;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.Utils.GridHighlighter;
import main.java.lucia.fxml.controllers.impl.main.tabs.employee.EmployeeLoginPaneController;

/**
 * The controller which controls the view pre-orders pane
 *
 * @author Brett Downey
 */
public class ViewPreordersPane implements Controller {

  @FXML
  private Pane viewPreordersPane;

  @FXML
  private JFXButton forward2;

  @FXML
  private JFXButton back2;

  @FXML
  private Label searchLabel;

  @FXML
  private JFXTextField searchBox;

  @FXML
  private AnchorPane viewOrdersAnchor;

  @FXML
  private GridPane viewOrderGridpane;

  @FXML
  private JFXDatePicker firstDayPicker;

  @FXML
  private JFXDatePicker secondDayPicker;

  /**
   * The highlighter for the view orders grid
   */
  private GridHighlighter highlighter;

  /**
   * a variable to store the font that will be used
   */
  public Font pt20Font, pt25Font;

  private static DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("MMM dd");

  private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a");

  final Background markedBackground = new Background(new BackgroundFill(Color.rgb(0x99, 0xE6, 0x99),
          CornerRadii.EMPTY,
          Insets.EMPTY));


  @FXML
  private void initialize(){
    ControllerMap.addController(ControllerType.VIEW_PREODERS_PANE_CONTROLLER, this);
    firstDayPicker.setValue(LocalDate.now());
    secondDayPicker.setValue(LocalDate.now());
    searchBox.setTextFormatter(EmployeeLoginPaneController.buildNumericFormatList(10));
    searchBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e){
        updateGridWithSearches();
      }
    });

    /* To make the date picker not show certain dates */
    firstDayPicker.setDayCellFactory(dp -> new DateCell() {
      {
        addEventHandler(MouseEvent.MOUSE_EXITED, evt -> {
          if(getItem().compareTo(LocalDate.now()) < 0){
            Platform.runLater(() -> {
              setBackground(markedBackground);
            });
          }
        });
        addEventHandler(MouseEvent.MOUSE_ENTERED, evt -> {
          if(getItem().compareTo(LocalDate.now()) < 0){
            Platform.runLater(() -> {
              setBackground(markedBackground);
            });
          }
        });
      }
      @Override
      public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item.compareTo(LocalDate.now()) < 0) {
          setBackground(markedBackground);
        }
      }
    });
    secondDayPicker.setDayCellFactory(dp -> new DateCell() {
      {
        addEventHandler(MouseEvent.MOUSE_EXITED, evt -> {
          if(getItem().compareTo(firstDayPicker.getValue()) < 0){
            Platform.runLater(() -> {
              setBackground(markedBackground);
            });
          }
        });
        addEventHandler(MouseEvent.MOUSE_ENTERED, evt -> {
          if(getItem().compareTo(firstDayPicker.getValue()) < 0){
            Platform.runLater(() -> {
              setBackground(markedBackground);
            });
          }
        });
      }
      @Override
      public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item.compareTo(firstDayPicker.getValue()) < 0) {
          setBackground(markedBackground);
        }
      }
    });
//
    firstDayPicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
      @Override
      public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
          LocalDate newValue) {
        if(newValue.isAfter(secondDayPicker.getValue())){
          secondDayPicker.setValue(newValue);
        }
        updateGridWithSearches();
      }
    });

    secondDayPicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
      @Override
      public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
          LocalDate newValue) {
        if(newValue.isBefore(firstDayPicker.getValue())){
          firstDayPicker.setValue(newValue);
        }
        updateGridWithSearches();
      }
    });

        highlighter = new GridHighlighter(viewOrderGridpane, "#FFFFFF");
    ControllerMap.addController(ControllerType.VIEW_ORDERS_PANE_CONTROLLER, this);
    highlighter.addSelectListener(r -> {
      if (r)
        orderSelected(OrderManager.INSTANCE.getAllOrders()[highlighter.getHighLightedRow()+1]);
      else
        unselected();
    });
    pt20Font = new Font("Calibri", 20);
    pt25Font = new Font("Calibri", 25);
    //orderDetailsPaneController.setParent(this);
    searchBox.setTextFormatter(EmployeeLoginPaneController.buildNumericFormatList(10));
  }


  /**
   * Used for opening this pane.
   */
  public void open(){
    resetConstraints();
    //orderInfoAnchor.setVisible(false);
    viewOrdersAnchor.setVisible(true);
    //viewOrdersInfoPane.setVisible(false);
    searchBox.setVisible(true);
    searchLabel.setVisible(true);
    //drawGrid(OrderManager.INSTANCE.getAllOrders(), OrderManager.getCurrentOrderNumber()+1);
    searchBox.setText("");
    firstDayPicker.setValue(LocalDate.now());
    secondDayPicker.setValue(LocalDate.now());
    Collections.sort(OrderManager.INSTANCE.getAllPreorders());
    System.out.println("preorder size: " + OrderManager.INSTANCE.getAllPreorders().size());
    drawGrid(OrderManager.INSTANCE.getAllPreorders());
  }

  /**
   * Resets the row constraints for the view order gridpane
   */
  private void resetConstraints(){
    RowConstraints r = viewOrderGridpane.getRowConstraints().get(0);
    viewOrderGridpane.getRowConstraints().clear();
    viewOrderGridpane.getRowConstraints().add(r);
    for(int i = 0; i<= OrderManager.getCurrentOrderNumber(); i++){
      viewOrderGridpane.getRowConstraints().add(r);
    }
  }

  private void drawGrid(List<Order> orders){
    int row = 0;
    viewOrderGridpane.getChildren().clear();
    highlighter.reset();
    for(int i = 0; i < orders.size(); i++){
      Order r = orders.get(i);
      //System.out.println("drawing preorder: " + r.getOrderTime().toString());
      if(r == null){
        continue;
      }
      Label orderDate = new Label(" "+ dayFormat.format(r.getOrderTime().getThisTime()) + "");
      registerItem(orderDate, 0);
      viewOrderGridpane.add(orderDate, 0, row);

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

      Label time = new Label(" "+ timeFormat.format(r.getOrderTime().getThisTime()));
      registerItem(time, 4);
      viewOrderGridpane.add(time, 4, row);


      Label cost = new Label(" "+ NumberFormat.getCurrencyInstance().format(r.getAllTotal()));
      registerItem(cost, 5);
      viewOrderGridpane.add(cost, 5, row);

      if(r.getPayment() != null){
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

  public void updateGridWithSearches(){
    List<Order> searchedPreorders = new ArrayList<Order>();
    String number = searchBox.getText();
    LocalDate to = firstDayPicker.getValue();
    LocalDate from = secondDayPicker.getValue();
    for(Order i: OrderManager.INSTANCE.getAllPreorders()){
      LocalDate orderDate = i.getOrderTime().getThisTime().toLocalDate();
      if(!orderDate.isBefore(to) && !orderDate.isAfter(from)){
        if(number.equals("") || i.getCustomerDetails().getPhoneNumber().contains(number)){
          searchedPreorders.add(i);
        }
      }else if(!number.equals("") && i.getCustomerDetails().getPhoneNumber().contains(number)){
        searchedPreorders.add(i);
      }
    }
    Collections.sort(searchedPreorders);
    drawGrid(searchedPreorders);
  }

  private void registerItem(Label s, int col){
    double width = viewOrderGridpane.getColumnConstraints().get(col).getPrefWidth();
    double height = viewOrderGridpane.getRowConstraints().get(0).getPrefHeight();
    s.setPrefSize(width, height);
    s.setFont(pt20Font);
    registerNode(s);
  }

  private void registerNode(Node n){
    highlighter.registerNode(n);
  }

  /**
   * Called when an order is selected
   */
  private void orderSelected(Order r) {
    //orderDetailsPaneController.setOrder(r);
//    viewOrderInfo2.setVisible(false);
//    viewOrderInfo3.setVisible(false);
//    viewOrdersInfoPane.setVisible(true);
//    viewOrderInfo1.setVisible(true);
//    viewOrderButton.setVisible(true);
//    if (r.isDelivery()) {
//      if (r.isTakenOut()) {
//        viewOrderInfo2.setVisible(true);
//        viewOrderInfo1.setText("Driver: " + EmployeePane.instance.getEmployeeName(r.getServer()));
//        viewOrderInfo2.setText("Time out: " + EmployeePane.getTimeFormat().format(r.getTimeOut()));
//      } else {
//        viewOrderInfo1.setText("Order not taken out.");
//      }
//    } else {
//      if(r.getServer() > 0){
//        viewOrderInfo1.setText("Server: " + EmployeePane.instance.getEmployeeNameShort(r.getServer()));
//      }else{
//        viewOrderInfo1.setText("Not paid yet.");
//      }
//
//    }
  }

  private void unselected(){

  }

  @FXML
  void viewThisMonthsPreorders(ActionEvent event) {
    firstDayPicker.setValue(LocalDate.now());
    secondDayPicker.setValue(LocalDate.now().plusMonths(1));
    updateGridWithSearches();
  }

  @FXML
  void viewTodaysPreorders(ActionEvent event) {
    firstDayPicker.setValue(LocalDate.now());
    secondDayPicker.setValue(LocalDate.now());
    updateGridWithSearches();
  }

  @FXML
  void viewThisWeeksPreorders(ActionEvent event) {
    firstDayPicker.setValue(LocalDate.now());
    secondDayPicker.setValue(LocalDate.now().plusWeeks(1));
    updateGridWithSearches();
  }

  public void reset(){
    firstDayPicker.setValue(LocalDate.now());
    secondDayPicker.setValue(LocalDate.now());
  }


  @Override
  public ControllerType getType() {
    return ControllerType.VIEW_PREODERS_PANE_CONTROLLER;
  }
}