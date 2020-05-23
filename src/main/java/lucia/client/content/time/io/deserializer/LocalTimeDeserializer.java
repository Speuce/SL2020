package main.java.lucia.client.content.time.io.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import main.java.lucia.client.content.time.TimeFormat;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Deserializer for {@link LocalDateTime}
 * @author Matthew Kwiatkowski
 */
public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return LocalTime.parse(json.getAsString(), TimeFormat.FORMATTER_ISO_STANDARD_TIME.getFormat());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
