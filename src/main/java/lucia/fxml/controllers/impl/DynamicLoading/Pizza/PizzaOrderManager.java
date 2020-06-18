package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza;

import main.java.lucia.client.content.menu.item.descriptor.SizeableItemDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Pizza;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

import java.util.HashMap;

/**
 * Instance for the pizza order listeners
 *
 * Will be updated to the 'current' pizza whenever button is clicked on the GUI
 */
public class  PizzaOrderManager {

    /**
     * TOPPINGS instance
     */
    public HashMap<ToppingType, Integer> toppings;

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
     * The selected crust
     */
    public Crust crustOption;

    /**
     * The selected sauce
     */
    public Sauce sauceOption;

    /**
     * Determines what the topping strength will be
     */
    public int easyToExtraOption;

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

    /**
     * The current order for this instance
     */
    private Order currentOrder;

    /**
     * The current pickupDelivery controller instance
     */
    private PickupDeliveryPaneController pickupDeliveryPaneController;


    private PizzaOrderManager() {
        toppings = new HashMap<>();
        currentPizza = null;
        currentSpecialPizza = null;
        madePizza = null;
        selectedSize = -1;
        secondHalf = null;
        crustOption = null;
        sauceOption = null;
        currentOrder = new Order(OrderType.UNSELECTED);
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
        toppings = new HashMap<>();
        currentPizza = null;
        currentSpecialPizza = null;
        madePizza = null;
        selectedSize = -1;
        secondHalf = null;
        crustOption = null;
        sauceOption = null;
    }

    /**
     * When 'Make' is clicked, creates the Pizza for the Order
     */
    public Pizza makePizza() {
        if (currentSpecialPizza != null) {
            setToppingsSpecial();
        } else if (!toppings.isEmpty()) {
            madePizza = new Pizza(selectedSize);
            setToppings(madePizza);
        } else System.out.println("SOMETHING WENT WRONG!!");
        setCrustSauceOptions();

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
        setCrustSauceOptions();

        return madePizza;
    }

    /**
     * When 'Make' is clicked, puts pizza in the Order system
     */
    public void addPizzaToOrder() {
        if(hasToppings()) {
            if (isSecondHalf() && findSize()) {
                OrderManager orderManager = OrderManager.INSTANCE;

                currentOrder.addItem(makeSecondHalf());

                pickupDeliveryPaneController = DynamicLoader.dynamicLoaderInstance.pickupDeliveryPaneController;
                pickupDeliveryPaneController.getOrderViewController().updateOrderView();

                DynamicLoader.dynamicLoaderInstance.clearDynamicLoaders();
                resetOrderManager();
            } else if (findSize()) {
//                OrderManager orderManager = OrderManager.INSTANCE;
//                Order order = new Order(OrderType.UNSELECTED); // todo
                currentOrder.addItem(makePizza());

                pickupDeliveryPaneController = DynamicLoader.dynamicLoaderInstance.pickupDeliveryPaneController;
                pickupDeliveryPaneController.getOrderViewController().updateOrderView();
                //     orderManager.registerOrder(order);

                DynamicLoader.dynamicLoaderInstance.clearDynamicLoaders();
                resetOrderManager();
            } else System.out.println("WAITING FOR A SIZE! " + selectedSize);
        } else DynamicLoader.dynamicLoaderInstance.pizzaController.setToppingsNeed(true);
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
           for (ToppingType topping : toppings.keySet()) {
//                if (!currentSpecialPizza.hasToppingType(topping)) {
                    madePizza.addTopping(topping, toppings.get(topping)); //TODO GET AMOUNT
                }
//            }
       }
    }

    /**
     * Sets the crust and sauce options if there are any
     */
    private void setCrustSauceOptions() {
        if(crustOption != null) {
            madePizza.setCrust(crustOption);
        }
        if(sauceOption != null) {
            madePizza.setSauce(sauceOption);
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
            for (ToppingType topping : toppings.keySet()) {
//                if (!currentSpecialPizza.hasToppingType(topping)) {
                secondHalfPizza.addTopping(topping, toppings.get(topping)); //TODO GET AMOUNT
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
            for (ToppingType topping : toppings.keySet()) {
//                if (currentPizza.hasToppingType(topping)) {
                    toBeMade.addTopping(topping, toppings.get(topping)); //todo get amnt
                }
//            }
        }
    }

    /**
     * Loads a pizza from the input
     */
    public void loadPizza(Pizza pizza) {
        toppings.clear();
     //   toppings.addAll(pizza.getAllToppingsOnPizza());
    }

    /**
     * Assisting method to enable the second half for half and half pizzas
     */
    public void enableSecondHalf() {
        splitHalves = true;
    }

    /**
     * Assisting method to disable the second half for half and half pizzas
     */
    public void disableSecondHalf() {
        splitHalves = false;
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
     * Checks to see if there are any toppings chosen on the GUI
     *
     * @return if there are toppings are not
     */
    public boolean hasToppings() {
        return !toppings.isEmpty();
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
     * GETTER FOR THE CURRENT ORDER
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Checks to see if the order is empty or not
     */
    public boolean isOrderEmpty() {
        if(currentOrder == null)
            return true;
        return currentOrder.isEmpty();
    }
//    /**
//     * Iterated through toppings list
//     *
//     * Meant for debug
//     */
//    public String printToppings() {
//        String s = "";
//        for (ToppingType topping : toppings.)
//            s += topping.getShortName() + " | ";
//        return s;
//    }
}
