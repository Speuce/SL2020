package main.java.lucia.client.content.menu.legacy.impl.misc;

/**
 * An enumerated type representing the different
 * types of crusts available.
 *
 * @author Brett Downey
 */
public enum CrustTypes {

  THIN("THIN"),

  THICK("THICK"),

  REGULAR_CRUST("REG"),

  KETO("KETO"),

  GLUTEN_FREE("GF"),

  WHOLE_WHEAT("WW");

  private final String SIMPLE_NAME;

  CrustTypes(final String SIMPLE_NAME) {
    this.SIMPLE_NAME = SIMPLE_NAME;
  }

  public String getSimpleName() {
    return SIMPLE_NAME;
  }
}