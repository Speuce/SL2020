package main.java.lucia.client.content.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import main.java.lucia.client.content.order.discount.Discount;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility Functions for Serialization
 * @author Matthew Kwiatkowski
 */
public class SerializationUtils {

    /**
     * Reads the applied discounts for a given {@link JsonObject} representation of an item
     * @param o the jsonobject to look in
     * @return the discount set found
     */
    public static Set<Discount> getAppliedDiscounts(JsonObject o){
        Set<Discount> appliedDiscounts = new HashSet<>();
        for(JsonElement e: o.getAsJsonArray("appliedDiscounts")){
            appliedDiscounts.add(new IDCaster<Discount>().cast(e.getAsInt()));
        }
        return appliedDiscounts;
    }
}
