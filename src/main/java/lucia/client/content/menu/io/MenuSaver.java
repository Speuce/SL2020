package main.java.lucia.client.content.menu.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import main.java.lucia.client.content.menu.io.serializer.local.*;
import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.client.content.menu.size.PricingScheme;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

import java.util.List;
import java.util.SortedSet;

/**
 * Class used for saving menu in JSON format.
 * @author Matt Kwiatkowski
 */
public class MenuSaver {

    private JsonObject menuJson, pizzaJson;

    public MenuSaver(JsonObject menuJson, JsonObject pizzaJson) {
        this.menuJson = menuJson;
        this.pizzaJson = pizzaJson;
    }

    /**
     * Save the defined list of sections
     * @param sections the list of sections in the menu
     */
    public void saveSections(List<String> sections){
        JsonArray secArr = new JsonArray();
        for(String s: sections){
            menuJson.add(s, null);
            secArr.add(s);
        }
        menuJson.add(Property.SECTION, secArr);
    }

    /**
     * Saves all of the defined items of a given section
     * @param section the name of the section
     * @param items the list of items in the section
     */
    public void saveSection(String section, List<SimpleItemDescriptor> items){
        JsonArray sectionArr = new JsonArray();
        for(SimpleItemDescriptor desc: items){
            //casting may be necessary if this does not work.
            if(desc instanceof ItemModifiableDescriptor){
                sectionArr.add(GsonTypeFactory.BASIC_GSON.toJsonTree(desc,
                        ItemModifiableDescriptor.class));
            }else if(desc instanceof AddonDescriptor){
                sectionArr.add(GsonTypeFactory.BASIC_GSON.toJsonTree(desc,
                        AddonDescriptor.class));
            }else{
                sectionArr.add(GsonTypeFactory.BASIC_GSON.toJsonTree(desc,
                        SimpleItemDescriptor.class));
            }
        }
        menuJson.add(section, sectionArr);
    }

    /**
     * Saves the list of defined pizza sizes
     * @param definedSizes the list of sizes to save.
     */
    public void savePizzaSizes(SortedSet<Integer> definedSizes){
        JsonArray sizeArr = new JsonArray();
        for(Integer i: definedSizes){
            sizeArr.add(i);
        }
        pizzaJson.add(Property.SIZE, sizeArr);
    }

    /**
     * Saves the list of defined pizza toppings.
     * @param toppings the list of toppings to save.
     */
    public void saveDefinedToppingList(Iterable<ToppingType> toppings){
        JsonArray toppingArr = new JsonArray();
        JsonSerializer<ToppingType> customSerializer = new ToppingTypeSerializer();
        for(ToppingType t: toppings){
           toppingArr.add(customSerializer.serialize(t, ToppingType.class, null));
        }
        pizzaJson.add(Property.TOPPING, toppingArr);

    }

    /**
     * Saves the list of defined pizza sauces.
     * @param sauces the list of {@link Sauce}'s to save.
     */
    public void saveDefinedSauceList(Iterable<Sauce> sauces){
        JsonArray sauceArr = new JsonArray();
        JsonSerializer<Sauce> customSerializer = new SauceSerializer();
        for(Sauce t: sauces){
            sauceArr.add(customSerializer.serialize(t, Sauce.class, null));
        }
        pizzaJson.add(Property.SAUCE, sauceArr);
    }

    /**
     * Saves the list of defined pizza crusts.
     * @param crusts the lit of {@link Crust}'s to save.
     */
    public void saveDefinedCrustList(Iterable<Crust> crusts){
        JsonArray crustArr = new JsonArray();
        JsonSerializer<Crust> customSerializer = new CrustSerializer();
        for(Crust t: crusts){
            crustArr.add(customSerializer.serialize(t, Crust.class, null));
        }
        pizzaJson.add(Property.CRUST, crustArr);
    }

    /**
     * Saves the list of defined specialty pizzas
     * @param specials the list of {@link SpecialtyPizzaDescriptor}'s to save.
     */
    public void saveSpecialtyList(Iterable<SpecialtyPizzaDescriptor> specials){
        JsonArray specialtyArr = new JsonArray();
        JsonSerializer<SpecialtyPizzaDescriptor> customSerializer = new SpecialtyPizzaDescriptorSerializer();
        for(SpecialtyPizzaDescriptor special: specials){
            specialtyArr.add(customSerializer.serialize(special, SpecialtyPizzaDescriptor.class, null));
        }
        pizzaJson.add(Property.SPECIAL, specialtyArr);
    }

    /**
     * Saves the list of defined pizza pricing schemes.
     * @param pricingSchemes the list of {@link PricingScheme}'s to save.
     */
    public void savePricingSchemes(Iterable<PricingScheme> pricingSchemes){
        JsonArray pricingArr = new JsonArray();
        JsonSerializer<PricingScheme> customSerializer = new PricingSchemeSerializer();
        for(PricingScheme p: pricingSchemes){
            pricingArr.add(customSerializer.serialize(p, PricingScheme.class, null));
        }
        pizzaJson.add(Property.PRICING, pricingArr);
    }

    /**
     * Saves the "regular" sauce.
     * @param regular the "regular" sauce object
     */
    public void saveRegularSauce(Sauce regular){
        pizzaJson.addProperty(Property.REGULAR_SAUCE, regular.getId());
    }

    /**
     * Saves the "regular" pizza pricing scheme.
     * @param s the pricing scheme of a regular cheese pizza.
     */
    public void saveRegularPizza(PricingScheme s){
        pizzaJson.addProperty(Property.REGULAR_PIZZA, s.getName());
    }



}
