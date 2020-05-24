package main.java.lucia.client.content.time.io.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;

import java.lang.reflect.Type;

/**
 * Serializer for {@link ClientTime}
 * @author Matthew Kwiatkowski
 */
public class ClientTimeSerializer implements JsonSerializer<ClientTime> {

    @Override
    public JsonElement serialize(ClientTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString(TimeFormat.FORMATTER_ISO_STANDARD));
    }
}
