/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.impl.addon.AddonType;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAddonNames;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;

public class SidesController implements ParentController<PickupDeliveryPaneController> {

    @FXML
    private Pane sidesPane;

    @FXML
    private JFXButton small_gravy;

    @FXML
    private JFXButton large_gravy;

    @FXML
    private JFXButton small_coleslaw;

    @FXML
    private JFXButton large_coleslaw;

    @FXML
    private JFXButton mustard;

    @FXML
    private JFXButton mayonnaise;

    @FXML
    private JFXButton hot_sauce;

    @FXML
    private JFXButton bbq;

    @FXML
    private JFXButton marinara;

    @FXML
    private JFXButton honey_dill;

    @FXML
    private JFXButton honey_mustard;

    @FXML
    private JFXButton sweet_chili;

    @FXML
    private JFXButton caesar_dressing;

    @FXML
    private JFXButton greek_dressing;

    @FXML
    private JFXButton ranch_dressing;

    @FXML
    private JFXButton raspberry_vinaigrette;

    @FXML
    private JFXButton suicidal;

    @FXML
    private JFXButton fire;

    @FXML
    private JFXButton spicy_caesar;

    @FXML
    private JFXButton cajun;

    @FXML
    private JFXButton parmesan;

    @FXML
    private JFXButton chili_flakes;

    @FXML
    private JFXButton honey_garlic;

    @FXML
    private JFXButton meatsauce;

    @FXML
    private JFXButton thousand_island_dressing;

    @FXML
    private JFXButton italian_dressing;

    @FXML
    private JFXButton sour_cream;

    @FXML
    private JFXButton pineapple_curry;

    @FXML
    private JFXButton tzaziki;

    private Addon dip = new Addon("test", AddonType.DIPS);

    JFXButton[] dips;


    private PickupDeliveryPaneController parent;

    @FXML
    public void initialize() {
        dips = new JFXButton[]{small_gravy, large_gravy, small_coleslaw, large_coleslaw, mustard, mayonnaise, hot_sauce, bbq, sweet_chili, honey_dill, caesar_dressing, italian_dressing, tzaziki,
                raspberry_vinaigrette, suicidal, fire, spicy_caesar, parmesan, chili_flakes, honey_garlic, meatsauce, thousand_island_dressing, greek_dressing,
                sour_cream, pineapple_curry, honey_mustard, cajun, marinara, ranch_dressing};
    }

    @Override
    public void close() {

    }

    public Addon getDipOrder() {
        return dip;
    }

    @Override
    public Object getCurrentItem() {
        return null;
    }

    @Override
    public void open() {

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

    // ------ "ADD TO ORDER" SERIES ------
    @FXML
    void addToOrderDips(MouseEvent event) {
        for (int x = 0; x < dips.length; x++) {
            if (isEnabled(dips[x], "ToppingsSelected")) {
                dip = Menu.loadedPreMadeFoods.getAddons().get(PremadeAddonNames.valueOf(dips[x].getId().toUpperCase()));
                dips[x].getStyleClass().removeAll("ToppingsSelected");
            }
        }
        if (!parent.popup) {
            //parent.addToOrder(dip);
            parent.orderCount++;
        }
        sidesPane.toBack();
    }

    public void setPopup(boolean popup) {
        parent.popup = popup;
    }

    // ------ "HOVER" SERIES ------
    @FXML
    void activateHover(MouseEvent event) {
        parent.activateHover(event);
    }
    @FXML
    void deactivateHover(MouseEvent event) {
        Style.deactivateHover(event);
    }

    // ------ "SELECTED" SERIES ------
    @FXML
    void selectedDips(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, sidesPane, "ToppingsSelected");
    }
}
