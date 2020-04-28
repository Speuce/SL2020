package main.java.lucia.client.content.menu.legacy.premade.impl;

import java.util.ArrayList;

import main.java.lucia.Client;
import main.java.lucia.client.content.menu.legacy.AlternativeItemPackage;
import main.java.lucia.client.content.menu.legacy.Item;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.NameBuilder;
import main.java.lucia.client.content.menu.legacy.impl.Italian;
import main.java.lucia.client.content.menu.legacy.impl.Salad;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.legacy.premade.Premade;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAddonNames;
import main.java.lucia.client.content.menu.legacy.size.Size;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;

import javax.print.attribute.standard.Sides;

public abstract class PremadeItem extends Item implements Premade {

  /**
   * Items associated with this pre made item that which are sub items.
   */
  protected ArrayList<Item> subItems;

  /**
   * The origin name of this premade item
   */
  protected transient String ORIGIN_NAME;

  /**
   * The constructor for the item class which allows the class to be built utilizing {@link
   * com.google.gson.Gson} from a JSON file
   *
   * @param toppingList The list of toppings
   * @param specialInstructions The list of special instructions
   * @param name The name of the item
   * @param price The price of the item
   */
  public PremadeItem(ArrayList<Topping> toppingList,
      ArrayList<Addon> addonList,
      ArrayList<SpecialInstructions> specialInstructions,
      String name, long price) {
    super(toppingList, addonList, specialInstructions, name, price);
    ORIGIN_NAME = name;
  }

  /**
   * A new item that is being created, with the beginning piece of the name.
   *
   * @param name The beginning of the name for the item
   */
  public PremadeItem(String name) {
    super(name);
    this.subItems = new ArrayList<>();
    ORIGIN_NAME = name;
  }

  /**
   * Adds a sub item to this premade item
   *
   * @param item A sub item to add
   */
  public void addSubItem(Item item) {
    subItems.add(item);
  }

  /**
   * Gets the list of sub items
   *
   * @return Gets the sub items
   */
  public ArrayList<Item> getSubItems() {
    return subItems;
  }

  @Override
  public void generateName() {
    if (ORIGIN_NAME == null) {
      ORIGIN_NAME = name.replace(" ", "_");
    }
      try {
        NameBuilder builder = new NameBuilder();
        PremadeItem origin = Menu.loadedPreMadeFoods.getPremadeItem(ORIGIN_NAME);
        ArrayList<SpecialInstructions> crustInstructions = new ArrayList<>();
        ArrayList<SpecialInstructions> toppingInstructions = new ArrayList<>();
        ArrayList<SpecialInstructions> sauceInstructions = new ArrayList<>();

        prepareArraySort(origin, toppingInstructions, sauceInstructions);
        setInitialValues(origin, builder, crustInstructions, sauceInstructions);
        finalValues(origin, builder, toppingInstructions);
        applySpecialInstructions(builder);
        applyAddons(builder);
        name = builder.getString().trim();
        System.out.println(name);

      } catch (Exception e) {
        Client.logger
            .error("An error occurred while generating the name for: " + ORIGIN_NAME, e);
      }
  }

  private void applyAddons(NameBuilder builder) {
    builder.addAddons(addonList);
  }

  private void applySpecialInstructions(NameBuilder builder) {
    for(int x = 0; x < specialInstructions.size(); x++) {
      builder.putSITopping(specialInstructions.get(x));
      System.out.println("SPECIAL INST. : " + specialInstructions.get(x).getName());
    }
  }

  /**
   * Sorts the one array into three smaller more specializes arrays in order for us to handle the
   * specific instructions to format it nicely
   *
   * @param origin The origin premade item
   * @param toppingInstructions The topping instructions array
   * @param sauceInstructions The sauce instructions array
   */
  private void prepareArraySort(PremadeItem origin,
      ArrayList<SpecialInstructions> toppingInstructions,
      ArrayList<SpecialInstructions> sauceInstructions) {
    for (SpecialInstructions s : specialInstructions) {
      if (origin.specialInstructions.contains(s)) {
        continue;
      }

      if (s.inToppingArea()) {
        toppingInstructions.add(s);
      } else {
        sauceInstructions.add(s);
      }
    }
  }

