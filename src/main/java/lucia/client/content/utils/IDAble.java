package main.java.lucia.client.content.utils;

import com.google.gson.*;
import main.java.lucia.client.content.files.JsonSerializable;
import main.java.lucia.client.content.menu.Menu;

import java.lang.reflect.Type;

/**
 * Represents any single item defined on the menu,
 * which has no special properties.
 * @author Matt Kwiatkowski
 */
public class IDAble implements JsonSerializable<IDAble> {

    /**
     * The Id of this item defined within the menu json
     */
    private int id;

    public IDAble(int id) {
        this.id = id;
    }

    /**
     * Get the json menu defined id of this item
     */
    public int getId() {
        return id;
    }


    /**
     * Get and make the serializer for this type of item.
     *
     * @return the assosciated {@link JsonSerializer}
     */
    @Override
    public JsonSerializer<IDAble> getJsonSerializer() {
        return new JsonSerializer<IDAble>() {
            @Override
            public JsonElement serialize(IDAble src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getId());
            }
        };
    }

    /**
     * Create and get the Deserializer for this type of item.
     *
     * @return the associated {@link JsonDeserializer}
     */
    @Override
    public JsonDeserializer<IDAble> getJsonDeserializer() {
        return new JsonDeserializer<IDAble>() {
            @Override
            public IDAble deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                int id = json.getAsInt();
                return IDManager.instance.getMapping(id);
            }
        };
    }
}
