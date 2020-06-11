package test.lucia.discount;

import main.java.lucia.client.content.order.discount.DiscountManager;

import java.io.File;

/**
 * Test to load all json tests
 */
public class DiscountDeserializeTest {

    public static void main(String[] args){
        DiscountManager.initialize(new File("src/main/resources/discounts.json"));



    }
}
