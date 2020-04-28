package main.java.lucia.client.content.menu.legacy.impl.addon;

import main.java.lucia.client.content.menu.legacy.Item;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadeItem;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeBeverageNames;

/**
 * An addon, which can be added to any
 * existing item
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public class Addon extends Item {

  /**
   * The specific id of this addon on the server.
   * DO NOT SET. Let Gson do that.
   */
  private int rowNum = -1;

  /**
   * The type of addon that is being created
   */
  private AddonType addonType;

  /**
   * A new item that is being created, with the beginning piece of the name.
   *
   * @param addonType The addonType of addon
   */
  public Addon(String name, AddonType addonType) {
    super(name);
    this.addonType = addonType;
  }

  public AddonType getType() {
    return addonType;
  }

  @Override
  public Addon safeClone() {
    return new Addon(name, addonType);
  }

  @Override
  @Deprecated
  public void generateName() {
  }

  @Override
  @Deprecated
  public void calculatePrice() {

  }
}