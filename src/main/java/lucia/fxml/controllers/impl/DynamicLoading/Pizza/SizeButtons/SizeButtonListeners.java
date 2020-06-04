package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.SizeButtons;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Size Buttons in the FXML
 */
public class SizeButtonListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private int size; // size information
    private JFXButton button;
    private SizeButtonDesigns sizeButtonDesigns;
    private PizzaOrderManager pizzaOrderManager;
    private final int DEFAULT_VALUE_SIZE = -1;

    public SizeButtonListeners(PizzaController pizzaController, int size, JFXButton button) {
        this.pizzaController = pizzaController;
        this.size = size;
        this.button = button;
        sizeButtonDesigns = new SizeButtonDesigns(size);
        pizzaOrderManager = PizzaOrderManager.getPizzaOrderInstance();
    }

    /**
     * Initial method to set the listeners for the button
     * <p>
     * Originally under "Code" in SceneBuilder
     * <p>
     * todo will have the designs for the buttons changed once the new design is implemented
     */
    public void setListeners() {
        button.setOnMouseClicked(this::sizeButtonSelected);
        button.setOnMouseEntered(this::activateHover);
        button.setOnMouseExited(this::deactivateHover);
    }

    /**
     * Event Handler for when the button is hovered into
     */
    public void activateHover(MouseEvent event) {
        //   button.setStyle(dinnerModuleDesigns.g());
        if (!button.getStyle().equalsIgnoreCase(sizeButtonDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(sizeButtonDesigns.getHoveredStyleString());
    }

    /**
     * Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        //  button.setStyle(toppingDesigns.getDefaultStyleString());
        if (!button.getStyle().equalsIgnoreCase(sizeButtonDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(sizeButtonDesigns.getDefaultStyleString());
    }

    /**
     * Event Handler for when the button is 'clicked'
     * <p>
     * Goes to Order System
     */
    private void sizeButtonSelected(MouseEvent event) {
        sizeButtonClicked(size);
    }

    /**
     * Implements with the Order System
     */
    private void sizeButtonClicked(int size) {
        if(pizzaOrderManager.selectedSize == DEFAULT_VALUE_SIZE) {
            pizzaOrderManager.selectedSize = size;

            setStyle(sizeButtonDesigns.getSelectedStyleString());
        } else if(pizzaOrderManager.selectedSize == size) {
            pizzaOrderManager.selectedSize = DEFAULT_VALUE_SIZE;

            setStyle(sizeButtonDesigns.getDefaultStyleString());
        } else {
            DynamicLoader.dynamicLoaderInstance.getSizeButtonDynamicLoad().clearSelectedButtons();

            pizzaOrderManager.selectedSize = size;
            setStyle(sizeButtonDesigns.getSelectedStyleString());
        }
    }

    /**
     * Sets the style of the current button
     *
     * @param type the style to be changed to
     */
    public void setStyle(String type) {
        button.setStyle(type);
    }

    /**
     * GETTERS
     */
    public int getSize() {
        return size;
    }
}
