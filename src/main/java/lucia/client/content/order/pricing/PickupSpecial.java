package main.java.lucia.client.content.order.pricing;

public class PickupSpecial {

  private boolean MONDAY;

  private boolean TUESDAY;

  private boolean WEDNESDAY;

  private boolean THURSDAY;

  private boolean FRIDAY;

  private boolean SATURDAY;

  private boolean SUNDAY;

  public PickupSpecial(boolean MONDAY, boolean TUESDAY, boolean WEDNESDAY, boolean THURSDAY,
      boolean FRIDAY, boolean SATURDAY, boolean SUNDAY) {
    this.MONDAY = MONDAY;
    this.TUESDAY = TUESDAY;
    this.WEDNESDAY = WEDNESDAY;
    this.THURSDAY = THURSDAY;
    this.FRIDAY = FRIDAY;
    this.SATURDAY = SATURDAY;
    this.SUNDAY = SUNDAY;
  }

  public boolean getMonday() {
    return MONDAY;
  }

  public boolean getTuesday() {
    return TUESDAY;
  }

  public boolean getWednesday() {
    return WEDNESDAY;
  }

  public boolean getThursday() {
    return THURSDAY;
  }

  public boolean getFriday() {
    return FRIDAY;
  }

  public boolean getSaturday() {
    return SATURDAY;
  }

  public boolean getSunday() {
    return SUNDAY;
  }
}
