package main.java.lucia.client.content.order;

import java.time.Month;

import main.java.lucia.client.content.order.impl.PrintableOrder;

import java.util.ArrayList;

import main.java.lucia.client.content.order.impl.ModifiedTracker;
import main.java.lucia.client.content.order.impl.SpecialOrderInstructions;
import main.java.lucia.client.content.time.TimeFormat;

/**
 * A representation of an order
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 * @author Zachery Unrau
 */
public class Order extends PrintableOrder {

  /**
   * The default time format which is used by the Order class
   */
  private static final transient TimeFormat DEFAULT_TIME_FORMAT = TimeFormat.FORMATTER_12_HOUR;

  /**
   * The specific id of this pizza on the server.
   * DO NOT SET. Let Gson do that.
   */
  private int rowNum = -1;

  /**
   * Dont touch this either.
   */
  private String type = "foodOrder";

  /**
   * The list of special instructions an order can contain
   */
  private ArrayList<SpecialOrderInstructions> specialOrderInstructions;

  /**
   * An array of modified trackers, which keep track of which items were modified and how they were
   * modified
   */
  private ArrayList<ModifiedTracker> modifiedTrackers;

  private boolean isStaffOrder = false;

  private double staffDiscount;

  /**
   * Creates a new order, initializes important values and sets the creation date.
   */
  public Order(OrderType type) {
    super();
    setOrderType(type);
    this.specialOrderInstructions = new ArrayList<>();
    this.modifiedTrackers = new ArrayList<>();
  }

  /**
   * Creates a new order, which is a pre order
   *
   * @param type The type of order to create
   * @param year The year for the pre order
   * @param month The month for the pre order
   * @param dayOfMonth The day of the month for the pre order
   * @param hour The hour for the pre order
   * @param minute The minutes for the pre order
   */
  public Order(OrderType type, int year, Month month, int dayOfMonth, int hour, int minute) {
    this(type);
    this.setPreorder(true);
    this.setPreorderTime(year, month, dayOfMonth, hour, minute);
  }

  public void addStaffDiscount(boolean onShift) {
    if(onShift)
      staffDiscount = .25;
    else staffDiscount = .15;
    isStaffOrder = true;
  }

  public int getRowNum() {
    return rowNum;
  }
}