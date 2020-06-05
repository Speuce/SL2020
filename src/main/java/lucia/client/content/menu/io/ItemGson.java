package main.java.lucia.client.content.menu.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.order.discount.io.deserializer.AppliedDiscountDeserializer;
import main.java.lucia.client.content.order.discount.io.serializer.AppliedDiscountSerializer;
import main.java.lucia.client.content.order.io.deserializer.ListOfItemsDeserializer;
import main.java.lucia.client.content.payment.paymentmethods.PaymentMethod;
import main.java.lucia.client.content.time.io.TimeGson;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

import java.util.List;

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

        builder.registerTypeAdapterFactory(PaymentMethod.getPaymentAdapterFactory());

        TimeGson.addCustomJsonSerializers(builder);
        builder.registerTypeAdapter(new TypeToken<List<Item>>(){}.getType(), new ListOfItemsDeserializer());
        builder.registerTypeAdapter(Pizza.class, new PizzaSerializer());
        builder.registerTypeAdapter(Pizza.class, new PizzaDeserializer(false));
        builder.registerTypeAdapter(SimpleItem.class, new SimpleItemSerializer());
        builder.registerTypeAdapter(SimpleItem.class, new SimpleItemDeserializer());
        builder.registerTypeAdapter(ItemModifiable.class, new ItemModifiableDeserializer());
        builder.registerTypeAdapter(ItemModifiable.class, new ItemModifiableSerializer());
        builder.registerTypeAdapter(ItemBundle.class, new ItemBundleSerializer());
        builder.registerTypeAdapter(ItemBundle.class, new ItemBundleDeserializer());
        builder.registerTypeAdapter(AppliedDiscount.class, new AppliedDiscountSerializer());
        builder.registerTypeAdapter(AppliedDiscount.class, new AppliedDiscountDeserializer());


        return builder.create();
    }
}