  protected abstract void setInitialValues(PremadeItem origin, NameBuilder builder,
      ArrayList<SpecialInstructions> crustInstructions,
      ArrayList<SpecialInstructions> sauceInstructions);

  /**
   * Applies the final values such as the toppings information and the topping instruction
   * information to the name builder
   *
   * @param origin The origin premade pizza
   * @param builder The builder we are adding onto
   * @param toppingInstructions The topping instructions we need to add
   */
  private void finalValues(PremadeItem origin, NameBuilder builder,
      ArrayList<SpecialInstructions> toppingInstructions) {
    ArrayList<Topping> nonCollision = new ArrayList<>(toppingList);
    filterAndPut(builder, toppingInstructions, nonCollision, origin.toppingList);
    for (Topping t : nonCollision) {
      if (t.isNegation()) {
        builder.beginToppingNegation();
        builder.putTopping(t);
      }
    }
  }

  static void filterAndPut(NameBuilder builder,
      ArrayList<SpecialInstructions> toppingInstructions, ArrayList<Topping> nonCollision,
      ArrayList<Topping> originToppings) {

    for (Topping toRemove : originToppings) {
      nonCollision.remove(toRemove);
    }

    for (Topping t : nonCollision) {
      if (!t.isNegation() && !t.isEasy()) {
        builder.beginToppingAddition();
        builder.putTopping(t);
      }
    }

    for (Topping t : nonCollision) {
      if (!t.isNegation() && t.isEasy()) {
        builder.beginToppingAddition();
        builder.putEasyTopping(t);
      }
    }

    for (SpecialInstructions s : toppingInstructions) {
      builder.beginToppingAddition();
      builder.putSITopping(s);
    }
  }


  /**
   * Calculates the price of the premade item
   */
  @Override
  public void calculatePrice() {
    try {
      PremadeItem origin = Menu.loadedPreMadeFoods.getPremadeItem(ORIGIN_NAME);
      long totalCents = origin.getPriceNonCalculated();
      totalCents += getPriceForAlternatives(subItems);
      totalCents += calculateMiscPrices(origin);
      totalCents += calculateXCheese(origin);
      totalCents += calculateAddons(origin);
      price = totalCents;
    } catch (Exception e) {
      Client.logger.error("An error occurred while generating the price for: " + ORIGIN_NAME, e);
    }
  }

  /**
   * Calculates the additional price required if extra or extra cheese was added onto the pizza
   *
   * @param origin The origin premade pizza
   * @return The calculated value
   */
  private long calculateXCheese(PremadeItem origin) {
    long totalCents = 0;
    long mozzaPrice = Menu.loadedToppings.getMiscPrices().getPastaCheese();
    for (SpecialInstructions current : specialInstructions) {
      if (!origin.getSpecialInstructions().contains(current)) {
        if (current == SpecialInstructions.EXTRA_CHEESE) {
          totalCents += mozzaPrice;
        } else if (current == SpecialInstructions.EXTRA_EXTRA_CHEESE) {
          totalCents += (mozzaPrice * 2);
        }
      }
    }
    return totalCents;
  }

  /**
   * Calculates the misc prices, such as if this pizza needs to add additional charge because of
   * greek sauce, a keto selection, or another additional price
   *
   * @param origin The origin premade pizza
   * @return The calculated value
   */
  protected abstract long calculateMiscPrices(PremadeItem origin);

