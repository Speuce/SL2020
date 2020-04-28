package main.java.lucia.client.content.menu.io.deserializer.local;

import com.google.gson.*;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.size.PricingScheme;

import java.lang.reflect.Type;

/**
 * The deserializer for {@link PricingScheme}
 * @author Matthew Kwiatkowski
 */
public class PricingSchemeDeserializer implements JsonDeserializer<PricingScheme> {

    @Override
    public PricingScheme deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject src = json.getAsJsonObject();
        PricingScheme ret = new PricingScheme(src.get("name").getAsString());
        int siz;
        for(String s: src.keySet()){
            if(!s.equals("name")){
                try{
                    siz = java.lang.Integer.parseInt(s);
                    ret.addPrice(siz, src.get(s).getAsLong());
                }catch(NumberFormatException e){
                    MLogger.logError("Error parsing, Size not found in: " + s);
                }
            }
        }
        return ret;
    }
}
