package main.java.lucia.client.content.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility Functions for Serialization
 * @author Matthew Kwiatkowski
 */
public class SerializationUtils {

    /**
     * Reads the applied discounts for a given {@link JsonObject}
     * @param o the jsonobject to look in
     * @return the discount set found
     */
    public static Set<AppliedDiscount> getAppliedDiscounts(JsonObject o){
        Set<AppliedDiscount> appliedDiscounts = new HashSet<>();
        AppliedDiscount curr;
        for(JsonElement e: o.getAsJsonArray("appliedDiscounts")){
            curr = ItemGson.ITEM_GSON.fromJson(e, AppliedDiscount.class);
            appliedDiscounts.add(curr);
        }
        return appliedDiscounts;
    }
}
