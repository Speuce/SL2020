package main.java.lucia.client.content.menu.legacy.impl;

import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.NameBuilder;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.legacy.premade.Premade;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadeItem;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAppetizersName;
import java.util.ArrayList;

public class Appetizer extends PremadeItem implements Premade {

  /**
   * A new item that is being created, with the beginning piece of the name.
   *
   * @param name The beginning of the name for the item
   */
  public Appetizer(String name) {
    super(name);
    ORIGIN_NAME = name;
  }

  @Override
  protected void setInitialValues(PremadeItem origin, NameBuilder builder, ArrayList<SpecialInstructions> crustInstructions, ArrayList<SpecialInstructions> sauceInstructions) {
    builder.setSpecialName(PremadeAppetizersName.valueOf(ORIGIN_NAME.toUpperCase()).getSimpleName());
  }

  @Override
  protected long calculateMiscPrices(PremadeItem origin) {
    for(Addon a : addonList) {
    }
    return 0;
  }

  @Override
  public PremadeItem getBasic() {
    return new Appetizer(ORIGIN_NAME);
  }

  @Override
  public Premade getOrigin() {
    return Menu.loadedPreMadeFoods.getPremadeAppetizers()
        .get(PremadeAppetizersName.valueOf(ORIGIN_NAME.toUpperCase()));
  }

  @Override
  public ArrayList<Addon> getAddons() {
    return addonList;
  }
}