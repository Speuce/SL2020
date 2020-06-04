package main.java.lucia.client.content.menu.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.order.discount.Discount;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.utils.Tuple;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstract Item, contains mostly getters
 * and setters for an generic item
 * @author Matt Kwiatkowski
 */
public abstract class AbstractItem{

    /**
     * The specific id of this pizza on the server.
     * DO NOT SET. Let Gson do that.
     */
    private int rowNum = -1;

    /**
     * The display name of this item
     */
    private String displayName;

    /**
     * The actual name of this item, loaded from JSON
     */
    private String name;

    /**
     * The stored calculated price of this item
     */
    private long price;

    /**
     * The price of the item after any discounts have been applied.
     */
    private long discountedPrice;

    /**
     * The information of the item loaded from the menu
     */
    private Descriptor itemDescriptor;

    private final Set<AppliedDiscount> appledDiscounts;


//    @Deprecated
//    public boolean isChickenDinner() {
//        return isChickenDinner;
//    }
//
//    @Deprecated
//    public void setChickenDinner(boolean chickenDinner) {
//        isChickenDinner = chickenDinner;
//    }
//
//    @Deprecated
//    protected boolean isChickenDinner;


    public AbstractItem(String name, long price, Descriptor is) {
        this.name = name;
        this.price = price;
        this.itemDescriptor = is;
        this.discountedPrice = price;
        this.displayName = name;
        this.appledDiscounts = new HashSet<>();
    }

    public AbstractItem(String displayName, String name, long price, long discountedPrice, Descriptor itemDescriptor) {
        this.displayName = displayName;
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.itemDescriptor = itemDescriptor;
        this.appledDiscounts = new HashSet<>();
    }

    public AbstractItem(String name, Descriptor is) {
        this.name = name;
        this.price = -1;
        this.itemDescriptor = is;
        this.discountedPrice = -1;
        this.displayName = name;
        this.appledDiscounts = new HashSet<>();
    }

    public AbstractItem(int rowNum, String displayName, String name, long price, long discountedPrice, Descriptor itemDescriptor, Set<AppliedDiscount> appledDiscounts) {
        this.rowNum = rowNum;
        this.displayName = displayName;
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.itemDescriptor = itemDescriptor;
        this.appledDiscounts = appledDiscounts;
    }

    /**
     * Generates the display name of the item
     */
    public abstract String generateDisplayName();

    /**
     * Calculates the normal price of the item (excluding any discounts)
     */
    protected abstract long calculatePrice();

    /**
     * Copies the item and its' contents
     */
    public abstract AbstractItem deepCopy();

    /**
     * Calculates both the name and price
     * Resets any discounts applied.
     * WARNING: discounts will be overriden
     */
    public Tuple<String, Long> calcNameAndPrice() {
        this.displayName = generateDisplayName();
        this.price = calculatePrice();
        this.discountedPrice = this.price;
        return new Tuple<>(this.getName(), this.getPrice());
    }

    /**
     * Recalculates the undiscounted price of this item.
     * Also resets any applied discounts.
     */
    public void recalculatePrice(){
        this.price = calculatePrice();
        this.discountedPrice = this.price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        if(price == -1){
            recalculatePrice();
        }
        return price;
    }

    public long getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setDiscountedPrice(long discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    /**
     * Get the associated descriptor for this item
     * @return a {@link Descriptor} for this item
     */
    public Descriptor getItemDescriptor(){return this.itemDescriptor;}

    /**
     * Set the descriptor for this item
     * @param d the descriptor that will now be associated with this item
     */
    protected void setItemDescriptor(Descriptor d){
        this.itemDescriptor = d;
    }

    /**
     * Adds the properties of this AbstractItem object
     *  to the given JsonObject,
     *  meant to be used for serialization
     * @param o the JsonObject for which the properties will be added to.
     */
    public void addJsonProperties(JsonObject o){
        o.addProperty("rowNum", rowNum);
        o.addProperty("name", this.name);
        o.addProperty("displayName", this.displayName);
        o.addProperty("price", this.price);
        o.addProperty("discountedPrice", this.discountedPrice);
        o.addProperty("itemDescriptor", (itemDescriptor != null) ? itemDescriptor.getId() : 0);

        //add applied discounts
        JsonArray discounts = new JsonArray();
        appledDiscounts.forEach(d -> discounts.add(d.getId()));
        o.add("appliedDiscounts", discounts);


    }

    public Set<AppliedDiscount> getAppledDiscounts() {
        return appledDiscounts;
    }
}
