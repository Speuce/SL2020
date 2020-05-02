package main.java.lucia.client.content.order.discount.impl.times;

import main.java.lucia.client.content.order.discount.impl.CustomDiscount;

import java.time.LocalDateTime;

/**
 * Implemented {@link DiscountTime} where the discount
 * shall only apply at specific times. of the days that they apply on
 * @author Matthew Kwiatkowski
 */
public class TimeHourRange extends DiscountTime{


    public TimeHourRange(CustomDiscount o) {
        super(o);
    }

    /**
     * Indicates whether the discount applies at the given time.
     *
     * @param time the time to check
     * @return true if the discount applies at this time, false otherwise
     */
    @Override
    public boolean applies(LocalDateTime time) {
        return false;
    }
}
