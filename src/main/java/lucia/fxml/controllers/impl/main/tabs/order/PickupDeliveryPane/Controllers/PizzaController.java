/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import main.java.lucia.Client;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.Pizza;
import main.java.lucia.client.content.menu.legacy.impl.misc.CrustTypes;
import main.java.lucia.client.content.menu.legacy.impl.misc.Sauce;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadePizza;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadePizzaName;
import main.java.lucia.client.content.menu.legacy.size.Size;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;
import main.java.lucia.client.content.menu.legacy.toppings.names.GourmetToppingNames;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.pizzaMenuDynamicLoad;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;
import main.java.lucia.util.currency.CurrencyConverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class PizzaController implements ParentController<PickupDeliveryPaneController> {

    @FXML
    private Pane pizzaPane;

    @FXML
    public Pane settingsButtons;

    @FXML
    private Line line1;

    @FXML
    public JFXButton crust;

    @FXML
    public JFXButton sauce;

    @FXML
    private JFXButton make;

    @FXML
    public AnchorPane extraPane;

    @FXML
    public JFXButton easy;

    @FXML
    public JFXButton extra;

    @FXML
    public JFXButton xextra;

    @FXML
    private JFXButton light_sauce;

    @FXML
    private JFXButton well_done;

    @FXML
    private Pane pizzaSpecials;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public AnchorPane specialAnchor;

    @FXML
    private JFXButton sls;

    @FXML
    private JFXButton meat_lover;

    @FXML
    private JFXButton veggie;

    @FXML
    private JFXButton aloha;

    @FXML
    private JFXButton mexican;

    @FXML
    private JFXButton greek;

    @FXML
    private JFXButton mediterranean;

    @FXML
    private JFXButton chicken_supreme;

    @FXML
    private JFXButton healthy_choice;

    @FXML
    private JFXButton santa_fe;

    @FXML
    private JFXButton montenegro;

    @FXML
    private JFXButton bbq_chicken;

    @FXML
    private JFXButton italian;

    @FXML
    private JFXButton hal_joe;

    @FXML
    private JFXButton citi92;

    @FXML
    public Pane pizzaButtons;

    @FXML
    private JFXButton pepperoni;

    @FXML
    private JFXButton chorizo;

    @FXML
    private JFXButton banana_pepper;

    @FXML
    private JFXButton shrimp;

    @FXML
    private JFXButton broccoli;

    @FXML
    private JFXButton jalapeno;

    @FXML
    private JFXButton chicken;

    @FXML
    private JFXButton sausage;

    @FXML
    private JFXButton prosciutto;

    @FXML
    private JFXButton spinach;

    @FXML
    private JFXButton marinated_eggplant;

    @FXML
    private JFXButton feta;

    @FXML
    private JFXButton red_onion;

    @FXML
    private JFXButton salami;

    @FXML
    private JFXButton onion;

    @FXML
    private JFXButton chili_pepper;

    @FXML
    private JFXButton green_olives;

    @FXML
    private JFXButton black_olives;

    @FXML
    private JFXButton anchovies;

    @FXML
    private JFXButton artichoke;

    @FXML
    private JFXButton sundried_tomato;

    @FXML
    private JFXButton green_pepper;

    @FXML
    private JFXButton pineapple;

    @FXML
    private JFXButton tomato;

    @FXML
    private JFXButton mushroom;

    @FXML
    private JFXButton beef;

    @FXML
    private JFXButton side_bacon;

    @FXML
    private JFXButton red_pepper;

    @FXML
    private JFXButton back_bacon;

    @FXML
    private JFXButton ham;

    @FXML
    public AnchorPane cheeseAnchor;

    @FXML
    private JFXButton chz;

    @FXML
    private JFXButton nochz;

    @FXML
    private JFXButton cheddar;

    @FXML
    public Pane saucePane;

    @FXML
    private JFXButton extra_sauce;

    @FXML
    private JFXButton none;

    @FXML
    private JFXButton regular;

    @FXML
    private JFXButton greek_sauce;

    @FXML
    private JFXButton barbeque;

    @FXML
    private JFXButton alfredo;

    @FXML
    public Pane crustPane;

    @FXML
    private JFXButton thin;

    @FXML
    private JFXButton thick;

    @FXML
    private JFXButton regular_crust;

    @FXML
    private JFXButton gluten_free;

    @FXML
    private JFXButton keto;

    @FXML
    private JFXButton whole_wheat;

    @FXML
    private JFXButton chzPizza;

    @FXML
    public Pane sizeButtons;

    @FXML
    private JFXButton ten;

    @FXML
    private JFXButton thirteen;

    @FXML
    private JFXButton fifteen;

    @FXML
    private JFXButton eighteen;

    @FXML
    private JFXButton thirty;

    @FXML
    private Pane halfPane;

    @FXML
    private JFXButton half;

    @FXML
    private JFXButton togglePrice;

    private PickupDeliveryPaneController parent;
    public Pizza currentPizza = new Pizza();
    private Pizza secondHalfPizza = new Pizza();
    public ArrayList<Node> specialPizzasList = new ArrayList<>();
    public ArrayList<String> toppingsNamesList = new ArrayList<>();
    public ArrayList<String> specialPizzasName = new ArrayList<>();
    public ArrayList<Node> toppingsListScan = new ArrayList<>();
    public ArrayList<String> toppingNameListText = new ArrayList<>();
    Font originalToppingFont;
    private boolean isFirstHalf = false;
    private boolean isSecondHalf = false;
    private boolean changeSize = false;
    private boolean edit = false;
    private double pos = 0;

    /**
     * The currently selected size
     */
    private Size selectedSize;

    String secondHalfBuild = "";
    String firstHalfBuild = "";
    String specialBuild = "";
    NumberFormat formatter = new DecimalFormat("#0.00");

    JFXButton[] toppingsList;
    JFXButton[] sizeList;
    JFXButton[] crustList;
    JFXButton[] halfCheckList;
    JFXButton[] specialsList;
    JFXButton[] cheeseArray;
    JFXButton[] sauceList;
    Pane[] specialPaneArray;

    public String[] defaultSelectedGroups;

    @FXML
    public void initialize() {
        crustList = new JFXButton[]{thin, thick, regular_crust, gluten_free, keto, whole_wheat};
        defaultSelectedGroups = new String[]{"ToppingsSelected", "ToppingsSelectedEZ", "ToppingsSelectedX", "ToppingsSelectedXX"};
        toppingsList = new JFXButton[]{pepperoni, chorizo, banana_pepper, shrimp, broccoli, jalapeno, chicken, sausage, prosciutto, cheddar, spinach, chz, nochz, marinated_eggplant,
                feta, red_onion, salami, onion, chili_pepper, green_olives, black_olives, anchovies, artichoke, sundried_tomato, green_pepper, pineapple,
                tomato, mushroom, beef, side_bacon, red_pepper, back_bacon, ham, chzPizza};
        sizeList = new JFXButton[]{ten, thirteen, fifteen, eighteen, thirty};
        halfCheckList = new JFXButton[]{half, togglePrice};
        specialsList = new JFXButton[]{sls, meat_lover, veggie, aloha, mexican, greek, mediterranean, chicken_supreme,
                healthy_choice, santa_fe, montenegro, bbq_chicken, italian, hal_joe, citi92};
        cheeseArray = new JFXButton[]{chz, cheddar, nochz};
        sauceList = new JFXButton[]{none, regular, greek_sauce, barbeque, alfredo};
        specialPaneArray = new Pane[]{extraPane, pizzaButtons, settingsButtons, cheeseAnchor};
        pizzaButtons.getChildren().forEach(button -> {
            toppingsNamesList.add(button.getId());
            toppingsListScan.add(button);
        });
        specialAnchor.getChildren().forEach(button -> {
            specialPizzasName.add(button.getId());
            specialPizzasList.add(button);
        });
        IntStream.range(0, toppingsList.length).forEach(x -> toppingNameListText.add(toppingsList[x].getText()));
        originalToppingFont = toppingsList[2].getFont();
    }
    public boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }
    @FXML
    void scroll(ScrollEvent event) {
        double currentPos = event.getDeltaY();
        if (currentPos < pos)
            scrollPane.setHvalue(pos++ / 5);
        else scrollPane.setHvalue(pos-- / 5);
    }
    @FXML
    void setAdditionalPane(ActionEvent event) {
        parent.setAdditionalPane(event);
    }

    // ------ "ADD TO ORDER" SERIES ------
    @FXML
    void addToOrder(ActionEvent event) {
        try{
            if(edit) checkToppings();
            boolean checkHalf = false;
            boolean checkToppings = false;
            boolean special = false;

            JFXButton theSpecial = new JFXButton();

            if(isEnabled(keto, "ToppingsSelectedCrustSauce"))
                currentPizza.setSize(Size.TEN);
            if(isEnabled(gluten_free, "ToppingsSelectedCrustSauce"))
                currentPizza.setSize(Size.TEN);
            else{
                for(int x = 0; x < sizeList.length; x++)
                    if(isEnabled(sizeList[x], "ToppingsSelected"))
                        selectedSize = Size.valueOf(sizeList[x].getId().toUpperCase());
                if(selectedSize == null){
                    setButtonPane(sizeButtons, "BackgroundDefault", "BackgroundNeed");
                    return;
                }else{
                    currentPizza.setSize(selectedSize);
                }
            }
            if (isEnabled(half, "ToppingsSelected"))
                checkHalf = true;
            for (int x = 0; x < toppingsList.length; x++)
                for (int y = 0; y < defaultSelectedGroups.length; y++)
                    if (isEnabled(toppingsList[x], defaultSelectedGroups[y])) {
                        checkToppings = true;
                        break;
                    }
            for (int x = 0; x < specialsList.length; x++)
                if (isEnabled(specialsList[x], "ToppingsSelectedGreen")) {
                    special = true;
                    theSpecial = specialsList[x];
                    break;
                }
            if (special) {
                if (isFirstHalf) {
                    currentPizza.enableSecondHalf();
                    for (int x = 0; x < toppingsList.length; x++)
                        toppingsList[x].getStyleClass().removeAll("ToppingsSelected");
                    theSpecial.getStyleClass().removeAll("ToppingsSelectedGreen");
                    half.setText("2nd Half");
                    isSecondHalf = true;
                    isFirstHalf = false;
                } else if (isSecondHalf) {
                    currentPizza.setSecondHalf(secondHalfPizza);
                    currentPizza.calcName();
                    //theOrderPizza.add(currentPizza);
                    secondHalfBuild = currentPizza.getName();
                    //parent.getOrderViewController().addToGrid(secondHalfBuild, parent.orderCount);
                    half.getStyleClass().removeAll("ToppingsSelected");
                    parent.removeAllSelected();
                    parent.orderCount++;
                    secondHalfBuild = "";
                    firstHalfBuild = "";
                    currentPizza.disableSecondHalf();
                    isFirstHalf = false;
                    isSecondHalf = false;
                    half.setText("1st Half");
                    calcOrder(currentPizza);
                    //parent.getOrderViewController().updateOrderGrid();
                    //parent.updateSubtotal();
                    parent.setDefaultSelected("ToppingsSelected");
                    currentPizza = new Pizza();
                    secondHalfPizza = new Pizza();
                } else {
                    currentPizza.calcName();
                    specialBuild += currentPizza.getName();
                    //parent.getOrderViewController().addToGrid(specialBuild, parent.orderCount);
                    parent.orderCount++;
                    parent.removeAllSelected();
                    specialBuild = "";
                    //theOrderPizza.add(currentPizza);
                    calcOrder(currentPizza);
                    //parent.getOrderViewController().updateOrderGrid();
                    //parent.updateSubtotal();
                    parent.setDefaultSelected("ToppingsSelected");
                    currentPizza = new Pizza();
                }
            } else if (checkToppings) {
                if (isFirstHalf) {
                    currentPizza.enableSecondHalf();
                    for (int x = 0; x < toppingsList.length; x++)
                        toppingsList[x].getStyleClass().removeAll("ToppingsSelected");
                    half.setText("2nd Half");
                    isSecondHalf = true;
                    isFirstHalf = false;
                } else if (isSecondHalf) {
                    currentPizza.setSecondHalf(secondHalfPizza);
                    currentPizza.calcName();
                    secondHalfBuild = currentPizza.getName();
                    //parent.getOrderViewController().addToGrid(secondHalfBuild, parent.orderCount);
                    // theOrderPizza.add(currentPizza);
                    calcOrder(currentPizza);
                    //parent.getOrderViewController().updateOrderGrid();
                    //parent.updateSubtotal();
                    half.getStyleClass().removeAll("ToppingsSelected");
                    parent.removeAllSelected();
                    parent.orderCount++;
                    secondHalfBuild = "";
                    firstHalfBuild = "";
                    currentPizza.disableSecondHalf();
                    isFirstHalf = false;
                    isSecondHalf = false;
                    half.setText("1st Half");
                    currentPizza = new Pizza();
                    secondHalfPizza = new Pizza();
                } else
                    try {
                        currentPizza.calcName();
                        //parent.getOrderViewController().addToGrid(currentPizza.getName(), parent.orderCount);
                        parent.orderCount++;
                        parent.removeAllSelected();
                        // theOrderPizza.add(currentPizza);
                        calcOrder(currentPizza);
                        // parent.getOrderViewController().updateOrderGrid();
                        // parent.updatePrice();
                        currentPizza = new Pizza();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
            }
            else if (!checkToppings)
                setButtonPane(pizzaButtons, "BackgroundDefault", "BackgroundNeed");
            else setButtonPane(sizeButtons, "BackgroundDefault", "BackgroundNeed");
            edit = false;
            selectedSize = null;

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ------ "CHECK TOPPINGS" SERIES ------
    public void checkToppings() {
        currentPizza = new Pizza();
        Topping topping = null;
        for(int x = 0; x < specialsList.length; x++)
            if(isEnabled(specialsList[x], "ToppingsSelectedGreen")) {
                currentPizza = Menu.loadedPreMadeFoods.getPizza(PremadePizzaName.valueOf(specialsList[x].getId().toUpperCase()));
                for(int y = 0; y < toppingsList.length; y++) {
                    try {
                        topping = Menu.loadedToppings.get(toppingsList[y].getId());
                    }
                    catch (Exception e) {}
                    if (!currentPizza.getToppingList().contains(topping))
                        try {
                            for (int i = 0; i < defaultSelectedGroups.length; i++)
                                if (isEnabled(toppingsList[y], defaultSelectedGroups[i])) {
                                    if (parent.getDefaultSelected().equals("ToppingsSelectedEZ"))
                                        currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[y].getId()).setEasy(true));
                                    else if (parent.getDefaultSelected().equals("ToppingsSelectedX")) {
                                        currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[y].getId()));
                                        currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[y].getId()));
                                    } else if (parent.getDefaultSelected().equals("ToppingsSelectedXX")) {
                                        currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[y].getId()));
                                        currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[y].getId()));
                                        currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[y].getId()));
                                    } else currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[y].getId()));
                                }
                        } catch (Exception e) {}
                    if (currentPizza.getToppingList().contains(topping))
                        if(!isEnabled(toppingsList[y], "ToppingsSelected")) {
                            try {
                                currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[y].getId()).setNegation(true));
                            }
                            catch (Exception e) {};
                        }
                }
                return;
            }
        for(int x = 0; x < toppingsList.length; x++)
            for(int y = 0; y < defaultSelectedGroups.length; y++)
                if(isEnabled(toppingsList[x], defaultSelectedGroups[y])) {
                    try {
                        if (defaultSelectedGroups[y].equals("ToppingsSelectedEZ")) {
                            currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[x].getId()).setEasy(true));
                        } else if (defaultSelectedGroups[y].equals("ToppingsSelectedX")) {
                            currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[x].getId()));
                            currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[x].getId()));
                        } else if (defaultSelectedGroups[y].equals("ToppingsSelectedXX")) {
                            currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[x].getId()));
                            currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[x].getId()));
                            currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[x].getId()));
                        } else currentPizza.addTopping(Menu.loadedToppings.get(toppingsList[x].getId()));
                    }
                    catch (Exception e) {}
                }
    }

    public void setCurrentPizza(Pizza p){
        this.currentPizza = p;
        bringUpPizza(p);
    }

    // ------ "BRING UP" SERIES ------
    private void bringUpPizza(Pizza pizza) {
        for(int x = 0; x < pizza.getToppingList().size(); x++)
            for(int y = 0; y < toppingsList.length; y++)
                if(pizza.getToppingList().get(x).getName().equalsIgnoreCase(toppingsList[y].getId()))
                    if(!pizza.getToppingList().get(x).isNegation())
                        toppingsList[y].getStyleClass().add("ToppingsSelected");
                    else toppingsList[y].getStyleClass().removeAll("ToppingsSelected");
        for(int x = 0; x < specialsList.length; x++) {
            if (pizza instanceof PremadePizza)
                if ((pizza.getName().substring(pizza.getName().indexOf(" ") + 1).trim()).contains(specialsList[x].getId().toUpperCase()))
                    specialsList[x].getStyleClass().add("ToppingsSelectedGreen");
        }
        bringUpSize(pizza);
        edit = true;
    }
    private void bringUpSize(Pizza pizza) {
        Size size = pizza.getSize();
        sizeSelected(size);
    }


    // ------ "HOVER" SERIES ------
    @FXML
    public void activateHover(MouseEvent event) {
        if(parent == null){
            return;
        }
        parent.activateHover(event);
    }
    @FXML
    public void activateHoverGreen(MouseEvent event) {
        parent.activateHoverGreen(event);
    }
    @FXML
    public void deactivateHover(MouseEvent event) {
        Style.deactivateHover(event);
    }
    @FXML
    public void deactivateHoverGreen(MouseEvent event) {
        Style.deactivateHoverGreen(event);
    }

    // ------ "SET" SERIES ------
    public void setButtonPane(Pane pane, String remove, String add) {
        pane.getStyleClass().removeAll(remove);
        pane.getStyleClass().add(add);
    }

    // ------ "CALC" SERIES ------
    public void calcOrder(Pizza currentPizza) {
        System.out.println("added pizza to order!");
       // parent.addToOrder(currentPizza);
    }

