package main.java.lucia.client.content.menu.io.serializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.Topping;

import java.lang.reflect.Type;

/**
 * Serializer for {@link SpecialtyPizzaDescriptor} objects (local)
 * @author Matthew Kwiatkowski
 */
public class SpecialtyPizzaDescriptorSerializer implements JsonSerializer<SpecialtyPizzaDescriptor> {

    @Override
    public JsonElement serialize(SpecialtyPizzaDescriptor src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject ret = new JsonObject();
        ret.addProperty("id", src.getId());
        ret.addProperty("name", src.getBaseName());
        ret.addProperty("pricing", src.getPricingScheme().getName());
        ret.addProperty("sauce", src.getSauce().getId());
        ret.addProperty("crust", src.getCrust().getId());
        JsonArray toppingArr = new JsonArray();
        JsonObject add;
        for(Topping t: src.getToppings()){
            add = new JsonObject();
            add.addProperty("type", t.getType().getId());
            add.addProperty("amount", t.getAmount());
            toppingArr.add(add);
        }
        ret.add("toppings", toppingArr);
        JsonArray instructions = new JsonArray();
        for(String s: src.getSpecialInstructions()){
            instructions.add(s);
        }
        ret.add("specialInstructions", instructions);
        return ret;
    }
}
