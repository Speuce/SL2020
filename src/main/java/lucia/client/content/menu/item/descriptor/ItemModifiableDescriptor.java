package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an item, loaded to the menu,
 * which can contains modifications
 * @author Matt Kwiatkowski
 */
public class ItemModifiableDescriptor extends SimpleItemDescriptor {

    /**
     * The list of addons which can be applied to this item
     */
    private List<AddonDescriptor> appliableAddons;

    public ItemModifiableDescriptor(int id, String name, long price) {
        super(id, name, price);
        appliableAddons = new ArrayList<>();
    }


    /**
     * Gets the list of all of the addons that can be applied to this item
     * @return an List<Addon>
     */
    public List<AddonDescriptor> getAppliableAddons(){
        return this.appliableAddons;
    }

    @Override
    public ItemModifiable getAsItem(){
        return new ItemModifiable(this.getBaseName(), this.getBasePrice(), this);
    }



}
