package main.java.lucia.client.content.menu.test;

import com.google.gson.Gson;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.content.menu.pizza.Pizza;
import main.java.lucia.client.content.menu.pizza.ToppingType;

import java.io.File;

/**
 * Tests json creation from menu items
 * @author Matthew Kwiatkowski
 */
public class MenuJsonCreateTest {

    public static void main(String[] args){
        File menu = new File("src/main/resources/menutest.json");
        Menu.get.loadMenu(menu);

        Pizza byo = Menu.pizza.getBasePizza().getAsItem(13);
        ToppingType t = Menu.pizza.getDefinedToppings().get(0);
        System.out.println("Added topping: " + t.getId());
        byo.addTopping(t, 3);

        Gson thing = ItemGson.ITEM_GSON;
        System.out.println(thing.toJson(byo));
    }

}
