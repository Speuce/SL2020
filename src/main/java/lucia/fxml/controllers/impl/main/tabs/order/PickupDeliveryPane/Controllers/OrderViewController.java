/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.Pizza;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadePizza;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerOrderManager;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.util.currency.CurrencyConverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderViewController implements ParentController<PickupDeliveryPaneController> {

    @FXML
    private Pane orderView;

    @FXML
    private AnchorPane orderGridAnchor;

    @FXML
    private AnchorPane pizzaDetailAnchorPane;

    @FXML
    private GridPane pizzaDetailGrid;

    @FXML
    private GridPane orderGrid;

    @FXML
    private JFXTabPane orderTabPane;

    @FXML
    private Tab pizzaTab;

    @FXML
    private JFXTreeTableView<PizzaOrderView> pizzaTable;

    @FXML
    private Tab dinnerTab;

    @FXML
    private JFXTreeTableView<DinnerOrderView> dinnerTable;

    @FXML
    private Tab finalTab;

    @FXML
    private JFXTreeTableView<OrderView> finalTable;

    @FXML
    private Tab priceBreakDownTab;

    @FXML
    private JFXTreeTableView<?> priceBreakdownTable;

    private OrderManager orderManager = OrderManager.INSTANCE;
    private PizzaOrderManager pizzaOrderManager = PizzaOrderManager.getPizzaOrderInstance();
    private DinnerOrderManager dinnerOrderManager = DinnerOrderManager.getDinnerOrderInstance();

    private JFXTreeTableColumn<PizzaOrderView, String> pizzaColumnName;
    private JFXTreeTableColumn<PizzaOrderView, String> pizzaColumnPrice;
    private JFXTreeTableColumn<DinnerOrderView, String> dinnerColumnName;
    private JFXTreeTableColumn<DinnerOrderView, String> dinnerColumnPrice;
    private JFXTreeTableColumn<OrderView, String> orderColumnName;
    private JFXTreeTableColumn<OrderView, String> orderColumnPrice;

    private PickupDeliveryPaneController parent;
    NumberFormat formatter = new DecimalFormat("#0.00");

    private Pizza currentlySelectedPizza;

    private Order currentOrder;

    public OrderViewController() {
    }

    public void updateOrderView() {

        if(!pizzaOrderManager.isOrderEmpty()) {
            makeOrderViewPizza();
        }
        if(!dinnerOrderManager.isOrderEmpty()) {
            makeOrderViewDinner();
        }
        makeOrderView();
    }

    public void makeOrderView() {
        List<Item> orderPre;

        orderColumnName = new JFXTreeTableColumn<>("Item");
        orderColumnPrice = new JFXTreeTableColumn<>("Price");
        JFXTreeTableColumn<OrderView, String> settingsColumn = new JFXTreeTableColumn<>();
        JFXTreeTableColumn<OrderView, String> deleteColumn = new JFXTreeTableColumn<>();

        orderColumnName.setPrefWidth(200);
        orderColumnPrice.setPrefWidth(50);
        settingsColumn.setPrefWidth(50);
        deleteColumn.setPrefWidth(50);

        ObservableList<OrderView> orders = FXCollections.observableArrayList();
        if(!DynamicLoader.dynamicLoaderInstance.pickupDeliveryPaneController.isOrderEmpty()) {
            orderPre = DynamicLoader.dynamicLoaderInstance.pickupDeliveryPaneController.getCurrentOrder().getItems();
        } else {
            orderPre = null;
            System.out.println("NO ORDERS");
        }

        if(orderPre != null) {
            for (Item item : orderPre) {
                orders.add(new OrderView(item.getName(), String.valueOf(item.getPrice()), item));
            }
        }

        Callback<TreeTableColumn<OrderView, String>, TreeTableCell<OrderView, String>> editCellFactory =
                new Callback<TreeTableColumn<OrderView, String>, TreeTableCell<OrderView, String>>() {
            @Override
            public TreeTableCell<OrderView, String> call(TreeTableColumn<OrderView, String> stringTreeTableColumn) {
                TreeTableCell<OrderView, String> cell = new TreeTableCell<OrderView, String>() {

                    final JFXButton btn = new JFXButton("Edit");

                    @Override
                    public void updateItem(String item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setButtonType(JFXButton.ButtonType.RAISED);
                            btn.setOnAction(event -> {
                                try {
                                    System.out.println("EDIT BUTTON CLICKED " +
                                            this.getTreeTableRow().getTreeItem().getValue().currentItem.getName());
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }

                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        Callback<TreeTableColumn<OrderView, String>, TreeTableCell<OrderView, String>> deleteCellFactory =
                new Callback<TreeTableColumn<OrderView, String>, TreeTableCell<OrderView, String>>() {
                    @Override
                    public TreeTableCell<OrderView, String> call(TreeTableColumn<OrderView, String> stringStringTreeTableColumn) {
                        final TreeTableCell<OrderView, String> cell = new TreeTableCell<OrderView, String>() {

                            final JFXButton btn = new JFXButton("Delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setButtonType(JFXButton.ButtonType.RAISED);
                                    btn.setOnAction(event -> {
                                        System.out.println("DELETE BUTTON CLICKED");

                                        orders.remove(finalTable.getSelectionModel().selectedItemProperty().get().getValue());
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        settingsColumn.setCellFactory(editCellFactory);
        deleteColumn.setCellFactory(deleteCellFactory);
        orderColumnName.setCellValueFactory(pizzaOrderViewStringCellDataFeatures ->
                pizzaOrderViewStringCellDataFeatures.getValue().getValue().orderName);
        orderColumnPrice.setCellValueFactory(pizzaOrderViewStringCellDataFeatures ->
                pizzaOrderViewStringCellDataFeatures.getValue().getValue().price);

        TreeItem<OrderView> root = new RecursiveTreeItem<OrderView>(orders, RecursiveTreeObject::getChildren);
        finalTable.getColumns().setAll(orderColumnName, settingsColumn, deleteColumn, orderColumnPrice);
        finalTable.setRoot(root);
        finalTable.setShowRoot(false);
    }

    public void makeOrderViewDinner() {
        List<Item> orderPre;

        dinnerColumnName = new JFXTreeTableColumn<>("Dinner");
        dinnerColumnPrice = new JFXTreeTableColumn<>("Price");

        dinnerColumnName.setPrefWidth(300);
        dinnerColumnPrice.setPrefWidth(50);

        ObservableList<DinnerOrderView> dinnerOrders = FXCollections.observableArrayList();
        if(!dinnerOrderManager.isOrderEmpty()) {
            orderPre = dinnerOrderManager.getCurrentOrder().getItems();
        } else {
            orderPre = null;
            System.out.println("NO ORDERS DINNER");
        }

        for (Item item : orderPre) {
            dinnerOrders.add(new DinnerOrderView(item.getName(), String.valueOf(item.getPrice())));
        }

        dinnerColumnName.setCellValueFactory(pizzaOrderViewStringCellDataFeatures ->
                pizzaOrderViewStringCellDataFeatures.getValue().getValue().dinnerName);
        dinnerColumnPrice.setCellValueFactory(pizzaOrderViewStringCellDataFeatures ->
                pizzaOrderViewStringCellDataFeatures.getValue().getValue().price);

        TreeItem<DinnerOrderView> root = new RecursiveTreeItem<DinnerOrderView>(dinnerOrders, RecursiveTreeObject::getChildren);
        dinnerTable.getColumns().setAll(dinnerColumnName, dinnerColumnPrice);
        dinnerTable.setRoot(root);
        dinnerTable.setShowRoot(false);
    }

    public void makeOrderViewPizza() {
        List<Item> orderPre;

        pizzaColumnName = new JFXTreeTableColumn<>("Pizza");
        pizzaColumnPrice = new JFXTreeTableColumn<>("Price");

        pizzaColumnName.setPrefWidth(300);
        pizzaColumnPrice.setPrefWidth(50);

        ObservableList<PizzaOrderView> pizzaOrders = FXCollections.observableArrayList();

        if(!pizzaOrderManager.isOrderEmpty()) {
            orderPre = pizzaOrderManager.getCurrentOrder().getItems();
        } else {
            orderPre = null;
            System.out.println("NO ORDERS PIZZA");
        }

        for (Item item : orderPre) {
            pizzaOrders.add(new PizzaOrderView(item.getName(), String.valueOf(item.getPrice())));
        }

        pizzaColumnName.setCellValueFactory(pizzaOrderViewStringCellDataFeatures ->
                pizzaOrderViewStringCellDataFeatures.getValue().getValue().pizzaName);
        pizzaColumnPrice.setCellValueFactory(pizzaOrderViewStringCellDataFeatures ->
                pizzaOrderViewStringCellDataFeatures.getValue().getValue().price);

        TreeItem<PizzaOrderView> root = new RecursiveTreeItem<PizzaOrderView>(pizzaOrders, RecursiveTreeObject::getChildren);
        pizzaTable.getColumns().setAll(pizzaColumnName, pizzaColumnPrice);
        pizzaTable.setRoot(root);
        pizzaTable.setShowRoot(false);
    }


    @Override
    public void close() {

    }

    @Override
    public Object getCurrentItem() {
        return null;
    }

    @Override
    public void open() {
//        if(hasParent()){
//            currentOrder = parent.getCurrentOrder();
//        }
        updateOrderView();
    }

    @Override
    public void setParent(PickupDeliveryPaneController o) {
        this.parent = o;
    }



    // ------ "MISC" SERIES ------
    @FXML
    public void editPizza() {
        Pizza originalPizza = currentlySelectedPizza;
   //     parent.getPizzaController().setCurrentPizza(originalPizza);
        removePizza();
    }

    @FXML
    public void openPizzaOrderGrid() {
        orderGrid.setVisible(true);
        pizzaDetailGrid.setVisible(false);
        pizzaDetailAnchorPane.setVisible(false);
    }

//    // ------ "CLEAR" SERIES ------
//    public void clearPizzaOrder() {
//       // parent.clearPrices();
//        try {
//            orderGrid.getChildren().clear();
//            //TODO should this clear differently?
//        } catch (Exception e) {
//        }
//        for(int x = 0; x < parent.getPizzaController().theOrderPizza.size(); x++)
//            parent.orderCalc.removeItem(parent.getPizzaController().theOrderPizza.get(x));
//        parent.orderCalc.calculateGrandTotalWNames();
//        parent.orderCount = 0;
//        parent.pizzaOrderLabelList.clear();
//        parent.pizzaOrderLabelSizeList.clear();
//        parent.priceList.clear();
//        parent.getPizzaController().theOrderPizza.clear();
//        parent.calcPriceSummaryPizza();
//        parent.updateSubtotal();
//    }

    /**
     * Safely clears the detail array
     */
    public void clearDetailArray() {
        try{
            pizzaDetailGrid.getChildren().clear();
        }catch(Exception e){
            System.out.println("Error clearing the details array");
        }

    }

    // ------ "GRID PANE" SERIES ------
    /**
     * Refreshes the grid
     */
    public void updateOrderGrid() {
        System.out.println("updating order grid");
        try {
            orderGrid.getChildren().clear();
        } catch (Exception e) {}
        updateGridArray();
    }

    /**
     * Adds everything back to the grid
     */
    private void updateGridArray() {
     //   parent.getCurrentOrder().calculateFees();
//        DinnerOrderManager dinnerOrderManager = DinnerOrderManager.getDinnerOrderInstance();
//        Order currentOrder = dinnerOrderManager.getCurrentOrder();
//        List<Item> orderList = currentOrder.getItems();


     //   List<Item> orderList = orderManager.get ;
        int count = 0;
        RowConstraints r = orderGrid.getRowConstraints().get(0);
        orderGrid.getRowConstraints().clear();
        orderGrid.getRowConstraints().add(r);
    //    System.out.println("updating order grid 1: " + orderList.size());
//        for(Item i: orderList){
//            orderGrid.getRowConstraints().add(r);
//            if(i instanceof Pizza){
////                String[] split = i.getName().split(" ");
////                Label label = new Label(split[0]);
//                try{
//                    addToGridPizza(i.getName(), count);
//                    addToGridPrice(i, count);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//
//            }else{
//               // System.out.println(i.getPriceNonCalculated());
//                addToGridDinner(i.getName(), count);
//                addToGridPrice(i, count);
//            }
//            count++;
//        }
    }

    /**
     * Adds a pizza to the grid
     * @param name the name of the pizza
     * @param pos the row index to add to
     */
    public void addToGridPizza(String name, int pos) {
        orderGrid.setVisible(true);
        JFXButton b = new JFXButton(name);
        b.setText(name.substring(3));
        b.setId(name.substring(3));
        addSize(name.substring(0, 3), pos);
        b.setPrefSize(265, 50);
        b.setWrapText(true);
        int fontsize = Math.min(Math.max((int)Math.round(orderGrid.getColumnConstraints().get(1).getPrefWidth() / name.length()),14),20);
        b.setStyle("-fx-font-size: "+fontsize+"; -fx-alignment: center-left");
        b.setOnMouseClicked((EventHandler<Event>) this::enableDetailList);
        orderGrid.add(b, 1, pos);
        //orderGridAnchor.setPrefHeight(orderGrid.getHeight() + 50);
    }

    /**
     * Adds any non-pizza item to the grid
     * @param name the name of the item to add
     * @param pos the row index to add the item
     */
    public void addToGridDinner(String name, int pos) {
        orderGrid.setVisible(true);
        JFXButton b = new JFXButton(name);
        b.setPrefSize(265, 50);
        b.setWrapText(true);
        int fontsize = Math.min(Math.max((int)Math.round(orderGrid.getColumnConstraints().get(1).getPrefWidth() / name.length()),14),20);
        b.setStyle("-fx-font-size: "+fontsize+"; -fx-alignment: center-left");
        b.setOnMouseClicked((EventHandler<Event>) this::removeDinner);
        //parent.dinnerOrderLabelList.add(parent.button);
        orderGrid.add(b, 1, pos);
    }

    /**
     * Adds a price to the grid
     * @param item the item to caculate the price of
     * @param pos the row index to add the price to
     */
    public void addToGridPrice(Item item, int pos) {
       // item.calculatePrice();
        Label label = new Label("$" + formatter.format(CurrencyConverter.build(item.getPrice())));
        if(item.getPrice() > 99)
            label.setStyle("-fx-font-size: 18; -fx-alignment: center-left");
        else label.setStyle("-fx-font-size: 22; -fx-alignment: center-left");
        label.setPrefSize(75, 40);
        label.setWrapText(true);
//        parent.priceList.add(label);
        orderGrid.add(label, 2, pos);
    }

    /**
     * For future menu building
     * @param item
     * @param pos
     */
    public void addToGridCatering(Item item, int pos) {
        orderGrid.setVisible(true);
        item.calcNameAndPrice();
        JFXButton button = new JFXButton(item.getName());
        button.setPrefSize(265, 50);
        button.setWrapText(true);
        button.setStyle("-fx-font-size: 15");
        button.setStyle("-fx-font-size: 15; -fx-alignment: center-left");
        orderGrid.add(button, 1, pos);
    }

    /**
     * Adds the size of a pizza to the grid
     * @param size the size to be added, as a string
     * @param pos the row index to add the size
     */
    public void addSize(String size, int pos) {
        Label label = new Label(size);
        label.setPrefSize(265, 50);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold");
        //parent.pizzaOrderLabelSizeList.add(label);
        orderGrid.add(label, 0, pos);
    }

    /**
     * Called when a dinner is clicked
     */
    public void removeDinner(Event event) {
        Object source = event.getSource();
        JFXButton toRemove;
        Item toRemoveItem = null;
        int row = GridPane.getRowIndex((Node)source);
        parent.getCurrentOrder().getItems().remove(row);
        updateOrderGrid();
        parent.updateSubtotal();
        long price = 0;
//        for(int x = 0; x < parent.orderCalc.getItems().size(); x++)
//            if(!(parent.orderCalc.getItems().get(x) instanceof Pizza))
//                price += parent.orderCalc.getItems().get(x).getPriceNonCalculated();
//        parent.orderCount = 0;
//        for(int x = 0; x < parent.orderCalc.getItems().size(); x++)
//            if(parent.orderCalc.getItems().get(x) instanceof Italian || parent.orderCalc.getItems().get(x) instanceof Appetizer ||
//                    parent.orderCalc.getItems().get(x) instanceof Salad  || parent.orderCalc.getItems().get(x) instanceof Addon)
//                parent.orderCount++;
    }

    /**
     * Called when you click the 'X' in the pizza detail pane
     */
    @FXML
    public void removePizza() {
//        parent.initializeTheOrderPizza();
//        orderGrid.getChildren().removeAll(parent.currentButtonToDelete);
//        orderGrid.getChildren().removeAll(parent.priceList.get(parent.currentCountToDelete));
//        orderGrid.getChildren().removeAll(parent.pizzaOrderLabelSizeList);
//        parent.pizzaOrderLabelList.remove(parent.currentButtonToDelete);
//        parent.getPizzaController().theOrderPizza.remove(currentlySelectedPizza);
//        parent.priceList.remove(parent.currentCountToDelete);
//        parent.pizzaOrderLabelSizeList.remove(parent.currentCountToDelete);
//        parent.orderCalc.removeItem(currentlySelectedPizza);
       // currentOrder.removeItem(currentlySelectedPizza);
        openPizzaOrderGrid();
//        parent.calcPriceSummaryPizza();
        updateOrderGrid();
        if(this.hasParent()){
            parent.updateSubtotal();
        }
//        parent.updatePrice();
    }

    // ------ "DETAIL PIZZA PANE" SERIES ------

    /**
     * Shows the pizza detail pane
     * @param event
     */
    @FXML
    public void enableDetailList(Event event) {
        try{
            Object source = event.getSource();
            clearDetailArray();
            //parent.initializeTheOrderPizza();
            Label label;
            int count = 1;
            int row = GridPane.getRowIndex((Node)source);
           // assert currentOrder.getItems().get(row) instanceof Pizza: "CALLED DETAIL LIST ON SOMETHING THAT ISNT A PIZZA!!";
            Item x = currentOrder.getItems().get(row);
           // currentlySelectedPizza = (Pizza)x;
            ArrayList<Topping> list = currentlySelectedPizza.getToppingList();
            RowConstraints r = pizzaDetailGrid.getRowConstraints().get(0);
            pizzaDetailGrid.getRowConstraints().clear();
            pizzaDetailGrid.getRowConstraints().add(r);

            Label pizzaName = new Label(currentlySelectedPizza.getName().substring(3));
            Label pizzaNameBase = new Label("Base Price:");
            Label size = new Label(currentlySelectedPizza.getName().substring(0, 3));

            Label pizzaPrice = new Label("$" + formatter.format(CurrencyConverter.build(currentlySelectedPizza.getPriceNonCalculated())));
            Label basePrice = new Label("$" + formatter.format(CurrencyConverter.build(Menu.loadedToppings.getBasePrices().getPriceForSize(currentlySelectedPizza.getSize()))));
            pizzaDetailGrid.add(size, 0, 0);

            if (currentlySelectedPizza.getClass() == PremadePizza.class) {
                pizzaDetailGrid.add(pizzaName, 1, 0);
                pizzaDetailGrid.add(pizzaPrice, 2, 0);
                // parent.previousArray.add(pizzaPrice);
                // parent.previousArray.add(pizzaName);
            } else {
                pizzaDetailGrid.add(pizzaNameBase, 1, 0);
                pizzaDetailGrid.add(basePrice, 2, 0);
                // parent.previousArray.add(toppingPrice);
                // parent.previousArray.add(basePrice);
                // parent.previousArray.add(pizzaNameBase);
            }
            for (Topping topping : list) {
                if (!topping.isNegation()) {
                    label = new Label(topping.getName().replace("_", " "));
                    pizzaDetailGrid.getRowConstraints().add(r);
                    pizzaDetailGrid.add(label, 1, count);
                    showPriceDetailList(currentlySelectedPizza, topping, count);
                    count++;
                }
            }
            for (Topping topping : list) {
                if (topping.isNegation()) {
                    label = new Label("MINUS: " + topping.getName().replace("_", " "));
                    pizzaDetailGrid.getRowConstraints().add(r);
                    pizzaDetailGrid.add(label, 1, count);
                    showPriceDetailList(currentlySelectedPizza, topping, count);
                    count++;
                }
            }
            orderGrid.setVisible(false);
            pizzaDetailAnchorPane.setVisible(true);
            pizzaDetailGrid.setVisible(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Shows the price detail list for a pizza
     * @param pizza the pizza that the price is for
     * @param topping the topping that the price is for
     * @param count the index of the topping
     */
    public void showPriceDetailList(Pizza pizza, Topping topping, int count) {
        Label toppingPrice = new Label("$" + formatter.format(CurrencyConverter.build(topping.getPrice(pizza.getSize()))));
        //parent.previousArray.add(size);
        //TODO am i making 2000 of these??
        if (pizza.getClass() != PremadePizza.class) {
            pizzaDetailGrid.add(toppingPrice, 2, count);
        }
    }

    private boolean hasParent(){
        if(this.parent != null){
            return true;
        }
        return false;
    }
}

class PizzaOrderView extends RecursiveTreeObject<PizzaOrderView> {
    StringProperty pizzaName;
    StringProperty price;

    public PizzaOrderView(String name, String price) {
        this.pizzaName = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
    }
}

class DinnerOrderView extends RecursiveTreeObject<DinnerOrderView> {
    StringProperty dinnerName;
    StringProperty price;

    public DinnerOrderView(String name, String price) {
        this.dinnerName = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
    }
}

class OrderView extends RecursiveTreeObject<OrderView> {
    StringProperty orderName;
    StringProperty price;
    Item currentItem;

    public OrderView(String name, String price, Item currentItem) {
        this.orderName = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.currentItem = currentItem;
    }
}
