package main.java.lucia.client.content.menu.io.deserializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.client.content.menu.size.PricingScheme;

import java.lang.reflect.Type;

/**
 * The deserializer for {@link Crust}
 */
public class CrustDeserializer implements JsonDeserializer<Crust> {

    @Override
    public Crust deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int id = obj.get("id").getAsInt();
        String fullName = obj.get("name").getAsString();
        String displayName = obj.get("displayName").getAsString();

        PricingScheme pricing = Menu.pizza.getPricingScheme(obj.get("pricing").getAsString());

        return new Crust(id, displayName, fullName, pricing);
    }
}
