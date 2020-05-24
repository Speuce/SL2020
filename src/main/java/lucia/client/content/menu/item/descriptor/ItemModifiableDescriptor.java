package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.structures.Exclude;
import main.java.lucia.client.content.utils.IDCaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * The list of ID:amt pairs of addons that come on this item by default.
     */
    private Map<Integer, Byte> addonsDefault;


    @Exclude
    private List<AddonDescriptor> applicableAddons;

    /**
     * The list of addons that come by default
     */
    @Exclude
    private List<Addon> appliedAddons;


    public ItemModifiableDescriptor(int id, String name, long price) {
        super(id, name, price);
        addons = new ArrayList<>();
        applicableAddons = new ArrayList<>();
        addonsDefault = new HashMap<>();
        appliedAddons = new ArrayList<>();
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
        appliedAddons = new ArrayList<>();
        AddonDescriptor curr;
        for(Map.Entry<Integer, Byte> t: addonsDefault.entrySet()){
            curr = caster.cast(t.getKey());
            if(curr != null){
                appliedAddons.add(curr.getAsItem(t.getValue()));
            }
        }
    }

    /**
     * Adds an appicable addon to this item
     * @param add the Addon to add
     */
    public void addAddon(AddonDescriptor add){
        applicableAddons.add(add);
        addons.add(add.getId());
    }

    public void addDefaultAddon(AddonDescriptor add, byte amt){
        this.appliedAddons.add(add.getAsItem(amt));
        this.addonsDefault.put(add.getId(), amt);
    }

    @Override
    public ItemModifiable getAsItem(){
        return new ItemModifiable(this.getBaseName(), this.getBasePrice(), this);
    }

    /**
     * The list of addons that come by default
     */
    public List<Addon> getAppliedAddons() {
        return appliedAddons;
    }

    public Map<Integer, Byte> getAddonsDefault() {
        return addonsDefault;
    }
}
