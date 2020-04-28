package main.java.lucia.client.content.menu.legacy.premade.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.NameBuilder;
import main.java.lucia.client.content.menu.legacy.PriceForSize;
import main.java.lucia.client.content.menu.legacy.impl.Pizza;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.impl.misc.CrustTypes;
import main.java.lucia.client.content.menu.legacy.impl.misc.MiscPrices;
import main.java.lucia.client.content.menu.legacy.impl.misc.Sauce;
import main.java.lucia.client.content.menu.legacy.instructions.SpecialInstructions;
import main.java.lucia.client.content.menu.legacy.premade.Premade;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadePizzaName;
import main.java.lucia.client.content.menu.legacy.size.Size;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;

import main.java.lucia.client.content.menu.legacy.toppings.names.GourmetToppingNames;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;
import main.java.lucia.client.structures.Exclude;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

/**
 * A class which represents a pre-made pizza
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public class PremadePizza extends Pizza implements Premade {

  /**
   * The pricing strategy for this pizza
   */
  @Exclude
  private final PriceForSize PRICE;

  /**
   * The origin name of this premade pizza
   */
  private final transient String ORIGIN_NAME;

  /**
   * The constructor for a premade pizza which requires a given price for size calculation and a
   * name for the pizza.
   *
   * @param PRICE The price of the pizza, depending on which size
   * @param NAME The name of the speciality pizza
   */
  public PremadePizza(final String NAME, final PriceForSize PRICE) {
    super(NAME);
    this.PRICE = PRICE;

    this.ORIGIN_NAME = NAME;
  }

  /**
   * Gets the price for the given side of the pizza
   *
   * @return The PriceForSize object that represents the prices for each size
   */
  public PriceForSize getPriceForSize(){
      return PRICE;
  }

  /**
   * Gets the price for the given side of the pizza
   *
   * @param size The given size
   * @return The int value of the price for all total cents
   */
  public int getPriceForSize(Size size) {
    return PRICE.getPriceForSize(size);
  }

  /**
   * This should be called after getting a new pizza
   */
  @Override
  public PremadePizza safeClone() {
    return (PremadePizza) applyValues(this, new PremadePizza(name, PRICE));
  }

  @Override
  public void generateName() {
    NameBuilder builder = new NameBuilder();
    PremadePizza origin = Menu.loadedPreMadeFoods.getPremadePizzas()
        .get(PremadePizzaName.valueOf(ORIGIN_NAME.toUpperCase()));
    ArrayList<SpecialInstructions> crustInstructions = new ArrayList<>();
    ArrayList<SpecialInstructions> toppingInstructions = new ArrayList<>();
    ArrayList<SpecialInstructions> sauceInstructions = new ArrayList<>();

    prepareArraySort(origin, crustInstructions, toppingInstructions, sauceInstructions);
    setInitialValues(origin, builder, crustInstructions, sauceInstructions);
    finalValues(origin, builder, toppingInstructions);

    checkSplitHalves(builder);
  }

  /**
   * Sorts the one array into three smaller more specializes arrays in order for us to handle the
   * specific instructions to format it nicely
   *
   * @param origin The origin premade pizza
   * @param crustInstructions The crust instructions array
   * @param toppingInstructions The topping instructions array
   * @param sauceInstructions The sauce instructions array
   */
  public void prepareArraySort(PremadePizza origin,
      ArrayList<SpecialInstructions> crustInstructions,
      ArrayList<SpecialInstructions> toppingInstructions,
      ArrayList<SpecialInstructions> sauceInstructions) {
    for (SpecialInstructions s : specialInstructions) {
      if (origin.specialInstructions.contains(s)) {
        continue;
      }
        Pizza.inputToArray(crustInstructions, toppingInstructions, sauceInstructions, s);
    }
  }

  /**
   * Sets the initial values, which goes as follows: Size of pizza -> crust options of pizza ->
   * sauce options -> "special name" -> special instructions
   *
   * @param origin The origin premade pizza
   * @param builder The builder we are adding onto
   * @param crustInstructions The crust instructions to add
   * @param sauceInstructions The sauce instructions to add
   */
  private void setInitialValues(PremadePizza origin, NameBuilder builder,
      ArrayList<SpecialInstructions> crustInstructions,
      ArrayList<SpecialInstructions> sauceInstructions) {
    builder.setSize(size);

    if (origin.crust != crust) {
      builder.setCrust(crust, false, crustInstructions);
    } else {
      builder.setCrust(crustInstructions);
    }

    if (origin.sauce != sauce) {
      builder.setSauce(sauce, false, sauceInstructions);
    } else {
      builder.setSauce(sauceInstructions);
    }

    builder.setSpecialName(PremadePizzaName.valueOf(ORIGIN_NAME.toUpperCase()).getSimpleName());
  }

  /**
   * Sets the initial values, which goes as follows: Size of pizza -> crust options of pizza ->
   * sauce options -> "special name" -> special instructions
   *
   * @param builder The builder we are adding onto
   * @param sauceInstructions The other instructions to add
   */
  private void setInitialValuesSecondHalf(PremadePizza origin, NameBuilder builder,
      ArrayList<SpecialInstructions> sauceInstructions) {
    if (origin.sauce != sauce) {
      builder.setSauce(sauce, false, sauceInstructions);
    } else {
      builder.setSauce(sauceInstructions);
    }

    builder.setSpecialName(PremadePizzaName.valueOf(ORIGIN_NAME.toUpperCase()).getSimpleName());
  }

  /**
   * Applies the final values such as the toppings information and the topping instruction
   * information to the name builder
   *
   * @param origin The origin premade pizza
   * @param builder The builder we are adding onto
   * @param toppingInstructions The topping instructions we need to add
   */
  private void finalValues(PremadePizza origin, NameBuilder builder,
      ArrayList<SpecialInstructions> toppingInstructions) {

    ArrayList<Topping> nonCollision = new ArrayList<>(toppingList);
    PremadeItem.filterAndPut(builder, toppingInstructions, nonCollision, origin.toppingList);

    if(justCheese) {
      builder.setCheese();
    }

    for (Topping t : nonCollision) {
      if (t.isNegation()) {
        builder.beginToppingNegation();
        builder.putTopping(t);
      }
    }
  }

  /**
   * Calculates the price of the pizza
   */
  @Override
  public void calculatePrice() {
    PremadePizza origin = Menu.loadedPreMadeFoods.getPremadePizzas()
        .get(PremadePizzaName.valueOf(ORIGIN_NAME.toUpperCase()));

    long totalCents = PRICE.getPriceForSize(size);
    totalCents += calculateMiscPrices(origin);
    totalCents += calculateXCheese(origin);
    totalCents += calculateToppingsAddons(origin);

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
   * @param origin The origin premade pizza
   * @return The calculated value
   */
  private long calculateMiscPrices(PremadePizza origin) {
    MiscPrices misc = Menu.loadedToppings.getMiscPrices();
    long totalCents = 0;
    if (sauce.equals(Sauce.GREEK_SAUCE) && sauce != origin.sauce) {
      if (specialInstructions.contains(SpecialInstructions.EXTRA_SAUCE)
          && !origin.specialInstructions.contains(SpecialInstructions.EXTRA_SAUCE)) {
        totalCents += (2 * misc.getGreekSauce().getPriceForSize(size));
      } else {
        totalCents += misc.getGreekSauce().getPriceForSize(size);
      }
    }
    if (crust.equals(CrustTypes.GLUTEN_FREE) && crust != origin.crust) {
      totalCents += misc.getGlutenFree();
    } else if (crust.equals(CrustTypes.KETO) && crust != origin.crust) {
      totalCents += misc.getKeto();
    }
    return totalCents;
  }

  /**
   * Calculates the additional price required if extra or extra cheese was added onto the pizza
   *
   * @param origin The origin premade pizza
   * @return The calculated value
   */
  private long calculateXCheese(PremadePizza origin) {
    long totalCents = 0;
    long mozzaPrice = Menu.loadedToppings.getGourmetToppings()
        .get(GourmetToppingNames.MOZZARELLA).getPrice(size);
    for (SpecialInstructions current : specialInstructions) {
      if (!origin.specialInstructions.contains(current)) {
        if (current == SpecialInstructions.EXTRA_CHEESE) {
          totalCents += mozzaPrice;
        } else if (current == SpecialInstructions.EXTRA_EXTRA_CHEESE) {
          totalCents += (mozzaPrice * 2);
        }
      }
    }
    return totalCents;
  }

  /**
   * Calculates the additional added toppings, since some toppings were already on the pizza since
   * this is a premade pizza, then we need to determine which toppings were actually added and which
   * toppings did not exist before. Toppings that are flagged as a negative topping does not reduce
   * the price.
   *
   * @param origin The origin premade pizza
   * @return The calculated value
   */
  private long calculateToppingsAddons(PremadePizza origin) {
    long totalCents = 0;
    ArrayList<Topping> nonCollision = new ArrayList<>(toppingList);
    ArrayList<Addon> nonCollisionAddon = new ArrayList<>(addonList);
    PremadeItem.toppingsAddonsSort(origin, toppingList, nonCollision, nonCollisionAddon);

    /* Don't bother with the just toppings since it's just a cheese pizza, special case */
    if(justCheese) {
      return totalCents;
    }

    for (Topping filtered : nonCollision) {
      if (!filtered.isNegation()) {
        if(filtered.isEasy() && origin.toppingList.contains(filtered.toppingClone().setEasy(false))) {
          /* Don't add the price of an easy topping if it already exists within the list */
          continue;
        }
        totalCents += filtered.getPrice(size);
      }
    }

    return totalCents;
  }

  @Override
  public String getSecondHalfName() {
    PremadePizza origin = Menu.loadedPreMadeFoods.getPremadePizzas()
        .get(PremadePizzaName.valueOf(ORIGIN_NAME.toUpperCase()));
    name = ORIGIN_NAME;
    NameBuilder builder = new NameBuilder();
    ArrayList<SpecialInstructions> toppingInstructions = new ArrayList<>();
    ArrayList<SpecialInstructions> sauceInstructions = new ArrayList<>();
    super.prepareArraySort(toppingInstructions, sauceInstructions);
    setInitialValuesSecondHalf(origin, builder, sauceInstructions);
    finalValues(origin, builder, toppingInstructions);

    return builder.buildPizzaFormat();
  }

  @Override
  public Premade getOrigin() {
    return Menu.loadedPreMadeFoods.getPremadePizzas()
        .get(PremadePizzaName.valueOf(ORIGIN_NAME.toUpperCase()));
  }

  @Override
  public ArrayList<Topping> getToppings() {
    return toppingList;
  }

  @Override
  public ArrayList<Addon> getAddons() {
    return addonList;
  }
  /**
   * Builds a json serializer for pizzas
   * @return the Json Serializer for pizzas
   */
  public static JsonSerializer<PremadePizza> getJsonPremadeSerializer(){
    return new JsonSerializer<PremadePizza>() {
      @Override
      public JsonElement serialize(PremadePizza src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject ret = new JsonObject();
        ret.addProperty("type", "PremadePizza");
        ret.addProperty("rowNum", src.getRowNum());
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
       // ret.add("builtTime",  new JsonPrimitive(src.builtTime.toString(TimeFormat.FORMATTER_ISO_STANDARD)));
        ret.addProperty("orgin_name", src.ORIGIN_NAME);
        ret.addProperty("pricing", new Gson().toJson(src.PRICE));

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
   * @param rowNum
   * @param justCheese
   * @param size
   * @param sauce
   * @param crust
   * @param splitHalves
   * @param secondHalf
   * @param builtTime
   */
  public PremadePizza(ArrayList<Topping> toppingList, ArrayList<Addon> addonList,
                      ArrayList<SpecialInstructions> specialInstructions, String name,
                      long price, int rowNum, boolean justCheese, Size size, Sauce sauce,
                      CrustTypes crust, boolean splitHalves, Pizza secondHalf, ClientTime
                              builtTime, PriceForSize PRICE, String ORIGIN_NAME) {
    super(toppingList, addonList, specialInstructions, name, price, rowNum, justCheese,
            size, sauce, crust, splitHalves, secondHalf, builtTime);
    this.PRICE = PRICE;
    this.ORIGIN_NAME = ORIGIN_NAME;
  }

  /**
   * Builds the json deserializer for pizza
   * @return the {@link JsonDeserializer} for pizza
   */
  public static JsonDeserializer<PremadePizza> getJsonPremadeDeserializer(){
    return new JsonDeserializer<PremadePizza>() {
      @Override
      public PremadePizza deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
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
        return new PremadePizza(toppingArrayList, gson.fromJson(o.get("addonList"), new TypeToken<ArrayList<Addon>>(){}.getType()),
                gson.fromJson(o.get("specialInstructions"), new TypeToken<ArrayList<SpecialInstructions>>(){}.getType()),
                o.get("name").getAsString(), o.get("price").getAsLong(), rowNum, o.get("justCheese").getAsBoolean(),
                Size.fromInt(o.get("size").getAsInt()), Sauce.valueOf(o.get("sauce").getAsString()),
                CrustTypes.valueOf(o.get("crust").getAsString()), o.get("splitHalves").getAsBoolean(),
                new GsonBuilder().registerTypeAdapter(Pizza.class, Pizza.getJsonDeserializer()).create()
                        .fromJson(o.get("secondHalf"), Pizza.class),new GsonBuilder().registerTypeAdapter(ClientTime.class,
                ClientTime.getJsonDeserializer()).create()
                .fromJson(o.get("builtTime"), ClientTime.class),
                 new Gson().fromJson("pricing", PriceForSize.class),o.get("origin_name").getAsString());


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
}