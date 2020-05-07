package main.java.lucia.client.content.menu.test;

import com.google.gson.Gson;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;

import java.io.File;

/**
 * Tests json creation from menu items
 * @author Matthew Kwiatkowski
 */
public class MenuJsonCreateTest {

    public static void main(String[] args){
        File menu = new File("src/main/resources/menutest.json");
        Menu.get.loadMenu(menu);

        Gson thing = ItemGson.ITEM_GSON;

        Pizza byo = Menu.pizza.getBasePizza().getAsItem(13);
        ToppingType t = Menu.pizza.getDefinedToppings().get(0);
        System.out.println("Added topping: " + t.getId());
        byo.addTopping(t, 3);
        System.out.println("Pizza: ");
        System.out.println(thing.toJson(byo));


        ItemModifiable mod = ItemTester.getSomeModifiableItem();
        assert mod != null;
        System.out.println("Modifiable (no addons):");
        System.out.println(thing.toJson(mod));


        mod.addAddon(mod.getItemDescriptor().getAppliableAddons().get(0).getAsItem(2));
        System.out.println("Modifiable (w/ addons):");
        System.out.println(thing.toJson(mod));


        System.out.println();



    }

}
