package main.java.lucia.fxml.controllers.impl.DynamicLoading;

import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems.DinnerDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules.DinnerModuleDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.SizeButtons.SizeButtonDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special.SpecialDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping.ToppingDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust.CrustDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Sauce.SauceDynamicLoad;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

/**
 * Loads the dynamic building menu
 *
 * A class built purely to keep things neat
 */
public class DynamicLoader {
    public static DynamicLoader dynamicLoaderInstance;


    private DinnerModuleDynamicLoad dinnerModuleDynamicLoad;
    private DinnerDynamicLoad dinnerDynamicLoad;
    private SpecialDynamicLoad specialDynamicLoad;
    private ToppingDynamicLoad toppingDynamicLoad;
    private SizeButtonDynamicLoad sizeButtonDynamicLoad;
    private CrustDynamicLoad crustDynamicLoad;
    private SauceDynamicLoad sauceDynamicLoad;
    public PizzaController pizzaController;

    public DynamicLoader(PickupDeliveryPaneController pickupDeliveryPaneController, PizzaController pizzaController) {
        dinnerDynamicLoad = new DinnerDynamicLoad(pickupDeliveryPaneController);
        dinnerModuleDynamicLoad = new DinnerModuleDynamicLoad(pickupDeliveryPaneController, dinnerDynamicLoad);
        specialDynamicLoad = new SpecialDynamicLoad(pizzaController);
        toppingDynamicLoad = new ToppingDynamicLoad(pizzaController);
        sizeButtonDynamicLoad = new SizeButtonDynamicLoad(pizzaController);
        crustDynamicLoad = new CrustDynamicLoad(pizzaController);
        sauceDynamicLoad = new SauceDynamicLoad(pizzaController);
        this.pizzaController = pizzaController;

    }

    /**
     * Creates the instance if there is none, gives the instance if there is one
     *
     * @return DynamicLoader instance
     */
    public static DynamicLoader getDynamicLoaderInstance(PickupDeliveryPaneController pickupDeliveryPaneController, PizzaController pizzaController) {
        if(dynamicLoaderInstance == null)
            dynamicLoaderInstance = new DynamicLoader(pickupDeliveryPaneController, pizzaController);
        return dynamicLoaderInstance;
    }

    /**
     * Creates the buttons and panes for the menu
     */
    public void runDynamicLoader() {
        toppingDynamicLoad.createToppings();
        specialDynamicLoad.createSpecials();
        dinnerModuleDynamicLoad.createDinnerModules();
        dinnerDynamicLoad.createDinners();
        sizeButtonDynamicLoad.createSizeButtons();

        sauceDynamicLoad.createSauces();
        crustDynamicLoad.createCrusts();
        pizzaController.saucePane.toFront();
        pizzaController.crustPane.toFront();
    }

    /**
     * Assisting method to clean up code
     * Resets the buttons to unselected
     */
    public void clearDynamicLoaders() {
        specialDynamicLoad.clearSelectedButtons();
        toppingDynamicLoad.clearSelectedButtons();
        sizeButtonDynamicLoad.clearSelectedButtons();
        pizzaController.resetSizeArea();
    }

    /**
     * GETTERS
     */
    public SpecialDynamicLoad getSpecialDynamicLoad() {
        return specialDynamicLoad;
    }

    public ToppingDynamicLoad getToppingDynamicLoad() {
        return toppingDynamicLoad;
    }

    public SizeButtonDynamicLoad getSizeButtonDynamicLoad() {
        return sizeButtonDynamicLoad;
    }
}
