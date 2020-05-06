package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.structures.Exclude;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an item, loaded to the menu,
 * which can contains modifications
 * @author Matt Kwiatkowski
 */
public class ItemModifiableDescriptor extends SimpleItemDescriptor {

    /**
     * The list of addons which can be applied to this item (by id)
     */
    private List<Integer> addons;

    @Exclude
    private List<AddonDescriptor> applicableAddons;


    public ItemModifiableDescriptor(int id, String name, long price) {
        super(id, name, price);
        addons = new ArrayList<>();
        applicableAddons = new ArrayList<>();
    }

    public ItemModifiableDescriptor(int id, String baseName, String defaultColor, String selectedColor, String hoverColor, String textColor, long price) {
        super(id, baseName, defaultColor, selectedColor, hoverColor, textColor, price);
    }

    /**
     * Gets the list of all of the addons that can be applied to this item
     * @return an List<Addon>
     */
    public List<AddonDescriptor> getAppliableAddons(){
        return this.applicableAddons;
    }

    /**
     * Uses an IDCaster to fill the id mappings of the applicable addons
     */
    public void fillMappings(){
        IDCaster<AddonDescriptor> caster = new IDCaster<>();
        applicableAddons = addons.stream().map(caster::cast).collect(Collectors.toList());
    }

    /**
     * Adds an appicable addon to this item
     * @param add the Addon to add
     */
    public void addAddon(AddonDescriptor add){
        applicableAddons.add(add);
        addons.add(add.getId());
    }

    @Override
    public ItemModifiable getAsItem(){
        return new ItemModifiable(this.getBaseName(), this.getBasePrice(), this);
    }



}
