package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.Client;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.content.order.pricing.DiscountOthersCalculator;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.*;
import main.java.lucia.util.currency.CurrencyConverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The controller which controls the pickup delivery pane
 *
 * @author Zachery Unraru
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public class PickupDeliveryPaneController extends PickupDelivery implements Controller {

  /* Different panes */
  @FXML
  private Pane menuPanes;

  @FXML
  private Pane paymentPane;

  @FXML
  private Pane additionalPane;

  @FXML
  private Pane enterNumberPane;

  @FXML
  private Pane orderView;

  @FXML
  private Pane orderSummaryPane;

  @FXML
  private Pane specialPane;

  @FXML
  JFXButton additionalInfo;

  @FXML
  JFXButton additionalButton;

  /* The different panes for the option menus */
  @FXML
  public Pane PaneChange;

  @FXML
  public Pane paneModules;

  @FXML
  private JFXButton pizzaPaneChange;

  @FXML
  private JFXButton appetizersPaneChange;

  @FXML
  private JFXButton saladsPaneChange;

  @FXML
  private JFXButton chickenPaneChange;

  @FXML
  private JFXButton italianPaneChange;

  @FXML
  private JFXButton beveragesPaneChange;

  @FXML
  private JFXButton dipsPaneChange;

  @FXML
  public JFXButton cateringOrder;

  @FXML
  public JFXButton button;

  /* The different option pane */

  @FXML
  private Pane beveragesPane;

  @FXML
  private Pane appetizersPane;

  @FXML
  private Pane italianPane;

  @FXML
  private Pane saladsPane;

  @FXML
  public Pane sidesPane;

  @FXML
  private Pane cateringPane;

  @FXML
  private Pane chickenPane;

  @FXML
  public Pane pizza;

  @FXML
  private Label grandTotalLabel;

  @FXML
  private Label PSTLabel;

  @FXML
  private Label GSTLabel;

  @FXML
  private Label subtotalLabel;

  @FXML
  private Label deliveryChargeLabel;

  @FXML
  private JFXButton catering;

  @FXML
  private JFXButton misc;

  @FXML
  private JFXButton specialsButton;

  @FXML
  private JFXButton notes;

  @FXML
  private JFXButton preorder;

  @FXML
  private JFXButton editCustomer;

  @FXML
  private ItalianController italianPaneController;

  @FXML
  private AppetizersController appetizersPaneController;

  @FXML
  private SaladsController saladsPaneController;

  @FXML
  private PizzaController pizzaController;

  @FXML
  private ChickenController chickenPaneController;

  @FXML
  private BeveragesController beveragesPaneController;

  @FXML
  private SidesController sidesPaneController;

  @FXML
  private CateringController cateringPaneController;

  @FXML
  private OrderViewController orderViewController;

  @FXML
  private EnterNumberPane enterNumberPaneController;

  @FXML
  public Pane orderLabel;
  
  @FXML
  private Pane miscPane;

  @FXML
  private JFXTextField miscDescription;

  @FXML
  private JFXTextField miscPrice;

  @FXML
  private Label errorLabelMisc;

  NumberFormat formatter = new DecimalFormat("#0.00");


  private ArrayList<Label> priceList = new ArrayList<>();
  //private ArrayList<Node> buildList = new ArrayList<>();

  private ArrayList<JFXButton> pizzaOrderLabelList = new ArrayList<>();
  public JFXButton currentButtonToDelete;
  public int currentCountToDelete;
  public boolean popup = false;
//  public ArrayList<JFXButton> dinnerOrderLabelList = new ArrayList<>();
//  public ArrayList<Label> dinnerOrderPriceList = new ArrayList<>();
//  public ArrayList<Label> pizzaOrderLabelSizeList = new ArrayList<>();
//  public ArrayList<JFXButton> cateringOrderLabelList = new ArrayList<>();
  public ArrayList<Label> previousArray = new ArrayList<>();
//  public ArrayList<Item> dinnerList = new ArrayList<>();

  Pane[] paneArray;
  Pane[] specialPaneArray;

  @FXML
  private Pane pickupDeliveryPane;

  private Pane getPickupDeliveryPane(){
    return this.pickupDeliveryPane;
  }

  public PickupDeliveryPaneController(){
    super();
  }

  /**
   * The children controllers of this pane
   */
  private Set<ParentController> children;

  // ------ "INITIALIZE" SERIES ------
  @FXML
  public void initialize() {
    children = new HashSet<>();
    initializeArrays();
    ControllerMap.addController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER, this);

    registerChild(italianPaneController);
    registerChild(appetizersPaneController);
    registerChild(saladsPaneController);
    registerChild(pizzaController);
    registerChild(chickenPaneController);
    registerChild(beveragesPaneController);
    registerChild(sidesPaneController);
    registerChild(cateringPaneController);
    registerChild(orderViewController);
    registerChild(enterNumberPaneController);

    /**
     * Dynamic Loading
     */
    pizza.setId("pizza"); // ensures that the pizza module can be selected
    DynamicLoader dynamicLoader = new DynamicLoader(this, pizzaController);
    dynamicLoader.runDynamicLoader();
    open();
  }

  public void registerChild(ParentController c) {
      if(c != null){
          children.add(c);
          c.setParent(this);
      }else{
          Client.logger.warn("Parent controller found was null!");
      }

  }

  /**
   * Triggers the controller to run open-pane procedures
   * @param r the order to open to
   */
  public void open(Order r){
    changeOrder(r);
    getCustomerPanes();
  }

  /**
   * Triggers the controller to run open-pane procedures
   * If there is no current order, a new order will be made
   */
  public void open(){
    enterNumberPaneController.open();
    if(getCurrentOrder() == null){
      open(new Order(OrderType.UNSELECTED));
    }else{
      open(getCurrentOrder());
    }
  }

  public void initializeArrays() {

//    paneArray = new Pane[]{pizzaController.specialAnchor, pizzaController.sizeButtons, pizzaController.extraPane, pizzaController.pizzaButtons,
//            pizzaController.settingsButtons, pizzaController.cheeseAnchor, appetizersPane, italianPane,
//            saladsPane, chickenPaneController.chickenPane, beveragesPane};
//    specialPaneArray = new Pane[]{pizzaController.extraPane, pizzaController.pizzaButtons, pizzaController.settingsButtons, pizzaController.cheeseAnchor};
  }