//    // ------ "GRID" SERIES ------
//    public void addPrices(Order order) {
//        parent.orderCalc.calculateGrandTotalWNames();
//        parent.clearPrices();
//        Label label = new Label();
//        for (int x = 0; x < parent.orderCalc.getItems().size(); x++) {
//            label = new Label("$" + formatter.format(CurrencyConverter.build(order.getItems().get(x).getPriceNonCalculated())));
//            if (order.getItems().get(order.getItems().size() - 1).getPriceNonCalculated() > 99)
//                label.setStyle("-fx-font-size: 12; -fx-alignment: center-left");
//            else label.setStyle("-fx-font-size: 15; -fx-alignment: center-left");
//
//            parent.priceList.add(label);
//            label.setPrefSize(50, 30);
//            label.setMaxSize(50, 30);
//            label.setMinSize(50, 30);
//
//            parent.getOrderViewController().orderGrid.add(label, 2, x);
//            //addToGrid(order.getItems().get(x).getName(), x + 1);
//        }
//    }

    // ------ "BUILD" SERIES ------
    public void cheeseBuild() {
        for (int x = 0; x < cheeseArray.length; x++)
            if (isEnabled(cheeseArray[x], parent.getDefaultSelected())) {
                if (isFirstHalf)
                    extraEasy(x, secondHalfPizza);
                else
                    extraEasy(x, currentPizza);
            }
    }
    @FXML
    void crustBuild(MouseEvent event) {
        if (isEnabled(well_done, "ToppingsSelectedGreen"))
            currentPizza.setWellDone(true);
        for (int x = 0; x < crustList.length; x++){
            if (isEnabled(crustList[x], "ToppingsSelectedCrustSauce")) {
                currentPizza.setCrust(CrustTypes.valueOf(crustList[x].getId().toUpperCase()));
                crustList[x].getStyleClass().removeAll("ToppingsSelectedCrustSauce");
            }
        }
        //Default keto and gluten to 10 inch.
        if(event.getSource() == keto || event.getSource() == gluten_free){
            sizeSelected(Size.TEN);
            currentPizza.setSize(Size.TEN);
        }else{
            currentPizza.setSize(Size.NINE);
        }
        currentPizza.calcName();
    }

    @FXML
    void sauceBuild(MouseEvent event) {
        if (isEnabled(light_sauce, "ToppingsSelectedGreen")) {
            currentPizza.setLightSauce(true);
            light_sauce.getStyleClass().removeAll("ToppingsSelectedGreen");
        }
        else if(isEnabled(extra_sauce, "ToppingsSelectedCrustSauce")) {
            currentPizza.setExtraSauce(true);
            extra_sauce.getStyleClass().removeAll("ToppingsSelectedCrustSauce");
        }
        for (int x = 0; x < sauceList.length; x++)
            if (isEnabled(sauceList[x], "ToppingsSelectedCrustSauce")) {
                currentPizza.setSauce(Sauce.valueOf(sauceList[x].getId().toUpperCase()));
                sauceList[x].getStyleClass().removeAll("ToppingsSelectedCrustSauce");
            }
        currentPizza.setSize(Size.NINE);
        currentPizza.calcName();
    }

    // ------ "SELECTED" SERIES ------
    @FXML
    void justCheeseSelected(MouseEvent event) {
        if(isEnabled(chzPizza, "ToppingsSelected")) {
            currentPizza.setJustCheese(false);
            chzPizza.getStyleClass().removeAll("ToppingsSelected");
        }
        else {
            currentPizza.setJustCheese(true);
            chzPizza.getStyleClass().add("ToppingsSelected");
        }
        currentPizza.setSize(Size.NINE);
    }
    @FXML
    public void selected(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        setButtonPane(pizzaButtons, "BackgroundNeed", "BackgroundDefault");
        JFXButton button = (JFXButton) event.getSource();
        currentPizza.setSize(Size.NINE);
        for (int x = 0; x < specialPizzasList.size(); x++) {
            JFXButton special = (JFXButton) specialPizzasList.get(x);
            if (isEnabled(special, "ToppingsSelectedGreen")) {
                for (int y = 0; y < defaultSelectedGroups.length; y++)
                    if (isEnabled(button, defaultSelectedGroups[y])) {
                        try {
                            if (isSecondHalf) {
                                secondHalfPizza.addTopping(Menu.loadedToppings.get(button.getId().toLowerCase()).setNegation(true));
                                secondHalfPizza.setSize(Size.NINE);
                                button.getStyleClass().removeAll("ToppingsSelected");
                            } else {
                                currentPizza.addTopping(Menu.loadedToppings.get(button.getId().toLowerCase()).setNegation(true));
                                currentPizza.calcName();
                                currentPizza.setSize(Size.NINE);
                                button.getStyleClass().removeAll(defaultSelectedGroups[y]);
                            }
                        } catch (Exception e) {
                            Client.logger.error("Invalid Button Name " + button.getId() + " ", e);
                        }
                        return;
                    } else {
                        button.getStyleClass().add(parent.getDefaultSelected());
                        try {
                            if (isSecondHalf) {
                                secondHalfPizza.addTopping(Menu.loadedToppings.get(button.getId().toLowerCase()));
                                secondHalfPizza.calcName();
                                secondHalfPizza.setSize(Size.NINE);
                            } else {
                                easyExtraCurrent(button, currentPizza);
                                currentPizza.setSize(Size.NINE);
                                currentPizza.calcName();
                            }
                        } catch (Exception e) {
                            Client.logger.error("Invalid Button Name " + button.getId() + " ", e);
                        }
                        return;
                    }
            }
        }
        for (int x = 0; x < defaultSelectedGroups.length; x++)
            if (isEnabled(button, defaultSelectedGroups[x])) {
                button.getStyleClass().removeAll(defaultSelectedGroups[x]);
                try {
                    if (isSecondHalf) {
                        secondHalfPizza.addTopping(Menu.loadedToppings.get(button.getId()).setNegation(true));
                        secondHalfPizza.setSize(Size.NINE);
                    } else {
                        currentPizza.addTopping(Menu.loadedToppings.get(button.getId()).setNegation(true));
                        currentPizza.setSize(Size.NINE);
                    }
                } catch (Exception e) {
                    Client.logger.error("Invalid Button Name " + button.getId() + " ", e);
                }
                currentPizza.setSize(Size.NINE);
                return;
            }
        try {
            if (isSecondHalf) {
                easyExtraCurrent(button, secondHalfPizza);
            } else {
                if (!edit) {
                    extraToppingAdd(button, currentPizza);
                }
            }
        } catch (Exception e) {
            Client.logger.error("Invalid Button Name " + button.getId() + " ", e);
        }
        currentPizza.setSize(Size.NINE);
        button.getStyleClass().clear();
        button.getStyleClass().add(parent.getDefaultSelected());
    }
    @FXML
    void selectedGreen(ActionEvent event) {
        parent.selectedGreen(event);
    }

    /**
     * Called when a size is selected
     * @param s the {@link Size} selected
     */
    private void sizeSelected(Size s){
        parent.setOptionPanesVisible(false);
        /* Change Background back to normal after selected */
        setButtonPane(sizeButtons, "BackgroundNeed", "BackgroundDefault");
        Button b = mapSizeToButton(s);
        if(s != selectedSize){
            /* Reset style of the other buttons */
            for(JFXButton button: sizeList){
                if(button != b){
                    button.getStyleClass().removeAll("ToppingsSelected");
                }
            }
            if(b != null){
                b.getStyleClass().add("ToppingsSelected");
            }
            selectedSize = s;
        }else if(b != null){
            selectedSize = null;
            b.getStyleClass().removeAll("ToppingsSelected");
        }
        if(isEnabled(togglePrice, "ToppingsSelected")) {
            changeSize = true;
            togglePrice();
        }
    }

    /**
     * Maps a size to its corresponding button
     * @param r the size to map to
     * @return the JFXButton corresponding to this size
     */
    private JFXButton mapSizeToButton(Size r){
        switch(r){
            case TEN:
                return ten;
            case THIRTEEN:
                return thirteen;
            case FIFTEEN:
                return fifteen;
            case EIGHTEEN:
                return eighteen;
            case THIRTY:
                return thirty;
            default:
                return null;
        }
    }

    /**
     * If applicable, resets the size area.
     */
    private void resetSizeArea(){
        for(JFXButton button: sizeList){
                button.getStyleClass().removeAll("ToppingsSelected");
        }
        setButtonPane(sizeButtons, "BackgroundNeed", "BackgroundDefault");
    }

    @FXML
    void selectedSize10(MouseEvent event) {
        sizeSelected(Size.TEN);
    }

    @FXML
    void selectedSize13(MouseEvent event) {
        sizeSelected(Size.THIRTEEN);
    }

    @FXML
    void selectedSize15(MouseEvent event) {
        sizeSelected(Size.FIFTEEN);
    }

    @FXML
    void selectedSize18(MouseEvent event) {
        sizeSelected(Size.EIGHTEEN);
    }

    @FXML
    void selectedSize30(MouseEvent event) {
        pizzaMenuDynamicLoad tester = new pizzaMenuDynamicLoad(this);
        tester.createToppings();
        sizeSelected(Size.THIRTY);
    }

    public void test() {
        pizzaButtons.getChildren().add(new JFXButton("TEST"));
    }

    @FXML
    public void selectedHalf(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        if(half.getText().equalsIgnoreCase("1st half")) {
            currentPizza.enableSecondHalf();
            half.getStyleClass().add("ToppingsSelected");
            isFirstHalf = true;
        }
    }
    @FXML
    public void selectedExtra(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, extraPane, "ToppingsSelectedGreen");
    }
    @FXML
    public void selectedChz(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        changeGroupButtonCheese(event, cheeseAnchor, parent.getDefaultSelected());
        cheeseBuild();
    }
    @FXML
    public void justCheeseSelected() {
        if(isEnabled(chzPizza, "ToppingsSelected")) {
            currentPizza.setJustCheese(false);
            chzPizza.getStyleClass().removeAll("ToppingsSelected");
        }
        else {
            currentPizza.setJustCheese(true);
            chzPizza.getStyleClass().add("ToppingsSelected");
        }
        currentPizza.setSize(Size.NINE);
    }
    @FXML
    public void selectedSauce(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, saucePane, "ToppingsSelectedCrustSauce");
        sauceBuild(event);
    }
    @FXML
    public void selectedCrust(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, crustPane, "ToppingsSelectedCrustSauce");
        crustBuild(event);
    }
    @FXML
    void selectedSpecials(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, specialAnchor, "ToppingsSelectedGreen");
        specialsBuild();
    }

    // ------ "EASY EXTRA" SERIES ------
    private void easyExtraCurrent(JFXButton button, Pizza currentPizza) throws Exception {
        extraToppingAdd(button, currentPizza);
    }
    private void extraToppingAdd(JFXButton button, Pizza currentPizza) throws Exception {
        if (parent.getDefaultSelected().equals("ToppingsSelectedEZ")) {
            currentPizza.addTopping(Menu.loadedToppings.get(button.getId()).setEasy(true));
        } else if (parent.getDefaultSelected().equals("ToppingsSelectedX")) {
            currentPizza.addTopping(Menu.loadedToppings.get(button.getId()));
            currentPizza.addTopping(Menu.loadedToppings.get(button.getId()));
        } else if (parent.getDefaultSelected().equals("ToppingsSelectedXX")) {
            currentPizza.addTopping(Menu.loadedToppings.get(button.getId()));
            currentPizza.addTopping(Menu.loadedToppings.get(button.getId()));
            currentPizza.addTopping(Menu.loadedToppings.get(button.getId()));
        } else currentPizza.addTopping(Menu.loadedToppings.get(button.getId()));
    }
    public void changeGroupButtonCheese(MouseEvent event, Pane pane, String selectedCSS) {
        JFXButton button = (JFXButton) event.getSource();
        if (!isEnabled(button, selectedCSS)) {
            if (button.equals(nochz)) {
                if (isEnabled(cheddar, selectedCSS) && isEnabled(chz, selectedCSS)) {
                    for (int x = 0; x < defaultSelectedGroups.length; x++) {
                        chz.getStyleClass().remove(defaultSelectedGroups[x]);
                        cheddar.getStyleClass().remove(defaultSelectedGroups[x]);
                    }
                } else if (isEnabled(cheddar, selectedCSS))
                    for (int x = 0; x < defaultSelectedGroups.length; x++)
                        cheddar.getStyleClass().remove(defaultSelectedGroups[x]);
                else if (isEnabled(chz, selectedCSS))
                    for (int x = 0; x < defaultSelectedGroups.length; x++)
                        chz.getStyleClass().remove(defaultSelectedGroups[x]);
            }
            if (button.equals(chz) || button.equals(cheddar)) {
                for (int x = 0; x < defaultSelectedGroups.length; x++)
                    nochz.getStyleClass().remove(defaultSelectedGroups[x]);
            }
            button.getStyleClass().add(selectedCSS);
        } else for (int x = 0; x < defaultSelectedGroups.length; x++)
            button.getStyleClass().remove(defaultSelectedGroups[x]);
    }

    public void specialsBuild() {
        ArrayList<Topping> toppingsList = new ArrayList<>();
        String name;
        for (int x = 0; x < specialPizzasList.size(); x++) {
            if (isEnabled((JFXButton) specialPizzasList.get(x), "ToppingsSelectedGreen")) {
                if (isSecondHalf) {
                    name = specialPizzasList.get(x).getId().toUpperCase();
                    secondHalfPizza = Menu.loadedPreMadeFoods.getPizza(PremadePizzaName.valueOf(name));
                    toppingsList = secondHalfPizza.getToppingList();
                    secondHalfPizza.setSize(Size.NINE);
                } else {
                    name = specialPizzasList.get(x).getId().toUpperCase();
                    currentPizza = Menu.loadedPreMadeFoods.getPizza(PremadePizzaName.valueOf(name));
                    toppingsList = currentPizza.getToppingList();
                    currentPizza.setSize(Size.NINE);
                }
            }
        }
        enableSpecialToppings(toppingsList);
    }
    public void extraEasy(int x, Pizza secondHalfPizza) {
        if (cheeseArray[x].equals(chz)) {
            switch (parent.getDefaultSelected()) {
                case "ToppingsSelectedEZ":
                    secondHalfPizza.addTopping(
                        Menu.loadedToppings.get(GourmetToppingNames.MOZZARELLA).setEasy(true));
                    break;
                case "ToppingsSelectedX":
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.MOZZARELLA));
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.MOZZARELLA));
                    break;
                case "ToppingsSelectedXX":
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.MOZZARELLA));
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.MOZZARELLA));
                    break;
                default:
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.MOZZARELLA));
                    break;
            }
        } else if (cheeseArray[x].equals(cheddar)) {
            switch (parent.getDefaultSelected()) {
                case "ToppingsSelectedEZ":
                    secondHalfPizza.addTopping(
                        Menu.loadedToppings.get(GourmetToppingNames.CHEDDAR).setEasy(true));
                    break;
                case "ToppingsSelectedX":
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.CHEDDAR));
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.CHEDDAR));
                    break;
                case "ToppingsSelectedXX":
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.CHEDDAR));
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.CHEDDAR));
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.CHEDDAR));
                    break;
                default:
                    secondHalfPizza
                        .addTopping(Menu.loadedToppings.get(GourmetToppingNames.CHEDDAR));
                    break;
            }
        } else if (cheeseArray[x].equals(nochz))
            secondHalfPizza.setNoCheese(true);
    }
    public void enableSpecialToppings(ArrayList<Topping> array) {
        removeSpecialSelected();
        if(array.size() == 0)
            currentPizza = new Pizza();
        for (int x = 0; x < array.size(); x++)
            for (int y = 0; y < toppingsList.length; y++)
                if (array.get(x).getName().equalsIgnoreCase(toppingsList[y].getId()))
                    toppingsList[y].getStyleClass().add("ToppingsSelected");
    }
    public void removeSpecialSelected() {
        Pane pane;
        for (int x = 0; x < specialPaneArray.length; x++) {
            pane = specialPaneArray[x];
            pane.getChildren().forEach(theButtons ->
                    theButtons.getStyleClass().removeAll("ToppingsSelectedGreen", "ToppingsSelected", "ToppingsSelectedEZ", "ToppingsSelectedX", "ToppingsSelectedXX"));
        }
    }
    @FXML
    public void togglePrice() {
        boolean sizeCheck = true;
        Size size = selectedSize;
        /* If no size has been selected */
        if (size == null){
            setButtonPane(sizeButtons, "BackgroundDefault", "BackgroundNeed");
            sizeCheck = false;
        }
        if (isEnabled(togglePrice, "ToppingsEnableHover") && sizeCheck || changeSize) {
                for (int x = 0; x < toppingsList.length; x++) {
                    Topping topping;
                    if (toppingsList[x].equals(chz) || toppingsList[x].equals(nochz) || toppingsList[x].equals(chzPizza))
                        topping = Menu.loadedToppings.get(GourmetToppingNames.MOZZARELLA);
                    else {
                        try {
                            topping = Menu.loadedToppings.get(toppingsList[x].getId().toUpperCase());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    toppingsList[x].setText(toppingNameListText.get(x) + "\n" + "$" + formatter.format(CurrencyConverter.build(topping.getPrice(size))));
                    toppingsList[x].setFont(new Font(22));
                }
                togglePrice.getStyleClass().add("ToppingsSelected");
                togglePrice.getStyleClass().removeAll("ToppingsEnableHover");
        }
        else {
            togglePrice.getStyleClass().removeAll("ToppingsSelected");
            togglePrice.getStyleClass().add("ToppingsDefault");
            for(int x = 0; x < toppingsList.length; x++) {
                toppingsList[x].setText(toppingNameListText.get(x));
                toppingsList[x].setFont(originalToppingFont);
            }
        }
        changeSize = false;
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
        selectedSize = null;
        resetSizeArea();
    }

    @Override
    public void setParent(PickupDeliveryPaneController o) {
        this.parent = o;
    }
}
