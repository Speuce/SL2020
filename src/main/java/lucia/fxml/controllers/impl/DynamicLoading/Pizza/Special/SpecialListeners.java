package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Specialty Pizzas in the FXML
 */
public class SpecialListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private SpecialtyPizzaDescriptor name; // specialty pizza information
    private JFXButton button;
    private SpecialDesigns specialDesigns;

    public SpecialListeners(PizzaController pizzaController, SpecialtyPizzaDescriptor name, JFXButton button) {
        this.pizzaController = pizzaController;
        this.name = name;
        this.button = button;
        specialDesigns = new SpecialDesigns(name);
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public void setListeners() {
        button.setOnMouseClicked(this::specialSelected);
        button.setOnMouseEntered(this::activateHover);
        button.setOnMouseExited(this::deactivateHover);
    }

    /**
     *  Event Handler for when the button is hovered into
     */
    public void activateHover(MouseEvent event) {
        button.setStyle(specialDesigns.getHoveredStyleString());
    }

    /**
     *  Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        button.setStyle(specialDesigns.getDefaultStyleString());
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    private void specialSelected(MouseEvent event) {
        specialClicked(name.getId());
    }

    /**
     *  Implements with the Order System
     */
    private void specialClicked(int id) {
        // add to order system
    //    ToppingType type = new IDCaster<ToppingType>().cast(id);
       // if(pizzaController.getCurrentPizza().has(type)){
            //add topping
  //      }else{
            //remove topping
        }

}
