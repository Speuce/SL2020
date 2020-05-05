package main.java.lucia.client.content.menu.io.deserializer.server;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import main.java.lucia.client.content.utils.IDCaster;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.*;
import main.java.lucia.client.content.order.discount.Discount;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Deserializer for {@link Pizza}
 */
public class PizzaDeserializer implements JsonDeserializer<Pizza> {

    /**
     * Flag indicating whether or not to IGNORE the second half.
     */
    private boolean secondOverride;

    /**
     * Constructor for a pizza deserializer
     * @param secondOverride Flag indicating whether or not to IGNORE the second half.
     */
    public PizzaDeserializer(boolean secondOverride) {
        this.secondOverride = secondOverride;
    }

    @Override
    public Pizza deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json == null || json.isJsonNull()){
            return null;
        }
        JsonObject o = (JsonObject) json;
        //first check if the pizza is a specialty
        boolean isSpecialty = o.get("isSpecialty").getAsBoolean();
        int specialtyId = o.get("specialty").getAsInt();
        SpecialtyPizzaDescriptor special = null;
        if(isSpecialty && specialtyId > 0){
            special = new IDCaster<SpecialtyPizzaDescriptor>().cast(specialtyId);
        }
        //now add the toppings
        JsonObject toppings = o.getAsJsonObject("toppings");
        ArrayList<Topping> toppingList = new ArrayList<>();
        for(Map.Entry<String, JsonElement> e: toppings.entrySet()) {
            try {
                int id = Integer.parseInt(e.getKey());
                int amt = e.getValue().getAsInt();
                ToppingType type = new IDCaster<ToppingType>().cast(id);
                if (type != null) {
                    if (amt == 0) {
                        //if the amount of the topping is 0, it MAY be nothing,
                        // or it may be a negation that needs to be displayed.
                        if (isSpecialty && special != null && special.hasToppingType(type))
                            toppingList.add(type.toTopping(amt));
                    } else {
                        toppingList.add(type.toTopping(amt));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //load the properties of pizza.
        int rowNum = o.get("rowNum").getAsInt();
        String name = o.get("name").getAsString();
        String displayName = o.get("displayName").getAsString();
        long price = o.get("price").getAsLong();
        long discountedPrice = o.get("discountedPrice").getAsLong();

        Set<Discount> appliedDiscounts = new HashSet<>();
        int id;
        for(JsonElement e: o.getAsJsonArray("appliedDiscounts")){
            id = e.getAsInt();
        }

        int size = o.get("size").getAsInt();
        int sauceId = o.get("sauce").getAsInt();
        Sauce sauce = new IDCaster<Sauce>().cast(sauceId);
        int crustId = o.get("crust").getAsInt();
        Crust crust = new IDCaster<Crust>().cast(crustId);
        boolean splitHalves = o.get("splitHalves").getAsBoolean();
        Gson gson = GsonTypeFactory.GENERIC_GSON;
        List<String> specialInstructions = gson.fromJson(o.get("specialInstructions"),
                new TypeToken<ArrayList<String>>(){}.getType());
        Pizza secondHalf = null;
        //Get the second half (if applicable)
        if(splitHalves && !secondOverride){
            secondHalf = new GsonBuilder().registerTypeAdapter(Pizza.class, new PizzaDeserializer(true)).create()
                    .fromJson(o.get("secondHalf"), Pizza.class);
        }
        return new Pizza(displayName, name, price, discountedPrice, special, rowNum, specialInstructions,
                toppingList, size, isSpecialty, sauce, crust, splitHalves, secondHalf);
    }
}
