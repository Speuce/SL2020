package main.java.lucia.client.content.menu.item.type;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;

/**
 * Represents an item with an amount
 * i.e a topping or addon
 * @author Matt Kwiatkowski
 */
public abstract class ItemAmountable extends Item {

    /**
     * The "amount" of this addon
     * 0 = No (a negation)
     * 1 = Light
     * 2 = Regular amount
     * 3 = Xtra
     * 4 = XXTra
     */
    private int amount = 2;

    public ItemAmountable(String name, long price, int amount, Descriptor n) {
        super(name, price, n);
        this.amount = amount;
    }

    public ItemAmountable(String name, int amount, Descriptor n) {
        super(name, n);
        this.amount = amount;
    }

    public ItemAmountable(String name, long price, Descriptor n) {
        super(name, price, n);
    }

    public ItemAmountable(String name,Descriptor n) {
        super(name, n);
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
     * Get the "amount" of this addon
     * 0 = No (a negation)
     * 1 = Regular amount
     * 2 = Xtra
     * 3 = XXTra
     */
    public int getAmount(){
        return this.amount;
    }

    /**
     * Set the "amount" of this addon
     * 0 = No (a negation)
     * 1 = Regular amount
     * 2 = Xtra
     * 3 = XXTra
     */
    public void setAmount(int amount){
        this.amount = amount;
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

}
