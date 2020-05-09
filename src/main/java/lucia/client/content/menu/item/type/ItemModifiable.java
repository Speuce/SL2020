package main.java.lucia.client.content.menu.item.type;


import main.java.lucia.client.content.menu.item.AbstractItem;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Represents an Item that can have modifications done on it,
 * pasta salad, etc.
 * Substitutions and addons are considered modifications
 * @author Matt Kwiatkowski
 */
public class ItemModifiable extends Item {

    /**
     * The applied addons to this item
     */
    private ArrayList<Addon> addonList;

    /**
     * Construct a new Modifiable item with the given parameters
     * @param name the name of the item
     * @param price the base price of the item
     */
    public ItemModifiable(String name, long price, ItemModifiableDescriptor parent) {
        super(name, price, parent);
        addonList = new ArrayList<Addon>();
        for(Addon add: parent.getAppliedAddons()){
            addonList.add(add.deepCopy());
        }
    }

    /**
     * Generates the display name of the item
     */
    @Override
    public String generateDisplayName() {
        StringJoiner base = new StringJoiner(" ", "", this.getItemDescriptor().getBaseName());
        for(Addon a: addonList){
            base.add(a.generateShortName());
        }
        return base.toString();
    }

    /**
     * Calculates the normal price of the item (excluding any discounts)
     */
    @Override
    public long calculatePrice() {
        long base = this.getItemDescriptor().getBasePrice();
        //add addon pricing
        for(Addon a: addonList){
            base += a.calculatePrice();
        }
        return base;
    }

    /**
     * Copies the item and its' contents
     */
    @Override
    public AbstractItem deepCopy() {
        return null;
    }

    /**
     * Get the currently applied addons to this item
     * @return an arraylist containing all of the applied addons
     */
    public List<Addon> getAddons(){
        return addonList;
    }

    public boolean hasAddon(Addon a){
        return addonList.contains(a);
    }

    /**
     * Add an addon to this item
     * @param a the Addon to add
     * @return this instance (for chaining commands)
     */
    public ItemModifiable addAddon(Addon a){
        addonList.add(a);
        return this;
    }

    /**
     * Add an addon to this item
     * @param a the Addon to add
     * @return this instance (for chaining commands)
     */
    public ItemModifiable removeAddon(Addon a){
        addonList.remove(a);
        return this;
    }

    @Override
    public ItemModifiableDescriptor getItemDescriptor(){
        return (ItemModifiableDescriptor)super.getItemDescriptor();
    }


}
