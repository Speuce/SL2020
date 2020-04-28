package main.java.lucia.client.content.menu.legacy;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import main.java.lucia.client.content.menu.legacy.impl.Pizza;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.premade.Premade;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadeItem;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadePizza;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;
import main.java.lucia.util.currency.CurrencyConverter;


/**
 * An abstract class which represents a menu item.
 *
 * @author Brett Downey
 * @author zacunrau
 * @author matt
 */
public abstract class Item {

  /**
   * The list of toppings that are to be added to the item
   */
  protected ArrayList<Topping> toppingList;

  /**
   * The list of misc that may be able to be added to this item.
   */
  protected ArrayList<Addon> addonList;

  /**
   * The list of special instructions that are attached to this item
   */
  protected ArrayList<SpecialInstructions> specialInstructions;

  /**
   * The list of alternative items associated to this item, these
   * items can be used instead of the origin item if the secondary HashMap
   * flag has been associated with it
   */
  protected ArrayList<AlternativeItemPackage> alternativeItems;

  /**
   * The name of this item, the item name can change depending on the added/removed toppings, and
   * other special functions.
   */
  protected String name;

  /**
   * The price of this item
   */
  protected long price;

  protected boolean isDiscountEligible = false;

  public boolean isChickenDinner() {
    return isChickenDinner;
  }

  public void setChickenDinner(boolean chickenDinner) {
    isChickenDinner = chickenDinner;
  }

  protected boolean isChickenDinner;

  /**
   * The Date/Time when the item was built
   */
  protected ClientTime builtTime = new ClientTime(TimeFormat.FORMATTER_ISO_STANDARD,
          ClientTime.getWinnipegTimeZone());


  /**
   * The flag which indicates if this item has been discounted
   * once before, if it has then another discount will not be applied to
   * this item
   */
  private boolean discounted;


  /**
   * The constructor for the item class which allows the class to be built utilizing {@link
   * Gson} from a JSON file
   *
   * @param toppingList The list of toppings
   * @param specialInstructions The list of special instructions
   * @param name The name of the item
   * @param price The price of the item
   */
  public Item(ArrayList<Topping> toppingList,
              ArrayList<Addon> addonList, ArrayList<SpecialInstructions> specialInstructions, String name,
              long price) {
    this.toppingList = toppingList;
    this.addonList = addonList;
    this.specialInstructions = specialInstructions;
    this.name = name;
    this.price = price;
  }

  /**
   * A new item that is being created, with the beginning piece of the name.
   *
   * @param name The beginning of the name for the item
   */
  public Item(String name) {
    this.name = name;
    this.toppingList = new ArrayList<>();
    this.addonList = new ArrayList<>();
    this.specialInstructions = new ArrayList<>();
  }

  /**
   * Sets the discounted state to the given state
   *
   * @param state The given state
   */
  public void setDiscounted(boolean state) {
    discounted = state;
  }

  /**
   * Gets the discounted flag
   *
   * @return {@code true} if it has been discounted, {@code} false
   *         otherwise.
   */
  public boolean isDiscounted() {
    return discounted;
  }

  /**
   * Gets the topping list associated to
   * this item
   *
   * @return The topping list
   */
  public ArrayList<Topping> getToppingList() {
    return toppingList;
  }

  /**
   * Gets the list of special instructions associated to this item
   *
   * @return The special instructions
   */
  public ArrayList<SpecialInstructions> getSpecialInstructions() {
    return specialInstructions;
  }

  /**
   * If the name has not yet been calculated, then an automatic
   * calculation will occur.
   */
  public String calcName() {
    generateName();
    return name;
  }

  /**
   * If the price has not yet been calculated, then an
   * automatic calculation will occur
   */
  public long calcPrice() {
    calculatePrice();
    return price;
  }

  /**
   * Sets the price to the given value
   *
   * @param price The given value
   */
  public void setPrice(long price) {
    this.price = price;
  }

  /**
   * Calculates both the name and price
   * and resets this modified attribute
   */
  public NamePrice calcNameAndPrice() {
    discounted = false;
    generateName();
    calculatePrice();
    return new NamePrice(name, price);
  }

