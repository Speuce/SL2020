package main.java.lucia.fxml.controllers.impl.main.tabs.viewOrder;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.Pizza;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadePizza;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.ViewOrdersPane;
import main.java.lucia.util.currency.CurrencyConverter;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * The controller for the view order pane
 * @author Matt Kwiatkowski
 */
public class OrderInfoController implements ParentController<ViewOrdersPane> {

    @FXML
    private Pane complaintPane;

    @FXML
    private Pane orderDetailsPane;

    @FXML
    private Pane complaintAnchor;

    @FXML
    private ComplaintPaneController complaintPaneController;

    @FXML
    private ScrollPane viewOrderDetailsScrollPane;

    /**
     * the currently selected order
     */
    private Order currentOrder;

    public ViewOrdersPane parent;

    NumberFormat formatter = new DecimalFormat("#0.00");

    @FXML
    public GridPane orderGrid;

    @FXML
    private GridPane pizzaDetailGrid;

    @FXML
    private AnchorPane orderGridAnchor;

    @FXML
    private AnchorPane pizzaDetailAnchorPane;

    private Pizza currentlySelectedPizza;

    @FXML
    private void initialize(){
        complaintAnchor.setVisible(false);
        complaintPane.setVisible(true);
        complaintPaneController.setParent(this);
    }

    public void setOrder(Order o){
        this.currentOrder = o;
    }

    @FXML
    private void editCustomer(MouseEvent e){

    }

    @FXML
    private void reprintOrder(MouseEvent e){
        PrinterJob job = PrinterJob.getPrinterJob();
        currentOrder.setReprint(true);
        job.setPrintable(currentOrder);
        try {
            job.print();
        } catch (PrinterException e1){
            //ignored
        }
    }

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
        currentOrder.calculateFees();
        ArrayList<Item> orderList = currentOrder.getItems();
        int count = 0;
        RowConstraints r = orderGrid.getRowConstraints().get(0);
        orderGrid.getRowConstraints().clear();
        orderGrid.getRowConstraints().add(r);
        System.out.println("updating order grid 1: " + orderList.size());
        for(Item i: orderList){
            orderGrid.getRowConstraints().add(r);
            if(/*i instanceof Pizza*/ true){
//                String[] split = i.getName().split(" ");
//                Label label = new Label(split[0]);
                try{
                    addToGridPizza(i.getName(), count);
                    addToGridPrice(i, count);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }else{
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
        b.setStyle("-fx-font-size: 20; -fx-alignment: center-left");

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
        b.setStyle("-fx-font-size: 20");
        b.setStyle("-fx-font-size: 20; -fx-alignment: center-left");
        //parent.dinnerOrderLabelList.add(parent.button);
        orderGrid.add(b, 1, pos);
    }

    /**
     * Adds a price to the grid
     * @param item the item to caculate the price of
     * @param pos the row index to add the price to
     */
    public void addToGridPrice(Item item, int pos) {
        //item.calculatePrice();
        Label label = new Label("$" + formatter.format(CurrencyConverter.build(item.getPrice())));
        if(item.getPrice()> 99)
            label.setStyle("-fx-font-size: 20; -fx-alignment: center-left");
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
            //currentlySelectedPizza = (Pizza)x;
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
                    label.setStyle("-fx-font-size: 18; -fx-alignment: center-left");
                    pizzaDetailGrid.getRowConstraints().add(r);
                    pizzaDetailGrid.add(label, 1, count);
                    showPriceDetailList(currentlySelectedPizza, topping, count);
                    count++;
                }
            }
            for (Topping topping : list) {
                if (topping.isNegation()) {
                    label = new Label("MINUS: " + topping.getName().replace("_", " "));
                    label.setStyle("-fx-font-size: 18; -fx-alignment: center-left");
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
        toppingPrice.setStyle("-fx-font-size: 18; -fx-alignment: center-left");
        //TODO am i making 2000 of these??
        if (pizza.getClass() != PremadePizza.class) {
            pizzaDetailGrid.add(toppingPrice, 2, count);
        }
    }

    @FXML
    private void complaint(MouseEvent e){
        closePanes();
        complaintAnchor.setVisible(true);
        if(currentOrder.hasComplaint()){
            complaintPaneController.open(currentOrder.getApplicableComplaint(), false);
        }else{
            complaintPaneController.open(currentOrder.getOrderNumber(), currentOrder.getCustomerDetails().getRowNum());
        }
    }

    @FXML
    private void editPayment(MouseEvent e){

    }

    /**
     * Opens this pane
     */
    @Override
    public void open(){
        closePanes();
        currentlySelectedPizza = null;
        updateOrderGrid();
        openPizzaOrderGrid();
    }

    @Override
    public void setParent(ViewOrdersPane o) {
        this.parent = o;
    }

    @FXML
    private void back(MouseEvent e){
        orderDetailsPane.setVisible(false);
        complaintAnchor.setVisible(false);
        parent.close();
    }

    public void closePanes(){
        complaintAnchor.setVisible(false);
    }

    @FXML
    private void editOrder(MouseEvent e){

    }

    @FXML
    public void openPizzaOrderGrid() {
        orderGrid.setVisible(true);
        pizzaDetailGrid.setVisible(false);
        pizzaDetailAnchorPane.setVisible(false);
    }

    @Override
    public void close() {
        closePanes();
    }

    @Override
    public Object getCurrentItem() {
        return currentOrder;
    }
}
