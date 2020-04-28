/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.Pizza;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadePizza;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.util.currency.CurrencyConverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class OrderViewController implements ParentController<PickupDeliveryPaneController> {

    @FXML
    public GridPane orderGrid;

    @FXML
    private GridPane pizzaDetailGrid;

    @FXML
    private AnchorPane orderGridAnchor;

    @FXML
    private AnchorPane pizzaDetailAnchorPane;




    private PickupDeliveryPaneController parent;
    NumberFormat formatter = new DecimalFormat("#0.00");

    private Pizza currentlySelectedPizza;

    private Order currentOrder;



    @Override
    public void close() {

    }

    @Override
    public Object getCurrentItem() {
        return null;
    }

    @Override
    public void open() {
        if(hasParent()){
            currentOrder = parent.getCurrentOrder();
        }
    }

    @Override
    public void setParent(PickupDeliveryPaneController o) {
        this.parent = o;
    }



    // ------ "MISC" SERIES ------
    @FXML
    public void editPizza() {
        Pizza originalPizza = currentlySelectedPizza;
        parent.getPizzaController().setCurrentPizza(originalPizza);
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
        parent.getCurrentOrder().calculateFees();
        ArrayList<Item> orderList = parent.getCurrentOrder().getItems();
        int count = 0;
        RowConstraints r = orderGrid.getRowConstraints().get(0);
        orderGrid.getRowConstraints().clear();
        orderGrid.getRowConstraints().add(r);
        System.out.println("updating order grid 1: " + orderList.size());
        for(Item i: orderList){
            orderGrid.getRowConstraints().add(r);
            if(/*i instanceof Pizza*/true){
//                String[] split = i.getName().split(" ");
//                Label label = new Label(split[0]);
                try{
                    addToGridPizza(i.getName(), count);
                    addToGridPrice(i, count);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }else{
               // System.out.println(i.getPriceNonCalculated());
                addToGridDinner(i.getName(), count);
                addToGridPrice(i, count);
            }
            count++;
        }
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
