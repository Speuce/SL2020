package main.java.lucia.client.content.menu.legacy.premade.impl.names;

/**
 * The list of the premade appetizers names
 *
 * @author Brett Downey
 */
public enum PremadeAppetizersName {

  WINGS("Wings"),

  MOZZA_BREAD_STICKS("Mozza Bread Sticks"),

  BREAD_STICKS("Bread Sticks"),

  GARLIC_TOAST("Garlic Toast"),

  GARLIC_TOAST_THREE_PIECE("3x Garlic Toast"),

  CHEESE_TOAST("Cheese Toast"),

  CHICKEN_STRIPS("Ckn Strips"),

  FRENCH_FRIES("French Fries"),

  POUTINE("Poutine"),

  MEATBALLS("Meatballs");


  private final String SIMPLE_NAME;

  PremadeAppetizersName(final String SIMPLE_NAME) {
    this.SIMPLE_NAME = SIMPLE_NAME;
  }

  public String getSimpleName() {
    return SIMPLE_NAME;
  }
}
