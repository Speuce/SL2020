package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.type.pizza.Topping;
import main.java.lucia.client.content.menu.size.PricingScheme;

/**
 * Represents any single topping that can be applied to a pizza.
 * @author Matt Kwiatkowski
 */
public class ToppingType extends Descriptor {


    /**
     * The short (display) name of this topping
     */
    private String shortName;

    /**
     * The pricing scheme of this item
     */
    private PricingScheme pricingScheme;

    public ToppingType(int id, String name,String shortName, PricingScheme pricingScheme) {
        super(id, name);
        this.shortName = shortName;
        this.pricingScheme = pricingScheme;
    }

    public ToppingType(int id, String baseName, String defaultColor, String selectedColor, String hoverColor, String textColor, String shortName, PricingScheme pricingScheme) {
        super(id, baseName, defaultColor, selectedColor, hoverColor, textColor);
        this.shortName = shortName;
        this.pricingScheme = pricingScheme;
    }

    /**
     * Get the short (abbreviated) name of this topping
     */
    public String getShortName() {
        return shortName;
    }

    @Deprecated
    public String getName() {
        return super.getBaseName();
    }

    public PricingScheme getPricingScheme() {
        return pricingScheme;
    }



    /**
     * Serializes the item into readable json format
     * @return this item, in Json
     */
    public String toJson(){
        return "";
    }

    /**
     * The amount of this topping,
     * 0 if it is a negation
     * 1 if it is EZ
     * 2 is regular
     * 3 is Xtra
     * 4 is XXtra
     */
    public Topping toTopping(int amt){
        return new Topping(this, amt);
    }


}
