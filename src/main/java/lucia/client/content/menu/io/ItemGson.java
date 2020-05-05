package main.java.lucia.client.content.menu.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.employee.Driver;
import main.java.lucia.client.content.employee.Employee;
import main.java.lucia.client.content.employee.Manager;
import main.java.lucia.client.content.menu.io.deserializer.server.PizzaDeserializer;
import main.java.lucia.client.content.menu.io.serializer.server.PizzaSerializer;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.SimpleItem;
import main.java.lucia.client.content.menu.pizza.Pizza;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.util.gson.RuntimeTypeAdapterFactory;

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
