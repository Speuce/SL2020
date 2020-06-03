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
import main.java.lucia.client.content.menu.legacy.size.Size;
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
    private JFXButton half;

    /**
     * The instance for the order manager (Zach's)
     */
    PizzaOrderManager pizzaOrderManager = PizzaOrderManager.getPizzaOrderInstance();

    /**
     * The parent controller
     */
    private PickupDeliveryPaneController parent;

    /**
     * Used for scrolling, will be updated!
     */
    private double pos = 0; //todo use new scrollpane method

    /**
     * The currently selected size
     */
    private Size selectedSize;

    /**
     * Size List for the pizza sizes
     * //todo update with dynamic loading
     */
    JFXButton[] sizeList;

    @FXML
    public void initialize() {
        sizeList = new JFXButton[]{ten, thirteen, fifteen, eighteen, thirty};
    }


    /**
     * When 'make' is clicked
     * implements with the order manager system
     *
     * @param event useless
     */
    @FXML
    public void makeClicked(ActionEvent event) {
        PizzaOrderManager pizzaOrderManager = PizzaOrderManager.getPizzaOrderInstance();
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
     * If applicable, resets the size area.
     */
    public void resetSizeArea() {
        for (JFXButton button : sizeList) {
            if(button.getStyleClass().contains("ToppingsSelected")) {
                button.getStyleClass().remove("ToppingsSelected");
                button.getStyleClass().add("ToppingsDefault");
            }
        }
        setButtonPane(sizeButtons, "BackgroundNeed", "BackgroundDefault");
    }

    /**
     * When the button is selected, will coordinate with the order manager and change styles
     * @param event useless
     */
    @FXML
    void selectedSize10(MouseEvent event) {
        resetSizeArea();

        if (pizzaOrderManager.selectedSize == 10) {
            pizzaOrderManager.selectedSize = -1;
            ten.getStyleClass().remove("ToppingsSelected");
            ten.getStyleClass().add("ToppingsDefault");
        } else {
            pizzaOrderManager.selectedSize = 10;
            ten.getStyleClass().add("ToppingsSelected");
            ten.getStyleClass().remove("ToppingsDefault");
        }
    }

    /**
     * When the button is selected, will coordinate with the order manager and change styles
     * @param event useless
     */
    @FXML
    void selectedSize13(MouseEvent event) {
        resetSizeArea();

        if (pizzaOrderManager.selectedSize == 13) {
            pizzaOrderManager.selectedSize = -1;
            thirteen.getStyleClass().remove("ToppingsSelected");
            thirteen.getStyleClass().add("ToppingsDefault");
        } else {
            pizzaOrderManager.selectedSize = 13;
            thirteen.getStyleClass().add("ToppingsSelected");
            thirteen.getStyleClass().remove("ToppingsDefault");
        }
    }

    /**
     * When the button is selected, will coordinate with the order manager and change styles
     * @param event useless
     */
    @FXML
    void selectedSize15(MouseEvent event) {
        resetSizeArea();

        if (pizzaOrderManager.selectedSize == 15) {
            pizzaOrderManager.selectedSize = -1;
            fifteen.getStyleClass().remove("ToppingsSelected");
            fifteen.getStyleClass().add("ToppingsDefault");
        } else {
            pizzaOrderManager.selectedSize = 15;
            fifteen.getStyleClass().add("ToppingsSelected");
            fifteen.getStyleClass().remove("ToppingsDefault");
        }
    }

    /**
     * When the button is selected, will coordinate with the order manager and change styles
     * @param event useless
     */
    @FXML
    void selectedSize18(MouseEvent event) {
        resetSizeArea();

        if (pizzaOrderManager.selectedSize == 18) {
            pizzaOrderManager.selectedSize = -1;
            eighteen.getStyleClass().remove("ToppingsSelected");
            eighteen.getStyleClass().add("ToppingsDefault");
        } else {
            pizzaOrderManager.selectedSize = 18;
            eighteen.getStyleClass().add("ToppingsSelected");
            eighteen.getStyleClass().remove("ToppingsDefault");
        }
    }

    /**
     * When the button is selected, will coordinate with the order manager and change styles
     * @param event useless
     */
    @FXML
    void selectedSize30(MouseEvent event) {
        resetSizeArea();

        if (pizzaOrderManager.selectedSize == 30) {
            pizzaOrderManager.selectedSize = -1;
            thirty.getStyleClass().remove("ToppingsSelected");
            thirty.getStyleClass().add("ToppingsDefault");
        } else {
            pizzaOrderManager.selectedSize = 30;
            thirty.getStyleClass().add("ToppingsSelected");
            thirty.getStyleClass().remove("ToppingsDefault");
        }
    }

    /**
     * When the half and half option is selected
     * @param event useless
     */
    @FXML
    public void selectedHalf(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        if (half.getText().equalsIgnoreCase("1st half")) {
            //  currentPizza.enableSecondHalf();
            half.getStyleClass().add("ToppingsSelected");
            //     isFirstHalf = true;
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
        selectedSize = null;
        resetSizeArea();
    }

    @Override
    public void setParent(PickupDeliveryPaneController o) {
        this.parent = o;
    }
}
