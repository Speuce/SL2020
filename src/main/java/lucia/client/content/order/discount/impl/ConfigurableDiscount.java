package main.java.lucia.client.content.order.discount.impl;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.order.discount.Discount;
import org.joda.time.LocalDate;

import java.time.DayOfWeek;
import java.time.MonthDay;
import java.util.List;
import java.util.Set;

/**
 * Represents any discount that can be configured and set up through json
 */
public abstract class ConfigurableDiscount extends Discount {

    /**
     * The days of the week that this is active
     * (if alwaysActive is false)
     */
    private List<DayOfWeek> recurringActiveDays;

    /**
     * If this discount is always active
     */
    private boolean alwaysActive;

    /**
     * If this discount is active right now.
     */
    private boolean activeNow;

    /**
     * Specific days where this is active.
     * (combines with reuccirngActiveDays)
     */
    private List<LocalDate> activeDates;

    /**
     * The items that this discount applies to.
     */
    private Set<Descriptor> applicableItems;



    public ConfigurableDiscount(String name) {
        super(name);
    }


}
