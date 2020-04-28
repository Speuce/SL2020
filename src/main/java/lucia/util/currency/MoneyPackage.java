package main.java.lucia.util.currency;

import java.text.NumberFormat;

public class MoneyPackage {

  private static final NumberFormat FORMATTER = NumberFormat.getCurrencyInstance();

  private final long DOLLAR;

  private final long CENT;

  public MoneyPackage(final long DOLLAR, final long CENT) {
    this.DOLLAR = DOLLAR;
    this.CENT = CENT;
  }

  public long getDollarAmount() {
    return DOLLAR;
  }

  public long getCentAmount() {
    return CENT;
  }

  public String format() {
    String money = DOLLAR + "." + CENT;
    return FORMATTER.format(money);
  }
}