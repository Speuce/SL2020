package main.java.lucia.client.content.order.discount.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.order.discount.impl.amount.*;
import main.java.lucia.client.content.order.discount.impl.items.*;
import main.java.lucia.client.content.order.discount.impl.times.*;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.util.gson.RuntimeTypeAdapterFactory;

/**
 * The Gson for {@link main.java.lucia.client.content.order.discount.Discount}
 * @author Matthew Kwiatkowski
 */
public class DiscountGson {

    /**
     * Discount for seializing {@link main.java.lucia.client.content.order.discount.Discount} related items
     */
    public static Gson DISCOUNT_GSON = getDiscountGson();


    private static Gson getDiscountGson(){
        GsonBuilder b = new GsonBuilder();
        b.setPrettyPrinting();
        b.serializeNulls();
        GsonTypeFactory.addExclusionPolicy(b);
        b.registerTypeAdapterFactory(getPriceAdapter());
        b.registerTypeAdapterFactory(getItemAdapter());
        b.registerTypeAdapterFactory(getTimeAdapter());

        return b.create();
    }

    /**
     * Get the adapter for DiscountAmount
     */
    private static RuntimeTypeAdapterFactory<DiscountAmount> getPriceAdapter(){
        return RuntimeTypeAdapterFactory.of(DiscountAmount.class)
                .registerSubtype(PriceOffPerItem.class)
                .registerSubtype(PercentageOff.class)
                .registerSubtype(BundlePrice.class)
                .registerSubtype(BundlePriceToppingAllowance.class)
                .registerSubtype(BundlePriceAddonAllowance.class);

    }

    private static RuntimeTypeAdapterFactory<DiscountApplicable> getItemAdapter(){
        return RuntimeTypeAdapterFactory.of(DiscountApplicable.class)
                .registerSubtype(RequireAND.class)
                .registerSubtype(RequireANY.class)
                .registerSubtype(RequireNumToppings.class)
                .registerSubtype(RequireOR.class)
                .registerSubtype(RequirePizzaSize.class)
                .registerSubtype(RequireSimpleItem.class)
                .registerSubtype(RequireSpecialtyPizza.class);
    }

    private static RuntimeTypeAdapterFactory<DiscountTime> getTimeAdapter(){
        return RuntimeTypeAdapterFactory.of(DiscountTime.class)
                .registerSubtype(TimeDaysPerWeek.class)
                .registerSubtype(TimeEveryDay.class)
                .registerSubtype(TimeHourRange.class)
                .registerSubtype(TimeRange.class)
                .registerSubtype(TimeSpecificDatesAndTimes.class)
                .registerSubtype(TimeSpecifiedDays.class);
    }
}
