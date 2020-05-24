package main.java.lucia.client.content.order.test;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.content.menu.test.ItemTester;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;

import java.io.File;

/**
 * Tests order-server serialization
 * @author Matthew Kwiatkowski
 */
public class OrderJsonTesting {

    public static void main(String args[]){
        File menu = new File("src/main/resources/menutest.json");
        Menu.get.loadMenu(menu);

        Order order = new Order(OrderType.PICKUP);
        order.addItem(ItemTester.build15SpecialWithTop());
        order.addItem(ItemTester.build10Chz());
        order.addItem(ItemTester.getSomeSimpleItem());



        System.out.println(ItemGson.ITEM_GSON.toJson(order));

    }
}
