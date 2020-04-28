package main.java.lucia.client.content.menu.size;

import com.google.gson.*;
import main.java.lucia.client.content.files.JsonSerializable;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.Menu;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Represents a pricing scheme for every pizza Integer
 * @author Matthew Kwiatkowski
 */
public class PricingScheme {

    /**
     * A table mapping various Integers to prices for this type of topping
     */
    private Map<Integer, Long> priceMap;

    /**
     * The defined name of this pricing scheme.
     */
    private String name;

    /**
     * Constructor for a new pricing scheme, with no defined pricing assosciations.
     * @param name the name of this pricing scheme.
     */
    public PricingScheme(String name){
        this.priceMap = new HashMap<Integer, Long>();
        this.name = name;
    }


    /**
     * Get the price of this topping for a given Integer
     * @param s The size that you would like to get the price for
     * @return a long, representing the price (in cents) of this topping for the given Integer
     *          (returns 0 if this pricing scheme is not defined for the given Integer)
     */
    public Long getPrice(Integer s){
        if(priceMap.containsKey(s)){
            return priceMap.get(s);
        }
        return 0L;
    }

    /**
     * Define an addtional price for this pricing scheme
     * @param s the Integer which to add
     * @param price the price of this item for the given Integer
     */
    public void addPrice(Integer s, long price){
        priceMap.put(s, price);
    }

    /**
     * Indicates whether or not this pricing scheme has a defined price for a given Integer
     * @param s the Integer to check for
     * @return {@code true} if the pricing is defined for the given Integer, false otherwise.
     */
    public boolean hasPrice(Integer s){
        return priceMap.containsKey(s);
    }

    /**
     * Get the defined name of this pricing scheme.
     */
    public String getName() {
        return name;
    }


    /**
     * Get the map of prices, where the size is the key
     * and the associated price is the value.
     */
    public Map<Integer, Long> getPriceMap() {
        return priceMap;
    }
}
