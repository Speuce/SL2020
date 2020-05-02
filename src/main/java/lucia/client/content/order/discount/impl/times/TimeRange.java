package main.java.lucia.client.content.order.discount.impl.times;

import main.java.lucia.client.content.order.discount.impl.CustomDiscount;

import java.time.LocalDateTime;

/**
 * An implemented {@link DiscountTime} where the discount
 * is valid within a specific time window for every day that it is valid.
 * @author Matthew Kwiatkowski
 */
public class TimeRange extends DiscountTime{

    /**
     * The time range of which the discount is valid
     */
    private LocalTimeRange range;

    public TimeRange(CustomDiscount o, LocalTimeRange range) {
        super(o);
        this.range = range;
    }

    /**
     * Indicates whether the discount applies at the given time.
     *
     * @param time the time to check
     * @return true if the discount applies at this time, false otherwise
     */
    @Override
    public boolean applies(LocalDateTime time) {
        return range.isWithin(time);
    }
}
