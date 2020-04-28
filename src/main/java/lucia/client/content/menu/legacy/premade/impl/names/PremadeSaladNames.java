package main.java.lucia.client.content.menu.legacy.premade.impl.names;

public enum PremadeSaladNames {

  GREEK_SALAD("Greek Salad"),

  STARTER_GREEK_SALAD("Starter Greek Salad"),

  CAESAR_SALAD("Caesar Salad"),

  STARTER_CAESAR_SALAD("Starter Caesar Salad"),

  LARGE_GARDEN_SALAD("Large Garden Salad"),

  SMALL_GARDEN_SALAD("Small Garden Salad"),

  SMALL_COLESLAW("Small Coleslaw"),

  LARGE_COLESLAW("Large Coleslaw"),

  CHEF_SALAD("Chef Salad"),

  CHEESE_SALAD("Cheese Salad");

  private final String SIMPLE_NAME;

  PremadeSaladNames(final String SIMPLE_NAME) {
    this.SIMPLE_NAME = SIMPLE_NAME;
  }

  public String getSimpleName() {
    return SIMPLE_NAME;
  }
}