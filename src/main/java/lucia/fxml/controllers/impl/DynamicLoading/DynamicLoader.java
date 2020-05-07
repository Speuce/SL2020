package main.java.lucia.fxml.controllers.impl.DynamicLoading;

import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems.DinnerDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules.DinnerModuleDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special.SpecialDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping.ToppingDynamicLoad;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

/**
 * Loads the dynamic building menu
 *
 * A class built purely to keep things neat
 */
public class DynamicLoader {
    private DinnerModuleDynamicLoad dinnerModuleDynamicLoad;
    private DinnerDynamicLoad dinnerDynamicLoad;
    private SpecialDynamicLoad specialDynamicLoad;
    private ToppingDynamicLoad toppingDynamicLoad;

    public DynamicLoader(PickupDeliveryPaneController pickupDeliveryPaneController, PizzaController pizzaController) {
        dinnerDynamicLoad = new DinnerDynamicLoad(pickupDeliveryPaneController);
        dinnerModuleDynamicLoad = new DinnerModuleDynamicLoad(pickupDeliveryPaneController);
        specialDynamicLoad = new SpecialDynamicLoad(pizzaController);
        toppingDynamicLoad = new ToppingDynamicLoad(pizzaController);
    }

    /**
     * Creates the buttons and panes for the menu
     */
    public void runDynamicLoader() {
        toppingDynamicLoad.createToppings();
        specialDynamicLoad.createSpecials();
        dinnerModuleDynamicLoad.createDinnerModules();
        dinnerDynamicLoad.createDinners();
    }
}
