package main.java.lucia.client.content.order.discount.impl;


import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.discount.Discount;

import java.util.*;

/**
 * Object that represents the pizza double deal
 * Buy one pizza (10, 13, or 15) and get the
 * second pizza of equal or lesser value 50% off
 */
public class PizzaDoubleDeal extends Discount {

    /**
     * Sizes that this deal applies to.
     */
    private Set<Integer> applicableSizes;

    public PizzaDoubleDeal() {
        super("DoubleDeal", 1);
        applicableSizes = new HashSet<>();
        loadApplicableSizes();
    }

    /**
     * Loads all applicable sizes for this deal.
     */
    private void loadApplicableSizes(){
        applicableSizes.add(10);
        applicableSizes.add(13);
        applicableSizes.add(15);
    }

    /**
     * Check to see if this discount is compatible
     * with an order
     *
     * @param p the order to check compatibility with
     * @return true if the order is eligible for this discount
     */
    @Override
    public boolean isDiscountEligible(Order p) {
        int eligiblePizzas = 0;
        for(Item i: p.getItems()){
            if(i instanceof Pizza){
                Pizza pizza = (Pizza) i;
                if(applicableSizes.contains(pizza.getSize())){
                    eligiblePizzas++;
                    if(eligiblePizzas >= 2){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Calculates the total discount amount for an Order
     *
     * @param p the order to get the total discount amount of.
     * @return the total amount saved by this discount
     */
    @Override
    public long calcDiscount(Order p) {
        long saved = 0L;
        Queue<Pizza> pizzaSorted = getEligiblePizzasSorted(p);
        //Take out groups of two, calc discount price on that
        Pizza p1 = pizzaSorted.poll(); // the more expensive of the pair (will NOT be discounted)
        Pizza p2 = pizzaSorted.poll(); //the less expensive of the pair (WILL be discounted)
        while(p1 != null && p2 != null){
            saved += p2.getPrice()/2L;
            p1 = pizzaSorted.poll();
            p2 = pizzaSorted.poll();
        }
        return saved;
    }

    /**
     * Finds and sorts (by descending price) all discount elgible
     * pizzas in the given order.
     * @param o the Order of which to grab to pizzas from
     * @return the Sorted List of Discount eligible pizzas
     */
    private LinkedList<Pizza> getEligiblePizzasSorted(Order o){
        //Load the eligible pizzas onto the list
        LinkedList<Pizza> sortedPizza = new LinkedList<>();
        for(Item i: o.getItems()){
            if(i instanceof Pizza){
                Pizza pizza = (Pizza) i;
                if(applicableSizes.contains(pizza.getSize())){
                    sortedPizza.add(pizza);
                }
            }
        }
        //sort the list, Highest->Lowest Price
        Collections.sort(sortedPizza);
        return sortedPizza;
    }

    /**
     * Applies the discount to the given order
     *
     * @param p the order to apply this discount to.
     */
    @Override
    public void applyDiscount(Order p, Map<String, Object> mapa) {
        Queue<Pizza> pizzaSorted = getEligiblePizzasSorted(p);
        //Take out groups of two, calc discount price on that
        Pizza p1 = pizzaSorted.poll(); // the more expensive of the pair (will NOT be discounted)
        Pizza p2 = pizzaSorted.poll(); //the less expensive of the pair (WILL be discounted)
        while(p1 != null && p2 != null){
            p2.setDiscountedPrice(p2.getPrice()/2L);
            p1 = pizzaSorted.poll(); // the more expensive of the pair (will NOT be discounted)
            p2 = pizzaSorted.poll(); //the less expensive of the pair (WILL be discounted)
        }
    }

    /**
     * UN applies this discount from the given order.
     *
     * @param p the order to take the discount from.
     */
    @Override
    public void unApplyDiscount(Order p) {

    }
}
