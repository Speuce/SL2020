package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Crust for the Topping Selection in the FXML
 */
public class CrustListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private Crust name; // crust information
    private JFXButton button; // the button instance
    private CrustDesigns crustDesigns;
    PizzaOrderManager pizzaOrderManager;

    public CrustListeners(PizzaController pizzaController, Crust name, JFXButton button) {
        this.pizzaController = pizzaController;
        this.name = name;
        this.button = button;
        crustDesigns = new CrustDesigns(name);
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
        button.setOnMouseClicked(this::crustSelected);
        button.setOnMouseEntered(this::activateHover);
        button.setOnMouseExited(this::deactivateHover);
    }

    /**
     *  Event Handler for when the button is hovered into
     */
    public void activateHover(MouseEvent event) {
        //   button.setStyle(dinnerModuleDesigns.g());
        if(!button.getStyle().equalsIgnoreCase(crustDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(crustDesigns.getHoveredStyleString());
    }

    /**
     *  Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        //  button.setStyle(toppingDesigns.getDefaultStyleString());
        if(!button.getStyle().equalsIgnoreCase(crustDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(crustDesigns.getDefaultStyleString());
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    public void crustSelected(MouseEvent event) {
        crustClicked(name.getId());
    }

    /**
     *  Implements with the Order System
     */
    private void crustClicked(int id) {
        // add to order system
   //     ToppingType type = new IDCaster<ToppingType>().cast(id);
       // if(pizzaController.getCurrentPizza().hasToppingType(type)){
            //add crust option
      //  }else{
            //remove crust option
    //    }


    }

    /**
     * Sets the style of the current button
     * @param type the style to be changed to
     */
    public void setStyle(String type) {
        button.setStyle(type);
    }
}