//  public void initializeTheOrderPizza() {
//    pizzaPaneController.theOrderPizza.clear();
//    for(int x = 0; x < orderCalc.getItems().size(); x++)
//      pizzaPaneController.theOrderPizza.add((Pizza)orderCalc.getItems().get(x));
//  }

  public PizzaController getPizzaController() {
    return pizzaController;
  }
  public SaladsController getSaladsPaneController() {
    return saladsPaneController;
  }
  public OrderViewController getOrderViewController() {
    return orderViewController;
  }
  public SidesController getSidesPaneController() { return  sidesPaneController;}

  // ------ "PANE" SERIES ------


  // ------ "RANDOM" SERIES ------

  public void resetEnterNumberPane() {
    EnterNumberPane enterNumberPane = (EnterNumberPane) ControllerMap.getController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER);
    enterNumberPane.reset();
  }
  public void addToCustomer() {
    // TODO add to db
    menuPanes.setOpacity(1);
    menuPanes.setDisable(false);
    enterNumberPane.setVisible(false);
  }
  @Override
  public ControllerType getType() {
    return ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER;
  }

  // ------ "ORDER" SERIES ------


  public void setChild() {
    this.orderSummaryPane.getChildren().add(orderView);
  }
  @Override
  public void onFinalize() {
    setChild();
    cancelOrder();
    resetEnterNumberPane();
    getCustomerPanes();
  }

//  public void updatePrice() {
//    clearPrices();
//    updateGridPrice(getCurrentOrder().getItems());
//  }
  public void clearPrices() {
    for(int x = 0; x < priceList.size(); x++)
      orderViewController.orderGrid.getChildren().removeAll(priceList);
    priceList.clear();
  }
