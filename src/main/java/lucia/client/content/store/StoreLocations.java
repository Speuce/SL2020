package main.java.lucia.client.content.store;

import java.time.DayOfWeek;
import main.java.lucia.client.content.files.json.StoreInformationDefinition;
import main.java.lucia.client.content.files.json.StoreInformationDefinition.OpenAndClose;
import main.java.lucia.client.content.order.impl.Address;

/**
 * An enumerated type representing the different store locations for Santa Lucia.
 *
 * @author Brett Downey
 */
public enum StoreLocations {

  /**
   * The element used to represent no given store location
   */
  NONE(null, null, false),

  /**
   * The element used to represent the St. Mary's location
   */
  STMARYS("STMARYS", new Address("4 St. Mary's Rd", "49.8808498", "-97.1283796"), true),

  /**
   * The element used to represent the Main Street location
   */
  MAINSTREET("MAINSTREET", new Address("1473 Main Street", "49.9727734", "-97.0733578"), true),

  /**
   * The element used to represent the Pembina Highway location
   */
  PEMBINA("PEMBINA", new Address("2589 Pembina Hwy", "49.7464928", "-97.1398779"), false),

  /**
   * The element used to represent the Corydon location
   */
  CORYDON("CORYDON", new Address("905 Corydon Avenue", "49.8689127", "-97.1596252"), true),

  /**
   * The element used to represent the Portage location
   */
  PORTAGE("PORTAGE", new Address("2029 Portage Avenue", "49.8948528", "-97.1359822"), false),

  /**
   * The element used to represent the Henderson location
   */
  HENDERSON("HENDERSON", new Address("805 Henderson Hwy", "49.9115597", "-97.11703"), false),

  /**
   * The element used to represent the Regent Avenue East location
   */
  REGENT("REGENT", new Address("108 Regent Ave East", "49.8950993", "-96.9957258"), false),

  /**
   * The element used to represent the Morris Manitoba location
   */
  MORRIS_MANITOBA("MORRIS_MANITOBA", new Address("Morris Manitoba", "49.3510282", "-97.3644057"),
      true),

  /**
   * The element used to represent the Brandon Manitoba location
   */
  BRANDON_MANITOBA("BRANDON_MANITOBA", new Address("Brandon Manitoba", "49.829342", "-99.961709"),
      false),

  /**
   * The element used to represent the Saskatoon East location
   */
  SASKATOON_EAST("SASKATOON_EAST", new Address("Saskatoon East", "52.114713", "-106.593942"),
      false),

  /**
   * The element used to represent the Saskatoon West location
   */
  SASKATOON_WEST("SASKATOON_WEST", new Address("Saskatoon West", "52.129315", "-106.704256"),
      false);

  /**
   * The name associated with this element.
   */
  private final String NAME;

  /**
   * The address associated with this element.
   */
  private final Address ADDRESS;

  /**
   * A boolean representing if this location has dine-in options.
   */
  private final boolean DINE_IN;

  StoreLocations(String name, Address address, boolean dineIn) {
    this.NAME = name;
    this.ADDRESS = address;
    this.DINE_IN = dineIn;
  }

  /**
   * Gets the name associated with this store
   *
   * @return The store's name
   */
  public String getName() {
    return NAME;
  }

  /**
   * Gets the address associated with the Store Location
   *
   * @return The Store location's address
   */
  public Address getAddress() {
    return ADDRESS;
  }

  /**
   * Gets the boolean value stating if this Store Location has a dine in option
   *
   * @return The Store Location's dine in status
   */
  public boolean getDineIn() {
    return DINE_IN;
  }

  /**
   * Gets the opening and closing hours for the associated store with the associated day.
   *
   * @return The {@link OpenAndClose} which
   * contains the opening and closing hours within String format.
   */
  public StoreInformationDefinition.OpenAndClose getHours(DayOfWeek day) {
    return StoreInformationDefinition.definitions.get(getName()).getHours(day);
  }
}