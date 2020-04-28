package main.java.lucia.client.content.menu.io.serializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.menu.size.PricingScheme;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The serializer for {@link PricingScheme}
 * @author Matthew Kwiatkowski
 */
public class PricingSchemeSerializer implements JsonSerializer<PricingScheme> {

    @Override
    public JsonElement serialize(PricingScheme src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject ret = new JsonObject();
        ret.addProperty("name", src.getName());
        List<Integer> IntegerList = new ArrayList<>(src.getPriceMap().keySet());
        Collections.sort(IntegerList);
        for(Integer s: IntegerList){
            ret.addProperty(s + "", src.getPriceMap().get(s)+ "");
        }
        return ret;
    }
}
