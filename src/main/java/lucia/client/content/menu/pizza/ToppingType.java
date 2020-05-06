package main.java.lucia.client.content.menu.pizza;

import main.java.lucia.client.content.menu.item.IDAble;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.size.PricingScheme;

import java.awt.*;

/**
 * Represents any single topping that can be applied to a pizza.
 * @author Matt Kwiatkowski
 */
public class ToppingType extends Descriptor {

    /**
     * The full name of this topping
     */
    private String name;

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
        this.name = name;
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

    public String getName() {
        return name;
    }

    public PricingScheme getPricingScheme() {
        return pricingScheme;
    }

    /**
     * get the selected color
     */
    public Color getSelectedColor(){
        return selectedColor;
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
