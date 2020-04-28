package main.java.lucia.client.content.menu.legacy.instructions;


/**
 * The enumerated type representing different special instructions along with their given names.
 */
public enum SpecialInstructions {

  WELL_DONE("Well Done", "WD", false, true),

  UNDER_DONE("Under Done", "UD", false, true),

  NO_SAUCE("No Sauce", "NS", false, false),

  LIGHT_SAUCE("Light Sauce", "LS", false, false),

  EXTRA_SAUCE("Extra Sauce", "XS", false, false),

  NO_CHEESE("No Cheese", "NOCHZ", true, false),

  EASY_CHEESE("Easy Cheese", "EZCHEESE", true, false),

  EXTRA_CHEESE("Extra Cheese", "XCHEESE", true, false),

  EXTRA_EXTRA_CHEESE("Extra Extra Cheese", "XXCHEESE", true, false);

  private final String NAME;

  private final String SIMPLE_NAME;

  private final boolean TOPPING_AREA;

  private final boolean CRUST_MODIFIER;

  SpecialInstructions(final String NAME, final String SIMPLE_NAME, final boolean TOPPING_AREA, final boolean CRUST_MODIFIER) {
    this.NAME = NAME;
    this.SIMPLE_NAME = SIMPLE_NAME;
    this.TOPPING_AREA = TOPPING_AREA;
    this.CRUST_MODIFIER = CRUST_MODIFIER;
  }

  public String getName() {
    return NAME;
  }

  public String getSimpleName() {
    return SIMPLE_NAME;
  }

  public boolean inToppingArea() {
    return TOPPING_AREA;
  }

  public boolean inCrustModifier() {
    return CRUST_MODIFIER;
  }

  /**
   * Verifies if the special instruction given is a cheese option.
   *
   * @param specialInstructions The special instruction
   * @return True if it is a cheese option, false otherwise.
   */
  public static boolean verifyCheese(SpecialInstructions specialInstructions) {
    switch (specialInstructions) {
      case EASY_CHEESE:
      case EXTRA_CHEESE:
      case EXTRA_EXTRA_CHEESE:
      case NO_CHEESE:
        return true;
      default:
        return false;
    }
  }

  /**
   * Verifies if the special instruction given is a cooked state option.
   *
   * @param specialInstructions The special instruction
   * @return True if it is a cooked state option, false otherwise.
   */
  public static boolean verifyCookedState(SpecialInstructions specialInstructions) {
    switch (specialInstructions) {
      case WELL_DONE:
      case UNDER_DONE:
        return true;
      default:
        return false;
    }
  }

  /**
   * Verifies if the special instruction given is a sauce option.
   *
   * @param specialInstructions The special instruction
   * @return True if it is a sauce option, false otherwise.
   */
  public static boolean verifySauce(SpecialInstructions specialInstructions) {
    switch (specialInstructions) {
      case LIGHT_SAUCE:
      case EXTRA_SAUCE:
        return true;
      default:
        return false;
    }
  }
}