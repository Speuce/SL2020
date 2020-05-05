package main.java.lucia.client.content.menu.io.serializer.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;

import java.lang.reflect.Type;

/**
 * Serializer for {@link Pizza}
 * @author Matthew Kwiatkowski
 */
public class PizzaSerializer implements JsonSerializer<Pizza> {

    @Override
    public JsonElement serialize(Pizza src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject ret = new JsonObject();
        src.addJsonProperties(ret);
        ret.addProperty("type", "Pizza");
        return ret;
    }
}
