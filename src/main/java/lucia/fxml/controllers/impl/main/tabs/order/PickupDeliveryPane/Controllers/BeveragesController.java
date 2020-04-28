/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.legacy.impl.addon.AddonType;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeBeverageNames;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDelivery;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;

public class BeveragesController implements ParentController<PickupDelivery> {
    @FXML
    private Pane beveragesPane;

    @FXML
    private JFXButton coke;

    @FXML
    private JFXButton sprite;

    @FXML
    private JFXButton rootbeer;

    @FXML
    private JFXButton icedTea;

    @FXML
    private JFXButton orange;

    @FXML
    private JFXButton water;

    @FXML
    private Pane beveragesCustomPane;

    @FXML
    private JFXButton canDrink;

    @FXML
    private JFXButton litreDrink;

    @FXML
    private JFXButton diet;

    private PickupDelivery parent;
    private Addon drink = null;//new Addon("test", AddonType.BEVERAGE);
    JFXButton[] drinks;

    @FXML
    public void initialize() {
        drinks = new JFXButton[]{coke, sprite, rootbeer, icedTea, orange, water};
    }
    public void setParent(PickupDeliveryPaneController p) {
        this.parent = p;
    }
    public boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }

    // ------ "HOVER" SERIES ------
    @FXML
    void activateHover(MouseEvent event) {
        parent.activateHover(event);
    }
    @FXML
    void activateHoverGreen(MouseEvent event) {
        parent.activateHoverGreen(event);
    }
    @FXML
    void deactivateHover(MouseEvent event) {
        Style.deactivateHover(event);
    }
    @FXML
    void deactivateHoverGreen(MouseEvent event) {
        Style.deactivateHoverGreen(event);
    }

    // ------ "ADD TO ORDER" SERIES ------
    @FXML
    void addToOrderDrinks(MouseEvent event) {
        if (!check())
            litreDrink.getStyleClass().add("ToppingsSelectedGreen");
//        for (int x = 0; x < drinks.length; x++) {
//            if (isEnabled(drinks[x], "ToppingsSelected")) {
//                if (isEnabled(canDrink, "ToppingsSelectedGreen")) {
//                    if(isEnabled(coke, "ToppingsSelected")) {
//                        if(isEnabled(diet, "ToppingsSelectedGreen"))
//                            drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames.CAN_COKE_ZERO);
//                        else drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames.CAN_COKE);
//                    }
//                    else if(isEnabled(sprite, "ToppingsSelected"))
//                        drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames.CAN_SPRITE);
//                    else if(isEnabled(rootbeer, "ToppingsSelected"))
//                        drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames.CAN_ROOTBEER);
//                    else if(isEnabled(icedTea, "ToppingsSelected"))
//                        drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames.CAN_ICED_TEA);
//                    else if(isEnabled(orange, "ToppingsSelected"))
//                        drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames.CAN_ORANGE);
//                } else if (isEnabled(litreDrink, "ToppingsSelectedGreen")) {
//                    if(isEnabled(coke, "ToppingsSelected")) {
//                        if(isEnabled(diet, "ToppingsSelectedGreen"))
//                            drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames._2L_COKE_ZERO);
//                        else drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames._2L_COKE);
//                    }
//                    else if(isEnabled(sprite, "ToppingsSelected"))
//                        drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames._2L_SPRITE);
//                    else if(isEnabled(rootbeer, "ToppingsSelected"))
//                        drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames._2L_ROOTBEER);
//                    else if(isEnabled(icedTea, "ToppingsSelected"))
//                        drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames._2L_ICED_TEA);
//
//                }
//                if(isEnabled(water, "ToppingsSelected"))
//                    drink = Menu.loadedPreMadeFoods.getPremadeBeverage().get(PremadeBeverageNames.WATER_BOTTLE);
//                drinks[x].getStyleClass().removeAll("ToppingsSelected");
//                reset();
//            }
//        }
 //       drink.calcName();
        parent.addToOrder(drink);
        parent.orderCount++;
        beveragesCustomPane.setVisible(false);
    }

    private boolean check() {
        if(isEnabled(canDrink, "ToppingsSelectedGreen") || isEnabled(litreDrink,"ToppingsSelectedGreen"))
            return true;
        return false;
    }

    // ------ "SELECTED SERIES ------
    @FXML
    public void selectedDrinks(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        diet.setVisible(false);
        ((PickupDeliveryPaneController)parent).changeGroupButton(event, beveragesPane, "ToppingsSelected");
        if (isEnabled(water, "ToppingsSelected")) {
            canDrink.setVisible(false);
            litreDrink.setVisible(false);
        } else {
            canDrink.setVisible(true);
            litreDrink.setVisible(true);
        }
        if(isEnabled(coke, "ToppingsSelected"))
            diet.setVisible(true);
        if(isEnabled(orange, "ToppingsSelected"))
            litreDrink.setVisible(false);
        beveragesCustomPane.setVisible(true);
    }

    public void reset() {
        beveragesCustomPane.getChildren().forEach(theButtons ->
                theButtons.getStyleClass().removeAll("ToppingsSelectedGreen"));
    }
    @FXML
    public void selectedDrinksCustom(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        ((PickupDeliveryPaneController)parent).changeGroupButton(event, beveragesCustomPane, "ToppingsSelectedGreen");
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

    }

    @Override
    public void setParent(PickupDelivery o) {
        this.parent = o;
    }
}
