package main.java.lucia.client.content.menu.legacy;

/**
 * A data class to store alternative items and if
 * they are in use or not.
 *
 * @author Brett Downey
 */
public class AlternativeItemPackage {

  /**
   * The item associated to this data package
   */
  private Item alternativeItem;

  /**
   * The flag which indicates if this is within use
   */
  private boolean isInUse;

  /**
   * The constructor for the alternative item package which
   * by default has the is in use flag set to false
   *
   * @param alternativeItem The item to set to the package
   */
  public AlternativeItemPackage(Item alternativeItem) {
    this.alternativeItem = alternativeItem;
    isInUse = false;
  }

  /**
   * The constructor for the alternative item package which takes in the
   * in use state
   *
   * @param alternativeItem The item to set to the package
   * @param isInUse The state of the in use
   */
  public AlternativeItemPackage(Item alternativeItem, boolean isInUse) {
    this.alternativeItem = alternativeItem;
    this.isInUse = isInUse;
  }

  /**
   * Sets the in use state to true
   */
  public void setInUse() {
    isInUse = true;
  }

  /**
   * Sets the in use state to false
   */
  public void setNotInUse() {
    isInUse = false;
  }

  /**
   * Sets the in use state to the given state
   *
   * @param state The given state
   */
  public void setInUseState(boolean state) {
    isInUse = state;
  }

  /**
   * Gets the in use flag
   *
   * @return {@code true} if this alternative item is within use,
   *          {@code false} otherwise.
   */
  public boolean isInUse() {
    return isInUse;
  }

  /**
   * Gets the alternative item associated with this
   *
   * @return The alternative item
   */
  public Item getAlternativeItem() {
    return alternativeItem;
  }

  /**
   * Safe clones this alternative item package so that the item within it
   * can be used without modifying any other instances of this
   *
   * @return The safe clone
   */
  public AlternativeItemPackage safeClone() {
    return new AlternativeItemPackage(alternativeItem.safeClone(), isInUse);
  }
}