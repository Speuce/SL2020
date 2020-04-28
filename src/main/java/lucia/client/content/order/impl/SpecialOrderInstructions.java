package main.java.lucia.client.content.order.impl;

/**
 * An enumerated type of different special
 * order instructions
 *
 * @author Brett Downey
 * @author Zachery Unrau
 */
public enum SpecialOrderInstructions {

  CALL_WHEN_THERE("Call When There"),

  WALK_IN("Walk In"),

  SIDE_DOOR("Go to Side Door"),

  BACK("Go to Back Door"),

  ALLERGY("**Allergy**"),

  ALTERNATE_PICKUP_NAME("Alternate Pickup Name");

  private final String formattedText;

  SpecialOrderInstructions(final String formattedText) {
    this.formattedText = formattedText;
  }

  public String getFormattedText() {
    return formattedText;
  }
}