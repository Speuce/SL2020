package main.java.lucia.client.content.menu.legacy.impl.catering;

import java.util.ArrayList;

import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.NameBuilder;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.legacy.Item;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.premade.Premade;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadeItem;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeCateringNames;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadePizzaName;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;

/**
 * An sub-item which represents a catering
 * item
 *
 * @author Brett Downey
 */
public class Catering extends PremadeItem implements Premade {

  public Catering(ArrayList<Topping> toppingList,
      ArrayList<Addon> addonList,
      ArrayList<SpecialInstructions> specialInstructions,
      String name, long price) {
    super(toppingList, addonList, specialInstructions, name, price);
  }

  public Catering(String name) {
    super(name);
  }

  @Override
  protected void setInitialValues(PremadeItem origin, NameBuilder builder, ArrayList<SpecialInstructions> crustInstructions, ArrayList<SpecialInstructions> sauceInstructions) {
    builder.setSpecialName(ORIGIN_NAME);
  }

  @Override
  protected long calculateMiscPrices(PremadeItem origin) {
    return 0;
  }

  @Override
  public PremadeItem getBasic() {
    return new Catering(ORIGIN_NAME);
  }

  @Override
  public Premade getOrigin() {
    return Menu.loadedPreMadeFoods.getPremadeCatering().get(PremadeCateringNames.valueOf(ORIGIN_NAME.toUpperCase()));
  }

  @Override
  public ArrayList<Addon> getAddons() {
    return addonList;
  }
}