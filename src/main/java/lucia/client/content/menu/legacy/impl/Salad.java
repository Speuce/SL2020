package main.java.lucia.client.content.menu.legacy.impl;

import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.NameBuilder;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.impl.misc.MiscPrices;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.legacy.premade.Premade;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadeItem;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadePizzaName;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeSaladNames;
import java.util.ArrayList;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;

public class Salad extends PremadeItem implements Premade {

  /**
   * The origin name of this premade italian meal
   */
  private final transient String ORIGIN_NAME;

  /**
   * The specific id of this pizza on the server.
   * DO NOT SET. Let Gson do that.
   */
  private int rowNum = -1;

  /**
   * A new salad that is being created, with the beginning piece of the name.
   *
   * @param name The beginning of the name for the item
   */
  public Salad(String name) {
    super(name);
    ORIGIN_NAME = name;
  }

  @Override
  protected void setInitialValues(PremadeItem origin, NameBuilder builder, ArrayList<SpecialInstructions> crustInstructions, ArrayList<SpecialInstructions> sauceInstructions) {
    builder.setSpecialName(PremadeSaladNames.valueOf(ORIGIN_NAME.toUpperCase()).getSimpleName());
  }

  @Override
  protected long calculateMiscPrices(PremadeItem origin) {
    long total = 0;
    for (Topping t : toppingList) {
      if (t.getName().equalsIgnoreCase("Chicken")) {
        total += Menu.loadedToppings.getMiscPrices().getSaladChicken();
      }
    }
    return total;
  }

  @Override
  public PremadeItem getBasic() {
    return new Salad(ORIGIN_NAME);
  }

  @Override
  public Premade getOrigin() {
    return Menu.loadedPreMadeFoods.getPremadeSalad()
        .get(PremadeSaladNames.valueOf(ORIGIN_NAME.toUpperCase()));
  }

  @Override
  public ArrayList<Addon> getAddons() {
    return addonList;
  }
}