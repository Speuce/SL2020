package main.java.lucia.client.content.menu.io;

import com.google.gson.*;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.io.deserializer.local.*;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.item.type.pizza.Crust;
import main.java.lucia.client.content.menu.item.type.pizza.Sauce;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;
import main.java.lucia.client.content.menu.size.PricingScheme;
import main.java.lucia.client.content.utils.IDCaster;
import main.java.lucia.consts.JavaConstants;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

import java.util.*;

/**
 * A class to load the menu.
 * @author Matt Kwiatkowski
 */
public class MenuLoader {


    /**
     * The parser for the json file that the menu
     * is to be loaded from.
     */
    private JsonObject parser;

    /**
     * The Json object representations of the Pizza part of the menu.
     */
    private JsonObject pizzaParser;

    public MenuLoader(JsonObject parser) {
        this.parser = parser.getAsJsonObject("menu");
        this.pizzaParser = parser.getAsJsonObject("pizza");
    }

    /**
     * Finds and returns all loaded sections of the menu (does not include pizza)
     */
    public List<String> loadSections(){
        JsonElement el = parser.get(Property.SECTION);
        return new Gson().fromJson(el, JavaConstants.STRING_LIST_TYPE);
    }

    /**
     * Parses a given section and returns all of the items in it.
     * @param section the section to parse
     * @return a List of the items in the section.
     */
    public List<SimpleItemDescriptor> loadSectionItems(String section){
        JsonArray items = parser.get(section).getAsJsonArray();
        List<SimpleItemDescriptor> ret = new ArrayList<>();
        JsonObject item;
        SimpleItemDescriptor descriptor;
        for(JsonElement e: items){
            item = e.getAsJsonObject();
                descriptor = deserializeItem(item);
                if(descriptor != null){
                    ret.add(descriptor);
                }else{
                    MLogger.parseError("Error parsing item: ***" + e.getAsString() + "***");
                }

        }
        return ret;
    }

    /**
     * Parses an item and returns it.
     * @param o the JsonObject representation of the item to parse
     * @return the item, parsed, or null if it it not parsable.
     */
    private SimpleItemDescriptor deserializeItem(JsonObject o){
        return (SimpleItemDescriptor) GsonTypeFactory.MENU_ITEM_GSON.fromJson(o, Descriptor.class);
    }

    /**
     * Gets the defined list of pizza sizes
     * @return a {@link TreeSet} containing all of the defined sizes
     * The nature of an {@link TreeSet} will keep the sizes ordered.
     */
    public TreeSet<Integer> loadDefinedSizes(){
        JsonArray arr = pizzaParser.getAsJsonArray(Property.SIZE);
        TreeSet<Integer> ret = new TreeSet<>();
        for(JsonElement e : arr){
            try{
                ret.add(Integer.parseInt(e.getAsString()));
            }catch(NumberFormatException ex){
                MLogger.parseError("error parsing size: \"" + e.getAsString() + "\"");
            }
        }
        return ret;
    }

    /**
     * Gets the list of all defined pizza toppings.
     * @return an {@link ArrayList} of {@link ToppingType} objects.
     */
    public List<ToppingType> loadDefinedToppingList(){
        List<ToppingType> ret = new ArrayList<>();
        JsonArray toppingArr = pizzaParser.getAsJsonArray(Property.TOPPING);
        JsonDeserializer<ToppingType> customSerializer = new ToppingTypeDeserializer();
        for(JsonElement e: toppingArr){
            ret.add(customSerializer.deserialize(e, ToppingType.class, null));
        }
        return ret;
    }

    /**
     * Gets all of the defined pizza sauces from the pizza json.
     */
    public List<Sauce> loadDefinedSauceList(){
        List<Sauce> ret = new ArrayList<>();
        JsonArray sauceArr = pizzaParser.getAsJsonArray(Property.SAUCE);
        JsonDeserializer<Sauce> sauceDeseserializer = new SauceDeserializer();
        for(JsonElement e: sauceArr){
            ret.add(sauceDeseserializer.deserialize(e, Sauce.class, null));
        }
        return ret;
    }

    /**
     * Gets all of the defined pizza crusts from the pizza json.
     */
    public List<Crust> loadDefinedCrustList(){
        List<Crust> ret = new ArrayList<>();
        JsonArray sauceArr = pizzaParser.getAsJsonArray(Property.CRUST);
        JsonDeserializer<Crust> crustDeseserializer = new CrustDeserializer();
        for(JsonElement e: sauceArr){
            ret.add(crustDeseserializer.deserialize(e, Crust.class, null));
        }
        return ret;
    }

    /**
     * Gets all of the defined pizza specials from the pizza json.
     */
    public List<SpecialtyPizzaDescriptor> loadSpecialtyList(){
        List<SpecialtyPizzaDescriptor> ret = new ArrayList<>();
        JsonArray arr = pizzaParser.getAsJsonArray(Property.SPECIAL);
        JsonDeserializer<SpecialtyPizzaDescriptor> deseserializer = new SpecialtyPizzaDescriptorDeserializer();
        for(JsonElement e: arr){
            ret.add(deseserializer.deserialize(e, SpecialtyPizzaDescriptor.class, null));
        }
        return ret;
    }

    /**
     * Gets all of the defined pizza specials from the pizza json.
     */
    public List<PricingScheme> loadPricingSchemes(){
        List<PricingScheme> ret = new ArrayList<>();
        JsonArray arr = pizzaParser.getAsJsonArray(Property.PRICING);
        JsonDeserializer<PricingScheme> deseserializer = new PricingSchemeDeserializer();
        for(JsonElement e: arr){
            ret.add(deseserializer.deserialize(e, SpecialtyPizzaDescriptor.class, null));
        }
        return ret;
    }

    /**
     * Gets the "regular" sauce object
     */
    public Sauce loadRegularSauce(){
        return new IDCaster<Sauce>().cast((pizzaParser.getAsJsonPrimitive(Property.REGULAR_SAUCE).getAsInt()));
    }

    /**
     * Gets the "regular" crust object
     */
    public Crust loadRegularCrust(){
        return new IDCaster<Crust>().cast(pizzaParser.getAsJsonPrimitive(Property.REGULAR_CRUST).getAsInt());
    }

    /**
     * Gets the Base pizza pricing
     */
    public PricingScheme loadBasePizzaPricing(){
        return Menu.pizza.getPricingScheme(pizzaParser.getAsJsonPrimitive(Property.REGULAR_PIZZA).getAsString());
    }




}
