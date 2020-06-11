package main.java.lucia.client.content.order.discount.impl.amount;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.Discount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.io.PrintStream;
import java.util.Set;

/**
 * Pricing amount involving a given percentage off
 * the price of each item.
 * Does NOT account for additional/extra toppings/addons
 * Percentage off is given for these as well
 *
 * @author Matthew Kwiatkowski
 */
public class PercentageOff extends DiscountAmount{

    /**
     * Percent (0-1) given off of the items
     * where 1=free
     */
    private float percent;

    public PercentageOff(float percent) {
        super();
        this.percent = percent;
    }

    /**
     * Applies the discount to the given subset of the order
     *
     * @param list  the set of items to apply to
     * @param order the order being applied to.
     * @return the amount (in cents) saved by applying this discount
     */
    @Override
    public int applyDiscount(Discount o, Set<Item> list, ItemList order) {
        int totalDis = 0;
        int discountAmt;
        for(Item i: list){
            i.getAppledDiscounts().add(o);
            discountAmt = (int)(i.getPrice()*percent);
            i.setDiscountedPrice(i.getDiscountedPrice() - discountAmt);
            totalDis+=discountAmt;
        }
        return totalDis;
    }

    /**
     * Percent (0-1) given off of the items
     * where 1=free
     */
    public float getPercent() {
        return percent;
    }

    /**
     * Percent (0-1) given off of the items
     * where 1=free
     */
    public void setPercent(float percent) {
        this.percent = percent;
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print("Percentage Discount: " + getPercent());
    }
}
