package main.java.lucia.client.content.menu.legacy;

import main.java.lucia.client.content.menu.legacy.size.Size;

/**
 * Prices for various toppings depending on their sizes.
 *
 * @author Brett Downey
 */
public class PriceForSize {

  /**
   * The price for the ten inch sizing tier
   */
  private final int TEN;

  /**
   * The price for the thirteen inch sizing tier
   */
  private final int THIRTEEN;

  /**
   * The price for the fifteen inch sizing tier
   */
  private final int FIFTEEN;

  /**
   * The price for the eighteen inch sizing tier
   */
  private final int EIGHTEEN;

  /**
   * The price for the thirty inch sizing tier
   */
  private final int THIRTY;

  /**
   * The constructor for a new price for size object
   *
   * @param TEN The ten inch pricing
   * @param THIRTEEN The thirteen inch pricing
   * @param FIFTEEN The fifteen inch pricing
   * @param EIGHTEEN The eighteen inch pricing
   * @param THIRTY The thirty inch pricing
   */
  public PriceForSize(final int TEN, final int THIRTEEN, final int FIFTEEN, final int EIGHTEEN, final int THIRTY) {
    this.TEN = TEN;
    this.THIRTEEN = THIRTEEN;
    this.FIFTEEN = FIFTEEN;
    this.EIGHTEEN = EIGHTEEN;
    this.THIRTY = THIRTY;
  }

  /**
   * Gets the price for the ten inch
   *
   * @return The price
   */
  public int getTenPrice() {
    return TEN;
  }

  /**
   * Gets the price for the thirteen inch
   *
   * @return The price
   */
  public int getThirteenPrice() {
    return THIRTEEN;
  }

  /**
   * Gets the price for the fifteen inch
   *
   * @return The price
   */
  public int getFifteenPrice() {
    return FIFTEEN;
  }

  /**
   * Gets the price for the eighteen inch
   *
   * @return The price
   */
  public int getEighteenPrice() {
    return EIGHTEEN;
  }

  /**
   * Gets the price for the thirty inch
   *
   * @return The price
   */
  public int getThirtyPrice() {
    return THIRTY;
  }

  /**
   * Gets the price for the pizza from the given size
   *
   * @param size The size to get the price for
   * @return The price
   */
  public int getPriceForSize(Size size) {
    switch(size) {
      case TEN:
        return getTenPrice();
      case THIRTEEN:
        return getThirteenPrice();
      case FIFTEEN:
        return getFifteenPrice();
      case EIGHTEEN:
        return getEighteenPrice();
      case THIRTY:
        return getThirtyPrice();
      default:
        return -1;
    }
  }
}