package main.java.lucia.client.content.order.impl;

import java.util.ArrayList;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;

/**
 * A class which keeps track of different
 * modifications that occur to an order's items
 *
 * @author Brett Downey
 */
public class ModifiedTracker {

  /**
   * The index for the original item
   */
  private static final int ORIGINAL = 0;

  /**
   * The index for the modified item
   */
  private static final int MODIFIED = 1;

  /**
   * The date when this order was modified
   */
  private String modifiedDate;

  /**
   * The array list of the modified items
   */
  private ArrayList<Item[]> modifiedItems;

  public ModifiedTracker(ArrayList<Item[]> modifiedItems) {
    this.modifiedDate = ClientTime.getCurrentLocalTime()
        .format(TimeFormat.FORMATTER_12_HOUR.getFormat());
    this.modifiedItems = modifiedItems;
  }
}