package main.java.lucia.client.content.menu.legacy;

/**
 * An enumerated class representing the different
 * possible pizza sizes.
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public enum Size {

  /**
   * 9 inch pizza
   */
  NINE(9),

  /**
   * 10 inch pizza
   */
  TEN(10),

  /**
   * 13 inch pizza
   */
  THIRTEEN(13),

  /**
   * 15 inch pizza
   */
  FIFTEEN(15),

  /**
   * 18 inch pizza
   */
  EIGHTEEN(18),

  /**
   * 30 inch pizza
   */
  THIRTY(30);

  private final int size;

  Size(final int size) {
    this.size = size;
  }

  public int intSize() {
    return size;
  }

  /**
   * Gets the size with the given integer size
   * @param size the size to find
   * @return the Size with the given integer size
   */
  public static Size fromInt(int size){
    for(Size s: Size.values()){
      if(s.size == size){
        return s;
      }
    }
    return null;
  }
}