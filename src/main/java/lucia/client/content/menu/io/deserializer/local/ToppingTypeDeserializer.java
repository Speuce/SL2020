package main.java.lucia.client.content.menu.io.deserializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.client.content.menu.size.PricingScheme;

import java.awt.*;
import java.lang.reflect.Type;

/**
 * Deserializer for {@link ToppingType} (local)
 * @author Matthew Kwiatkowski
 */
public class ToppingTypeDeserializer implements JsonDeserializer<ToppingType> {
    /**
     * Get the custom serializer for ToppingTypes,
     * used to save menu.
     */
    @Override
    public ToppingType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int id = obj.get("id").getAsInt();
        Color color = new Color(obj.get("buttonColor").getAsInt());
        String name = obj.get("name").getAsString();
        String shortName = obj.get("short").getAsString();
        PricingScheme pricing = Menu.pizza.getPricingScheme(obj.get("pricing").getAsString());

        return new ToppingType(id, name,shortName, color, pricing);
    }
}
