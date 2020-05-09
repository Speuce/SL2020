package main.java.lucia.client.content.menu.legacy.impl;

import com.google.common.base.Preconditions;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import main.java.lucia.client.content.menu.legacy.Item;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.NameBuilder;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.impl.misc.CrustTypes;
import main.java.lucia.client.content.menu.legacy.impl.misc.MiscPrices;
import main.java.lucia.client.content.menu.legacy.impl.misc.Sauce;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadePizza;
import main.java.lucia.client.content.menu.legacy.size.Size;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;
import main.java.lucia.client.content.menu.legacy.toppings.names.GourmetToppingNames;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.io.deserializer.ClientTimeDeserializer;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representation of a pizza item
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public class Pizza extends Item implements Comparable<Pizza> {

  /**
   * The specific id of this pizza on the server.
   * DO NOT SET. Let Gson do that.
   */
  private int rowNum = -1;

  /**
   * The flag which indicates if this pizza is just a cheese pizza, if it is then the price and the
   * name will just be the base price and "Size Cheese" respectively.
   */
  protected boolean justCheese;

  /**
   * The size associated with this pizza.
   */
  protected Size size;

  /**
   * The sauce associated with this pizza, which is default the regular pizza sauce.
   */
  protected Sauce sauce = Sauce.REGULAR;

  /**
   * The crust associated with this pizza, which is default the regular pizza crust.
   */
  protected CrustTypes crust = CrustTypes.REGULAR_CRUST;

  /**
   * The flag which indicates if this Pizza has split halves
   */
  protected boolean splitHalves;

  /**
   * The second half to this pizza, if {@code splitHalves} is {@code true}. Else this variable will
   * be null
   */
  private Pizza secondHalf;

  /**
   * An empty pizza, which does not yet currently have a size
   */
  public Pizza() {
    super(StringUtils.EMPTY);
  }

  /**
   * Checks if this is just a chesee pizza, the special case where the topping should not be added
   * onto the base price for just cheese
   *
   * @return {@code true} if just a cheese pizza, {@code false} otherwise
   */
  public boolean isJustCheese() {
    return justCheese;
  }

  /**
   * A new pizza that is being created with a given size to begin the pizza
   *
   * @param size The size of the pizza
   */
  public Pizza(Size size) {
    super(StringUtils.EMPTY);
    this.size = size;
  }

  /**
   * A new pizza that is being created, with the beginning name.
   *
   * @param name The name
   */
  public Pizza(String name) {
    super(name);
  }

  /**
   * Disables the second half option on this pizza
   */
  public void disableSecondHalf() {
    splitHalves = false;
    secondHalf = null;
  }

  /**
   * Enables the second half option on this pizza
   */
  public void enableSecondHalf() {
    splitHalves = true;
    secondHalf = new Pizza();
    secondHalf.setSize(size);
  }

  public boolean isSecondHalf() {
    return splitHalves;
  }

  /**
   * Gets the second half of this pizza, this second half may be a {@link PremadePizza} {@see
   * instanceof}. Note that this may return null if {@code secondHalf} is false
   *
   * @return The second half of this pizza
   */
  public Pizza getSecondHalf() {
    return secondHalf;
  }

  /**
   * Sets the second half of this pizza, this can be used if the second half of the other pizza is
   * another specialty pizza.
   *
   * @param pizza The pizza to set the second half to
   */
  public void setSecondHalf(Pizza pizza) {
    splitHalves = true;
    secondHalf = pizza;
    secondHalf.setSize(size);
  }

  /**
   * Gets the size of this pizza
   *
   * @return The size
   */
  public Size getSize() {
    return size;
  }

  /**
   * Sets the size to the given size type
   *
   * @param size The given size type
   */
  public void setSize(Size size) {
    this.size = size;
  }

  /**
   * Sets the sauce to the given sauce type
   *
   * @param sauce The given Sauce type
   */
  public void setSauce(Sauce sauce) {
    this.sauce = sauce;
  }

  /**
   * Sets the crust to the given crust type
   *
   * @param crust The given crust type
   */
  public void setCrust(CrustTypes crust) {
    this.crust = crust;
  }

  /**
   * Sets the well done cooked option to the given state
   *
   * @param state The given state
   */
  public void setWellDone(boolean state) {
    removeCookedOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.WELL_DONE);
    }
  }

  /**
   * Sets the under done cooked option to the given state
   *
   * @param state The given state
   */
  public void setUnderDone(boolean state) {
    removeCookedOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.UNDER_DONE);
    }
  }

  /**
   * Sets the light sauce to the given state
   *
   * @param state The given state
   */
  public void setLightSauce(boolean state) {
    removeSauceOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.LIGHT_SAUCE);
    }
  }

  /**
   * Sets the sauce option to none
   *
   * @param state The given state
   */
  public void setNoSauce(boolean state) {
    removeSauceOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.NO_SAUCE);
    }
  }

  /**
   * Sets the extra sauce to the given state
   *
   * @param state The given state
   */
  public void setExtraSauce(boolean state) {
    removeSauceOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.EXTRA_SAUCE);
    }
  }

  /**
   * Sets the no cheese option to the given state
   *
   * @param state The given state
   */
  public void setNoCheese(boolean state) {
    removeCheeseOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.NO_CHEESE);
    }
  }

  /**
   * Sets the easy cheese to the given state
   *
   * @param state The given state
   */
  public void setEasyCheese(boolean state) {
    removeCheeseOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.EASY_CHEESE);
    }
  }

  /**
   * Sets the extra cheese to the given state
   *
   * @param state The given state
   */
  public void setExtraCheese(boolean state) {
    removeCheeseOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.EXTRA_CHEESE);
    }
  }

  /**
   * Sets the extra extra cheese to the given state
   *
   * @param state The given state
   */
  public void setExtraExtraCheese(boolean state) {
    removeCheeseOptions();
    if (state) {
      specialInstructions.add(SpecialInstructions.EXTRA_EXTRA_CHEESE);
    }
  }

  /**
   * Sets the just cheese option, which will ensure that this pizza is just a cheese pizza with no
   * added toppings or anything else of the sort.
   *
   * @param state The just cheese option
   */
  public void setJustCheese(boolean state) {
    justCheese = state;
  }

  /**
   * Removes all cooked options
   */
  private void removeCookedOptions() {
    specialInstructions.removeIf(SpecialInstructions::verifyCookedState);
  }

  /**
   * Removes all sauce options
   */
  private void removeSauceOptions() {
    specialInstructions.removeIf(SpecialInstructions::verifySauce);
  }

  /**
   * Removes all cheese options
   */
  private void removeCheeseOptions() {
    specialInstructions.removeIf(SpecialInstructions::verifyCheese);
  }

  /**
   * Removes all the given options
   */
  public void removeAllOptions() {
    removeCookedOptions();
    removeSauceOptions();
    removeCheeseOptions();
  }

  /**
   * This should be called after getting a new pizza
   */
  @Override
  public Pizza safeClone() {
    return applyValues(this, new Pizza(name));
  }

  /**
   * Applies values from an origin to a clone
   *
   * @param origin The origin pizza
   * @param clone The cloned pizza
   */
  protected Pizza applyValues(Pizza origin, Pizza clone) {
    if (size != null) {
      clone.setSize(size);
    }
    clone.setCrust(origin.crust);
    clone.setSauce(origin.sauce);
    clone.toppingList.addAll(origin.toppingList);
    clone.specialInstructions.addAll(origin.specialInstructions);
    clone.addonList.addAll(origin.addonList);
    return clone;
  }

  @Override
  public void generateName() {
    NameBuilder builder = new NameBuilder();
    ArrayList<SpecialInstructions> crustInstructions = new ArrayList<>();
    ArrayList<SpecialInstructions> toppingInstructions = new ArrayList<>();
    ArrayList<SpecialInstructions> sauceInstructions = new ArrayList<>();

    prepareArraySort(crustInstructions, toppingInstructions, sauceInstructions);
    setInitialValues(builder, crustInstructions, sauceInstructions);
    finalValues(builder, toppingInstructions);

    checkSplitHalves(builder);
  }

  protected void checkSplitHalves(NameBuilder builder) {
    if (splitHalves) {
      builder.buildPizzaSecondHalfFormat(getSecondHalf().getSecondHalfName());
      applyAddons(builder);
      name = builder.getString().trim();
    } else {
      applyAddons(builder);
      name = builder.buildPizzaFormat().trim();
    }
  }

  /**
   * Sorts the one array into three smaller more specializes arrays in order for us to handle the
   * specific instructions to format it nicely
   *
   * @param crustInstructions The crust instructions array
   * @param toppingInstructions The topping instructions array
   * @param sauceInstructions The sauce instructions array
   */
  private void prepareArraySort(ArrayList<SpecialInstructions> crustInstructions,
      ArrayList<SpecialInstructions> toppingInstructions,
      ArrayList<SpecialInstructions> sauceInstructions) {
    for (SpecialInstructions s : specialInstructions) {
      inputToArray(crustInstructions, toppingInstructions, sauceInstructions, s);
    }
  }

  /**
   * Sorts the one array into three smaller more specializes arrays in order for us to handle the
   * specific instructions to format it nicely
   *
   * @param toppingInstructions The topping instructions array
   * @param sauceInstructions The sauce instructions array
   */
  protected void prepareArraySort(ArrayList<SpecialInstructions> toppingInstructions,
      ArrayList<SpecialInstructions> sauceInstructions) {
    for (SpecialInstructions s : specialInstructions) {
      if (s.inToppingArea()) {
        toppingInstructions.add(s);
      } else if (!s.inCrustModifier()) {
        sauceInstructions.add(s);
      }
    }
  }

  /**
   * Sets the initial values, which goes as follows: Size of pizza -> crust options of pizza ->
   * sauce options -> "special name" -> special instructions
   *
   * @param builder The builder we are adding onto
   * @param crustInstructions The crust instructions to add
   * @param sauceInstructions The other instructions to add
   */
  private void setInitialValues(NameBuilder builder,
      ArrayList<SpecialInstructions> crustInstructions,
      ArrayList<SpecialInstructions> sauceInstructions) {
    builder.setSize(size);
    builder.setCrust(crust, true, crustInstructions);
    builder.setSauce(sauce, true, sauceInstructions);
  }

  /**
   * Sets the initial values, which goes as follows: Size of pizza -> crust options of pizza ->
   * sauce options -> "special name" -> special instructions
   *
   * @param builder The builder we are adding onto
   * @param sauceInstructions The other instructions to add
   */
  private void setInitialValuesSecondHalf(NameBuilder builder,
      ArrayList<SpecialInstructions> sauceInstructions) {
    builder.setSauce(sauce, true, sauceInstructions);
  }

  /**
   * Applies the final values such as the toppings information and the topping instruction
   * information to the name builder
   *
   * @param builder The builder we are adding onto
   * @param toppingInstructions The topping instructions we need to add
   */
  private void finalValues(NameBuilder builder,
      ArrayList<SpecialInstructions> toppingInstructions) {
    for (Topping t : toppingList) {
      if (!t.isEasy()) {
        builder.putTopping(t);
      }
    }

    for (SpecialInstructions s : toppingInstructions) {
      builder.putSITopping(s);
    }

    if (justCheese) {
      builder.setCheese();
    }

    for (Topping t : toppingList) {
      if (t.isEasy()) {
        builder.putEasyTopping(t);
      }
    }
  }

  /**
   * Applies the item's addons to the back of the list
   *
   * @param builder The builder we are adding onto
   */
  private void applyAddons(NameBuilder builder) {
    builder.addAddons(addonList);
  }

  /**
   * Calculates the price of the pizza
   */
  @Override
  public void calculatePrice() {
    long totalCents = Menu.loadedToppings.getBasePrices().getPriceForSize(size);
    totalCents += calculateMiscPrices();
    totalCents += calculateXCheese();
    totalCents += calculateToppingsAddons();

    if (splitHalves) {
      Pizza secondHalf = getSecondHalf();
      secondHalf.calcPrice();
      price = Math.max(totalCents, secondHalf.getPriceNonCalculated());
    } else {
      price = totalCents;
    }
  }

  /**
   * Calculates the misc prices, such as if this pizza needs to add additional charge because of
   * greek sauce, a keto selection, or another additional price
   *
   * @return The calculated value
   */
  private long calculateMiscPrices() {
    MiscPrices misc = Menu.loadedToppings.getMiscPrices();
    long totalCents = 0;
    if (sauce.equals(Sauce.GREEK_SAUCE)) {
      if (specialInstructions.contains(SpecialInstructions.EXTRA_SAUCE)) {
        totalCents += (2 * misc.getGreekSauce().getPriceForSize(size));
      } else {
        totalCents += misc.getGreekSauce().getPriceForSize(size);
      }
    }
    if (crust.equals(CrustTypes.GLUTEN_FREE)) {
      totalCents += misc.getGlutenFree();
    } else if (crust.equals(CrustTypes.KETO)) {
      totalCents += misc.getKeto();
    }
    return totalCents;
  }

  /**
   * Calculates the additional price required if extra or extra cheese was added onto the pizza
   *
   * @return The calculated value
   */
  private long calculateXCheese() {
    long totalCents = 0;
    long mozzaPrice = Menu.loadedToppings.getGourmetToppings()
        .get(GourmetToppingNames.MOZZARELLA).getPrice(size);
    for (SpecialInstructions current : specialInstructions) {
      if (current == SpecialInstructions.EXTRA_CHEESE) {
        totalCents += mozzaPrice;
      } else if (current == SpecialInstructions.EXTRA_EXTRA_CHEESE) {
        totalCents += (mozzaPrice * 2);
      }
    }
    return totalCents;
  }

  /**
   * Calculates the additional added toppings
   *
   * @return The calculated value
   */
  private long calculateToppingsAddons() {
    long totalCents = 0;

    for (Addon current : addonList) {
      totalCents += current.getPriceNonCalculated();
    }

    /* Don't bother with the just toppings since it's just a cheese pizza, special case */
    if (justCheese) {
      return totalCents;
    }

    for (Topping current : toppingList) {
      /* Ignore easy cheese since it's a reduction */
      if (current.isEasy() && current.getName()
          .equals(Menu.loadedToppings.get(GourmetToppingNames.MOZZARELLA).getName())) {
        continue;
      }
      totalCents += current.getPrice(size);
    }
    return totalCents;
  }

  /**
   * Gets the second half name, which is calculated slightly differently
   * than the first half of a pizza
   *
   * @return The second half name
   */
  public String getSecondHalfName() {
    NameBuilder builder = new NameBuilder();
    ArrayList<SpecialInstructions> toppingInstructions = new ArrayList<>();
    ArrayList<SpecialInstructions> sauceInstructions = new ArrayList<>();
    prepareArraySort(toppingInstructions, sauceInstructions);
    setInitialValuesSecondHalf(builder, sauceInstructions);
    finalValues(builder, toppingInstructions);

    return builder.buildPizzaFormat();
  }

  /**
   * Inputs the given instructions to their associated arrays
   *
   * @param crustInstructions The crust instructions array
   * @param toppingInstructions The topping instructions array
   * @param sauceInstructions The sauce instructions array
   * @param s The special instruction to sort
   */
  public static void inputToArray(ArrayList<SpecialInstructions> crustInstructions, ArrayList<SpecialInstructions> toppingInstructions, ArrayList<SpecialInstructions> sauceInstructions, SpecialInstructions s) {
    if (s.inToppingArea()) {
      toppingInstructions.add(s);
    } else if (s.inCrustModifier()) {
      crustInstructions.add(s);
    } else {
      sauceInstructions.add(s);
    }
  }

  /**
   * Compares the first pizza to the second pizza
   * depending on the sizes of the two pizzas. Note that
   * this compareTo method checks to confirm the given
   * pizza is not null and reports an error if it is.
   *
   * @param other The other pizza to compare to
   * @return the compareTo result
   */
  @Override
  public int compareTo(Pizza other) {
    Preconditions.checkState(other != null, "The given comparison pizza cannot be null!");
    if (other.getSize() == size) {
      return 0;
    } else {
      return -1 * other.getSize().name().compareTo(size.name()); // Multiply by -1 to reverse the order, so larger pizzas first
    }
  }

  /**
   * Builds a json serializer for pizzas
   * @return the Json Serializer for pizzas
   */
  public static JsonSerializer<Pizza> getJsonSerializer(){
    return new JsonSerializer<Pizza>() {
      @Override
      public JsonElement serialize(Pizza src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject ret = new JsonObject();
        ret.addProperty("type", "Pizza");
        ret.addProperty("rowNum", src.rowNum);
        ret.addProperty("justCheese", src.justCheese);
        ret.addProperty("size", src.getSize().intSize());
        ret.addProperty("sauce", src.sauce.toString());
        ret.addProperty("crust", src.crust.toString());
        ret.addProperty("splitHalves", src.splitHalves);
        ret.addProperty("isJustCheese", src.isJustCheese());
        ret.add("addonList", GsonTypeFactory.BASIC_GSON.toJsonTree(src.addonList));
        //ret.add("alternativeItems", GsonTypeFactory.BASIC_GSON.toJsonTree(src.alternativeItems));
        ret.add("specialInstructions", GsonTypeFactory.BASIC_GSON.toJsonTree(src.specialInstructions));
        ret.add("secondHalf", new GsonBuilder().registerTypeAdapter(Pizza.class, Pizza.getJsonSerializer()).create().toJsonTree(src.getSecondHalf()));
        ret.addProperty("price", src.price);
        ret.addProperty("name", src.name);
        ret.addProperty("discounted", src.isDiscounted());
        //ret.add("builtTime",  new JsonPrimitive(src.builtTime.toString(TimeFormat.FORMATTER_ISO_STANDARD)));

        /*Serialize Topping List */
        Map<String, Integer> toppings = new HashMap<>();
        for(Topping t: src.getToppingList()){
          if(!toppings.containsKey(t.getName())){
            toppings.put(t.getName(), getAmount(t));
          }else{
            int current = toppings.get(t.getName());
            int ne = getAmount(t);
            if(current == -1){
              continue;
            }else if(ne == -1){
              toppings.put(t.getName(), -1);
            }else{
              toppings.put(t.getName(), ne+current);
            }
          }
        }
        JsonObject j = new JsonObject();
        /* add it to ret */
        toppings.forEach(j::addProperty);
        ret.add("toppings", j);

        return ret;
      }
    };
  }

  /**
   * The constructor for the item class which allows the class to be built utilizing {@link
   * Gson} from a JSON file
   *
   * @param toppingList         The list of toppings
   * @param addonList
   * @param specialInstructions The list of special instructions
   * @param name                The name of the item
   * @param price               The price of the item
   */
  public Pizza(ArrayList<Topping> toppingList, ArrayList<Addon> addonList,
               ArrayList<SpecialInstructions> specialInstructions, String name, long price,
               int rowNum, boolean justCheese, Size size, Sauce sauce, CrustTypes crust,
               boolean splitHalves, Pizza secondHalf, ClientTime builtTime) {
    super(toppingList, addonList, specialInstructions, name, price);
    this.rowNum = rowNum;
    this.justCheese = justCheese;
    this.size = size;
    this.sauce = sauce;
    this.crust = crust;
    this.splitHalves = splitHalves;
    this.secondHalf = secondHalf;
    //this.builtTime = builtTime;
  }

  /**
   * Builds the json deserializer for pizza
   * @return the {@link JsonDeserializer} for pizza
   */
  public static JsonDeserializer<Pizza> getJsonDeserializer(){
    return new JsonDeserializer<Pizza>() {
      @Override
      public Pizza deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json == null || json.isJsonNull()){
          return null;
        }
        JsonObject o = (JsonObject) json;
        JsonObject toppings = o.getAsJsonObject("toppings");
        ArrayList<Topping> toppingArrayList = new ArrayList<>();
        toppings.entrySet().forEach(e -> {
          try {
            toppingArrayList.addAll(Topping.fromCompressed(e.getKey(), e.getValue().getAsInt()));
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });
        int rowNum = o.get("rowNum").getAsInt();
        Gson gson = GsonTypeFactory.GENERIC_GSON;
        return new Pizza(toppingArrayList, gson.fromJson(o.get("addonList"), new TypeToken<ArrayList<Addon>>(){}.getType()),
                gson.fromJson(o.get("specialInstructions"), new TypeToken<ArrayList<SpecialInstructions>>(){}.getType()),
                o.get("name").getAsString(), o.get("price").getAsLong(), rowNum, o.get("justCheese").getAsBoolean(),
                Size.fromInt(o.get("size").getAsInt()), Sauce.valueOf(o.get("sauce").getAsString()),
                CrustTypes.valueOf(o.get("crust").getAsString()), o.get("splitHalves").getAsBoolean(),
                new GsonBuilder().registerTypeAdapter(Pizza.class, Pizza.getJsonDeserializer()).create()
                        .fromJson(o.get("secondHalf"), Pizza.class),new GsonBuilder().registerTypeAdapter(ClientTime.class,
                new ClientTimeDeserializer()).create()
                .fromJson(o.get("builtTime"), ClientTime.class));

      }
    };
  }

  private static int getAmount(Topping t){
    if(t.isNegation()){
      return -1;
    }else if(t.isEasy()){
      return 0;
    }else{
      return 1;
    }
  }

  public int getRowNum() {
    return rowNum;
  }
}