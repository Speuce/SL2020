package main.java.lucia.client.content.menu.io.serializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.item.type.pizza.Sauce;

import java.lang.reflect.Type;

/**
 * The local serializer for {@link Sauce}
 * @author Matthew Kwiatkowski
 */
public class SauceSerializer implements JsonSerializer<Sauce> {

    @Override
    public JsonElement serialize(Sauce src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject ret = new JsonObject();
        ret.addProperty("displayName", src.getDisplayName());
        ret.addProperty("name", src.getFullName());
        ret.addProperty("id", src.getId());
        ret.addProperty("pricing", src.getPricingScheme().getName());
        return ret;
    }
}
