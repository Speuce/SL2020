package main.java.lucia.client.content.menu.item.type.pizza;

import main.java.lucia.client.content.utils.IDAble;
import main.java.lucia.client.content.menu.size.PricingScheme;

/**
 * Represents a crust which a pizza is built upon.
 * @author Matt Kwiatkowski
 */
public class Crust extends IDAble {
    /**
     * The display name of this type of crust without the brackets
     * (GF, WW, etc)
     */
    private String displayName;

    /**
     * The FULL name of this crust (Gluten Free, Whole Wheat, etc)
     */
    private String fullName;

    /**
     * The pricing scheme of this type of crust.
     */
    private PricingScheme pricingScheme;

    /**
     * Construct a new crust type
     * @param displayName the display (WW, GF) name of the item
     * @param fullName the full (Whole Wheat, Gluten Free) name of the item
     */
    public Crust(int id, String displayName, String fullName, PricingScheme pricing) {
        super(id);
        this.displayName = displayName;
        this.fullName = fullName;
        this.pricingScheme = pricing;
        //this.pricingScheme = new PricingScheme();
    }

    //default constructor for json
    public Crust(){
        super(0);
    }

    /**
     * The display name of this type of crust without the brackets
     * GS, AS, RS, etc
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * The FULL name of this crust (Gluten Free, Whole Wheat, etc)
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Get the pricing scheme for this type of crust
     * @return
     */
    public PricingScheme getPricingScheme() {
        return pricingScheme;
    }


}
