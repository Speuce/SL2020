package main.java.lucia.client.content.menu.legacy.premade;

import java.util.HashMap;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadeItem;
import main.java.lucia.client.content.menu.legacy.toppings.Toppings;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.impl.Appetizer;
import main.java.lucia.client.content.menu.legacy.impl.Italian;
import main.java.lucia.client.content.menu.legacy.impl.Salad;
import main.java.lucia.client.content.menu.legacy.impl.catering.Catering;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAddonNames;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAppetizersName;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeBeverageNames;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeCateringNames;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeItalianName;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadePizza;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadePizzaName;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeSaladNames;
import main.java.lucia.client.content.order.Order;
import org.apache.commons.lang3.EnumUtils;

/**
 * The pre-made food list which contains the prices
 * and the different addons and options for the different
 * assortment of pre-made food
 *
 * @author Brett Downey
 */
public class PremadeFoodList {

  /**
   * The premade pizzas
   */
  private final HashMap<PremadePizzaName, PremadePizza> PREMADE_PIZZAS;

  /**
   * The premade appetizers
   */
  private final HashMap<PremadeAppetizersName, Appetizer> PREMADE_APPETIZERS;

  /**
   * The premade italian
   */
  private final HashMap<PremadeItalianName, Italian> PREMADE_ITALIAN;

  /**
   * The premade salads
   */
  private final HashMap<PremadeSaladNames, Salad> PREMADE_SALAD;

  /**
   * The premade beverages
   */
  private final HashMap<PremadeBeverageNames, Addon> PREMADE_BEVERAGE;

  /**
   * The premade addons
   */
  private final HashMap<PremadeAddonNames, Addon> ADDONS;

  /**
   * The premade catering
   */
  private final HashMap<PremadeCateringNames, Catering> PREMADE_CATERING;

  /**
   * A private constructor since the {@link com.google.gson.Gson} utilizes
   * reflection in order to build our list
   *
   * @param premade_pizzas The pizza map
   * @param premade_appetizers The appetizers map
   * @param premade_italian The italian map
   * @param premade_salad The salad map
   * @param premade_beverage The beverage map
   * @param addons The addons map
   * @param premade_catering The catering map
   */
  private PremadeFoodList(
      HashMap<PremadePizzaName, PremadePizza> premade_pizzas,
      HashMap<PremadeAppetizersName, Appetizer> premade_appetizers,
      HashMap<PremadeItalianName, Italian> premade_italian,
      HashMap<PremadeSaladNames, Salad> premade_salad,
      HashMap<PremadeBeverageNames, Addon> premade_beverage,
      HashMap<PremadeAddonNames, Addon> addons,
      HashMap<PremadeCateringNames, Catering> premade_catering) {
    PREMADE_PIZZAS = premade_pizzas;
    PREMADE_APPETIZERS = premade_appetizers;
    PREMADE_ITALIAN = premade_italian;
    PREMADE_SALAD = premade_salad;
    PREMADE_BEVERAGE = premade_beverage;
    ADDONS = addons;
    PREMADE_CATERING = premade_catering;
  }

  /**
   * Gets the premade item origin depending on the name of the given
   * item. Note that the input is case insensitive.
   * Please note that this class does not return safe clones of
   * the objects, unlike {@link Toppings}
   * method {@code get}. Therefore use this method with caution
   * as it is dangerous to modify any of the returned objects.
   * If you need to use the object returned by this function with
   * modifications, then {@code safeClone} should be applied
   * to the given PremadeItem
   *
   * @param name The name to get the item from
   * @return The premade item if it exists
   *
   * @throws Exception if the item does not exist
   */
  public PremadeItem getPremadeItem(String name) throws Exception {
    String upper = name.toUpperCase().replace(" ", "_");
    if(EnumUtils.isValidEnum(PremadeSaladNames.class, upper)) {
      return PREMADE_SALAD.get(PremadeSaladNames.valueOf(upper));
    } else if (EnumUtils.isValidEnum(PremadeItalianName.class, upper)) {
      return PREMADE_ITALIAN.get(PremadeItalianName.valueOf(upper));
    } else if(EnumUtils.isValidEnum(PremadeAppetizersName.class, upper)) {
      return PREMADE_APPETIZERS.get(PremadeAppetizersName.valueOf(upper));
    } else if (EnumUtils.isValidEnum(PremadeCateringNames.class, upper)) {
      return PREMADE_CATERING.get(PremadeCateringNames.valueOf(upper));
    } else {
      throw new Exception("Invalid premade item name! " + name);
    }
  }

  /**
   * Gets the premade item and clones it safely
   *
   * @param name The name to get the item for
   * @return The safe cloned premade item
   * @throws Exception If the given item name does not exist
   */
  public PremadeItem getPremadeItemSafe(String name) throws Exception {
    return getPremadeItem(name).safeClone();
  }

  /**
   * Gets a pizza given a name, note that this should
   * be used instead of getting the individual list and
   * getting the item from there. This is because this method
   * performs a clone of the given pizza, and therefore will allow
   * safe modification of the given item.
   *
   * @return The safely cloned pizza
   */
  public PremadePizza getPizza(PremadePizzaName name) {
    return PREMADE_PIZZAS.get(name).safeClone();
  }

  /**
   * Gets the premade pizza list, note that this should
   * not be used in order to get a pizza to add to a
   * {@link Order}. {@see getPizza} for the proper use of getting
   * a pizza and adding it to an order.
   *
   * @return The premade pizza list
   */
  public HashMap<PremadePizzaName, PremadePizza> getPremadePizzas() {
    return PREMADE_PIZZAS;
  }

  /**
   * Gets the premade appetizers list
   *
   * @return The premade appetizers list
   */
  public HashMap<PremadeAppetizersName, Appetizer> getPremadeAppetizers() {
    return PREMADE_APPETIZERS;
  }

  /**
   * Gets the premade italian list
   *
   * @return The premade italian list
   */
  public HashMap<PremadeItalianName, Italian> getPremadeItalian() {
    return PREMADE_ITALIAN;
  }

  /**
   * Gets the premade salad list
   *
   * @return The premade salad list
   */
  public HashMap<PremadeSaladNames, Salad> getPremadeSalad() {
    return PREMADE_SALAD;
  }

  /**
   * Gets the premade beverage list
   *
   * @return The premade beverage list
   */
  public HashMap<PremadeBeverageNames, Addon> getPremadeBeverage() {
    return PREMADE_BEVERAGE;
  }

  /**
   * Gets the premade addons list
   *
   * @return The premade addons list
   */
  public HashMap<PremadeAddonNames, Addon> getAddons() {
    return ADDONS;
  }

  /**
   * Gets the premade catering list
   *
   * @return The premade catering list
   */
  public HashMap<PremadeCateringNames, Catering> getPremadeCatering() {
    return PREMADE_CATERING;
  }
}