//  public void updateGridPrice(ArrayList<Item> order) {
//    clearPrices();
//    int count = 0;
//    for(int x = 0; x < order.size(); x++) {
//      Label label = new Label("$" + formatter.format(CurrencyConverter.build(order.get(x).getPriceNonCalculated())));
//      priceList.add(label);
//      label.setPrefSize(50, 30);
//      label.setMaxSize(50, 30);
//      label.setMinSize(50, 30);
//      label.setStyle("-fx-font-size: 15; -fx-alignment: center-left");
//      orderViewController.orderGrid.add(label, 2, count);
//      count++;
//    }
//  }

  // ------ "CALCULATE" SERIES ------

  @Override
  public void onSubtotalUpdate(long price) {
    if(isDelivery())
      price += DiscountOthersCalculator.getInstance().getDeliveryFee();
    subtotalLabel.setText("$" + formatter.format(CurrencyConverter.build(price)));
    GSTLabel.setText("$" + formatter.format(CurrencyConverter.taxAndBuildGST(price)));
    PSTLabel.setText("$" + formatter.format(CurrencyConverter.taxAndBuildPST(price)));
    //if (isDelivery()) {
    //  deliveryChargeLabel.setText("$2.50");
    // calcPriceGrandTotal(price + 250);

    price = getCurrentOrder().getGrandTotalLongWithTax();
    grandTotalLabel.setText("$" + formatter.format(CurrencyConverter.taxAndBuild(price)));
    if(isDelivery())
      deliveryChargeLabel.setText("$3.50");
  }

  @Override
  protected void onItemAdd(Item i) {
    updateSubtotal();
    orderViewController.updateOrderGrid();

  }

  @Override
  protected void onOrderChange(Order newOrder) {
      updateSubtotal();
      orderViewController.open();
      orderViewController.updateOrderGrid();


  }

  // ------ "EXTRA BUTTON" SERIES ------
