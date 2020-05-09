package main.java.lucia.client.content.menu.test;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PrintablePizzaMenu;

import java.io.File;

/**
 * Loads the menu and prints it.
 * @author Matthew Kwiatkowski
 */
public class MenuLoadTest {

    public static void main(String[] args){
        File menu = new File("src/main/resources/menu.json");
        Menu.get.loadMenu(menu);
        Menu.get.printMenu(System.out);
        ((PrintablePizzaMenu)Menu.pizza).print(System.out);
    }
}
