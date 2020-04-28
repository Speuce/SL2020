package main.java.lucia.client.content.menu.pizza;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import main.java.lucia.client.content.files.JsonSerializable;
import main.java.lucia.client.content.menu.size.Size;

public class Topping implements JsonSerializable {

    /**
     * The kind of topping that this topping is.
     * Eg, Pepperoni, Pineapple, etc.
     */
    private ToppingType type;

    /**
     * The amount of this topping,
     * 0 if it is a negation
     * 1 if it is EZ
     * 2 is regular
     * 3 is Xtra
     * 4 is XXtra
     */
    private int amount;

    public Topping(ToppingType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    //Default constructor for json
    public Topping(){}

    /**
     * Get the type of this topping
     * @return the assosciated {@link ToppingType}
     */
    public ToppingType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Calculates the normal price of the item (excluding any discounts)
     */
    public long calculatePrice(Integer size) {
        if(this.getAmount() == 0){
            return -this.getPrice(size);
        }else{
            return this.getAmount() * this.getPrice(size);
        }
    }

    /**
     * Get the normal price of the topping
     * @param size the given size for which the price will be gotten
     * @return the price for a SINGLE amount of this topping, at the given size.
     */
    public long getPrice(Integer size){
        return this.type.getPricingScheme().getPrice(size);
    }

    /**
     * Copies the item and its' contents
     */
    public Topping deepCopy() {
        return new Topping(type, amount);
    }

    /**
     * Generate and get the name of this addon when added to an item
     * i.e +XXn, -n, etc
     */
    public String generateShortName() {
        if(this.getAmount() == 1){
            return "+"+this.getName();
        }else if(this.getAmount() > 1){
            if(this.getAmount() == 2){
                return "+XX"+this.getName();
            }else{
                return "+X"+this.getName();
            }
        }else{
            return "-"+this.getName();
        }
    }

    /**
     * Get the non-amount-accounted-for topping name
     */
    public String getName(){
        return type.getName();
    }

    /**
     * Check whether or not this addon is a negation ('NO nn')
     * @return true if this addon is a negation, false otherwise
     */
    public boolean isNegation(){
        return this.amount == 0;
    }

    /**
     * Check whether or not this addon is extra ('+Xnn')
     * @return true if this addon is extra, false otherwise
     */
    public boolean isExtra(){
        return this.amount == 2;
    }

    /**
     * Check whether or not this addon is extraextra ('+XXnn')
     * @return true if this addon is extraextra, false otherwise
     */
    public boolean isXExtra(){
        return this.amount == 3;
    }

    /**
     * Call this to set this to a negation (-nn)
     */
    public void setNegation(){
        this.amount = 0;
    }

    /**
     * Call this to set this to a Regular (+nn)
     * NOTE: an addon is regular by defaut
     */
    public void setRegular(){
        this.amount = 1;
    }

    /**
     * Call this to set this to an Extra addon (+Xnn)
     */
    public void setExtra(){
        this.amount = 2;
    }

    /**
     * Call this to set this to an ExtraExtra addon (+XXnn)
     */
    public void setXExtra(){
        this.amount = 3;
    }

    /**
     * Get and make the serializer for this type of item.
     *
     * @return the assosciated {@link JsonSerializer}
     */
    @Override
    public JsonSerializer getJsonSerializer() {
        //TODO
        return null;
    }

    /**
     * Create and get the Deserializer for this type of item.
     *
     * @return the associated {@link JsonDeserializer}
     */
    @Override
    public JsonDeserializer getJsonDeserializer() {
        //TODO
        return null;
    }
}
