package main.java.lucia.client.content.time.io.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import main.java.lucia.client.content.time.TimeFormat;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Deserializer for {@link LocalDateTime}
 * @author Matthew Kwiatkowski
 */
public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return LocalDate.parse(json.getAsString(), TimeFormat.FORMATTER_ISO_STANDARD_DATE.getFormat());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
