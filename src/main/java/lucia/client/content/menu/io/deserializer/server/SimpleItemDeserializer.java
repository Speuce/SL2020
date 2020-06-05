package main.java.lucia.client.content.menu.io.deserializer.server;

import com.google.gson.*;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.client.content.menu.item.type.SimpleItem;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.utils.IDCaster;
import main.java.lucia.client.content.utils.SerializationUtils;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * Deserializer for server-communication with {@link main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor}
 * @author Matthew Kwiatkowski
 */
public class SimpleItemDeserializer implements JsonDeserializer<SimpleItem> {
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
    public SimpleItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject o = json.getAsJsonObject();
        int rowNum = o.get("rowNum").getAsInt();
        String name = o.get("name").getAsString();
        SimpleItemDescriptor desc = new IDCaster<SimpleItemDescriptor>().cast(o.get("itemDescriptor").getAsInt());
        String displayName = o.get("displayName").getAsString();
        long price = o.get("price").getAsLong();
        long discountedPrice = o.get("discountedPrice").getAsLong();
        Set<AppliedDiscount> appliedDiscounts = SerializationUtils.getAppliedDiscounts(o);
        return new SimpleItem(rowNum, displayName, name, price, discountedPrice, desc, appliedDiscounts);
    }
}
