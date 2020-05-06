package main.java.lucia.client.content.menu.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.menu.io.deserializer.server.ItemBundleDeserializer;
import main.java.lucia.client.content.menu.io.deserializer.server.ItemModifiableDeserializer;
import main.java.lucia.client.content.menu.io.deserializer.server.PizzaDeserializer;
import main.java.lucia.client.content.menu.io.deserializer.server.SimpleItemDeserializer;
import main.java.lucia.client.content.menu.io.serializer.server.ItemBundleSerializer;
import main.java.lucia.client.content.menu.io.serializer.server.ItemModifiableSerializer;
import main.java.lucia.client.content.menu.io.serializer.server.PizzaSerializer;
import main.java.lucia.client.content.menu.io.serializer.server.SimpleItemSerializer;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.ItemBundle;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.item.type.SimpleItem;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
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

        builder.registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Item.class)
        .registerSubtype(Pizza.class)
        .registerSubtype(SimpleItem.class)
        .registerSubtype(ItemModifiable.class)
        .registerSubtype(ItemBundle.class));

        builder.registerTypeAdapter(ClientTime.class, ClientTime.getJsonDeserializer());
        builder.registerTypeAdapter(ClientTime.class, ClientTime.getJsonSerializer());
        builder.registerTypeAdapter(Pizza.class, new PizzaSerializer());
        builder.registerTypeAdapter(Pizza.class, new PizzaDeserializer(false));
        builder.registerTypeAdapter(SimpleItem.class, new SimpleItemSerializer());
        builder.registerTypeAdapter(SimpleItem.class, new SimpleItemDeserializer());
        builder.registerTypeAdapter(ItemModifiable.class, new ItemModifiableDeserializer());
        builder.registerTypeAdapter(ItemModifiable.class, new ItemModifiableSerializer());
        builder.registerTypeAdapter(ItemBundle.class, new ItemBundleSerializer());
        builder.registerTypeAdapter(ItemBundle.class, new ItemBundleDeserializer());


        return builder.create();
    }
}
