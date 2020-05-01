package main.java.lucia.client.content.order.discount.impl.times;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.impl.DiscountAttribute;

import java.time.LocalDateTime;

/**
 * Interface for timing a discount.
 * @author Matthew Kwiatkowski
 */
public abstract class DiscountTime extends DiscountAttribute {


    public DiscountTime(CustomDiscount o) {
        super(o);
    }

    /**
     * Indicates whether the discount applies at the given time.
     * @param time the time to check
     * @return true if the discount applies at this time, false otherwise
     */
    public abstract boolean applies(LocalDateTime time);
}
