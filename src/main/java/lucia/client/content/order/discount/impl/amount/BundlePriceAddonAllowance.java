package main.java.lucia.client.content.order.discount.impl.amount;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.ItemBundleDescriptor;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.item.type.ItemBundle;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.ArrayList;
import java.util.Set;

/**
 * A discount pricing where items are grouped into a single
 * 'bundle' and charged based on the overall bundle price
 * +extras (addons), where there is a certain 'allowance'
 * to spend on addons, and any extra over that amount will be billed
 * additional to the bundle price.
 * @author Matthew Kwiatkowski
 */
public class BundlePriceAddonAllowance extends BundlePriceToppingAllowance{

    /**
     * The maximum amount that the bundle can have spent on addons
     * before addons are charged extra.
     */
    private final int addonAllowance;

    public BundlePriceAddonAllowance(ItemBundleDescriptor bundleDescriptor
            ,int toppingAllowance,int addonAllowance) {
        super(bundleDescriptor, toppingAllowance);
        this.addonAllowance = addonAllowance;
    }

    /**
     * Applies the discount to the given subset of the order
     *
     * @param list  the set of items to apply to
     * @param order the order being applied to.
     * @return the amount (in cents) saved by applying this discount
     */
    @Override
    public int applyDiscount(AppliedDiscount o, Set<Item> list, ItemList order) {
        //create the bundle
        long allowanceLeft = addonAllowance;
        long pizzaAllowance = getToppingAllowance();
        order.getItems().removeAll(list);
        ItemBundle b = new ItemBundle(getBundleDescriptor(), new ArrayList<>(list));
        int beforePrice = 0;

        for(Item i: list){
            beforePrice += i.getDiscountedPrice();
            i.setDiscountedPrice(0);
            if(i instanceof ItemModifiable){
                allowanceLeft = handleItemModifiable((ItemModifiable) i, allowanceLeft);
            }else if(i instanceof Pizza){
                pizzaAllowance = handlePizza((Pizza)i, (int) pizzaAllowance);
            }
            i.getAppledDiscounts().add(o);
        }
        b.getAppledDiscounts().add(o);
        order.addItem(b);
        return beforePrice - b.calcNameAndPrice().getVal2().intValue();
    }

    /**
     * Handles the allowance calculation of a pizza
     * @param mod the {@link ItemModifiable}
     * @param allowanceLeft how much of the allowance is left
     * @return the remaining balance of the allowance
     */
    protected long handleItemModifiable(ItemModifiable mod, long allowanceLeft){
        for(Addon a: mod.getAddons()){
            //3 cases: either there is enough allowance, there is SOME allowance. or none.
            if(allowanceLeft > a.getPrice()){
                allowanceLeft-=a.getPrice();
            }else if(allowanceLeft > 0){
                mod.setDiscountedPrice(mod.getDiscountedPrice()+a.getPrice()-allowanceLeft);
                allowanceLeft = 0;
            }else{
                mod.setDiscountedPrice(mod.getDiscountedPrice()+a.getPrice());
            }
        }
        return allowanceLeft;
    }


    public long getAddonAllowance() {
        return addonAllowance;
    }


}
