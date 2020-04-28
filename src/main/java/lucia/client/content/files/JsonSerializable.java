package main.java.lucia.client.content.files;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * Interface for anything that can be serialized into json easily,
 * but for which custom serialization is needed.
 * @param <T> the type of the item being serialized.
 * @author Matt Kwiatkowski
 */
public interface JsonSerializable<T> {

    /**
     * Get and make the serializer for this type of item.
     * @return the assosciated {@link JsonSerializer}
     */
    public JsonSerializer<T> getJsonSerializer();

    /**
     * Create and get the Deserializer for this type of item.
     * @return the associated {@link JsonDeserializer}
     */
    public JsonDeserializer<T> getJsonDeserializer();

}
