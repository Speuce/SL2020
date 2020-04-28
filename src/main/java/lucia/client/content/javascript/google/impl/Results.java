package main.java.lucia.client.content.javascript.google.impl;

import java.util.ArrayList;

public class Results {

  private ArrayList<String> types;

  private String formatted_addess;

  private ArrayList<AddressComponents> address_components;

  private boolean partial_match;

  private String place_id;

  private ArrayList<String> postcode_localities;

  private Geometry geometry;

  public ArrayList<String> getTypes() {
    return types;
  }

  public String getFormatted_addess() {
    return formatted_addess;
  }

  public ArrayList<AddressComponents> getAddress_components() {
    return address_components;
  }

  public boolean isPartial_match() {
    return partial_match;
  }

  public String getPlace_id() {
    return place_id;
  }

  public ArrayList<String> getPostcode_localities() {
    return postcode_localities;
  }

  public Geometry getGeometry() {
    return geometry;
  }
}