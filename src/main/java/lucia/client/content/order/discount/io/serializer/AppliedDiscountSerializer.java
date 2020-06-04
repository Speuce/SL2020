package main.java.lucia.client.content.order.discount.io.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.java.lucia.client.content.menu.io.Property;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Serializer for {@link main.java.lucia.client.content.order.discount.impl.AppliedDiscount}
 */
public class AppliedDiscountSerializer implements JsonSerializer<AppliedDiscount> {
    /**
     * Gson invokes this call-back method during serialization when it encounters a field of the
     * specified type.
     *
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
     * non-trivial field of the {@code src} object. However, you should never invoke it on the
     * {@code src} object itself since that will cause an infinite loop (Gson will call your
     * call-back method again).</p>
     *
     * @param src       the object that needs to be converted to Json.
     * @param typeOfSrc the actual type (fully genericized version) of the source object.
     * @param context idk
     * @return a JsonElement corresponding to the specified object.
     */
    @Override
    public JsonElement serialize(AppliedDiscount src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject ret = new JsonObject();
        ret.addProperty(Property.DISCOUNT_AMT_SAVED, src.getAmtSaved());

        ret.addProperty(Property.ROW_NUM, src.getRowNum());
        ret.addProperty(Property.TYPE, Property.DISCOUNT+src.getApplied().getId());

        //done this way by input specifications in the server DatabaseManager
        JsonObject properties = new JsonObject();
        for(Map.Entry<String, Object> ent: src.getFilledFields().entrySet()){
            properties.addProperty(ent.getKey(), ent.getValue().toString());
        }
        ret.add(Property.DISCOUNT_FIELDS, properties);
        return ret;
    }
}
