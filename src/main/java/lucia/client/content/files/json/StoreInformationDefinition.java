package main.java.lucia.client.content.files.json;

import com.google.gson.GsonBuilder;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

/**
 * A class which loads in the JSON information for hours.json
 *
 * @author Brett Downey
 */
public class StoreInformationDefinition {

  /**
   * The map containing all our {@link StoreInformationDefinition}s.
   */
  public static final Map<String, StoreInformationDefinition> definitions = new HashMap<>();

  /**
   * The store name.
   */
  private final String STORE;

  /**
   * The open and close times for Monday.
   */
  private final OpenAndClose MONDAY;

  /**
   * The open and close times for Tuesday.
   */
  private final OpenAndClose TUESDAY;

  /**
   * The open and close times for Wednesday.
   */
  private final OpenAndClose WEDNESDAY;

  /**
   * The open and close times for Thursday.
   */
  private final OpenAndClose THURSDAY;

  /**
   * The open and close times for Friday.
   */
  private final OpenAndClose FRIDAY;

  /**
   * The open and close times for Saturday.
   */
  private final OpenAndClose SATURDAY;

  /**
   * The open and close times for Sunday.
   */
  private final OpenAndClose SUNDAY;

  /**
   * The constructor for this StoreInformationDefinition which is used by the {@link GsonBuilder}
   */
  public StoreInformationDefinition(String store, OpenAndClose monday, OpenAndClose tuesday,
      OpenAndClose wednesday,
      OpenAndClose thursday, OpenAndClose friday, OpenAndClose saturday, OpenAndClose sunday) {
    this.STORE = store;
    this.MONDAY = monday;
    this.TUESDAY = tuesday;
    this.WEDNESDAY = wednesday;
    this.THURSDAY = thursday;
    this.FRIDAY = friday;
    this.SATURDAY = saturday;
    this.SUNDAY = sunday;
  }

  /**
   * Returns the store name.
   *
   * @return The store name
   */
  public String getStore() {
    return STORE;
  }

  /**
   * Returns the {@link OpenAndClose} hours for the given {@link DayOfWeek}
   *
   * @param day The given day
   * @return The given hours for the {@code day}
   */
  public OpenAndClose getHours(DayOfWeek day) {
    switch (day) {
      case MONDAY:
        return this.MONDAY;
      case TUESDAY:
        return this.TUESDAY;
      case WEDNESDAY:
        return this.WEDNESDAY;
      case THURSDAY:
        return this.THURSDAY;
      case FRIDAY:
        return this.FRIDAY;
      case SATURDAY:
        return this.SATURDAY;
      case SUNDAY:
        return this.SUNDAY;
      default:
        return null;
    }
  }

  /**
   * A public nested class which is used by the {@link GsonBuilder}
   */
  public class OpenAndClose {

    /**
     * A String that represents the opening hours
     */
    private final String opening;

    /**
     * A String that represents the closing hours
     */
    private final String closing;

    /**
     * The constructor for this OpenAndClose which is used by the {@link GsonBuilder}
     */
    public OpenAndClose(String opening, String closing) {
      this.opening = opening;
      this.closing = closing;
    }

    /**
     * Gets the opening hours String
     *
     * @return The opening hours
     */
    public String getOpening() {
      return opening;
    }

    /**
     * Gets the closing hours String
     *
     * @return The closing hours
     */
    public String getClosing() {
      return closing;
    }
  }
}