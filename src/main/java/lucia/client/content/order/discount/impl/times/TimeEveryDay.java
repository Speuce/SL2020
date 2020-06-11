package main.java.lucia.client.content.order.discount.impl.times;

import java.io.PrintStream;
import java.time.LocalDateTime;

/**
 * Implemented {@link DiscountTime} where the discount
 * should apply every day.
 * @author Matthew Kwiatkowski
 */
public class TimeEveryDay extends DiscountTime{

    public TimeEveryDay() {
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print("EVERYDAY");
    }

    /**
     * Indicates whether the discount applies at the given time.
     *
     * @param time the time to check
     * @return true if the discount applies at this time, false otherwise
     */
    @Override
    public boolean applies(LocalDateTime time) {
        return true;
    }
}
