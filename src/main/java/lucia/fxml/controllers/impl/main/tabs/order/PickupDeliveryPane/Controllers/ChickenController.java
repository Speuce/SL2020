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
import main.java.lucia.client.content.menu.legacy.impl.Italian;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAddonNames;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeItalianName;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;

public class ChickenController implements ParentController<PickupDeliveryPaneController> {
    @FXML
    public Pane chickenPane;

    @FXML
    private JFXButton chickenDinner;

    @FXML
    private Pane chickenCustomPane;

    @FXML
    private JFXButton dipsChicken;

    @FXML
    private JFXButton POUTINE_UPGRADE;




    private PickupDeliveryPaneController parent;
    @FXML
    private ItalianController italianPaneController;

    JFXButton[] chickenCustom;
    private Italian chicken = new Italian("test");

    @FXML
    public void initialize() {
       // chickenCustom = new JFXButton[]{lemonPotatoChicken, frenchFriesChicken, poutineChicken, riceButton};
    }
    public boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }
    @FXML
    void dipsPopup(ActionEvent event) {
        ((PickupDeliveryPaneController)parent).dipsPopup(event);
    }

    // ------ "ADD TO ORDER" SERIES ------
    @FXML
    void addToOrderChicken(MouseEvent event) {
        try {
            if (isEnabled(chickenDinner, "ToppingsSelected")) {
                chicken = Menu.loadedPreMadeFoods.getPremadeItalian().get(PremadeItalianName.CHICKEN_DINNER);
                    if (isEnabled(POUTINE_UPGRADE, "ToppingsSelectedGreen")) {
                        chicken.addAddon(Menu.loadedPreMadeFoods.getAddons().get(PremadeAddonNames.POUTINE_UPGRADE));
                        POUTINE_UPGRADE.getStyleClass().removeAll("ToppingsSelectedGreen");
                    }
                if (isEnabled(dipsChicken, "ToppingsSelectedGreen")) {
                    parent.popup = false;
                    try {
                        //chicken.addAddon(parent.getSidesPaneController().getDipOrder());
                        chicken.setChickenDinner(true);
                        dipsChicken.getStyleClass().removeAll("ToppingsSelectedGreen");
                    }
                    catch (Exception e) {
                        System.out.println("NO DIP FOUND: " + e);
                    }
                }
                chicken.calcNameAndPrice();
                //parent.addToOrder(chicken.safeClone());
                chickenDinner.getStyleClass().removeAll("ToppingsSelected");
                chickenCustomPane.setVisible(false);
                parent.popup = false;
                chicken.clearAddons();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
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

    // ------ "SELECTED" SERIES ------
    @FXML
    public void selectedChicken(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, chickenPane, "ToppingsSelected");
        chickenCustomPane.setVisible(true);
    }
    @FXML
    public void selectedChickenCustom(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, chickenCustomPane, "ToppingsSelectedGreen");
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
    public void setParent(PickupDeliveryPaneController o) {
        this.parent = o;
    }
}
