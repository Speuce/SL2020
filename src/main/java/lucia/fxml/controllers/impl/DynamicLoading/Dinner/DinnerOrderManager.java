package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner;

import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;

import java.util.ArrayList;

/**
 * Instance for the dinner order listeners
 *
 * Will be updated to the 'current' item whenever an item is clicked on the GUI
 */
public class DinnerOrderManager {

    /**
     * ADDONS instance
     */
    public ArrayList<AddonDescriptor> addons;

    /**
     * CURRENT ITEM instance
     */
    public Descriptor currentItem;

    private ItemModifiable itemModifiable; // created order item when 'Make' is clicked

    private static DinnerOrderManager dinnerOrderInstance;

    private DinnerOrderManager() {
        addons = new ArrayList<>();
    }

    /**
     * Creates the instance if there is none, gives the instance if there is one
     *
     * @return DinnerOrderManager instance
     */
    public static DinnerOrderManager getDinnerOrderInstance() {
        if(dinnerOrderInstance == null)
            dinnerOrderInstance = new DinnerOrderManager();
        return dinnerOrderInstance;
    }

    /**
     * When 'Make' is clicked, creates the item for the Order
     *
     */
    public ItemModifiable makeItem() {
        ItemModifiableDescriptor itemDescriptor = (ItemModifiableDescriptor)currentItem;
        itemModifiable = itemDescriptor.getAsItem();
        setItemModifiableAddons();
        return itemModifiable;
    }

    /**
     * Adds Addons to the Item if there are any,
     *
     * For purpose if when 'Make' is clicked
     */
    private void setItemModifiableAddons() {
        if(!addons.isEmpty())
          for (AddonDescriptor addon : addons) {
             itemModifiable.addAddon(addon.getAsItem());
           }
    }



}
