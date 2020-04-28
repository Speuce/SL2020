package main.java.lucia.client.content.menu.io.serializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.pizza.ToppingType;

import java.lang.reflect.Type;

/**
 * Serializer for {@link ToppingType}
 * @author Matthew Kwiatkowski
 */
public class ToppingTypeSerializer implements JsonSerializer<ToppingType> {
    /**
     * Get the custom serializer for ToppingTypes,
     * used to save menu.
     */
    @Override
    public JsonElement serialize(ToppingType src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject ret = new JsonObject();
        ret.addProperty("name", src.getName());
        ret.addProperty("buttonColor", src.getButtonColor().getRGB());
        ret.addProperty("id", src.getId());
        ret.addProperty("pricing", src.getPricingScheme().getName());
        ret.addProperty("short", src.getShortName());
        return ret;
    }
}
