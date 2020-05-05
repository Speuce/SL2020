package main.java.lucia.client.content.menu.pizza;

import main.java.lucia.client.content.utils.IDAble;
import main.java.lucia.client.content.menu.size.PricingScheme;

/**
 * Represents a sauce type for a pizza
 * @author Matt Kwiatkoski
 */
public class Sauce extends IDAble {

    /**
     * The display name of this type of sauce without the brackets
     * GS, AS, RS, etc
     */
    private String displayName;

    /**
     * The FULL name of this sauce(Greek Sauce, Alfredo Sauce, etc)
     */
    private String fullName;

    /**
     * The pricing scheme of this type of sauce.
     */
    private PricingScheme pricingScheme;

    public Sauce(int id,String displayName, String fullName, PricingScheme pricing) {
        super(id);
        this.displayName = displayName;
        this.fullName = fullName;
        this.pricingScheme = pricing;
    }

    //default constructor for json
    public Sauce() {
        super(0);
    }

    /**
     * The display name of this type of sauce without the brackets
     * GS, AS, RS, etc
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * The FULL name of this sauce(Greek Sauce, Alfredo Sauce, etc)
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Get the pricing scheme for this type of sauce
     * @return
     */
    public PricingScheme getPricingScheme() {
        return pricingScheme;
    }

}
