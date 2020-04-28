package main.java.lucia.client.content.menu.io.serializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Sauce;

import java.lang.reflect.Type;

/**
 * The serializer for {@link Crust} (local)
 * @author Matthew Kwiatkowski
 */
public class CrustSerializer implements JsonSerializer<Crust> {

    @Override
    public JsonElement serialize(Crust src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject ret = new JsonObject();
        ret.addProperty("displayName", src.getDisplayName());
        ret.addProperty("name", src.getFullName());
        ret.addProperty("id", src.getId());
        ret.addProperty("pricing", src.getPricingScheme().getName());
        return ret;
    }
}
