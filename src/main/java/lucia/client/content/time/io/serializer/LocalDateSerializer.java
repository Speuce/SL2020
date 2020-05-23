package main.java.lucia.client.content.time.io.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.java.lucia.client.content.time.TimeFormat;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Custom Json Serializer for {@link LocalDateTime}
 * @author Matthew Kwiatkowski
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(TimeFormat.FORMATTER_ISO_STANDARD_DATE.getFormat().format(src));
    }
}
