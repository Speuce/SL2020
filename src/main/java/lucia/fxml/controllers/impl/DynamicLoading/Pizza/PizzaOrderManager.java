package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza;

import main.java.lucia.client.content.menu.item.descriptor.SizeableItemDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.Pizza;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;

import java.util.ArrayList;

/**
 * Instance for the pizza order listeners
 *
 * Will be updated to the 'current' pizza whenever button is clicked on the GUI
 */
public class  PizzaOrderManager {

    /**
     * TOPPINGS instance
     */
    public ArrayList<ToppingType> toppings;

    /**
     * CURRENT pizza instance
     */
    public SizeableItemDescriptor currentPizza;

    public SpecialtyPizzaDescriptor currentSpecialPizza;

    private Pizza madePizza; // created when 'Make' is clicked

    public int selectedSize;

    private static PizzaOrderManager pizzaOrderInstance;



    private PizzaOrderManager() {
        toppings = new ArrayList<>();
        currentPizza = null;
        currentSpecialPizza = null;
        madePizza = null;
        selectedSize = -1;
    }

    /**
     * Creates the instance if there is none, gives the instance if there is one
     *
     * @return PizzaOrderManager instance
     */
    public static PizzaOrderManager getPizzaOrderInstance() {
        if (pizzaOrderInstance == null)
            pizzaOrderInstance = new PizzaOrderManager();
        return pizzaOrderInstance;
    }

    public void resetOrderManager() {
        pizzaOrderInstance = new PizzaOrderManager();
    }

    /**
     * When 'Make' is clicked, creates the Pizza for the Order
     */
    private Pizza makePizza() {
        if (currentSpecialPizza != null) {
            setToppingsSpecial();
        } else if (currentPizza != null) {
            madePizza = (Pizza)currentPizza.getAsItem(selectedSize);
            setToppings();
        }

        return madePizza;
    }

    /**
     * When 'Make' is clicked, puts pizza in the Order system
     */
    public void addPizzaToOrder() {
        if(findSize()) {
            OrderManager orderManager = OrderManager.INSTANCE;
            Order order = new Order(OrderType.UNSELECTED); // todo
            order.addItem(makePizza());
       //     orderManager.registerOrder(order);
            DynamicLoader.dynamicLoaderInstance.getToppingDynamicLoad().clearSelectedButtons();
            DynamicLoader.dynamicLoaderInstance.getSpecialDynamicLoad().clearSelectedButtons();
            resetOrderManager();
        } else System.out.println("WAITING FOR A SIZE!");

    }

    private boolean findSize() {
        if(selectedSize == -1) {
            DynamicLoader.dynamicLoaderInstance.pizzaController.setButtonPane(
                    DynamicLoader.dynamicLoaderInstance.pizzaController.sizeButtons, "BackgroundDefault", "BackgroundNeed");
            return false;
        }
        else return true;
    }

    /**
     * Adds Toppings to the SPECIALTY pizza if there are any NON DEFAULT toppings,
     * <p>
     * For purpose if when 'Make' is clicked
     */
    private void setToppingsSpecial() {
        madePizza = currentSpecialPizza.getAsItem(selectedSize);
        if(!toppings.isEmpty()) {
           for (ToppingType topping : toppings) {
//                if (!currentSpecialPizza.hasToppingType(topping)) {
                    madePizza.addTopping(topping, 2); //TODO GET AMOUNT
                }
//            }
       }
    }

    /**
     * Adds Toppings to the SPECIALTY pizza if there are any NON DEFAULT toppings,
     * <p>
     * For purpose if when 'Make' is clicked
     */
    private void setToppings() {
        if (!toppings.isEmpty()) {
            for (ToppingType topping : toppings) {
//                if (currentPizza.hasToppingType(topping)) {
                    madePizza.addTopping(topping, 2); //todo get amnt
                }
//            }
        }
    }

    /**
     * Checks if the current pizza being processed is a specialty or build your own
     * @return if the currentSpecialPizza is null or not
     */
    private boolean isSpecialtyPizza() {
        return currentSpecialPizza == null;
    }

    /**
     * Iterated through toppings list
     *
     * Meant for debug
     */
    public String printToppings() {
        String s = "";
        for (ToppingType topping : toppings)
            s += topping.getShortName() + " | ";
        return s;
    }
}
