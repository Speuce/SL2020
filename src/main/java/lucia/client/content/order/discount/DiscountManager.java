package main.java.lucia.client.content.order.discount;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import main.java.lucia.Client;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.io.DiscountGson;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all discount objects and interactions
 * @author Matthew Kwiatkowski
 */
public class DiscountManager {

    /**
     * The list of all loaded custom discounts.
     */
    private static final List<CustomDiscount> loadedCustomDiscounts = new ArrayList<CustomDiscount>();

    /**
     * Initializes this static class.
     * Loads in discount definitions.
     * @param discountDef the file containing the defined discounts.
     */
    public static void initialize(File discountDef){
        if(discountDef == null){
            return;
        }
        JsonParser parser = new JsonParser();
        FileReader r;
        try {
            r = new FileReader(discountDef);
            JsonArray arr = parser.parse(r).getAsJsonArray();
            loadedCustomDiscounts.clear();

            for(JsonElement e: arr){
                loadedCustomDiscounts.add(DiscountGson.DISCOUNT_GSON.fromJson(e, CustomDiscount.class));
            }

            Client.logger.info("Successfully loaded: " + loadedCustomDiscounts.size() + " custom discounts!");

            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Saves the loaded discounts into the given file
     * @param discountDef the file to save into
     */
    public static void save(File discountDef){
        JsonArray arr = new JsonArray();
        for(CustomDiscount d: loadedCustomDiscounts){
            arr.add(DiscountGson.DISCOUNT_GSON.toJsonTree(d, CustomDiscount.class));
        }
        try {
            FileWriter r = new FileWriter(discountDef);
            GsonTypeFactory.BASIC_GSON.toJson(arr, r);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The list of all loaded custom discounts.
     */
    public static List<CustomDiscount> getLoadedCustomDiscounts(){
        return loadedCustomDiscounts;
    }

}
