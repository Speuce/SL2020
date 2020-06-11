package main.java.lucia.client.content.menu.item.descriptor;

import java.io.PrintStream;

public class ItemBundleDescriptor extends Descriptor {

    /**
     * The base price of this bundle
     */
    private long basePrice;

    /**
     * Create a new bundle descriptor with a specific name
     * @param baseName the name of the bundle
     * @param basePrice the base price of this bundle
     */
    public ItemBundleDescriptor(String baseName, long basePrice) {
        //id irrelevant in bundles.
        super(-1, baseName);
        this.basePrice = basePrice;
    }

    /**
     * The base price of this bundle
     */
    public long getBasePrice() {
        return basePrice;
    }

    public void print(PrintStream out){
        out.print("Name: " + getBaseName() + " Price: " + basePrice);
    }
}
