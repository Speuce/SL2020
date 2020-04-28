/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.Appetizer;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAddonNames;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAppetizersName;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDelivery;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;

import java.util.ArrayList;
import java.util.List;

public class AppetizersController implements ParentController<PickupDelivery> {

    @FXML
    private Pane appetizersPane;

    @FXML
    private JFXButton wings;

    @FXML
    private JFXButton bread_sticks;

    @FXML
    private JFXButton mozza_bread_sticks;

    @FXML
    private JFXButton chicken_strips;

    @FXML
    private JFXButton french_fries;

    @FXML
    private JFXButton poutine;

    @FXML
    private JFXButton cheese_toast;

    @FXML
    private JFXButton garlic_toast;

    @FXML
    private JFXButton meatballs;

    @FXML
    private Pane appetizersCustomPane;

    @FXML
    private JFXButton quantityAppetizer;

    @FXML
    private JFXButton dipAppetizer;

    @FXML
    private JFXButton saltpepper;

    JFXButton[] appetizers;

    private PickupDeliveryPaneController parent;
    @FXML
    private SidesController sidesPaneController;

    private Appetizer appetizer;

    private List<JFXButton> buildList;

    private boolean isThereMeatballs = false;

    private Addon meatball;

    @FXML
    public void initialize() {
        appetizers = new JFXButton[]{wings, bread_sticks, mozza_bread_sticks, chicken_strips, french_fries, poutine, cheese_toast};
    }
    public void setParent(PickupDeliveryPaneController p) {
        this.parent = p;
    }
    @FXML
    void dipsPopup(ActionEvent event) {
        parent.dipsPopup(event);
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

    public boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }

    // ------ "ADD TO ORDER" SERIES ------
    @FXML
    public void addToOrderAppetizer(MouseEvent event) {
        for (int x = 0; x < appetizers.length; x++)
            if (isEnabled(appetizers[x], "ToppingsSelected")) {
                try {
                    appetizer = (Appetizer)Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeAppetizersName.valueOf(appetizers[x].getId().toUpperCase()).name());
                    appetizers[x].getStyleClass().removeAll("ToppingsSelected");
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }

        if(isEnabled(meatballs, "ToppingsSelected")) {
            //meatball = Menu.loadedPreMadeFoods.getAddons().get(PremadeAddonNames.MEATBALLS_APPETIZER);
            meatballs.getStyleClass().removeAll("ToppingsSelected");
            isThereMeatballs = true;
        }

        if (isEnabled(garlic_toast, "ToppingsSelected")) {
            if (isEnabled(quantityAppetizer, "ToppingsSelectedGreen")) {
                try {
                    appetizer = (Appetizer) Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeAppetizersName.GARLIC_TOAST_THREE_PIECE.name());
                    quantityAppetizer.getStyleClass().removeAll("ToppingsSelectedGreen");
                    garlic_toast.getStyleClass().removeAll("ToppingsSelected");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else {
                try {
                    appetizer = (Appetizer) Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeAppetizersName.GARLIC_TOAST.name());
                    garlic_toast.getStyleClass().removeAll("ToppingsSelected");
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        if(isEnabled(wings, "ToppingsSelected")) {
            if (isEnabled(saltpepper, "ToppingsSelectedGreen")) {
                appetizer.addAddon(Menu.loadedPreMadeFoods.getAddons().get(PremadeAddonNames.SALT_PEPPER));
                saltpepper.getStyleClass().removeAll("ToppingsSelectedGreen");
            }
        }
        if (isEnabled(dipAppetizer, "ToppingsSelectedGreen")) {
            parent.popup = false;
            try {
                //appetizer.addAddon(parent.getSidesPaneController().getDipOrder());
            }
            catch (Exception e) {
                System.out.println("COULD NOT GET DIP: " + e);
            }
            dipAppetizer.getStyleClass().removeAll("ToppingsSelectedGreen");
        }
        if(isThereMeatballs) {
            //meatball.calcName();
            parent.addToOrder(meatball);
            isThereMeatballs = false;
        }
        else {
            appetizer.calcName();
            //parent.addToOrder(appetizer);
        }
        parent.orderCount++;
        appetizersCustomPane.setVisible(false);
        saltpepper.setVisible(false);
        parent.popup = false;
    }


    // ------ "GROUP BUTTON" SERIES ------
    public void changeGroupButtonAppetizers(MouseEvent event, Pane pane, String selectedCSS) {
        JFXButton button = (JFXButton) event.getSource();
        if (isEnabled(button, selectedCSS)) {
            pane.getChildren().forEach(theButtons -> {
                theButtons.getStyleClass().removeAll(selectedCSS);
                quantityAppetizer.setVisible(false);
                dipAppetizer.setVisible(true);
            });
        } else {
            pane.getChildren().forEach(theButtons -> {
                theButtons.getStyleClass().removeAll(selectedCSS);
                button.getStyleClass().add(selectedCSS);
                saltpepper.setVisible(false);
                if (button.equals(garlic_toast)) {
                    quantityAppetizer.setVisible(true);
                    dipAppetizer.setVisible(true);
                } else if (button.equals(cheese_toast)) {
                    quantityAppetizer.setVisible(false);
                    dipAppetizer.setVisible(true);
                } else if (button.equals(meatballs) || button.equals(poutine)) {
                    dipAppetizer.setVisible(false);
                    quantityAppetizer.setVisible(false);
                }
                else if(button.equals(wings)) {
                    saltpepper.setVisible(true);
                }
                else {
                    quantityAppetizer.setVisible(false);
                    dipAppetizer.setVisible(true);
                }
                appetizersCustomPane.setVisible(true);
            });
        }
    }

    // ------ "SELECTED" SERIES ------
    @FXML
    void selectedAppetizers(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        changeGroupButtonAppetizers(event, appetizersPane, "ToppingsSelected");
    }
    @FXML
    void selectedGreen(MouseEvent event) {
        parent.selectedGreen(event);
    }

    @Override
    public void close() {
        appetizersCustomPane.setVisible(false);
    }

    @Override
    public Object getCurrentItem() {
        return null;
    }

    @Override
    public void open() {
        buildList = new ArrayList<JFXButton>();
    }

    @Override
    public void setParent(PickupDelivery o) {
        this.parent = (PickupDeliveryPaneController) o;
    }
}
