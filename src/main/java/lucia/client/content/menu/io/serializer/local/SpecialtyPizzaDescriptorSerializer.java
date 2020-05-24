package main.java.lucia.client.content.menu.io.serializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;

import java.lang.reflect.Type;
import java.util.Map;

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
        for(Map.Entry<ToppingType, Integer> t: src.getToppings().entrySet()){
            add = new JsonObject();
            add.addProperty("type", t.getKey().getId());
            add.addProperty("amount", t.getValue());
            toppingArr.add(add);
        }
        ret.add("toppings", toppingArr);
        JsonArray instructions = new JsonArray();
        for(String s: src.getSpecialInstructions()){
            instructions.add(s);
        }

        ret.addProperty("defaultColor", src.getDefaultColor());
        ret.addProperty("selectedColor", src.getSelectedColor());
        ret.addProperty("hoverColor", src.getHoverColor());
        ret.addProperty("textColor", src.getTextColor());

        ret.add("specialInstructions", instructions);
        return ret;
    }
}
