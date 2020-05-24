package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.type.pizza.*;
import main.java.lucia.client.content.menu.size.PricingScheme;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Descriptor for a specialty/gourmet pizza, contains
 * pricing info, topping, info, etc
 * @author Matt Kwiatkowski
 */
public class SpecialtyPizzaDescriptor extends SizeableItemDescriptor {

    /**
     * The toppings on this pizza
     */
    private Map<ToppingType, Integer> toppings;

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

    public SpecialtyPizzaDescriptor(int id, String baseName,@NotNull PricingScheme pricingScheme,
                                    Sauce sauce, Crust crust, Map<ToppingType, Integer> toppings, List<String> specialInstructions) {
        super(id, baseName, pricingScheme);
        this.sauce = sauce;
        this.crust = crust;
        this.specialInstructions = specialInstructions;
        this.toppings = toppings;
    }

    public SpecialtyPizzaDescriptor(int id, String baseName, String defaultColor,
                                    String selectedColor, String hoverColor, String textColor,
                                    @NotNull PricingScheme pricingScheme, Map<ToppingType, Integer> toppings,
                                    List<String> specialInstructions, Sauce sauce, Crust crust) {
        super(id, baseName, defaultColor, selectedColor, hoverColor, textColor, pricingScheme);
        this.toppings = toppings;
        this.specialInstructions = specialInstructions;
        this.sauce = sauce;
        this.crust = crust;
    }

    /**
     * Get the toppings on this pizza
     */
    public Map<ToppingType, Integer> getToppings() {
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
        return toppings.containsKey(type);
    }


}
