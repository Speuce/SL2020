package main.java.lucia.client.content.menu.io.deserializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;
import main.java.lucia.client.content.menu.size.PricingScheme;
import main.java.lucia.consts.ColorUtils;

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

        String name = obj.get("name").getAsString();
        String shortName = obj.get("short").getAsString();
        PricingScheme pricing = Menu.pizza.getPricingScheme(obj.get("pricing").getAsString());

        String defaultColor = ColorUtils.parseHex(obj.get("defaultColor").getAsString());
        String selectedColor = ColorUtils.parseHex(obj.get("selectedColor").getAsString());
        String hoverColor = ColorUtils.parseHex(obj.get("hoverColor").getAsString());
        String textColor = ColorUtils.parseHex(obj.get("textColor").getAsString());

        return new ToppingType(id, name,defaultColor, selectedColor, hoverColor, textColor, shortName, pricing);
    }
}
