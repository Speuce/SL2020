package main.java.lucia.client.content.menu.legacy.impl.misc;

/**
 * An enumerated type representing the different
 * assortments of sauces.
 */
public enum Sauce {

  /**
   * No sauce given
   */
  NONE("NS"),

  /**
   * The regular sauce which comes default
   * on all pizzas unless otherwise stated
   */
  REGULAR("SAUCE"),

  /**
   * Alfredo sauce
   */
  ALFREDO("AS"),

  /**
   * Salsa sauce
   */
  SALSA("SALSA"),

  /**
   * Barbeque sauce
   */
  BARBEQUE("BBQ"),

  /**
   * Greek sauce
   */
  GREEK_SAUCE("GS"),

  /**
   * Ranch sauce
   */
  RANCH("RS");

  /**
   * The simple name for this sauce type,
   * this name should be used for outputting
   * shorter amounts of ext while still retaining
   * the same "value"
   */
  private final String SIMPLE_NAME;

  /**
   * The constructor for the sauce which takes our
   * simple name
   *
   * @param SIMPLE_NAME The simple name associated to this sauce
   */
  Sauce(final String SIMPLE_NAME) {
    this.SIMPLE_NAME = SIMPLE_NAME;
  }

  /**
   * Gets the simple name, this name should
   * be used for outputting shorter amounts of
   * text while still retaining the same "value"
   *
   * @return The simple name associated to this sauce
   */
  public String getSimpleName() {
    return SIMPLE_NAME;
  }

}