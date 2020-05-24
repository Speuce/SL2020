package main.java.lucia.client.content.menu.item.type;

import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;

/**
 * An addon, which can be added to any
 * existing item
 *
 * @author Matt Kwiatkowski
 */
public class Addon extends ItemAmountable {

  /**
   * Construct a new item with the given name and price
   * @param name the name of the addon
   * @param price the cost to add this addon to an item
   */
  public Addon(String name, long price, AddonDescriptor d) {
    super(name, price, d);
  }

  /**
   * Construct a new item with the given name and price
   * @param name the name of the addon
   * @param price the cost to add this addon to an item
   * @param amount  the amount of the addon
   */
  public Addon(String name, long price, int amount, AddonDescriptor n) {
    super(name, price, amount, n);
  }

  /**
   * Generates the display name of the item
   */
  @Override
  public String generateDisplayName() {
    return this.getDisplayName();
  }

  /**
   * Calculates the normal price of the item (excluding any discounts)
   */
  @Override
  public long calculatePrice() {
    if(this.getItemDescriptor().isDiscountable() && this.getAmount() == 0){
      return -this.getPrice();
    }else{
      return this.getAmount() * this.getPrice();
    }
  }

  @Override
  public AddonDescriptor getItemDescriptor(){
    return (AddonDescriptor)super.getItemDescriptor();
  }

  /**
   * Copies the item and its' contents
   */
  @Override
  public Addon deepCopy() {
    return new Addon(this.getName(), this.getPrice(), this.getItemDescriptor());
  }



}