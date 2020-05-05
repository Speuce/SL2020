package main.java.lucia.client.content.menu.io.deserializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.client.content.menu.pizza.Topping;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.client.content.menu.size.PricingScheme;
import main.java.lucia.consts.ColorUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deserializer for {@link SpecialtyPizzaDescriptor} objects (local)
 */
public class SpecialtyPizzaDescriptorDeserializer implements JsonDeserializer<SpecialtyPizzaDescriptor>{

    @Override
    public SpecialtyPizzaDescriptor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int id = obj.get("id").getAsInt();
        String name = obj.get("name").getAsString();
        PricingScheme pricing = Menu.pizza.getPricingScheme(obj.get("pricing").getAsString());
        Sauce sauce = new IDCaster<Sauce>().cast(obj.get("sauce").getAsInt());
        Crust crust = new IDCaster<Crust>().cast(obj.get("crust").getAsInt());
        JsonArray toppingArr = obj.getAsJsonArray("toppings");
        Map<ToppingType, Integer> toppings = new HashMap<>();
        JsonObject curr;
        int amt;
        ToppingType type;
        for(JsonElement e: toppingArr){
            curr = e.getAsJsonObject();
            amt = curr.get("amount").getAsInt();
            type = new IDCaster<ToppingType>().cast(curr.get("type").getAsInt());
            if(type != null){
                toppings.put(type, amt);
            }else{
                MLogger.logError("Couldn't load topping: " + e.getAsString() + " on pizza: " + name);
            }
        }
        JsonArray specialInstructionsArr = obj.getAsJsonArray("specialInstructions");
        List<String> specialInstructions = new ArrayList<>();
        for(JsonElement e: specialInstructionsArr){
            specialInstructions.add(e.getAsString());
        }

        String defaultColor = ColorUtils.parseHex(obj.get("defaultColor").getAsString());
        String selectedColor = ColorUtils.parseHex(obj.get("selectedColor").getAsString());
        String hoverColor = ColorUtils.parseHex(obj.get("hoverColor").getAsString());
        String textColor = ColorUtils.parseHex(obj.get("textColor").getAsString());

        return new SpecialtyPizzaDescriptor(id, name, defaultColor, selectedColor, hoverColor, textColor, pricing,
                toppings, specialInstructions, sauce, crust);
    }
}
