package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Sauce;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Sauce Selection in the FXML
 */
public class SauceListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private Sauce name; // sauce information
    private JFXButton button; // the button instance
    private SauceDesigns sauceDesigns;
    PizzaOrderManager pizzaOrderManager;

    public SauceListeners(PizzaController pizzaController, Sauce name, JFXButton button) {
        this.pizzaController = pizzaController;
        this.name = name;
        this.button = button;
        sauceDesigns = new SauceDesigns(name);
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
        button.setOnMouseClicked(this::sauceSelected);
        button.setOnMouseEntered(this::activateHover);
        button.setOnMouseExited(this::deactivateHover);
    }

    /**
     *  Event Handler for when the button is hovered into
     */
    public void activateHover(MouseEvent event) {
        //   button.setStyle(dinnerModuleDesigns.g());
        if(!button.getStyle().equalsIgnoreCase(sauceDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(sauceDesigns.getHoveredStyleString());
    }

    /**
     *  Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        //  button.setStyle(toppingDesigns.getDefaultStyleString());
        if(!button.getStyle().equalsIgnoreCase(sauceDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(sauceDesigns.getDefaultStyleString());
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    public void sauceSelected(MouseEvent event) {
        sauceClicked(name.getId());
    }

    /**
     *  Implements with the Order System
     */
    private void sauceClicked(int id) {
        if(pizzaOrderManager.sauceOption == null) {
            pizzaOrderManager.sauceOption = name;
            setStyle(sauceDesigns.getSelectedStyleString());
        }else if(name.equals(pizzaOrderManager.sauceOption)) {
            pizzaOrderManager.sauceOption = null;
            setStyle(sauceDesigns.getDefaultStyleString());
        } else {
            DynamicLoader.dynamicLoaderInstance.getSauceDynamicLoad().clearSelectedButtons();
            pizzaOrderManager.sauceOption = name;
            setStyle(sauceDesigns.getSelectedStyleString());
        }
    }

    /**
     * Sets the style of the current button
     * @param type the style to be changed to
     */
    public void setStyle(String type) {
        button.setStyle(type);
    }

    /**
     * ACCESSOR
     * @return the sauce
     */
    public Sauce getSauce() {
        return name;
    }
}