  /**
   * Gets the name
   *
   * @return The name
   */
  public String getName() {
    return name;
  }

  public boolean isDiscountEligible() {
    return isDiscountEligible;
  }

  /**
   * Gets the display price of the item.
   * This method should only occur if the price
   * has already been calculated. Note that this
   * method adds tax
   *
   * @return The display price of the item
   */
  public BigDecimal getDisplayPriceTax() {
    Preconditions.checkState(price != 0, new Exception("The price has not been calculated"));
    return CurrencyConverter.taxAndBuild(price);
  }

  /**
   * Gets the display price of the item.
   * This method should only occur if the price
   * has already been calculated
   *
   * @return The display price of the item
   */
  public BigDecimal getDisplayPrice() {
    Preconditions.checkState(price != 0, new Exception("The price has not been calculated"));
    return CurrencyConverter.build(price);
  }

  /**
   * Gets the display price of the item without tax.
   * This method should only occur if the price
   * has already been calculated
   *
   * @return The display price of the item
   */
  public BigDecimal getDisplayPriceNoTax() {
    Preconditions.checkState(price != 0, new Exception("The price has not been calculated"));
    return CurrencyConverter.build(price);
  }

  /**
   * Gets the price of this item before any
   * tax calculations or anything of the sort.
   * If tax calculations are needed, then
   * {@see getDisplayPriceTax} which handles
   * tax calculations before the final price
   * is provided within a safe {@link BigDecimal}
   * form.
   * Note that if the item requires to be generated before
   * hand, {@see calculatePrice} beforehand, or else this
   * method will not function properly and the
   * price returned will be incorrect.
   *
   * @return The price without any tax calculations
   */
  public long getPriceNonCalculated() {
    return price;
  }

  /**
   * Adds a topping to the item and sets
   * the modified flag to {@code true}. This function may not
   * actually add the topping to the list, since there are many
   * factors to consider about the settings of the given topping, such
   * as if the topping is an easy topping, if the topping is a negation topping,
   * etc.
   *
   * @param toAdd The topping to add to the item
   */
  public void addTopping(Topping toAdd) {
    /* Deal with adding negation toppings first */
    if(toAdd.isNegation() && handleAddingNegation(toAdd)) {
      return;
    }

    /* Check for unnecessary additions */
    boolean isRemove = false;
    ArrayList<Topping> toRemove = new ArrayList<>();
    for (Topping currentToppings : toppingList) {
      if (currentToppings.equals(toAdd) && currentToppings.isNegation()) {
        /* Double negation, just return */
        return;
      }

      if(toAdd.equals(currentToppings) && toAdd.isEasy()) {
        /* Double easy of the same topping, just return */
        return;
      }

      /* Check for negation of easy for equal topping names */
      if(toAdd.getName().equals(currentToppings.getName()) && !toAdd.isEasy() && currentToppings.isEasy()) {
        toRemove.add(currentToppings);
        isRemove = true;
      }

      /* (negating, then re-adding) Check for removal of negation toppings */
      if (!toAdd.isNegation() && currentToppings.isNegation() && currentToppings.getName().equals(toAdd.getName())) {
        if(toAdd.isEasy()) {
          /* If the topping we are adding is an easy topping, then replace the negation with easy */
          currentToppings.setEasy(true);
        }
        toRemove.add(currentToppings);
        isRemove = true;
      }
    }
    if(isRemove) {
      /* Do not {@code removeAll} since it may have side effects */
      for(Topping toRemoveTopping : toRemove) {
        /* Check to see if we were trying to re-add an easy topping */
        if(toRemoveTopping.isEasy()) {
          toppingList.add(toRemoveTopping.toppingClone().setNegation(false));
          toRemoveTopping.setEasy(false);
        }
        toppingList.remove(toRemoveTopping);
      }
    } else {
      toppingList.add(toAdd);
    }
  }

