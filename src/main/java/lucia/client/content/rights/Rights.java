package main.java.lucia.client.content.rights;

import java.util.EnumSet;
import java.util.Set;

/**
 * The enumerated type whose elements represent the different types of rights for connected
 * clients.
 *
 * @author Brett Downey
 */
public enum Rights {

  /**
   * No permissions, or no known permissions.
   * No received commands or operation codes will
   * be processed with this Rights value.
   */
  NONE(0),

  /**
   * The permission given to developers.
   */
  DEVELOPER(5),

  /**
   * A kitchen staff, this ranking holds the same
   * permission as {@code DRIVER}
   */
  KITCHEN(2),

  /**
   * A driver staff, this ranking holds the same
   * permission as {@code KITCHEN}
   */
  DRIVER(2),

  /**
   * A customer, which is able to place an order.
   */
  CUSTOMER(1),

  /**
   * The rights given to a store manager
   */
  MANAGER(3),

  /**
   * A statistics account, which is used to view
   * any store and view it's statistics about
   * them
   */
  STATISTICS(4),

  /**
   * The rights given to a store owner
   */
  OWNER(5),

  /**
   * The restricted rights, which holds the same
   * permission value as {@code NONE}.
   */
  RESTRICTED(0),

  /**
   * The banned rights, which will result in a disconnection
   * to any user that holds this value.
   */
  BANNED(0);

  /**
   * The value associated with this rank
   */
  private int value;

  Rights(int value) {
    this.value = value;
  }

  /**
   * A list used to represent staff members
   */
  private static Set<Rights> STAFF = EnumSet.of(DEVELOPER, KITCHEN, DRIVER);

  /**
   * A list used to represent clients within a restricted state
   */
  private static Set<Rights> NO_PERMISSIONS = EnumSet.of(NONE, RESTRICTED, BANNED);

  /**
   * Gets whether this is a staff member
   *
   * @return The right's staff member status
   */
  public boolean isStaff() {
    return STAFF.contains(this);
  }

  /**
   * Gets whether this is a restricted member
   *
   * @return The right's restricted status
   */
  public boolean isRestricted() {
    return NO_PERMISSIONS.contains(this);
  }

  /**
   * Gets whether this is a customer
   *
   * @return The right's customer status
   */
  public boolean isCustomer() {
    return this == CUSTOMER;
  }
}