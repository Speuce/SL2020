package main.java.lucia.client.content.order.discount.impl.times;

import main.java.lucia.client.content.time.TimeFormat;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Implemented {@link DiscountTime} where the discount
 * shall apply on specific dates.
 * This would be used for something like GameDay specials
 * @author Matthew Kwiatkowski
 */
public class TimeSpecifiedDays extends DiscountTime{

    /**
     * The specific dates that this discount applies
     */
    private Set<LocalDate> dateApplies;

    public TimeSpecifiedDays(Set<LocalDate> dateApplies) {
        this.dateApplies = dateApplies;
    }

    /**
     * Indicates whether the discount applies at the given time.
     *
     * @param time the time to check
     * @return true if the discount applies at this time, false otherwise
     */
    @Override
    public boolean applies(LocalDateTime time) {
       return dateApplies.contains(time.toLocalDate());
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print("On Days: [");
        dateApplies.forEach( d -> out.print(d.format(TimeFormat.FORMATTER_ISO_STANDARD_DATE.getFormat()) + ","));
        out.print("]");
    }
}
