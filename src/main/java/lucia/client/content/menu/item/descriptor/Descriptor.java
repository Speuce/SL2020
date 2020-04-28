package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.IDAble;
import main.java.lucia.util.gson.RuntimeTypeAdapterFactory;

/**
 * Describes any item
 * @author Matt Kwiatkowski
 */
public abstract class Descriptor extends IDAble {
    /**
     * The name of this item
     */
    private String baseName;

    public Descriptor(int id, String baseName) {
        super(id);
        this.baseName = baseName;
    }

    /**
     * Get the basic, unmodified, un-added-to name of the item
     */
    public String getBaseName() {
        return baseName;
    }

    private static RuntimeTypeAdapterFactory<Descriptor> descriptorRuntimeTypeAdapterFactory;

    public static RuntimeTypeAdapterFactory<Descriptor> getDescriptorAdapterFactory() {
        if (descriptorRuntimeTypeAdapterFactory == null) {
            descriptorRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Descriptor.class)
                    .registerSubtype(SimpleItemDescriptor.class, "SimpleItem")
                    .registerSubtype(ItemModifiableDescriptor.class, "ModifiableItem")
                    .registerSubtype(SizeableItemDescriptor.class, "SizeableItem")
                    .registerSubtype(SimplePizzaDescriptor.class, "SimplePizza")
                    .registerSubtype(SpecialtyPizzaDescriptor.class, "SpecialtyPizza")
                    .registerSubtype(AddonDescriptor.class, "Addon");
        }
        return descriptorRuntimeTypeAdapterFactory;
    }
}
