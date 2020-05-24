package main.java.lucia.consts.FoodConstants.Dinner;

import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

/**
 * Constants for the Sides part of the Dinners
 *
 * Dinners | 'Sides'
 */
public class DinnerSidesConstants extends FoodConstants {
    private ItemModifiableDescriptor item;
    private List<AddonDescriptor> sides;

    /**
     *  Sets the constant values for the Dinner Items
     */
    public DinnerSidesConstants(ItemModifiableDescriptor item) {
        this.item = item;
        sides = createLists(item);
        setInitX(30);
        setInitY(25);
        setxMargin(200);
        setyMargin(110);
        setSizeX(180);
        setSizeY(93);
        setMaxX(629);
        setMaxY(675);
        // y value does not cover the entire height, leaves room for the 'make' button
    }

    /**
     *  Creates the lists from the json system
     *
     *  Based off what addons are available from the item
     */
    public List<AddonDescriptor> createLists(ItemModifiableDescriptor item) {
        return item.getAppliableAddons();
    }

    /**
     *  ACCESSORS for the dinner sides list
     */
    public List<AddonDescriptor> getDinnerSides() {
        return sides;
    }
}
