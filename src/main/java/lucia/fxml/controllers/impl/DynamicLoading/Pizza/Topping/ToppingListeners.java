package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Topping Selection in the FXML
 */
public class ToppingListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private ToppingType name; // topping information
    private JFXButton button; // the button instance
    private ToppingDesigns toppingDesigns;

    public ToppingListeners(PizzaController pizzaController, ToppingType name, JFXButton button) {
        this.pizzaController = pizzaController;
        this.name = name;
        this.button = button;
        toppingDesigns = new ToppingDesigns(name);
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public void setListeners() {
        button.setOnMouseClicked(this::toppingSelected);
        button.setOnMouseEntered(this::activateHover);
        button.setOnMouseExited(this::deactivateHover);
    }

    /**
     *  Event Handler for when the button is hovered into
     */
    public void activateHover(MouseEvent event) {
        button.setStyle(toppingDesigns.getHoveredStyleString());
    }

    /**
     *  Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        button.setStyle(toppingDesigns.getDefaultStyleString());
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    public void toppingSelected(MouseEvent event) {
        toppingClicked(name.getId());
    }

    /**
     *  Implements with the Order System
     */
    private void toppingClicked(int id) {
        // add to order system
        ToppingType type = new IDCaster<ToppingType>().cast(id);
        if(pizzaController.getCurrentPizza().hasToppingType(type)){
            //add topping
        }else{
            //remove topping
        }


    }
}
