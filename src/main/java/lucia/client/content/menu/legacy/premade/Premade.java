package main.java.lucia.client.content.menu.legacy.premade;

import java.util.ArrayList;
import main.java.lucia.client.content.menu.legacy.impl.addon.Addon;
import main.java.lucia.client.content.menu.legacy.toppings.Topping;

/**
 * The interface for a premade item
 * which allows the getting of the origin
 * premade item
 *
 * @author Brett Downey
 */
public interface Premade {

  Premade getOrigin();

  ArrayList<Topping> getToppings();

  ArrayList<Addon> getAddons();
}
