package main.java.lucia.client.content.utils.structures;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.time.ClientTime;

public class GsonFactory {




    /**
     * The builder for the {@link Gson} which is used for basic gson
     *
     * @return The {@link Gson} which is used for basic gson
     */
    private static Gson basicBuilder(){
        GsonBuilder b = new GsonBuilder();
        b.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getAnnotation(Exclude.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        b.setPrettyPrinting();
        b.serializeNulls();
        b.registerTypeAdapter(ClientTime.class, ClientTime.getJsonDeserializer());
        b.registerTypeAdapter(ClientTime.class, ClientTime.getJsonSerializer());
        return b.create();
    }
}
