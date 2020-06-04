package main.java.lucia.client.content.order.discount.io.deserializer;

import com.google.gson.*;
import main.java.lucia.client.content.menu.io.Property;
import main.java.lucia.client.content.order.discount.Discount;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.utils.IDCaster;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Server-inbound deserializer for {@link main.java.lucia.client.content.order.discount.impl.AppliedDiscount}
 * @author Matthew Kwiatkowski
 */
public class AppliedDiscountDeserializer implements JsonDeserializer<AppliedDiscount> {
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
    public AppliedDiscount deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int amtSaved = obj.get(Property.DISCOUNT_AMT_SAVED).getAsInt();
        String type = obj.get(Property.TYPE).getAsString();
        int id = Integer.parseInt(type.substring(Property.DISCOUNT.length()));
        int rowNum = obj.get(Property.ROW_NUM).getAsInt();

        Map<String, Object> mapa = new HashMap<>();
        JsonObject arr = obj.get(Property.DISCOUNT_FIELDS).getAsJsonObject();
        for(String key: arr.keySet()){
            mapa.put(key, arr.get(key));
        }

        return new AppliedDiscount(rowNum, new IDCaster<Discount>().cast(id), amtSaved, mapa);
    }
}
