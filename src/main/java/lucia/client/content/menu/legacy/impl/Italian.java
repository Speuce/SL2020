package main.java.lucia.client.content.menu.legacy.impl;

import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.NameBuilder;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.impl.misc.MiscPrices;
import main.java.lucia.client.content.menu.legacy.impl.misc.Sauce;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.legacy.premade.Premade;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadeItem;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeItalianName;

import java.util.ArrayList;

public class Italian extends PremadeItem implements Premade {

  /**
   * The origin name of this premade italian meal
   */
  private final transient String ORIGIN_NAME;

  /**
   * The specific id of this addon on the server.
   * DO NOT SET. Let Gson do that.
   */
  private int rowNum = -1;

  /**
   * The sauce associated with this pasta, which is
   * default the regular pasta sauce (same as regular pizza
   * sauce).
   */
  private Sauce sauce = Sauce.REGULAR;
  private boolean isNoCheese = false;



  public void setDiscountEligible(boolean discountEligible) {
    super.isDiscountEligible = discountEligible;
  }


  /**
   * A new pasta that is being created, with the beginning piece of the name.
   *
   * @param name The beginning of the name for the item
   */
  public Italian(String name) {
    super(name);
    ORIGIN_NAME = name;
  }

  @Override
  protected void setInitialValues(PremadeItem origin, NameBuilder builder, ArrayList<SpecialInstructions> crustInstructions, ArrayList<SpecialInstructions> sauceInstructions) {
    Italian originItalian = (Italian) origin;

    if (originItalian.sauce != sauce) {
      builder.setSauce(sauce, false, sauceInstructions);
    } else {
      builder.setSauce(sauceInstructions);
    }

    builder.setSpecialName(PremadeItalianName.valueOf(ORIGIN_NAME.toUpperCase()).getSimpleName());
  }

  @Override
  protected long calculateMiscPrices(PremadeItem origin) {
    long totalCents = 0;
    MiscPrices misc = Menu.loadedToppings.getMiscPrices();

      if (specialInstructions.contains(SpecialInstructions.EXTRA_SAUCE)
              && !origin.getSpecialInstructions().contains(SpecialInstructions.EXTRA_SAUCE)) {
        totalCents += misc.getPastaSauce();
      }
    return totalCents;
  }

  @Override
  public PremadeItem getBasic() {
    return new Italian(ORIGIN_NAME);
  }



  /**
   * Sets the sauce to the given sauce type
   *
   * @param sauce The given Sauce type
   */
  public void setSauce(Sauce sauce) {
    this.sauce = sauce;
  }

  /**
   * Sets the light sauce to the given state
   *
   * @param state The given state
   */
  public void setLightSauce(boolean state) {
    removeSauceOptions();
    if(state) {
      specialInstructions.add(SpecialInstructions.LIGHT_SAUCE);
    }
  }

  /**
   * Sets the extra sauce to the given state
   *
   * @param state The given state
   */
  public void setExtraSauce(boolean state) {
    removeSauceOptions();
    if(state) {
      specialInstructions.add(SpecialInstructions.EXTRA_SAUCE);
    }
  }


  /**
   * Sets the no cheese option to the given state
   *
   * @param state The given state
   */
  public void setNoCheese(boolean state) {
    removeCheeseOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.NO_CHEESE);
     // isNoCheese = true;
    }
  }

  public boolean isNoCheese() {
    return this.isNoCheese;
  }


  /**
   * Sets the easy cheese to the given state
   *
   * @param state The given state
   */
  public void setEasyCheese(boolean state) {
    removeCheeseOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.EASY_CHEESE);
    }
  }

  /**
   * Sets the extra cheese to the given state
   *
   * @param state The given state
   */
  public void setExtraCheese(boolean state) {
    removeCheeseOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.EXTRA_CHEESE);
    }
  }

  /**
   * Sets the extra extra cheese to the given state
   *
   * @param state The given state
   */
  public void setExtraExtraCheese(boolean state) {
    removeCheeseOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.EXTRA_EXTRA_CHEESE);
    }
  }

  /**
   * Removes all sauce options
   */
  private void removeSauceOptions() {
    specialInstructions.removeIf(SpecialInstructions::verifySauce);
  }

  /**
   * Removes all cheese options
   */
  private void removeCheeseOptions() {
    specialInstructions.removeIf(SpecialInstructions::verifyCheese);
    specialInstructions.clear();
  }


  @Override
  public Premade getOrigin() {
    return Menu.loadedPreMadeFoods.getPremadeItalian()
        .get(PremadeItalianName.valueOf(ORIGIN_NAME.toUpperCase()));
  }

  @Override
  public ArrayList<Addon> getAddons() {
    return addonList;
  }

 }