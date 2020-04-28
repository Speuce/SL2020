package main.java.lucia.client.content.menu.legacy.toppings;

import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.Pizza;
import main.java.lucia.client.content.menu.legacy.size.Size;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * A representation of a topping, which consists
 * of a few basic values
 *
 * @author Brett Downey
 */
public class Topping {

  /**
   * The full name of the topping
   */
  private final String NAME;

  /**
   * The simple name of the topping, which is used to represent the entire order in a smaller,
   * neater fashion
   */
  private final String SIMPLE_NAME;

  /**
   * The tier of the topping, which is used to distinguish it's pricing bracket
   */
  private final ToppingType TIER;

  /**
   * A flag representing if this topping has an "easy" status, meaning there will be less of the
   * said topping when the topping is applied to whichever item it is within
   */
  private boolean easy;

  /**
   * A flag which represents if this topping is a negation topping or not. If it is a negation
   * topping, it will display on the name {@code -simpleName}
   */
  private transient boolean negation;

  /**
   * The constructor for a topping which takes mandatory values
   *
   * @param NAME The mandatory name value
   * @param SIMPLE_NAME The mandatory simple name value
   * @param TIER The mandatory pricing tier
   */
  public Topping(final String NAME, final String SIMPLE_NAME, final ToppingType TIER) {
    this.NAME = NAME;
    this.SIMPLE_NAME = SIMPLE_NAME;
    this.TIER = TIER;
  }

  /**
   * Returns the full name of this Topping
   *
   * @return The full name
   */
  public String getName() {
    return NAME;
  }

  /**
   * Returns the simple name of this Topping
   *
   * @return The simple name
   */
  public String getSimpleName() {
    return SIMPLE_NAME;
  }

  /**
   * Returns the {@link ToppingType} which represents the pricing for this topping
   *
   * @return The pricing tier for this
   */
  public ToppingType getTier() {
    return TIER;
  }

  /**
   * Determines if this is an "easy" flagged topping or not
   *
   * @return The {@code easy} flag
   */
  public boolean isEasy() {
    return easy;
  }

  /**
   * Sets ths topping to be an "easy" status depending on the given state
   */
  public Topping setEasy(boolean state) {
    easy = state;
    return this;
  }

  /**
   * Sets this negation flag to the given value
   *
   * @param flag the value to give {@code negation}
   */
  public Topping setNegation(boolean flag) {
    negation = flag;
    return this;
  }

  /**
   * Gets if this is a negation topping
   *
   * @return The negation
   */
  public boolean isNegation() {
    return negation;
  }

  /**
   * Gets the price of this topping, depending on it's tier and pizza size. The price is loaded from
   * the {@link Menu}
   *
   * @param pizza The given pizza the topping is applied to
   * @return The int value for the price
   */
  public int getPrice(Pizza pizza) {
    return Menu.loadedToppings.getPrices().get(TIER).getPriceForSize(pizza.getSize());
  }

  /**
   * Gets the price of this topping, depending on it's tier and pizza size. The price is loaded from
   * the {@link Menu}
   *
   * @param size The given size
   * @return The int value for the price
   */
  public int getPrice(Size size) {
    return Menu.loadedToppings.getPrices().get(TIER).getPriceForSize(size);
  }

  public Topping toppingClone() {
    Topping clone = new Topping(NAME, SIMPLE_NAME, TIER);
    clone.setNegation(negation);
    clone.setEasy(easy);

    return clone;
  }

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject instanceof Topping) {
      Topping otherTopping = (Topping) otherObject;
      return getName().equals(otherTopping.getName()) && (isNegation() == otherTopping
          .isNegation()) && (isEasy() == otherTopping.isEasy());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31)
        .append(getName())
        .append(isNegation())
        .append(isEasy())
        .toHashCode();
  }

  /**
   * Gets a topping from its compressed state
   * @param type the string representing the type of topping
   * @param amt the amount of topping
   * @return the topping (or toppings) represented
   */
  public static Set<Topping> fromCompressed(String type, int amt) throws Exception {
    Set<Topping> ret = new HashSet<Topping>();
    if(amt == -1){
      ret.add(Menu.loadedToppings.get(type).setNegation(true));
    }else if(amt == 0){
      ret.add(Menu.loadedToppings.get(type).setEasy(true));
    }else{
      while(amt > 0){
        ret.add(Menu.loadedToppings.get(type));
        amt--;
      }
    }
    return ret;
  }
}