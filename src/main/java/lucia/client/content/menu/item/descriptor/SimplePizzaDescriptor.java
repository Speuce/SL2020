package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.pizza.Pizza;
import main.java.lucia.client.content.menu.size.Size;
import main.java.lucia.client.content.menu.size.PricingScheme;
import org.jetbrains.annotations.NotNull;

/**
 * An implemented descriptor to use for build-your-own
 * pizzas. Contains all of the base prices for pizzas.
 * @author Matt Kwiatkowski
 */
public class SimplePizzaDescriptor extends SizeableItemDescriptor {

    public SimplePizzaDescriptor(int id, String baseName,@NotNull PricingScheme pricingScheme) {
        super(id, baseName, pricingScheme);
    }


    /**
     * Convert this descriptor to an actual item
     *
     * @param s the size of the item
     * @return the item
     */
    @Override
    public Pizza getAsItem(Integer s) {
        return new Pizza(s);
    }
}
