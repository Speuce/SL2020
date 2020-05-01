package main.java.lucia.client.content.order.discount.impl.fields;

import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.impl.DiscountAttribute;

/**
 * Represents a field that must be filled in before
 * a discount can be applied.
 * Usually not checked
 * @author Matthew Kwiatkowski
 */
public class DiscountField extends DiscountAttribute {


    public DiscountField(CustomDiscount o) {
        super(o);
    }
}
