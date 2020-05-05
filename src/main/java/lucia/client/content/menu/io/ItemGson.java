package main.java.lucia.client.content.menu.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.menu.io.deserializer.server.PizzaDeserializer;
import main.java.lucia.client.content.menu.io.serializer.server.PizzaSerializer;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

/**
 * The Gson for {@link main.java.lucia.client.content.menu.item.Item}
 * @author Matthew Kwiatkowski
 */
public class ItemGson {

    public static Gson ITEM_GSON = getItemGson();


    /**
     * Builds the item gson
     */
    private static Gson getItemGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        GsonTypeFactory.addExclusionPolicy(builder);


        builder.registerTypeAdapter(ClientTime.class, ClientTime.getJsonDeserializer());
        builder.registerTypeAdapter(ClientTime.class, ClientTime.getJsonSerializer());
        builder.registerTypeAdapter(Pizza.class, new PizzaSerializer());
        builder.registerTypeAdapter(Pizza.class, new PizzaDeserializer(false));


        return builder.create();
    }
}