  /**
   * This function handles more complicated negations, which is adding a topping then negating it. For a
   * simple {@link Pizza}, this is trivial, but for premade pizzas,
   * we need to keep track of the toppings we are modifying.
   *
   * @param toNegate The topping that we are negating
   * @return {@code true} if the topping was found worthy to be removed from the list automatically,
   * {@code false} if the topping still needs to undergo other checks before a decision has been
   * made.
   */
  private boolean handleAddingNegation(Topping toNegate) {
    if (!(this instanceof PremadePizza || this instanceof PremadeItem)) {
      toppingList.remove(toNegate.setNegation(false));
      return true;

    } else {
      Premade origin = ((Premade) this).getOrigin();

      if(origin.getToppings().contains(toNegate.setNegation(false))) {
        toNegate.setNegation(true);
        return false;
      } else {
        toppingList.remove(toNegate);
        return true;
      }
    }
  }

  /**
   * Removes a topping from the item and
   * sets the modified flag to {@code true}.
   * Note that this removes the entire topping,
   * and if this is an {@code instanceof} {@link PremadePizza}
   * or {@link PremadeItem} then {@see addTopping} with the topping
   * negation flag set to true.
   *
   * @param toRemove The topping to remove from the list
   */
  public void remove(Topping toRemove) {
    toppingList.remove(toRemove);
  }

  /**
   * Adds a special instruction to the item
   *
   * @param toAdd The special instruction to add to the item
   */
  public void addSpecialInstruction(SpecialInstructions toAdd) {
    specialInstructions.add(toAdd);
  }

  /**
   * Sets the name of the item.
   *
   * @param name The name to give the item
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Adds an addon to this order, only if the addon does not already exist.
   *
   * @param toAdd The addon to add
   */
  public void addAddon(Addon toAdd) {
    if (!addonList.contains(toAdd)) {
      addonList.add(toAdd);
    }
  }

  public void removeAddon(Addon remove) {
    if(addonList.contains(remove)) {
      addonList.remove(remove);
    }
  }

  public void clearAddons() {
    if(!addonList.isEmpty())
      addonList.clear();
  }

  public void clearSI() {
    if(!specialInstructions.isEmpty())
      specialInstructions.clear();
  }

  /**
   * Adds an alternative item with the given flag into the
   * alternative items Hash Map. Note that this
   * negates all other alternative items since one
   * alternative item can only be active at a time
   *
   * @param toAdd The item to add to the map
   * @param inUse The flag to associate the item with
   */
  public void addAlternativeItem(Item toAdd, Boolean inUse) {
    negateAllAlternativeItems();
    alternativeItems.add(new AlternativeItemPackage(toAdd, inUse));
  }

  /**
   * Sets the specific alternative item to the given state, if the
   * item is within the list. If the item is not within the list
   * then nothing happens.
   *
   * @param item The item to set the specific state for
   * @param state The state to set it to
   */
  public void setSpecificItemToState(Item item, Boolean state) {
    alternativeItems.forEach(i -> {
      if(i.getAlternativeItem().equals(item)) {
        i.setInUseState(state);
      }
    });
  }

  /**
   * Negates all other alternative items so that
   * every current alternative item that is within the
   * list is not active
   */
  public void negateAllAlternativeItems() {
    alternativeItems.forEach(AlternativeItemPackage::setNotInUse);
  }

  protected long getPriceForAlternatives(ArrayList<Item> subItems) {
    long totalCents = 0;
    /* First check for substitutes */
    for (AlternativeItemPackage i : alternativeItems) {
      if (i.isInUse()) {
        totalCents += i.getAlternativeItem().getPriceNonCalculated();
      }
    }

    /* Then for sub items */
    if(subItems != null) {
      for(Item subItem : subItems) {
        for(AlternativeItemPackage alternativeItem : alternativeItems) {
          if(alternativeItem.isInUse()) {
            totalCents += alternativeItem.getAlternativeItem().getPriceNonCalculated();
          }
        }
      }
    }

    return totalCents;
  }

  /**
   * Gets a safe clone of the given item
   *
   * @return A safe clone of the item
   */
  public abstract Item safeClone();

  /**
   * An abstract method to generate the name of the item, since some items require different name
   * generation than others.
   */
  public abstract void generateName();

  /**
   * An abstract method to generate the price of the item, since some items require different price
   * generation than others.
   */
  public abstract void calculatePrice();
}