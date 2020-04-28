package main.java.lucia.client.content.menu.legacy.toppings;

import com.google.gson.Gson;
import java.util.HashMap;
import main.java.lucia.client.content.menu.legacy.PriceForSize;
import main.java.lucia.client.content.menu.legacy.impl.misc.MiscPrices;
import main.java.lucia.client.content.menu.legacy.toppings.names.GourmetToppingNames;
import main.java.lucia.client.content.menu.legacy.toppings.names.MiscNames;
import main.java.lucia.client.content.menu.legacy.toppings.names.NormalToppingNames;
import org.apache.commons.lang3.EnumUtils;

/**
 * The toppings class, which holds all important
 * toppings information which is loaded from
 * a JSON file using {@link Gson}
 *
 * @author Brett Downey
 */
public class Toppings {

  /**
   * The base prices of pizzas, before any
   * additional toppings are added on
   */
  private final PriceForSize BASE_PRICES;

  /**
   * The misc prices within the menu
   */
  private final MiscPrices MISC_PRICES;

  /**
   * The {@link PriceForSize} for a given
   * topping type, there are four different
   * classes of toppings, therefore there are
   * four different pricing schemes per size for
   * said toppings
   */
  private final HashMap<ToppingType, PriceForSize> PRICES_FOR_PIZZA_SIZES;

  /**
   * The list of the normal class toppings
   */
  private final HashMap<NormalToppingNames, Topping> NORMAL_TOPPINGS;

  /**
   * The list of the gourmet class toppings
   */
  private final HashMap<GourmetToppingNames, Topping> GOURMET_TOPPINGS;

  /**
   * The list of the misc toppings
   */
  private final HashMap<MiscNames, Topping> MISC_TOPPINGS;

  /**
   * A private constructor which isn't used since
   * {@link Gson} builds this class from reflection
   *
   * @param base_prices The base prices
   * @param misc_prices The misc prices
   * @param prices_for_pizza_sizes The prices for the pizza sizes
   * @param normal_toppings The normal toppings
   * @param gourmet_toppings The gourmet toppings
   * @param misc_toppings The misc toppings
   */
  public Toppings(PriceForSize base_prices,
      MiscPrices misc_prices,
      HashMap<ToppingType, PriceForSize> prices_for_pizza_sizes,
      HashMap<NormalToppingNames, Topping> normal_toppings,
      HashMap<GourmetToppingNames, Topping> gourmet_toppings,
      HashMap<MiscNames, Topping> misc_toppings) {
    BASE_PRICES = base_prices;
    MISC_PRICES = misc_prices;
    PRICES_FOR_PIZZA_SIZES = prices_for_pizza_sizes;
    NORMAL_TOPPINGS = normal_toppings;
    GOURMET_TOPPINGS = gourmet_toppings;
    MISC_TOPPINGS = misc_toppings;
  }

  /**
   * Gets a topping based off a name, case insensitive.
   *
   * @param toppingName The topping name you'd like
   * @return The topping
   *
   * @throws Exception if the given name is an invalid topping name
   */
  public Topping get(String toppingName) throws Exception {
    String upper = toppingName.toUpperCase();
    if(EnumUtils.isValidEnum(NormalToppingNames.class, upper)) {
      return get(NormalToppingNames.valueOf(upper));
    } else if(EnumUtils.isValidEnum(GourmetToppingNames.class, upper)) {
      return get(GourmetToppingNames.valueOf(upper));
    } else if(EnumUtils.isValidEnum(MiscNames.class, upper)) {
      return get(MiscNames.valueOf(upper));
    } else {
      throw new Exception("Invalid Topping Name: " + toppingName);
    }
  }

  public Topping get(NormalToppingNames name) {
    return NORMAL_TOPPINGS.get(name).toppingClone();
  }

  public Topping get(GourmetToppingNames name) {
    return GOURMET_TOPPINGS.get(name).toppingClone();
  }

  public Topping get(MiscNames name) {
    return MISC_TOPPINGS.get(name).toppingClone();
  }

  /**
   * Returns the {@link HashMap} which relates a
   * {@link ToppingType} to a {@link PriceForSize}, which
   * represents the different prices for the different
   * corresponding sizes.
   *
   * @return The prices map
   */
  public HashMap<ToppingType, PriceForSize> getPrices() {
    return PRICES_FOR_PIZZA_SIZES;
  }

  /**
   * Gets the list of the normal toppings
   *
   * @return The normal toppings list
   */
  public HashMap<NormalToppingNames, Topping> getNormalToppings() {
    return NORMAL_TOPPINGS;
  }

  /**
   * Gets the list of the gourmet toppings
   *
   * @return The gourmet toppings list
   */
  public HashMap<GourmetToppingNames, Topping> getGourmetToppings() {
    return GOURMET_TOPPINGS;
  }

  /**
   * Gets the list of the gourmet plus toppings
   *
   * @return The gourmet plus toppings list
   */
  public HashMap<MiscNames, Topping> getMiscToppings() {
    return MISC_TOPPINGS;
  }

  /**
   * Gets the base pizza prices
   *
   * @return The base pizza prices
   */
  public PriceForSize getBasePrices() {
    return BASE_PRICES;
  }

  /**
   * Gets the misc prices
   *
   * @return The misc prices
   */
  public MiscPrices getMiscPrices() {
    return MISC_PRICES;
  }

  /**
   * Gets the price for pizza sizes information
   *
   * @return The price for pizza sizes information
   */
  public HashMap<ToppingType, PriceForSize> getPricesForPizzaSizes() {
    return PRICES_FOR_PIZZA_SIZES;
  }

  /**
   * Adds a topping to the given {@link ToppingType} so
   * it goes into it's respective list
   *
   * @param type The topping's type
   * @param toAdd The topping to add
   */
  public void addTopping(ToppingType type, Topping toAdd) {
    switch(type) {
      case NORMAL:
        NORMAL_TOPPINGS.put(NormalToppingNames.valueOf(toAdd.getName()), toAdd);
        break;
      case GOURMET:
        GOURMET_TOPPINGS.put(GourmetToppingNames.valueOf(toAdd.getName()), toAdd);
        break;
      case CHILI_PEPPER:
        MISC_TOPPINGS.put(MiscNames.valueOf(toAdd.getName()), toAdd);
        break;
    }
  }
}