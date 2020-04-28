package main.java.lucia.client.content.menu.io;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * A second json serializable interface,
 * for {@link lucia.client.content.files.JsonSerializable} (or not) objects that
 * need secondary serialization specifications.
 * This custom serialization should be used for loading COMPLETE
 * object info. (i.e menu loading/saving)
 * @author Matt Kwiatkwoski
 */
public interface JsonCustomSerializable<T> {

    public JsonSerializer<T> getCustomSerializer();

    public JsonDeserializer<T> getCustomDeserializer();

}
