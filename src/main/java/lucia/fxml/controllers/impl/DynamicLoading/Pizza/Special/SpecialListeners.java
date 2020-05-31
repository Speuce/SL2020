package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Specialty Pizzas in the FXML
 */
public class SpecialListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private SpecialtyPizzaDescriptor specialtyPizzaDescriptor; // specialty pizza information
    private JFXButton button;
    private SpecialDesigns specialDesigns;
    private PizzaOrderManager pizzaOrderManager;

    public SpecialListeners(PizzaController pizzaController, SpecialtyPizzaDescriptor specialtyPizzaDescriptor, JFXButton button) {
        this.pizzaController = pizzaController;
        this.specialtyPizzaDescriptor = specialtyPizzaDescriptor;
        this.button = button;
        specialDesigns = new SpecialDesigns(specialtyPizzaDescriptor);
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
        specialClicked(specialtyPizzaDescriptor.getId());
    }

    /**
     *  Implements with the Order System
     */
    private void specialClicked(int id) {
        Menu menuInstance = Menu.get;
       // System.out.println(specialtyPizzaDescriptor + " | " + menuInstance.getItemFromId(id));
       // if(specialtyPizzaDescriptor.equals(menuInstance.getItemFromId(id))) {
            if(pizzaOrderManager.currentSpecialPizza == null) {
                pizzaOrderManager.currentSpecialPizza = specialtyPizzaDescriptor;

                System.out.println("ADDED " + specialtyPizzaDescriptor.getBaseName() + " to Order");
                button.setStyle(specialDesigns.getSelectedStyleString());
            }
            else if(pizzaOrderManager.currentSpecialPizza.equals(specialtyPizzaDescriptor)) {
                pizzaOrderManager.currentSpecialPizza = null;
                button.setStyle(specialDesigns.getDefaultStyleString());
                System.out.println("REMOVED " + specialtyPizzaDescriptor.getBaseName() + " from Order");
            } else {
                pizzaOrderManager.currentSpecialPizza = specialtyPizzaDescriptor;

                System.out.println("ADDED " + specialtyPizzaDescriptor.getBaseName() + " to Order");
                button.setStyle(specialDesigns.getSelectedStyleString());
            }
       // } else System.out.println("Something is Wrong! Pizzas clicked and class instance does not match!");

    }

}
