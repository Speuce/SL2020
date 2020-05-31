package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza;

import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.Pizza;
import main.java.lucia.client.content.menu.pizza.ToppingType;

import java.util.ArrayList;

/**
 * Instance for the pizza order listeners
 *
 * Will be updated to the 'current' pizza whenever button is clicked on the GUI
 */
public class PizzaOrderManager {

    /**
     * TOPPINGS instance
     */
    public ArrayList<ToppingType> toppings;

    /**
     * CURRENT pizza instance
     */
    public Pizza currentPizza;

    public SpecialtyPizzaDescriptor currentSpecialPizza;

    private Pizza madePizza; // created when 'Make' is clicked

    public int selectedSize;

    private static PizzaOrderManager pizzaOrderInstance;

    private PizzaOrderManager() {
        toppings = new ArrayList<>();
        currentPizza = null;
        currentSpecialPizza = null;
        madePizza = null;
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
     * When 'Make' is clicked, creates the Pizza for the Order
     */
    public Pizza makePizza() {
        if (currentSpecialPizza != null) {
            setToppingsSpecial();
        } else if (currentPizza != null) {
            setToppings();
            madePizza = currentPizza;
        }
        return madePizza;
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
                if (!currentSpecialPizza.hasToppingType(topping)) {
                    madePizza.addTopping(topping, 2); //TODO GET AMOUNT
                }
            }
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
                if (currentPizza.hasToppingType(topping)) {
                    currentPizza.addTopping(topping, 2); //todo get amnt
                }
            }
        }
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
