package main.java.lucia.client.content.menu.legacy.premade.impl.names;

public enum PremadeItalianName {

  SPAGHETTI("Spaghetti"),

  LASAGNA("Lasagna"),

  RAVIOLI("Ravioli"),

  FETTUCCINE("Fett Alf"),

  CHICKEN_PARMESAN("Ckn Parm"),

  MANICOTTI("Manicotti"),

  CHICKEN_DINNER("Ckn Dinner");

  private final String SIMPLE_NAME;

  PremadeItalianName(final String SIMPLE_NAME) {
    this.SIMPLE_NAME = SIMPLE_NAME;
  }

  /**
   * Gets the simple name of the premade italian item
   *
   * @return The simple name
   */
  public String getSimpleName() {
    return SIMPLE_NAME;
  }
}
