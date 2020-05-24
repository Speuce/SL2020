package main.java.lucia.client.content.order.io.deserializer;

import com.google.gson.*;
import main.java.lucia.Client;
import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.content.menu.io.MenuJsonConstants;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.ItemBundle;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.item.type.SimpleItem;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Deserializer for a list of (polymorphic) items.
 * @author Matthew Kwiatkowski
 */
public class ListOfItemsDeserializer implements JsonDeserializer<List<Item>> {
    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public List<Item> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray arr = json.getAsJsonArray();
        List<Item> ret = new ArrayList<>();
        JsonObject curr;
        for(JsonElement e: arr) {
            curr = e.getAsJsonObject();
            assert(curr.has(MenuJsonConstants.TYPE_FIELD));
            String type = curr.get(MenuJsonConstants.TYPE_FIELD).getAsString();
            switch(type){
                case MenuJsonConstants.BUNDLE_TYPE:
                    ret.add(ItemGson.ITEM_GSON.fromJson(curr, ItemBundle.class));
                    break;
                case MenuJsonConstants.PIZZA_TYPE:
                    ret.add(ItemGson.ITEM_GSON.fromJson(curr, Pizza.class));
                    break;
                case MenuJsonConstants.MODIFIABLE_TYPE:
                    ret.add(ItemGson.ITEM_GSON.fromJson(curr, ItemModifiable.class));
                    break;
                case MenuJsonConstants.SIMPLE_TYPE:
                    ret.add(ItemGson.ITEM_GSON.fromJson(curr, SimpleItem.class));
                    break;
                default:
                    Client.logger.error("Unknown type during deserialization: " + type);
                    break;
            }
        }
        return ret;
    }
}