//  public void setExtraButtons(boolean bool, String type) {
//    if (bool) {
//      if (type.equals("EZ"))
//        defaultSelected = "ToppingsSelectedEZ";
//      else if (type.equals("X"))
//        defaultSelected = "ToppingsSelectedX";
//      else if (type.equals("XX"))
//        defaultSelected = "ToppingsSelectedXX";
//    } else defaultSelected = "ToppingsSelected";
//  }

  @FXML
  public void completeOrder() {
    getCustomerPanesSummary();
//    Order order;
//    if(isDelivery())
//      order = new Order(OrderType.DELIVERY);
//    else order = new Order(OrderType.UNSELECTED);
//    for(int x = 0; x < pizzaPaneController.theOrderPizza.size(); x++)
//      order.addItem(pizzaPaneController.theOrderPizza.get(x));
//    completedOrder = order;
  }

  // ------ "SET HOVER" SERIES ------


  @FXML
  public void dipsPopup(ActionEvent event) {
    sidesPane.toFront();
    popup = true;
  }
  @FXML
  public void removeAllSelected() {
    Pane pane;
//    for (int x = 0; x < paneArray.length; x++) {
//      pane = paneArray[x];
//      pane.getChildren().forEach(theButtons ->
//              theButtons.getStyleClass().removeAll("ToppingsSelectedGreen", "ToppingsSelected", "ToppingsSelectedEZ", "ToppingsSelectedX", "ToppingsSelectedXX", "ToppingsEnableHover"));
//    }
  }

  // ------ "CHANGE GROUP BUTTON" SERIES ------
  public void changeGroupButton(MouseEvent event, Pane pane, String selectedCSS) {
    JFXButton button = (JFXButton) event.getSource();
    if (Style.isEnabled(button, selectedCSS)) {
      pane.getChildren().forEach(theButtons -> {
        theButtons.getStyleClass().removeAll(selectedCSS);
        if (theButtons.equals(pizzaController.easy) || theButtons.equals(pizzaController.extra) || theButtons.equals(pizzaController.xextra))
          setExtraButtons(false, "null");
      });
    } else {
      pane.getChildren().forEach(theButtons -> {
        theButtons.getStyleClass().removeAll(selectedCSS);
        button.getStyleClass().add(selectedCSS);
        if (Style.isEnabled(pizzaController.easy, selectedCSS))
          setExtraButtons(true, "EZ");
        else if (Style.isEnabled(pizzaController.extra, selectedCSS))
          setExtraButtons(true, "X");
        else if (Style.isEnabled(pizzaController.xextra, selectedCSS))
          setExtraButtons(true, "XX");
      });
    }
  }
  public Pane getOrderView(){
    return orderView;
  }




  @FXML
  public void cancelOrder() {
    menuPanes.setOpacity(1);
    menuPanes.setDisable(false);
    enterNumberPane.setVisible(false);
    changeOrder(new Order(OrderType.UNSELECTED));

  }

  @FXML
  public void setAdditionalPane(ActionEvent event) {
    Object source = event.getSource();
    if (pizzaController.saucePane.isVisible()) {
      if (!source.equals(pizzaController.saucePane))
        setOptionPane(source);
      pizzaController.saucePane.setVisible(false);
    } else if (pizzaController.crustPane.isVisible()) {
      if (!source.equals(pizzaController.crustPane))
        setOptionPane(source);
      pizzaController.crustPane.setVisible(false);
    } else if (additionalPane.isVisible()) {
      if (!source.equals(additionalPane))
        setOptionPane(source);
      additionalPane.setVisible(false);
    } else if (paymentPane.isVisible()) {
      if (!source.equals(paymentPane))
        setOptionPane(source);
      paymentPane.setVisible(false);
    } else {
      setOptionPanesVisible(false);
      setOptionPane(source);
    }
  }
  @FXML
  public void setOptionPane(Object source) {
    pizzaController.saucePane.setVisible(source.equals(pizzaController.sauce));
    pizzaController.crustPane.setVisible(source.equals(pizzaController.crust));
    additionalPane.setVisible(source.equals(additionalButton));
  }

  @Override
  public void setOptionPanesVisible(boolean change) {
    pizzaController.saucePane.setVisible(change);
    pizzaController.crustPane.setVisible(change);
    additionalPane.setVisible(change);
    paymentPane.setVisible(change);
  }
  @FXML
  public void additionalPane(ActionEvent event) {
    EnterNumberPane controller = (EnterNumberPane) ControllerMap.getController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER);
    final Object source = event.getSource();
    if (source.equals(editCustomer))
      getCustomerPanes();
    else if (source.equals(dipsPaneChange))
      sidesPane.toFront();
    else if (source.equals(catering))
      cateringPane.toFront();
    else if(source.equals(preorder)) {
      getCustomerPanes();
      controller.promptPreorder();
    }
    else if(source.equals(misc)) {
      miscPane.setVisible(true);
      menuPanes.setOpacity(.15);
      menuPanes.setDisable(true);
      specialPane.setVisible(false);
    }
    else if(source.equals(specialsButton)) {
      specialPane.setVisible(true);
      menuPanes.setOpacity(.15);
      menuPanes.setDisable(true);
      miscPane.setVisible(false);
    }
  }
  public void getCustomerPanesSummary() {
    getCustomerPanes();
    enterNumberPaneController.loadSummaryPane();
  }
  public void getCustomerPanes() {
    menuPanes.setOpacity(.15);
    menuPanes.setDisable(true);
    enterNumberPane.setVisible(true);
  }
  @FXML
  public void openCateringPane() {
    cateringPane.toFront();
  }
  @FXML
  public void changeOrderPane(ActionEvent event) {
    removeAllSelected();
    final Object source = event.getSource();
    if (source.equals(pizzaPaneChange))
      pizza.toFront();
    else if (source.equals(appetizersPaneChange))
      appetizersPane.toFront();
    else if (source.equals(italianPaneChange))
      italianPane.toFront();
    else if (source.equals(saladsPaneChange))
      saladsPane.toFront();
    else if (source.equals(chickenPaneChange))
      chickenPane.toFront();
    else if (source.equals(beveragesPaneChange))
      beveragesPane.toFront();
    else sidesPane.toFront();
    setDefaultSelected("ToppingsSelected");
  }

  @FXML
  public void miscAccept() {
    String check = miscPrice.getText();
    long price = 0;
    boolean cont = true;
    if(miscDescription.getText().equals("")) {
      errorLabelMisc.setText("Invalid Name!");
      cont = false;
    }
    if(!miscPrice.getText().equals("")) {
      if (miscPrice.getText().contains("."))
        check = miscPrice.getText().replace(".", "");
      else check = miscPrice.getText() + "00";
    }
    else {
      errorLabelMisc.setText("Invalid Price!");
      cont = false;
    }
    if(cont && isValidMiscPrice(check)) {
      price = Long.valueOf(check);
    }
    else {
      errorLabelMisc.setText("Invalid Price!");
      cont = false;
    }
    if(cont) {
      //getCurrentOrder().addMisc(miscDescription.getText(), price);
      //getCurrentOrder().calculateGrandTotal();
      menuPanes.setOpacity(1);
      menuPanes.setDisable(false);
      miscPane.setVisible(false);
      miscDescription.clear();
      miscPrice.clear();
      errorLabelMisc.setText("");
      updateSubtotal();
      orderViewController.updateOrderGrid();
    }
  }

  @FXML
  public void miscBack() {
    menuPanes.setOpacity(1);
    menuPanes.setDisable(false);
    miscPane.setVisible(false);
    miscDescription.clear();
    miscPrice.clear();
    errorLabelMisc.setText("");
  }

  @FXML
  public void specialAccept() {

    menuPanes.setOpacity(1);
    menuPanes.setDisable(false);
    specialPane.setVisible(false);
  }

  public boolean isValidMiscPrice(String check) {
    try{
      Long.valueOf(check);
    }
    catch (Exception e) {
      System.out.println(e);
      return false;
    }
    return true;
  }
}
