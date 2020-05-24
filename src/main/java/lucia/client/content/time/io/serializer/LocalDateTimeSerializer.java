package main.java.lucia.client.content.time.io.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.java.lucia.client.content.time.TimeFormat;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Custom Json Serializer for {@link java.time.LocalDateTime}
 * @author Matthew Kwiatkowski
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(TimeFormat.FORMATTER_ISO_STANDARD.getFormat().format(src));
    }
}
