package main.java.lucia.client.content.order.pricing;

import java.util.HashSet;
import main.java.lucia.client.content.menu.legacy.impl.Pizza;
import main.java.lucia.client.content.menu.legacy.size.Size;

public class HalfOff {

  private static HashSet<Size> NON_DISCOUNTED_SIZES;

  private boolean nine;

  private boolean ten;

  private boolean thirteen;

  private boolean fifteen;

  private boolean eighteen;

  private boolean thirty;

  public HalfOff(boolean nine, boolean ten, boolean thirteen, boolean fifteen, boolean eighteen,
      boolean thirty) {
    this.nine = nine;
    this.ten = ten;
    this.thirteen = thirteen;
    this.fifteen = fifteen;
    this.eighteen = eighteen;
    this.thirty = thirty;
  }

  public static HashSet<Size> getNonDiscountedSizes() {
    return NON_DISCOUNTED_SIZES;
  }

  public void calculateNonDiscountedSizes() {
    NON_DISCOUNTED_SIZES = new HashSet<>();
    if(!nine) {
      NON_DISCOUNTED_SIZES.add(Size.NINE);
    }

    if(!ten) {
      NON_DISCOUNTED_SIZES.add(Size.TEN);
    }

    if(!thirteen) {
      NON_DISCOUNTED_SIZES.add((Size.THIRTEEN));
    }

    if(!fifteen) {
      NON_DISCOUNTED_SIZES.add(Size.FIFTEEN);
    }

    if(!eighteen) {
      NON_DISCOUNTED_SIZES.add(Size.EIGHTEEN);
    }

    if(!thirty) {
      NON_DISCOUNTED_SIZES.add(Size.THIRTY);
    }
  }

  public boolean check(Pizza pizza) {
    for(Size size : NON_DISCOUNTED_SIZES) {
      if(pizza.getSize() == size) {
        return false;
      }
    }

    return true;
  }

  public boolean nineInchDiscount() {
    return nine;
  }

  public boolean tenInchDiscount() {
    return ten;
  }

  public boolean thirteenInchDiscount() {
    return thirteen;
  }

  public boolean fifteenInchDiscount() {
    return fifteen;
  }

  public boolean eighteenInchDiscount() {
    return eighteen;
  }

  public boolean thirtyInchDiscount() {
    return thirty;
  }

  public void setNineInchDiscount(boolean NINE) {
    this.nine = NINE;
  }

  public void setTenInchDiscount(boolean TEN) {
    this.ten = TEN;
  }

  public void setThirteenInchDiscount(boolean THIRTEEN) {
    this.thirteen = THIRTEEN;
  }

  public void setFifteenInchDiscount(boolean FIFTEEN) {
    this.fifteen = FIFTEEN;
  }

  public void setEighteenInchDiscount(boolean EIGHTEEN) {
    this.eighteen = EIGHTEEN;
  }

  public void setThirtyInchDiscount(boolean THIRTY) {
    this.thirty = THIRTY;
  }
}