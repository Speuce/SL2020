package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.AbstractItem;
import main.java.lucia.client.content.menu.size.Size;
import main.java.lucia.client.content.menu.size.PricingScheme;
import org.jetbrains.annotations.NotNull;

/**
 * Descriptor for an item whose price varies based on Pizza sizes
 * @author Matt Kwiaktowkski
 */
public abstract class SizeableItemDescriptor extends Descriptor{

    /**
     * The pricing scheme of this item
     */
    @NotNull
    private PricingScheme pricingScheme;

    public SizeableItemDescriptor(int id, String baseName, @NotNull PricingScheme pricingScheme) {
        super(id, baseName);
        this.pricingScheme = pricingScheme;
    }

    public SizeableItemDescriptor(int id, String baseName, String defaultColor,
                                  String selectedColor, String hoverColor, String textColor,
                                  @NotNull PricingScheme pricingScheme) {
        super(id, baseName, defaultColor, selectedColor, hoverColor, textColor);
        this.pricingScheme = pricingScheme;
    }

    /**
     * Get the pricing scheme for the base price of this item
     */
    @NotNull
    public PricingScheme getPricingScheme() {
        return pricingScheme;
    }

    /**
     * Get the price for this item for the given size
     * @param s the size to price for
     * @return the base price of the item
     */
    public long getPrice(int s){
        return pricingScheme.getPrice(s);
    }

    /**
     * Convert this descriptor to an actual item
     * @param s the size of the item
     * @return the item
     */
    public abstract AbstractItem getAsItem(Integer s);


}
