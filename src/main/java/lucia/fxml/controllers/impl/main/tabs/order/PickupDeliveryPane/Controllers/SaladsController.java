/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.Salad;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAddonNames;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeSaladNames;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;

public class SaladsController implements ParentController<PickupDeliveryPaneController> {
    @FXML
    private Pane saladsPane;

    @FXML
    private JFXButton caesar_salad;

    @FXML
    private JFXButton greek_salad;

    @FXML
    private JFXButton garden_salad;

    @FXML
    private JFXButton cheese_salad;

    @FXML
    private JFXButton chef_salad;

    @FXML
    private Pane saladCustomPane;

    @FXML
    private JFXButton CHICKEN_SALAD;

    @FXML
    private JFXButton starterSalad;

    @FXML
    private JFXButton ITALIAN_DRESSING;

    @FXML
    private JFXButton RASPBERRY_VINAIGRETTE;

    @FXML
    private JFXButton THOUSAND_ISLAND_DRESSING;

    @FXML
    private JFXButton GREEK_DRESSING;

    @FXML
    private JFXButton CAESAR_DRESSING;

    @FXML
    private JFXButton OIL_VINEGAR;

    @FXML
    private JFXButton RANCH_DRESSING;

    @FXML
    private JFXButton BLUE_CHEESE;

    @FXML
    private JFXButton CHEESE_TOAST_UPGRADE;
  
    private PickupDeliveryPaneController parent;
    private Salad salad = new Salad("test");

    JFXButton[] salads;
    JFXButton[] saladsCustom;
    JFXButton[] toggleArray;
    JFXButton[] toggleArrayDips;

    @FXML
    public void initialize() {
        salads = new JFXButton[]{caesar_salad, greek_salad, garden_salad, cheese_salad, chef_salad};
        saladsCustom = new JFXButton[]{CHICKEN_SALAD, starterSalad, ITALIAN_DRESSING, THOUSAND_ISLAND_DRESSING, GREEK_DRESSING,
                CAESAR_DRESSING, RANCH_DRESSING, OIL_VINEGAR, RASPBERRY_VINAIGRETTE, BLUE_CHEESE, CHEESE_TOAST_UPGRADE};
        toggleArray = new JFXButton[]{starterSalad, CHEESE_TOAST_UPGRADE,CHICKEN_SALAD};
        toggleArrayDips = new JFXButton[]{ITALIAN_DRESSING, THOUSAND_ISLAND_DRESSING, GREEK_DRESSING,
                CAESAR_DRESSING, RANCH_DRESSING, OIL_VINEGAR, RASPBERRY_VINAIGRETTE, BLUE_CHEESE};
    }
    public boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }

    // ------ "ADD TO ORDER" SERIES ------
    @FXML
    void addToOrderSalads(MouseEvent event) {
        try {
            for (int x = 0; x < salads.length; x++)
                if (isEnabled(salads[x], "ToppingsSelected")) {
                    if(salads[x].equals(garden_salad))
                        salad = (Salad) Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeSaladNames.LARGE_GARDEN_SALAD.name());
                    else salad = (Salad)Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeSaladNames.valueOf(salads[x].getId().toUpperCase()).name());
                }
            for (int x = 0; x < saladsCustom.length; x++)
                if (isEnabled(saladsCustom[x], "ToppingsSelectedGreen")) {
                    if(starterSalad.equals(saladsCustom[x])) {
                        for (int y = 0; y < salads.length; y++) {
                            if (isEnabled(salads[y], "ToppingsSelected")) {
                                if (salads[y].equals(greek_salad))
                                    salad = (Salad) Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeSaladNames.STARTER_GREEK_SALAD.name());
                                else if (salads[y].equals(caesar_salad))
                                    salad = (Salad) Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeSaladNames.STARTER_CAESAR_SALAD.name());
                                else if (salads[y].equals(garden_salad))
                                    salad = (Salad) Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeSaladNames.SMALL_GARDEN_SALAD.name());
                            }
                        }
                    }
                    else  {
                        salad.addAddon(Menu.loadedPreMadeFoods.getAddons().get(PremadeAddonNames.valueOf(saladsCustom[x].getId().toUpperCase())));
                    }
                    saladsCustom[x].getStyleClass().removeAll("ToppingsSelectedGreen");
                }
            for(int x = 0; x < salads.length; x++)
                salads[x].getStyleClass().removeAll("ToppingsSelected");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        salad.calcNameAndPrice();
        //parent.addToOrder(salad);
        parent.orderCount++;
        saladCustomPane.setVisible(false);
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
    public void selectedSalads(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, saladsPane, "ToppingsSelected");
        saladCustomPane.setVisible(true);
    }
    @FXML
    public void selectedSaladsCustom(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        changeGroupButtonSaladCustom(event, saladCustomPane, "ToppingsSelectedGreen");
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
    public void setParent(PickupDeliveryPaneController o) {
        this.parent = o;
    }

    public void changeGroupButtonSaladCustom(MouseEvent event, Pane pane, String selectedCSS) {
        JFXButton button = (JFXButton) event.getSource();
        if (isEnabled(button, selectedCSS))
            button.getStyleClass().removeAll(selectedCSS);
        else {
            if (button.equals(ITALIAN_DRESSING) || button.equals(THOUSAND_ISLAND_DRESSING) || button.equals(GREEK_DRESSING) || button.equals(CAESAR_DRESSING)
                    || button.equals(RANCH_DRESSING) || button.equals(RASPBERRY_VINAIGRETTE) || button.equals(OIL_VINEGAR) || button.equals(BLUE_CHEESE))
                for (int x = 0; x < toggleArrayDips.length; x++) {
                    toggleArrayDips[x].getStyleClass().removeAll(selectedCSS);
                    button.getStyleClass().add(selectedCSS);
                }
            if(button.equals(CHEESE_TOAST_UPGRADE) || button.equals(starterSalad) || button.equals(CHICKEN_SALAD))
                button.getStyleClass().add(selectedCSS);
        }
    }
}
