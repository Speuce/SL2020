package main.java.lucia.client.content.order.discount.impl.amount;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.ItemBundle;
import main.java.lucia.client.content.menu.item.descriptor.ItemBundleDescriptor;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.ArrayList;
import java.util.Set;

/**
 * A discount pricing where items are grouped into a single
 * 'bundle' and charged based on the overall bundle price
 * all items inside the bundle get a price of 0.
 * @author Matthew Kwiatkowski
 */
public class BundlePrice extends DiscountAmount{

    /**
     * The price/name of the given bundle
     */
    private ItemBundleDescriptor bundleDescriptor;

    public BundlePrice(CustomDiscount o, ItemBundleDescriptor bundleDescriptor) {
        super(o);
        this.bundleDescriptor = bundleDescriptor;
    }

    /**
     * Applies the discount to the given subset of the order
     *
     * @param list  the set of items to apply to
     * @param order the order being applied to.
     * @return the amount (in cents) saved by applying this discount
     */
    @Override
    public long applyDiscount(Set<Item> list, ItemList order) {
        //create the bundle
        order.getItems().removeAll(list);
        ItemBundle b = new ItemBundle(bundleDescriptor, new ArrayList<>(list));
        long beforePrice = 0;
        for(Item i: list){
            beforePrice += i.getDiscountedPrice();
            i.setDiscountedPrice(0);
            i.getAppledDiscounts().add(getParent());
        }
        b.getAppledDiscounts().add(getParent());
        order.addItem(b);
        return beforePrice - bundleDescriptor.getBasePrice();
    }

    public ItemBundleDescriptor getBundleDescriptor() {
        return bundleDescriptor;
    }


//    //this code is where you create a button
//    private void addButton(final ToppingType topping) {
//        JFXButton buttonThatIAdded;
//        buttonThatIAdded.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                toppingClicked(topping.getId());
//            }
//        });
//    }
//
//    private void toppingClicked(int id){
//        //handle click here. add topping, etc
//    }
}
