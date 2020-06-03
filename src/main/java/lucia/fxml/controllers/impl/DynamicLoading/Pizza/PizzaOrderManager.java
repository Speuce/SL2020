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

    /**
     * CURRENT pizza instance for specialties
     */
    public SpecialtyPizzaDescriptor currentSpecialPizza;

    /**
     * First half pizza variable
     */
    public SizeableItemDescriptor secondHalf;

    /**
     * Final product of pizza
     */
    private Pizza madePizza; // created when 'Make' is clicked

    /**
     * Selected Size instance
     */
    public int selectedSize;

    /**
     * The current instance!
     */
    private static PizzaOrderManager pizzaOrderInstance;

    /**
     * Boolean instance for second half
     *
     * Used in the case that the pizza does not have a selected size, so "madePizza" cant be used
     */
    private boolean splitHalves = false;


    private PizzaOrderManager() {
        toppings = new ArrayList<>();
        currentPizza = null;
        currentSpecialPizza = null;
        madePizza = null;
        selectedSize = -1;
        secondHalf = null;
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

    /**
     * Creates a new instance
     */
    public void resetOrderManager() {
        pizzaOrderInstance = new PizzaOrderManager();
    }

    /**
     * When 'Make' is clicked, creates the Pizza for the Order
     */
    public Pizza makePizza() {
        if (currentSpecialPizza != null) {
            setToppingsSpecial();
        } else if (currentPizza != null) {
            madePizza = (Pizza)currentPizza.getAsItem(selectedSize);
            setToppings(madePizza);
        }

        return madePizza;
    }

    /**
     * Adds the second half to the pizza
     */
    public Pizza makeSecondHalf() {
        Pizza secondHalf;
        madePizza.enableSecondHalf();
        if (currentSpecialPizza != null) {
            setToppingsSpecialSecondHalf();
        } else if (currentPizza != null) {
            secondHalf = (Pizza)currentPizza.getAsItem(selectedSize);
            setToppings(secondHalf);
            madePizza.setSecondHalf(secondHalf);
        }

        return madePizza;
    }

    /**
     * When 'Make' is clicked, puts pizza in the Order system
     */
    public void addPizzaToOrder() {
        if(isSecondHalf() && findSize()) {
            OrderManager orderManager = OrderManager.INSTANCE;
            Order order = new Order(OrderType.UNSELECTED); // todo

            order.addItem(makeSecondHalf());

            DynamicLoader.dynamicLoaderInstance.getToppingDynamicLoad().clearSelectedButtons();
            DynamicLoader.dynamicLoaderInstance.getSpecialDynamicLoad().clearSelectedButtons();

            resetOrderManager();
        }
        else if(findSize()) {
            OrderManager orderManager = OrderManager.INSTANCE;
            Order order = new Order(OrderType.UNSELECTED); // todo
            order.addItem(makePizza());
       //     orderManager.registerOrder(order);
            DynamicLoader.dynamicLoaderInstance.getToppingDynamicLoad().clearSelectedButtons();
            DynamicLoader.dynamicLoaderInstance.getSpecialDynamicLoad().clearSelectedButtons();
            DynamicLoader.dynamicLoaderInstance.pizzaController.resetSizeArea();
            resetOrderManager();
        } else System.out.println("WAITING FOR A SIZE!");

    }

    /**
     * Checks if the size for the pizza has been selected or not
     */
    public boolean findSize() {
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
    private void setToppingsSpecialSecondHalf() {
        Pizza secondHalfPizza = (Pizza)secondHalf.getAsItem(selectedSize);
        if(!toppings.isEmpty()) {
            for (ToppingType topping : toppings) {
//                if (!currentSpecialPizza.hasToppingType(topping)) {
                secondHalfPizza.addTopping(topping, 2); //TODO GET AMOUNT
            }
//            }
        }
        madePizza.setSecondHalf(secondHalfPizza);
    }

    /**
     * Adds Toppings to the BUILD YOUR OWN pizza if there are any NON DEFAULT toppings,
     * <p>
     * For purpose if when 'Make' is clicked
     */
    private void setToppings(Pizza toBeMade) {
        if (!toppings.isEmpty()) {
            for (ToppingType topping : toppings) {
//                if (currentPizza.hasToppingType(topping)) {
                    toBeMade.addTopping(topping, 2); //todo get amnt
                }
//            }
        }
    }

    /**
     * Assisting method to enable the second half for half and half pizzas
     */
    public void enableSecondHalf() {
        System.out.println("ENABLED SECOND HALF!");
        splitHalves = true;
    }

    /**
     * Assisting method to check if there is a second half enabled for half and half
     *
     * on make click
     */
    public boolean isSecondHalf() {
        return splitHalves;
    }

    /**
     * Assisting method to set the second half for the half and half pizzas
     * @param secondHalf the secondhalf
     */
    public void setSecondHalf(Pizza secondHalf) {
        madePizza.setSecondHalf(secondHalf);
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
