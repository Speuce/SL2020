package main.java.lucia.client.content.menu.legacy;

import main.java.lucia.client.content.menu.legacy.premade.PremadeFoodList;
import main.java.lucia.client.content.menu.legacy.toppings.Toppings;

/**
 * The class which contains all loaded
 * menu items.
 *
 * @author Brett Downey
 */
public class Menu {

  /**
   * The premade food list which holds all
   * the different types of premade food associations,
   * dips, and other items of the sort.
   */
  public static PremadeFoodList loadedPreMadeFoods;

  /**
   * The loaded toppings list, which holds all toppings
   * with their respective pricing for different size tiers.
   */
  public static Toppings loadedToppings;
}