  /**
   * Calculates the additional added toppings, since some toppings were already on the pizza since
   * this is a premade pizza, then we need to determine which toppings were actually added and which
   * toppings did not exist before. Toppings that are flagged as a negative topping does not reduce
   * the price.
   *
   * @param origin The origin premade pizza
   * @return The calculated value
   */
  private long calculateToppingsAddons(PremadeItem origin) {
    ArrayList<Topping> nonCollision = new ArrayList<>(toppingList);
    ArrayList<Addon> nonCollisionAddon = new ArrayList<>(addonList);
    long totalCents = toppingsAddonsSort(origin, toppingList, nonCollision, nonCollisionAddon);

    for (Topping filtered : nonCollision) {
      if (!filtered.isNegation()) {
        if (filtered.isEasy() && origin.toppingList.contains(filtered.toppingClone().setEasy(false))) {
          /* Don't add the price of an easy topping if it already exists within the list */
          continue;
        }

        /* Mushroom is the only supported topping, but if another topping is added treat like 15" // TODO FOR NOW */

        switch(filtered.getName().toUpperCase()) {
          case "MUSHROOM":
            if(this instanceof Italian) {
              totalCents += Menu.loadedToppings.getMiscPrices().getPastaMushroom();
            }
            break;
          case "SHRIMP":
            if(this instanceof Italian) {
              totalCents += Menu.loadedToppings.getMiscPrices().getPastaShrimp();
            }
            break;
          case "CHICKEN":
            if(this instanceof Italian) {
              totalCents += Menu.loadedToppings.getMiscPrices().getPastaChicken();
            } else if (this instanceof Salad) {
              totalCents += Menu.loadedToppings.getMiscPrices().getSaladChicken();
            }
            break;
          default:
            totalCents += filtered.getPrice(Size.FIFTEEN); // Unknown topping added, treat as 15"
            Client.logger.error("Warning, an unknown topping has been added to a " + this.getClass() + " for " + ORIGIN_NAME + " topping name = " + filtered.getName());
        }
      }
    }

    return totalCents;
  }

  public void addAddon(Addon toAdd) {
    if (!addonList.contains(toAdd)) {
      addonList.add(toAdd);
    }
  }

  private long calculateAddons(PremadeItem origin) {
    ArrayList<Addon> addons = addonList;
    long totalCents = 0;
    for(int x = 0; x < addons.size(); x++) {
      switch (addons.get(x).getName().toUpperCase()) {
        case "MUSHROOM":
          if (this instanceof Italian) {
            totalCents += Menu.loadedToppings.getMiscPrices().getPastaMushroom();
          }
          break;
        case "SHRIMP":
          if (this instanceof Italian) {
            totalCents += Menu.loadedToppings.getMiscPrices().getPastaShrimp();
          }
          break;
        case "CHEESE TOAST":
          if(this instanceof Salad) {
            totalCents += Menu.loadedPreMadeFoods.getAddons().get(PremadeAddonNames.CHEESE_TOAST_UPGRADE).getPriceNonCalculated();
          }
          break;
        case "CHICKEN":
          if (this instanceof Italian) {
            totalCents += Menu.loadedPreMadeFoods.getAddons().get(PremadeAddonNames.CHICKEN_PASTA).getPriceNonCalculated();
          } else if (this instanceof Salad) {
            totalCents += Menu.loadedToppings.getMiscPrices().getSaladChicken();
          }
          break;
        default:
          Client.logger.error("Warning, an unknown topping has been added to a " + this.getClass() + " for " + ORIGIN_NAME + " topping name = " + addons.get(x).getName());
      }
    }
    return totalCents;
  }

  static long toppingsAddonsSort(Premade origin, ArrayList<Topping> toppingList,
      ArrayList<Topping> nonCollision,
      ArrayList<Addon> nonCollisionAddon) {
    long totalCents = 0;
    for (Topping current : toppingList) {
      /* Do not calculate the price for an easy topping if the origin contains that topping already, since it's a reduction */
      if (current.isEasy() && origin.getToppings()
          .contains(current.toppingClone().setEasy(false))) {
        nonCollision.remove(current);
      }
    }

    /* Remove collisions, do not use removeAll since it will remove extra options */
    for (Topping toRemove : origin.getToppings()) {
      nonCollision.remove(toRemove);
    }
    for (Addon toRemove : origin.getAddons()) {
      nonCollisionAddon.remove(toRemove);
    }

    /* Finally calculate */
    for (Addon filtered : nonCollisionAddon) {
      totalCents += filtered.getPriceNonCalculated();
    }

    return totalCents;
  }

