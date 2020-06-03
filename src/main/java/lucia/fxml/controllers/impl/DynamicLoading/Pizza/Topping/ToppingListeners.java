package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Topping Selection in the FXML
 */
public class ToppingListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private ToppingType toppingType; // topping information
    private JFXButton button; // the button instance
    private ToppingDesigns toppingDesigns;
    PizzaOrderManager pizzaOrderManager;

    public ToppingListeners(PizzaController pizzaController, ToppingType toppingType, JFXButton button) {
        this.pizzaController = pizzaController;
        this.toppingType = toppingType;
        this.button = button;
        toppingDesigns = new ToppingDesigns(toppingType);
        pizzaOrderManager = PizzaOrderManager.getPizzaOrderInstance();
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
        //   button.setStyle(dinnerModuleDesigns.g());
        if(!button.getStyle().equalsIgnoreCase(toppingDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(toppingDesigns.getHoveredStyleString());
    }

    /**
     *  Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        //  button.setStyle(toppingDesigns.getDefaultStyleString());
        if(!button.getStyle().equalsIgnoreCase(toppingDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(toppingDesigns.getDefaultStyleString());
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    public void toppingSelected(MouseEvent event) {
        toppingClicked(toppingType.getId());
    }

    /**
     *  Implements with the Order System
     */
    public void toppingClicked(int id) {
        ToppingType theTopping = new IDCaster<ToppingType>().cast(id);

        //if(pizzaOrderManager.)

        if(theTopping.equals(toppingType)) {
            if (pizzaOrderManager.currentPizza == null) {
                pizzaOrderManager.toppings.add(toppingType);
                setStyle(toppingDesigns.getSelectedStyleString());
            } else if (pizzaOrderManager.toppings.contains(toppingType)) {
                pizzaOrderManager.toppings.remove(toppingType);
                setStyle(toppingDesigns.getDefaultStyleString());
            } else {
                pizzaOrderManager.toppings.add(toppingType);
                setStyle(toppingDesigns.getSelectedStyleString());
            }
        } else System.out.println("ERROR WHEN TOPPING CLICKED!");

       // System.out.println("CHECKING TOPPING LIST: " + pizzaOrderManager.printToppings());

    }

    /**
     * Sets the style of the current button
     * @param type the style to be changed to
     */
    public void setStyle(String type) {
        button.setStyle(type);
    }

    /**
     * GETTERS
     */
    public ToppingType getToppingType() {
        return toppingType;
    }
}
