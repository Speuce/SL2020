/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;

public class PizzaController implements ParentController<PickupDeliveryPaneController> {

    /**
     * FXML DECLARATIONS
     */
    @FXML
    public Pane settingsButtons;

    @FXML
    public JFXButton crust;

    @FXML
    public JFXButton sauce;

    @FXML
    public AnchorPane extraPane;

    @FXML
    public JFXButton easy;

    @FXML
    public JFXButton extra;

    @FXML
    public JFXButton xextra;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public AnchorPane specialAnchor;

    @FXML
    public Pane pizzaButtons;

    @FXML
    public Pane saucePane;

    @FXML
    public Pane crustPane;

    @FXML
    public Pane sizeButtons;

    @FXML
    private JFXButton half;

    /**
     * The instance for the order manager (Zach's)
     */
    PizzaOrderManager pizzaOrderManager = PizzaOrderManager.getPizzaOrderInstance();

    /**
     * The parent controller
     */
    public PickupDeliveryPaneController parent;

    /**
     * Used for scrolling, will be updated!
     */
    private double pos = 0; //todo use new scrollpane method


    /**
     * When 'make' is clicked
     * implements with the order manager system
     *
     * @param event useless
     */
    @FXML
    public void makeClicked(ActionEvent event) {
        PizzaOrderManager pizzaOrderManager = PizzaOrderManager.getPizzaOrderInstance();
        String halfText = DynamicLoader.dynamicLoaderInstance.pizzaController.half.getText();

        if(pizzaOrderManager.isSecondHalf() && pizzaOrderManager.findSize()) {
            if(halfText.equalsIgnoreCase("1st half")) {
                pizzaOrderManager.makePizza();

                // resetting some variables
                pizzaOrderManager.currentPizza = null;
                DynamicLoader.dynamicLoaderInstance.getToppingDynamicLoad().clearSelectedButtons();
                DynamicLoader.dynamicLoaderInstance.getSpecialDynamicLoad().clearSelectedButtons();
                DynamicLoader.dynamicLoaderInstance.pizzaController.half.setText("2nd Half");
            } else if(halfText.equalsIgnoreCase("2nd half") && pizzaOrderManager.findSize()) {
                pizzaOrderManager.makeSecondHalf();
                pizzaOrderManager.addPizzaToOrder();
                DynamicLoader.dynamicLoaderInstance.pizzaController.resetHalfButton();
                DynamicLoader.dynamicLoaderInstance.pizzaController.half.setText("1st Half");
            }
            return;
        }
        pizzaOrderManager.addPizzaToOrder();

    }

    /**
     * Old method of scroll speed handling
     * @param event useless
     */
    @FXML
    void scroll(ScrollEvent event) {
        double currentPos = event.getDeltaY();
        if (currentPos < pos)
            scrollPane.setHvalue(pos++ / 5);
        else scrollPane.setHvalue(pos-- / 5);
    }

    /**
     * Sets the additional pane that is being used
     * @param event useless
     */
    @FXML
    void setAdditionalPane(ActionEvent event) {
        parent.setAdditionalPane(event);
    }

    /** ------ "HOVER" SERIES ------ **/

    /**
     * Changes the style scheme when hovered
     * @param event useless
     */
    @FXML
    public void activateHover(MouseEvent event) {
        if (parent == null) {
            return;
        }
        parent.activateHover(event);
    }

    /**
     * Changes the style scheme when hovered
     * @param event useless
     */
    @FXML
    public void activateHoverGreen(MouseEvent event) {
        parent.activateHoverGreen(event);
    }

    /**
     * Changes the style scheme when hovered
     * @param event useless
     */
    @FXML
    public void deactivateHover(MouseEvent event) {
        Style.deactivateHover(event);
    }

    /**
     * Changes the style scheme when hovered
     * @param event useless
     */
    @FXML
    public void deactivateHoverGreen(MouseEvent event) {
        Style.deactivateHoverGreen(event);
    }

    /** ------ "SET" SERIES ------ **/

    /**
     * Sets the given pane styles
     * @param pane the pane for the style change
     * @param remove removal of the style
     * @param add style that will be put in
     */
    public void setButtonPane(Pane pane, String remove, String add) {
        pane.getStyleClass().removeAll(remove);
        pane.getStyleClass().add(add);
    }

    /**
     * Sets the pane background with the toppings buttons
     *
     * If there is a need for toppings or not it will show
     *
     * @param toppingsNeed if there is a need or not
     */
    public void setToppingsNeed(boolean toppingsNeed) {
        if(toppingsNeed) {
            setButtonPane(pizzaButtons, "BackgroundDefault", "BackgroundNeed");
        } else setButtonPane(pizzaButtons, "BackgroundNeed", "BackgroundDefault");
    }

    /**
     * If applicable, resets the size area.
     */
    public void resetSizeArea() {
        setButtonPane(sizeButtons, "BackgroundNeed", "BackgroundDefault");
    }

    public void resetHalfButton() {
        half.getStyleClass().remove("ToppingsSelected");
    }

    /**
     * When the half and half option is selected
     * @param event useless
     */
    @FXML
    public void selectedHalf(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        if (half.getText().equalsIgnoreCase("1st half")) {
            if(pizzaOrderManager.isSecondHalf()) {
                pizzaOrderManager.disableSecondHalf();
                resetHalfButton();
                return;
            }
            pizzaOrderManager.enableSecondHalf();
            half.getStyleClass().add("ToppingsSelected");
        }
    }

    /**
     * When a selected option is selected
     * @param event useless
     *
     * //todo implement
     */
    @FXML
    public void selectedExtra(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, extraPane, "ToppingsSelectedGreen");

        if (Style.isEnabled(easy, "ToppingsSelectedGreen"))
            pizzaOrderManager.easyToExtraOption = 1;
        else if (Style.isEnabled(extra, "ToppingsSelectedGreen"))
            pizzaOrderManager.easyToExtraOption = 3;
        else if (Style.isEnabled(xextra, "ToppingsSelectedGreen"))
            pizzaOrderManager.easyToExtraOption = 4;
        else pizzaOrderManager.easyToExtraOption = 2;
    }

    /**
     * Gives the prices of the toppings in the list
     *
     * //todo implement
     */
    @FXML
    public void togglePrice() {
    }


    /** OVERRIDES **/
    @Override
    public void close() {

    }

    @Override
    public Object getCurrentItem() {
        return null;
    }

    @Override
    public void open() {
        resetSizeArea();
    }

    @Override
    public void setParent(PickupDeliveryPaneController o) {
        this.parent = o;
    }
}
