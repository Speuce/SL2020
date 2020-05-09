package main.java.lucia.client.content.time.io.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.java.lucia.client.content.time.TimeFormat;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Custom Json Serializer for {@link LocalDateTime}
 * @author Matthew Kwiatkowski
 */
public class LocalTimeSerializer implements JsonSerializer<LocalTime> {

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(TimeFormat.FORMATTER_ISO_STANDARD.getFormat().format(src));
    }
}
