package main.java.lucia.client.content.menu;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import main.java.lucia.client.content.menu.legacy.impl.misc.CrustTypes;
import main.java.lucia.client.content.menu.legacy.impl.misc.Sauce;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.legacy.size.Size;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;

/**
 * The name builder
 *
 * @author Brett Downey
 */
public class NameBuilder {

  /**
   * The StringBuilder instance to allow efficient string building
   */
  private StringBuilder builder;

  /**
   * The flag which keeps track of this NameBuilder to see if it's began topping addition
   */
  private boolean beganAddition;

  /**
   * The flag which keeps track of this NameBuilder to see if it's began topping negation
   */
  private boolean beganNegation;

  /**
   * The flag which keeps track of this NameBuilder to see if it's began adding specials
   */
  private boolean beganSpecials;

  /**
   * Starts a new NameBuilding process
   */
  public NameBuilder() {
    builder = new StringBuilder();
  }

  /**
   * Sets the size of the item, this method call should be called first if a size is an application
   * option.
   *
   * @param size The size of the item
   * @return this
   */
  public NameBuilder setSize(Size size) {
    builder.append(size.intSize());
    builder.append("\" ");
    return this;
  }

  /**
   * Sets the special name, for example if the item was an "SLS". This should be called after size
   * if this is an application option
   *
   * @param name The special name
   * @return This
   */
  public NameBuilder setSpecialName(String name) {
    if (name != null && !name.isEmpty()) {
      builder.append(name);
      builder.append(" ");
    }
    return this;
  }

  /**
   * Sets the crust of the item, this should be called after set size
   *
   * @param crust The crust of the item
   * @param checkForRegular Checks if we should verify we are not adding the regular type
   * @param instructions The special instructions to add within the bracket
   * @return this
   */
  public NameBuilder setCrust(CrustTypes crust, boolean checkForRegular, ArrayList<SpecialInstructions> instructions) {
    if((checkForRegular && crust == CrustTypes.REGULAR_CRUST)) {
      applySpecialInstructions(instructions);
      return this;
    } else {
      builder.append("<");
      builder.append(crust.name());
      applySpecialInstructions(instructions);
      builder.append("> ");
      return this;
    }
  }

  private void applySpecialInstructions(ArrayList<SpecialInstructions> toApply) {
    if(toApply != null && toApply.size() > 0) {
      for(SpecialInstructions s : toApply) {
        builder.append(" ");
        builder.append(s.getSimpleName());
      }
    }
  }

  /**
   * Sets the crust of the item, this should be called after set size
   *
   * @param instructions The special instructions to add within the bracket
   * @return this
   */
  public NameBuilder setCrust(ArrayList<SpecialInstructions> instructions) {
    if(instructions != null && instructions.size() > 0) {
      builder.append("<");
      for(SpecialInstructions s : instructions) {
        builder.append(" ");
        builder.append(s.getSimpleName());
      }
      builder.append("> ");
    }
    return this;
  }

  /**
   * Sets the sauce of this item, this should be called after set crust
   *
   * @param sauce The sauce of the item
   * @param checkForRegular Checks if we should verify we are not adding the regular type
   * @param instructions The instructions to add to the sauce section
   * @return this
   */
  public NameBuilder setSauce(Sauce sauce, boolean checkForRegular, ArrayList<SpecialInstructions> instructions) {
    if(checkForRegular && sauce == Sauce.REGULAR) {
      return this;
    } else {
      builder.append("<");
      builder.append(sauce.getSimpleName());
      applySpecialInstructions(instructions);
      builder.append("> ");
      return this;
    }
  }

  /**
   * Sets the sauce of this item, this should be called after set crust
   *
   * @param instructions The instructions to add to the sauce section
   * @return this
   */
  public NameBuilder setSauce(ArrayList<SpecialInstructions> instructions) {
    if(instructions == null || instructions.size() == 0) {
      return this;
    }
    builder.append("<");
    for(SpecialInstructions s : instructions) {
      builder.append(" ");
      builder.append(s.getSimpleName());
    }
    builder.append("> ");
    return this;
  }

