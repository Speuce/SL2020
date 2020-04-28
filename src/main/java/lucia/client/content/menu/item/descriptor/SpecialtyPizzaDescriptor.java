package main.java.lucia.client.content.menu.item.descriptor;

import com.google.gson.*;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.pizza.*;
import main.java.lucia.client.content.menu.size.Size;
import main.java.lucia.client.content.menu.size.PricingScheme;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Descriptor for a specialty/gourmet pizza, contains
 * pricing info, topping, info, etc
 * @author Matt Kwiatkowski
 */
public class SpecialtyPizzaDescriptor extends SizeableItemDescriptor {

    /**
     * The toppings on this pizza
     */
    private List<Topping> toppings;

    /**
     * The special instructions for this pizza
     * WD, etc
     */
    private List<String> specialInstructions;

    /**
     * The sauce on this pizza
     */
    private Sauce sauce;

    /**
     * The crust of this pizza
     */
    private Crust crust;

    public SpecialtyPizzaDescriptor(int id, String baseName, PricingScheme pricingScheme,
                                    Sauce sauce, Crust crust, List<Topping> toppings, List<String> specialInstructions) {
        super(id, baseName, pricingScheme);
        this.sauce = sauce;
        this.crust = crust;
        this.specialInstructions = specialInstructions;
        this.toppings = toppings;
    }


    /**
     * Get the toppings on this pizza
     */
    public List<Topping> getToppings() {
        return toppings;
    }

    /**
     * Get the special instructions associated with this speecialy.
     * LS, Thin, WD etc.
     */
    public List<String> getSpecialInstructions(){
        return specialInstructions;
    }

    /**
     * Convert this descriptor to an actual pizza
     *
     * @param s the size of the item
     * @return the item
     */
    @Override
    public Pizza getAsItem(Integer s) {
        assert(getPricingScheme().hasPrice(s));
        return new Pizza(s, this);
    }

    public Sauce getSauce() {
        return sauce;
    }

    public Crust getCrust() {
        return crust;
    }

    public boolean hasToppingType(ToppingType type){
        boolean result = false;
        for(Topping top: toppings){
            if(top.getType() == type){
                result = true;
                break;
            }
        }
        return result;
    }

}
