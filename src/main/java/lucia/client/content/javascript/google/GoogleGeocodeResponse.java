package main.java.lucia.client.content.javascript.google;

import com.google.gson.reflect.TypeToken;
import main.java.lucia.client.content.javascript.google.impl.Results;
import main.java.lucia.client.content.store.StoreLocations;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

public class GoogleGeocodeResponse {

  private Results[] allResults;

  private StoreLocations location;

  private String formattedStoreName;

  public GoogleGeocodeResponse setAllResults(Results[] results) {
    allResults = results;
    return this;
  }

  public GoogleGeocodeResponse setLocation(String location) {
    this.location = StoreLocations.valueOf(location.toUpperCase());
    return this;
  }

  public GoogleGeocodeResponse setLocationFormatted(String name) {
    this.formattedStoreName = name;
    return this;
  }

  public static Results[] resultsFromJson(String json) {
    return GsonTypeFactory.GENERIC_GSON.fromJson(json, new TypeToken<Results[]>(){}.getType());
  }
}