  /**
   * Begins the topping addition selection
   *
   * @return this
   */
  public NameBuilder beginToppingAddition() {
    if (beganAddition) {
      return this;
    }
    beganAddition = true;
    builder.append("+ ");
    return this;
  }

  /**
   * Begins the topping negation selection
   *
   * @return this
   */
  public NameBuilder beginToppingNegation() {
    if (beganNegation) {
      return this;
    }
    beganNegation = true;
    builder.append("- ");
    return this;
  }

  /**
   * Begins the special instructions section
   *
   * @return this
   */
  public NameBuilder endSpecialInstructions() {
    if(!beganSpecials) {
      return this;
    }
    builder.append("| ");
    return this;
  }

  /**
   * Sets the "Cheese" option on the pizza, since there
   * is no actual "cheese" topping, this is here to add
   * the cheese option if the pizza is {@code justCheese}
   *
   * @return this
   */
  public NameBuilder setCheese() {
    builder.append("Cheese Pizza");
    builder.append(" ");
    return this;
  }

  /**
   * Adds a topping to the item, this method should be after the sauce and before special
   * instructions
   *
   * @param topping The topping to add the list
   * @return this
   */
  public NameBuilder putTopping(Topping topping) {
    builder.append(topping.getSimpleName());
    builder.append(" ");
    return this;
  }

  /**
   * Adds a topping to the item, this method should be after the sauce and before special
   * instructions. This is for easy toppings
   *
   * @param topping The topping to add the list
   * @return this
   */
  public NameBuilder putEasyTopping(Topping topping) {
    builder.append("EZ");
    builder.append(topping.getSimpleName());
    builder.append(" ");
    return this;
  }

  /**
   * Adds the toppings required within the special instructions section
   *
   * @param specialInstruction The special instructions to add
   * @return this
   */
  public NameBuilder putSITopping(SpecialInstructions specialInstruction) {
    if(!builder.toString().contains(specialInstruction.getSimpleName())) {
      builder.append(specialInstruction.getSimpleName());
      builder.append(" ");
    }
    return this;
  }

  public NameBuilder addAddons(ArrayList<Addon> addons) {
    if(addons == null || addons.size() == 0) {
      return this;
    } else {
      builder.append("W/ ");
      for(Addon a : addons) {
        builder.append(a);
        builder.append(" ");
      }
      return this;
    }
  }

  private NameBuilder addSecondHalf(String secondHalfComplete) {
    builder.append("*** ");
    builder.append(secondHalfComplete);
    return this;
  }

  /**
   * Builds the pizza for the second half format
   *
   * @return this
   */
  public NameBuilder buildPizzaSecondHalfFormat(String secondHalfComplete) {
    this.builder = new StringBuilder(buildPizzaFormat());
    addSecondHalf(secondHalfComplete);
    return this;
  }

  /**
   * Builds the final name
   *
   * @return The name
   */
  public String buildPizzaFormat() {
    String nonFormatted = builder.toString();
    LinkedHashMap<String, Integer> occurrences = new LinkedHashMap<>();

    for(String word : nonFormatted.split(" ")) {
      Integer oldCount = occurrences.get(word);
      if (oldCount == null) {
        oldCount = 0;
      }
      occurrences.put(word, oldCount + 1);
    }

    StringBuilder formatted = new StringBuilder();
    occurrences.forEach((piece, count) -> {
      if(count > 1) {
        StringBuilder builder = new StringBuilder();
        for(int x = count; x > 1; x--) {
          builder.append("X");
        }
        piece = builder.toString() + piece + " ";
      } else {
        piece += " ";
      }
      formatted.append(piece);
    });

    return formatted.toString()
        .replace("+ ", "+")
        .replace("- ", "-")
        .replace("< ", "<");
  }

  public String getString() {
    return builder.toString();
  }
}