/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.catering.Catering;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeCateringNames;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDelivery;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;

public class CateringController implements ParentController<PickupDelivery> {
    @FXML
    private Pane cateringPane;

    @FXML
    private AnchorPane cateringAnchor;

    @FXML
    private JFXButton full_lasagna;

    @FXML
    private JFXButton half_lasagna;

    @FXML
    private JFXButton full_spaghetti;

    @FXML
    private JFXButton half_ravioli;

    @FXML
    private JFXButton full_fettuccine;

    @FXML
    private JFXButton half_fettuccine;

    @FXML
    private JFXButton full_caesar_salad;

    @FXML
    private JFXButton half_caesar_salad;

    @FXML
    private JFXButton full_garden_salad;

    @FXML
    private JFXButton FULL_CHICKEN_STRIP_CATERING;

    @FXML
    private JFXButton full_mousaka;

    @FXML
    private JFXButton spanakopita;

    @FXML
    private JFXButton lemon_potatoes_full;

    @FXML
    private JFXButton lemon_potatoes_half;

    @FXML
    private JFXButton full_rice;

    @FXML
    private JFXButton half_spaghetti;

    @FXML
    private JFXButton full_ravioli;

    @FXML
    private JFXButton full_greek_salad;

    @FXML
    private JFXButton half_greek_salad;

    @FXML
    private JFXButton half_garden_salad;

    @FXML
    private JFXButton HALF_CHICKEN_STRIP_CATERING;

    @FXML
    private JFXButton souvlaki_skewer;

    @FXML
    private JFXButton shrimp_skewer;

    @FXML
    private JFXButton half_rice;

    @FXML
    private JFXButton garlic_toast_catering;

    @FXML
    private JFXButton packages;

    @FXML
    private Pane cateringCustomPane;

    @FXML
    private JFXButton firstDown;

    @FXML
    private JFXButton fieldGoal;

    @FXML
    private JFXButton touchDown;

    @FXML
    private JFXButton crowdPleaser;

    @FXML
    private JFXButton slSignature;

    @FXML
    private JFXButton tasteOfGreece;

    @FXML
    private JFXButton halfItaly;

    @FXML
    private JFXButton fullItaly;

    @FXML
    private JFXButton simpleGathering;

    private PickupDelivery parent;

    JFXButton[] cateringList;

    private Catering catering = new Catering("test");


    @FXML
    public void initialize() {
        cateringList = new JFXButton[]{full_lasagna, half_lasagna, full_spaghetti, half_spaghetti, full_ravioli, half_ravioli, full_fettuccine, half_fettuccine, full_greek_salad,
                half_greek_salad, full_caesar_salad, half_caesar_salad, full_garden_salad, half_garden_salad, HALF_CHICKEN_STRIP_CATERING, FULL_CHICKEN_STRIP_CATERING, full_mousaka, spanakopita,
                souvlaki_skewer, shrimp_skewer, lemon_potatoes_full, lemon_potatoes_half, full_rice, half_rice, garlic_toast_catering, firstDown, fieldGoal, touchDown,
                crowdPleaser, slSignature, tasteOfGreece, halfItaly, fullItaly, simpleGathering};
    }
    public boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }
    @FXML
    void packagesSwitch(ActionEvent event) {
        if (packages.getText().equals("Packages")) {
            packages.setText("Catering Menu");
            cateringCustomPane.setVisible(true);
        } else if (packages.getText().equals("Catering Menu")) {
            packages.setText("Packages");
            cateringCustomPane.setVisible(false);
        }
    }

    // ------ "ADD TO ORDER" SERIES ------
    @FXML
    void addToOrderCatering(MouseEvent event) {
        for (int x = 0; x < cateringList.length; x++)
            if (isEnabled(cateringList[x], "ToppingsSelected")) {
                System.out.println(cateringList[x].getId());
                catering = Menu.loadedPreMadeFoods.getPremadeCatering().get(PremadeCateringNames.valueOf(cateringList[x].getId().toUpperCase()));
                //parent.addToOrder(catering);
                cateringList[x].getStyleClass().removeAll("ToppingsSelected");
            }
        parent.orderCount++;
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
    public void selectedCatering(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        ((PickupDeliveryPaneController)parent).changeGroupButton(event, cateringAnchor, "ToppingsSelected");
        cateringCustomPane.getChildren().forEach(theButtons ->
                theButtons.getStyleClass().removeAll("ToppingsSelected"));
    }
    @FXML
    public void selectedCateringCustom(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        ((PickupDeliveryPaneController)parent).changeGroupButton(event, cateringCustomPane, "ToppingsSelected");
        cateringAnchor.getChildren().forEach(theButtons ->
                theButtons.getStyleClass().removeAll("ToppingsSelected"));
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
