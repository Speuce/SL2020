package main.java.lucia.client.content.javascript.google.impl;

/**
 * The Google LatLng bounds class from JavaScript
 *
 * @author Brett Downey
 */
public class LatLngBounds {

  /**
   * North east corner of the bound
   */
  private LatLng northeast;

  /**
   * Southwest corner of the bound
   */
  private LatLng southwest;

  public LatLng getNortheast() {
    return northeast;
  }

  public LatLng getSouthwest() {
    return southwest;
  }
}