  @Override
  public ArrayList<Topping> getToppings() {
    return toppingList;
  }

  /**
   * Returns the basic item of this premade item, that is an item of the exact same type and origin
   * name, but has no toppings or anything of the sort.
   *
   * @return The basic item
   */
  public abstract PremadeItem getBasic();

  /**
   * Performs a safe clone of this premade item, by safe cloning all smaller items to ensure all
   * objects are of their own instance
   *
   * @return The cloned Premade Item
   */
  @Override
  public PremadeItem safeClone() {
    PremadeItem clone = getBasic();
    ArrayList<Topping> toppings = new ArrayList<>();
    ArrayList<Item> subItems = new ArrayList<>();
    ArrayList<AlternativeItemPackage> alternativeItemPackages = new ArrayList<>();
    ArrayList<Addon> addons = new ArrayList<>();
    ArrayList<SpecialInstructions> newSpecialInstructions = new ArrayList<>();
    if(toppingList != null) {
      for (Topping i : toppingList) {
        toppings.add(i.toppingClone());
      }
    }

    if(this.subItems != null) {
      for (Item i : this.subItems) {
        subItems.add(i.safeClone());
      }
    }

    if(alternativeItems != null) {
      for (AlternativeItemPackage i : alternativeItems) {
        alternativeItemPackages.add(i.safeClone());
      }
    }

    if(addonList != null) {
      for(Addon i : addonList) {
        addons.add(i.safeClone());
      }
    }

    if(specialInstructions != null) {
      for(SpecialInstructions i : specialInstructions) {
        newSpecialInstructions.add(i);
      }
    }

    clone.addonList = addons;
    clone.toppingList = toppings;
    clone.subItems = subItems;
    clone.alternativeItems = alternativeItemPackages;
    clone.specialInstructions = specialInstructions;
    clone.setDiscounted(isDiscounted());
    clone.price = price;
    clone.name = name;
    clone.isDiscountEligible = isDiscountEligible;
    clone.specialInstructions = newSpecialInstructions;

    return clone;
  }

  /**
   * A private enumerated type representing the different toppings that are allowed to be placed
   * within the different premade items, as well as the associated item it's allowed to be placed
   * within
   */
  private enum PremadeToppings {

    /**
     * Chicken that is only allowed to go on salads
     */
    CHICKEN_SALAD(Salad.class, Menu.loadedToppings.getMiscPrices().getSaladChicken()),

    /**
     * Chicken that is only allowed to go on pastas
     */
    CHICKEN_PASTA(Italian.class, Menu.loadedToppings.getMiscPrices().getPastaChicken()),

    /**
     * Shrimp that is only allowed to go on pastas
     */
    SHRIMP(Italian.class, Menu.loadedToppings.getMiscPrices().getPastaShrimp()),

    /**
     * Mushrooms that is only allowed to go on pastas
     */
    MUSHROOM(Italian.class, Menu.loadedToppings.getMiscPrices().getPastaMushroom()),

    /**
     * Cheese that is only allowed to go on pastas
     */
    CHEESE(Italian.class, Menu.loadedToppings.getMiscPrices().getPastaCheese());

    private final Class classType;

    private final long price;

    PremadeToppings(Class classType, long price) {
      this.classType = classType;
      this.price = price;
    }

    /**
     * Checks if the other premade item is allowed to use the given premade topping
     *
     * @param other The other premade item
     * @return {@code true} if it is, {@code false} otherwise
     */
    boolean isAllowed(PremadeItem other) {
      return other.getClass().equals(classType);
    }

    /**
     * Gets the price associated to this object
     *
     * @return The price of the associated topping for the given item
     */
    long getPrice() {
      return price;
    }
  }
}