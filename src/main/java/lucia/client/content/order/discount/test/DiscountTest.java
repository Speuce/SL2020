package main.java.lucia.client.content.order.discount.test;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.descriptor.ItemBundleDescriptor;
import main.java.lucia.client.content.menu.test.ItemTester;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.impl.amount.BundlePrice;
import main.java.lucia.client.content.order.discount.impl.amount.BundlePriceToppingAllowance;
import main.java.lucia.client.content.order.discount.impl.amount.PercentageOff;
import main.java.lucia.client.content.order.discount.impl.amount.PriceOffPerItem;
import main.java.lucia.client.content.order.discount.impl.items.*;
import main.java.lucia.client.content.order.discount.impl.stacking.DiscountStacking;
import main.java.lucia.client.content.order.discount.impl.times.DiscountTime;
import main.java.lucia.client.content.order.discount.impl.times.TimeEveryDay;
import main.java.lucia.client.content.order.test.ItemListPrinter;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Quick class for testing discounts, ensuring that they work
 * as intended
 * @author Matthew Kwiatkowski
 */
public class DiscountTest {

    public static void main(String[] args){
        try{
            //load the menu first
            File menu = new File("src/main/resources/menu.json");
            Menu.get.loadMenu(menu);
            testDiscount2();

        }catch(Exception | Error e){
            e.printStackTrace();
        }
    }

    /**
     * tests the first discount (pizzas/specialties)
     */
    private static void testDiscount1(){
        System.out.println("Testing discount 1:");
        CustomDiscount d1 = buildDiscount1();
        Order o1 = buildOrder1();
        o1.finalizeOrder();
        ItemListPrinter p = new ItemListPrinter();
        System.out.println("Order before discount: ");
        p.printList(o1, System.out);
        if(d1.isDiscountEligible(o1)){
            d1.applyDiscount(o1);
        }else{
            System.out.println("ORDER NOT DISCOUNT ELGIBLE!!");
        }
        System.out.println("Order after discount: ");
        p.printList(o1, System.out);
    }

    /**
     * tests the first discount (pizzas/specialties)
     */
    private static void testDiscount2(){
        System.out.println("Testing discount 2:");
        CustomDiscount d1 = buildDiscount2();
        Order o1 = buildOrder2();
        o1.finalizeOrder();
        ItemListPrinter p = new ItemListPrinter();
        System.out.println("Order before discount: ");
        p.printList(o1, System.out);
        if(d1.isDiscountEligible(o1)){
            d1.applyDiscount(o1);
        }else{
            System.out.println("ORDER NOT DISCOUNT ELGIBLE!!");
        }
        System.out.println("Order after discount: ");
        p.printList(o1, System.out);
    }

    /**
     * Builds an order for the first discount
     */
    private static Order buildOrder1(){
        Order o = new Order(OrderType.PICKUP);
        o.addItem(ItemTester.build13Special());
        o.addItem(ItemTester.build15Special());
        o.addItem(ItemTester.build13Special());
        o.addItem(ItemTester.build10Chz());
        o.addItem(ItemTester.build13Chz());
        return o;
    }

    /**
     * Builds an order for the first discount
     */
    private static Order buildOrder2(){
        Order o = new Order(OrderType.PICKUP);
        o.addItem(ItemTester.build13Special());
        o.addItem(ItemTester.build15SpecialWithTop());
        o.addItem(ItemTester.build13Special());
        o.addItem(ItemTester.build10Chz());
        o.addItem(ItemTester.build13Chz());
        return o;
    }

    /**
     * Builds the first test discount (pizzas)
     */
    private static CustomDiscount buildDiscount1(){
        LinkedList<AmountRequirement> lis = new LinkedList<>();
        lis.add(buildAmount1());
        lis.add(buildAmount2());
        Set<DiscountTime> time = new HashSet<>();
        time.add(getEveryDay());
        return new CustomDiscount("test1", 2, lis,
                time, new DiscountStacking(),get10PercentOff(), true);
    }

    /**
     * Builds the second test discount (bundles!)
     */
    private static CustomDiscount buildDiscount2(){
        LinkedList<AmountRequirement> lis = new LinkedList<>();
        lis.add(buildAmount1());
        lis.add(buildAmount2());
        Set<DiscountTime> time = new HashSet<>();
        time.add(getEveryDay());
        return new CustomDiscount("test2", 3, lis,
                time, new DiscountStacking(),getBundlePrice(), true);
    }



    //builds an item requirement requiring 1 specialty pizza (the first specialty on the menu)
    private static AmountRequirement buildAmount1(){
        RequireSpecialtyPizza requireSpec = new RequireSpecialtyPizza(Menu.pizza.getSpecialties().get(0).getId());
        RequirePizzaSize requirePizzaSize = new RequirePizzaSize(13);
        RequireAND and = new RequireAND(requirePizzaSize, requireSpec);
        RequireOR or = new RequireOR(requirePizzaSize, requireSpec);
        AmountRequirement req = new AmountRequirement(and, 1);
        return req;
    }

    //builds an item requirement requiring 1 specialty pizza (the first specialty on the menu)
    private static AmountRequirement buildAmount2(){
        RequireSpecialtyPizza requireSpec = new RequireSpecialtyPizza(Menu.pizza.getSpecialties().get(0).getId());
        RequirePizzaSize requirePizzaSize = new RequirePizzaSize(15);
        RequireAND and = new RequireAND(requirePizzaSize, requireSpec);
        RequireOR or = new RequireOR(requirePizzaSize, requireSpec);
        AmountRequirement req = new AmountRequirement(and, 1);
        return req;
    }

    private static TimeEveryDay getEveryDay(){
        return new TimeEveryDay();
    }

    /**
     * Builds a discount calculator to give 10% off.
     */
    private static PercentageOff get10PercentOff(){
        return new PercentageOff(0.1f);
    }

    /**
     * Builds a discount calculator to give $5 off per item
     */
    private static PriceOffPerItem get5DollarsOff(){
        return new PriceOffPerItem(500);
    }

    /**
     * Builds a discount calculator to give the whole lot of items for a given price.
     */
    private static BundlePrice getBundlePrice(){
        return new BundlePriceToppingAllowance(new ItemBundleDescriptor("Special Pizza Bundle", 2499), 0);
    }


}
