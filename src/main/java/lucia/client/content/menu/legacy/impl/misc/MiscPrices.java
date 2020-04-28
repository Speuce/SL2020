package main.java.lucia.client.content.menu.legacy.impl.misc;

import main.java.lucia.client.content.menu.legacy.PriceForSize;

/**
 * The misc prices associated with the
 * Santa Lucia menu.
 *
 * @author Brett Downey
 */
public class MiscPrices {

  /**
   * The price for the greek sauce
   */
  private PriceForSize GREEK_SAUCE;

  private long GLUTEN_FREE;

  private long KETO;

  private long SALAD_CHICKEN;

  private long PASTA_SHRIMP;

  private long PASTA_MUSHROOM;

  private long PASTA_CHEESE;

  private long PASTA_CHICKEN;

  private long PASTA_SAUCE;

  public PriceForSize getGreekSauce() {
    return GREEK_SAUCE;
  }

  public long getGlutenFree() {
    return GLUTEN_FREE;
  }

  public long getKeto() {
    return KETO;
  }

  public long getPastaMushroom() {
    return PASTA_MUSHROOM;
  }

  public long getPastaCheese() {
    return PASTA_CHEESE;
  }

  public long getPastaChicken() {
    return PASTA_CHICKEN;
  }

  public long getPastaShrimp() {
    return PASTA_SHRIMP;
  }

  public long getSaladChicken() {
    return SALAD_CHICKEN;
  }

  public long getPastaSauce() {
    return PASTA_SAUCE;
  }
}