package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.utils.IDAble;
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

    /**
     * The default background color,
     * selected background color,
     * hover background color,
     * and text color,
     * respectively.
     */
    private String defaultColor = "#FFFFFF", selectedColor = "#FFFFFF", hoverColor = "#FFFFFF", textColor = "#000000";

    public Descriptor(int id, String baseName) {
        super(id);
        this.baseName = baseName;
    }

    public Descriptor(int id, String baseName, String defaultColor, String selectedColor, String hoverColor, String textColor) {
        super(id);
        this.baseName = baseName;
        this.defaultColor = defaultColor;
        this.selectedColor = selectedColor;
        this.hoverColor = hoverColor;
        this.textColor = textColor;
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

    /**
     * @return The default background color,
     */
    public String getDefaultColor() {
        return defaultColor;
    }

    /**
     * @param defaultColor The default background color,
     */
    public void setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
    }

    /**
     * @return selected background color
     */
    public String getSelectedColor() {
        return selectedColor;
    }

    /**
     * @param selectedColor the selected background color
     */
    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * @return hover background color
     */
    public String getHoverColor() {
        return hoverColor;
    }

    /**
     * @param hoverColor hover background color
     */
    public void setHoverColor(String hoverColor) {
        this.hoverColor = hoverColor;
    }

    /**
     * @return the text color
     */
    public String getTextColor() {
        return textColor;
    }

    /**
     * @param textColor the text color
     */
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
