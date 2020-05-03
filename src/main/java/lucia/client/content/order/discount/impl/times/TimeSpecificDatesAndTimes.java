package main.java.lucia.client.content.order.discount.impl.times;

import main.java.lucia.client.content.order.discount.impl.CustomDiscount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * An implemented {@link DiscountTime} where the
 * discount is valid at specific dates AND specific
 * times for each of those dates
 */
public class TimeSpecificDatesAndTimes extends DiscountTime{

    /**
     * The date/time pairs which the discount is valid for.
     */
    private HashMap<LocalDate, LocalTimeRange> valid;

    public TimeSpecificDatesAndTimes(HashMap<LocalDate, LocalTimeRange> valid) {
        this.valid = valid;
    }

    /**
     * Indicates whether the discount applies at the given time.
     *
     * @param time the time to check
     * @return true if the discount applies at this time, false otherwise
     */
    @Override
    public boolean applies(LocalDateTime time) {

        if(valid.containsKey(time.toLocalDate())){
            return valid.get(time.toLocalDate()).isWithin(time);
        }
        return false;
    }
}
