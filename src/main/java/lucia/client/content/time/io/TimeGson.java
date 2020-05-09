package main.java.lucia.client.content.time.io;

import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.io.deserializer.ClientTimeDeserializer;
import main.java.lucia.client.content.time.io.deserializer.LocalDateDeserializer;
import main.java.lucia.client.content.time.io.deserializer.LocalDateTimeDeserializer;
import main.java.lucia.client.content.time.io.deserializer.LocalTimeDeserializer;
import main.java.lucia.client.content.time.io.serializer.ClientTimeSerializer;
import main.java.lucia.client.content.time.io.serializer.LocalDateSerializer;
import main.java.lucia.client.content.time.io.serializer.LocalDateTimeSerializer;
import main.java.lucia.client.content.time.io.serializer.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Gson Manager class for all time-related features.
 * @author Matthew Kwiatkowski
 */
public class TimeGson {

    /**
     * Adds custom serializers & deserializer for all
     * {@link java.time.LocalDate}, {@link java.time.LocalTime} and {@link java.time.LocalDateTime}
     * @param b the {@link GsonBuilder} to add these serializers to
     */
    public static void addCustomJsonSerializers(GsonBuilder b){
        b.registerTypeAdapter(ClientTime.class, new ClientTimeDeserializer());
        b.registerTypeAdapter(ClientTime.class, new ClientTimeSerializer());
        b.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        b.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        b.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
        b.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        b.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        b.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
    }
}
