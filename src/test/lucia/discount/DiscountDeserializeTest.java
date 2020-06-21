package test.lucia.discount;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.order.discount.DiscountManager;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.io.DiscountPrinter;

import java.io.File;

/**
 * Test to load all json tests
 */
public class DiscountDeserializeTest {

    public static void main(String[] args){
        Menu.loadFromMenu();
        DiscountManager.initialize(new File("src/main/resources/discounts.json"));
        System.out.println("Loading discounts... ");
        DiscountPrinter pr = new DiscountPrinter();
        for (CustomDiscount d : DiscountManager.getLoadedCustomDiscounts()) {
            pr.print(d, System.out);
        }

    }
}
