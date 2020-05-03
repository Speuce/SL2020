package main.java.lucia.client.content.order.impl;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.pizza.Pizza;
import main.java.lucia.client.content.payment.PaidBillable;
import main.java.lucia.client.structures.Exclude;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class meant to hold a list of menu items,
 * and perform operations on said items
 * @author Matthew Kwiatkowski
 * @author Zachery Unrau
 */
public abstract class ItemList extends PaidBillable implements Iterable<Item>{

    /**
     * The list of items
     */
    private ArrayList<Item> items;

    /**
     * A sorted list of the pizzas (used for double deal price calculations
     */
    @Exclude
    private LinkedHashSet<Pizza> pizzaSorted;

    public ItemList(){
        super();
        items = new ArrayList<Item>();
    }

    /**
     * Cacluates the total cost, less taxes,
     * plus any additional fees
     */
    @Override
    protected long calculateCost() {
        for (Item item : items) {
            item.calcNameAndPrice();
        }
        long tot = 0;
        tot += calculateFees();
//        if (orderType == OrderType.DELIVERY) {
//            total += DiscountOthersCalculator.getInstance().getDeliveryFee();
//        }
        for (Item i : items) {
            tot += i.getDiscountedPrice();
        }
        return tot;
    }



//    /**
//     * Calculates double deals for pastas
//     */
//    private void calculateHalfOffDiscountsPasta() {
//        ArrayList<Item> pastas = new ArrayList<>();
//        for(int x = 0; x < items.size(); x++) {
//            if (items.get(x) instanceof Italian) {
//                if(items.get(x).isDiscountEligible())
//                    pastas.add(items.get(x));
//            }
//        }
//        if(!pastas.isEmpty()) {
//            for(int x = 1; x < pastas.size(); x += 2) {
//                if(!pastas.get(x - 1).isDiscounted()) {
//                    pastas.get(x).setPrice(pastas.get(x).getPriceNonCalculated() / 2);
//                    pastas.get(x).setDiscounted(true);
//                }
//            }
//        }
//    }
//
//    /**
//     * Calculates the double deal discounts
//     */
//    private void calculateHalfOffDiscounts() {
//        /* First map all of our pizzas based eligible discount on size, don't use stream as it's a performance loss */
//        // TODO If we add a 15 inch, 13 inch, 10 inch, which one gets discounted?
//        /* Store 18s and 30s separately */
//        LinkedHashSet<Pizza> thirties = new LinkedHashSet<Pizza>();
//        LinkedHashSet<Pizza> eighteens = new LinkedHashSet<Pizza>();
//
//        HalfOff halfOffDiscounts = DiscountOthersCalculator.getInstance().getHalfOffDiscounts();
//        BinarySearchTree<Pizza> pizzaSorted = new BinarySearchTree<>();
//        for (Item pizza : items) {
//            if (pizza instanceof Pizza) {
//                Pizza current = (Pizza) pizza;
//                if (!current.isDiscounted() && halfOffDiscounts.check(current)) {
//                    pizzaSorted.insert(current);
//                }else if(current.getSize() == Size.THIRTY){
//                    thirties.add(current);
//                }else if(current.getSize() == Size.EIGHTEEN){
//                    eighteens.add(current);
//                }
//            }
//        }
//        List<Pizza> sortedPizza = pizzaSorted.inOrder();
//        for (int i = 0; i < sortedPizza.size(); i = i + 2) {
//            if(i + 1 == sortedPizza.size()) {
//                break;
//            }
//            Pizza first = sortedPizza.get(i);
//            Pizza second = sortedPizza.get(i + 1);
//            if (first != null && second != null) {
//                if (first.getPriceNonCalculated() <= second.getPriceNonCalculated()) {
//                    // Since the first one is first, swap them in order to produce a better looking display
//                    int firstIndex2 = sortedPizza.indexOf(first);
//                    int secondIndex2 = sortedPizza.indexOf(second);
//                    sortedPizza.set(firstIndex2, second);
//                    sortedPizza.set(secondIndex2, first);
//
//                    first.setDiscounted(true);
//                    first.setPrice(first.getPriceNonCalculated() / 2);
//                } else {
//                    second.setDiscounted(true);
//                    second.setPrice(second.getPriceNonCalculated() / 2);
//                }
//            }
//        }
//        thirties.addAll(eighteens);
//        thirties.addAll(sortedPizza);
//        this.pizzaSorted = thirties;
//    }

//    /**
//     * Add a miscallaneous item to the list
//     * @param desc the description provided
//     * @param price the cost of the item
//     */
//    public void addMisc(String desc, long price) {
//        Misc misc = new Misc(desc, price);
//        addItem(misc);
//    }

    /**
     * Adds an item to the list
     *
     * @param item The item to add to the order
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Adds the item
     */
    public void addItem(){

    }

    /**
     * Removes the given item from the list
     *
     * @param item The item to remove
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Check to see if the item list is empty
     * @return true if the item list is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Get a sorted pizza list
     * @return a set of all the pizzas, sorted by price and size
     */
    public LinkedHashSet<Pizza> getPizzaSorted(){
        return pizzaSorted;
    }

    /**
     * Calculate any additional fees for this item list
     * @return additional fees.
     */
    public abstract long calculateFees();

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public Iterator<Item> iterator() {
        return this.items.iterator();
    }
}
