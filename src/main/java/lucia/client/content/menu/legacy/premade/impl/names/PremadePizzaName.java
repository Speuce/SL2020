package main.java.lucia.client.content.menu.legacy.premade.impl.names;

/**
 * The enumerated type representing all of the
 * different premade item names
 *
 * @author Brett Downey
 */
public enum PremadePizzaName {

  SLS("SLS"),

  MEAT_LOVER("ML"),

  ALOHA("ALOHA"),

  VEGGIE("VEG"),

  MEXICAN("MEX"),

  HAL_JOE("HAL&JOE"),

  CITI92("92CITI"),

  GREEK("GREEK"),

  CHICKEN_SUPREME("CKN SUP"),

  SANTA_FE("SANTA FE"),

  MEDITERRANEAN("MED"),

  HEALTHY_CHOICE("HH"),

  MONTENEGRO("MONTENEGRO"),

  ITALIAN("ITALIAN"),

  BBQ_CHICKEN("BBQ CKN"),

  GYRO("GYRO");

  private final String SIMPLE_NAME;

  PremadePizzaName(final String SIMPLE_NAME) {
    this.SIMPLE_NAME = SIMPLE_NAME;
  }

  /**
   * Gets the simple name of the given pizza type
   *
   * @return The String which holds the simple name
   */
  public String getSimpleName() {
    return SIMPLE_NAME;
  }
}