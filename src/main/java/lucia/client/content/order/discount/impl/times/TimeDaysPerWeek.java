package main.java.lucia.client.content.order.discount.impl.times;

import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Implemented {@link DiscountTime} where the discount
 * shall apply on certain days of the week
 * @author Matthew Kwiatkowski
 */
public class TimeDaysPerWeek extends DiscountTime{

    /**
     * The days of the week that this discount shall apply.
     */
    private Set<DayOfWeek> daysApplied;

    public TimeDaysPerWeek(Set<DayOfWeek> daysApplied) {
        this.daysApplied = daysApplied;
    }

    /**
     * Indicates whether the discount applies at the given time.
     *
     * @param time the time to check
     * @return true if the discount applies at this time, false otherwise
     */
    @Override
    public boolean applies(LocalDateTime time) {
        return daysApplied.contains(time.getDayOfWeek());
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print("Days per week: ");
        out.print("[");
        daysApplied.forEach(d ->{out.print(d.toString() + ",");});
        out.print("]");
    }
}
