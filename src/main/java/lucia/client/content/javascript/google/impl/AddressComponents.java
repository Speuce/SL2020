package main.java.lucia.client.content.javascript.google.impl;

import java.util.ArrayList;

/**
 * The address components from the Google Maps
 * JavaScript API V3
 *
 * @author Brett Downey
 */
public class AddressComponents {

  private String short_name;

  private String long_name;

  private ArrayList<String> postcode_localities;

  private ArrayList<String> types;

  public String getShort_name() {
    return short_name;
  }

  public String getLong_name() {
    return long_name;
  }

  public ArrayList<String> getPostcode_localities() {
    return postcode_localities;
  }

  public ArrayList<String> getTypes() {
    return types;
  }
}