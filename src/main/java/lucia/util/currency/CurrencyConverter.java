package main.java.lucia.util.currency;

import main.java.lucia.consts.TaxConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The currency converter which handles
 * many different operations such as large
 * calculations, tax evaluations, and other advanced
 * rounding calculation modes.
 *
 * @author Brett Downey
 * @author Matt Kwiatkowsi
 * @author Zachery Unrau
 */
public class CurrencyConverter {

  public static BigDecimal taxAndBuild(long cents) {
    long dollars = cents / 100;
    cents %= 100;
    BigDecimal val = new BigDecimal(dollars + "." + cents);
    return val.multiply(new BigDecimal(1 + "." + (TaxConstants.GST + TaxConstants.MANITOBA_PST))).setScale(2, RoundingMode.HALF_UP);
  }

  public static BigDecimal taxAndBuildGST(long cents) {
    long dollars = cents / 100;
    cents %= 100;
    BigDecimal val = new BigDecimal(dollars + "." + cents);
    return val.multiply(new BigDecimal( ".0" + (TaxConstants.GST))).setScale(2, RoundingMode.HALF_UP);
  }

  public static long withTax(long cents){ // TODO Should be using HALF_UP rounding mode
    return cents + Math.round(cents * ((TaxConstants.GST+TaxConstants.MANITOBA_PST) / 100.0));
  }

  public static Double taxAndBuildLong(long cents) { // TODO Method header says "Long" but returns Double ?
    BigDecimal toLong = taxAndBuild(cents);
    return toLong.doubleValue();
  }

  public static BigDecimal taxAndBuildPST(long cents) {
    long dollars = cents / 100;
    cents %= 100;
    BigDecimal val = new BigDecimal(dollars + "." + cents);
    return val.multiply(new BigDecimal( ".0" + (TaxConstants.MANITOBA_PST))).setScale(2, RoundingMode.HALF_UP);
  }


  /**
   * Converts the given amount of cents into a displayable bigdecimal
   * @param cents the cents to display
   * @return a bigdecimal in the format xxxx.xx
   */
  public static BigDecimal build(long cents) {
    long dollars = cents / 100;
    cents %= 100;
    return new BigDecimal(dollars + "." + cents);
  }
}