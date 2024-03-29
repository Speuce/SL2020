package main.java.lucia.client.content.order.discount.impl.amount;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.ItemBundle;
import main.java.lucia.client.content.menu.item.descriptor.ItemBundleDescriptor;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.ArrayList;
import java.util.Set;

public class BundlePriceToppingAllowance extends BundlePrice {

    /**
     * The maximum amount that the bundle can have spent on toppings
     * before toppings are charged extra.
     */
    private long toppingAllowance;

    public BundlePriceToppingAllowance(ItemBundleDescriptor bundleDescriptor, long toppingAllowance) {
        super(bundleDescriptor);
        this.toppingAllowance = toppingAllowance;
    }

    /**
     * Applies the discount to the given subset of the order
     *
     * @param list  the set of items to apply to
     * @param order the order being applied to.
     * @return the amount (in cents) saved by applying this discount
     */
    @Override
    public long applyDiscount(CustomDiscount o, Set<Item> list, ItemList order) {
        //create the bundle
        long allowanceLeft = toppingAllowance;
        order.getItems().removeAll(list);
        ItemBundle b = new ItemBundle(getBundleDescriptor(), new ArrayList<>(list));
        long beforePrice = 0;

        for(Item i: list){
            beforePrice += i.getDiscountedPrice();
            i.setDiscountedPrice(0);
            if(i instanceof Pizza){
                allowanceLeft = handlePizza((Pizza)i, allowanceLeft);
            }
            i.getAppledDiscounts().add(o);
        }
        b.getAppledDiscounts().add(o);
        order.addItem(b);
        return beforePrice - b.calcNameAndPrice().getVal2();
    }

    /**
     * Handles the allowance calculation of a pizza
     * @param p the pizza
     * @param allowanceLeft how much of the allowance is left
     * @return the remaining balance of the allowance
     */
    protected long handlePizza(Pizza p, long allowanceLeft){
        long cost = p.getToppingPrice();
        if(allowanceLeft > cost){
            allowanceLeft -= cost;
        }else if(allowanceLeft > 0){
            p.setDiscountedPrice(p.getDiscountedPrice() + (cost-allowanceLeft));
            allowanceLeft = 0;
        }else{
            p.setDiscountedPrice(p.getDiscountedPrice() + cost);
        }
        return allowanceLeft;
    }

    public long getToppingAllowance() {
        return toppingAllowance;
    }
}
