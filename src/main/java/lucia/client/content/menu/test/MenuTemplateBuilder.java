package main.java.lucia.client.content.menu.test;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PrintablePizzaMenu;
import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.client.content.menu.pizza.Topping;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.client.content.menu.size.PricingScheme;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a json template for the menu.
 * @author Matthew Kwiatkowski
 */
public class MenuTemplateBuilder {

    public static void main(String[] args) throws IOException {
        //main menu stuff
        String sectionName = "appetizers";
        List<SimpleItemDescriptor> itemList1 = new ArrayList<>();

        SimpleItemDescriptor cheeseToast = new SimpleItemDescriptor(2, "cheese toast", 700);
        SimpleItemDescriptor frenchFries = new SimpleItemDescriptor(3, "french fries", 495);

        itemList1.add(cheeseToast);
        itemList1.add(frenchFries);
        Menu.get.addSection(sectionName, itemList1);

        String sectionName2 = "Sauces";
        List<SimpleItemDescriptor> itemList2 = new ArrayList<>();

        AddonDescriptor hotSauce = new AddonDescriptor(4, "hot sauce", 0, false);
        AddonDescriptor bbqSauce = new AddonDescriptor(5, "bbq sauce", 0, false);

        itemList2.add(hotSauce);
        itemList2.add(bbqSauce);
        Menu.get.addSection(sectionName2, itemList2);

        ItemModifiableDescriptor wings = new ItemModifiableDescriptor(6, "wings", 1295);
        wings.addAddon(hotSauce);
        wings.addAddon(bbqSauce);
        itemList1.add(wings);

        //pizza menu things
        PrintablePizzaMenu pizza = (PrintablePizzaMenu) Menu.pizza;

        pizza.getDefinedSizes().add(10);
        pizza.getDefinedSizes().add(13);
        pizza.getDefinedSizes().add(15);

        PricingScheme noExtraCharge = new PricingScheme("free");
        noExtraCharge.addPrice(10, 0);
        noExtraCharge.addPrice(13, 0);
        noExtraCharge.addPrice(15, 0);

        pizza.addPricingScheme(noExtraCharge);

        Sauce regular = new Sauce(7, "regular", "regular", noExtraCharge);
        pizza.setRegularSauce(regular);
        pizza.getDefinedSauces().add(regular);

        PricingScheme basePizza = new PricingScheme("basePizza");
        basePizza.addPrice(10, 999);
        basePizza.addPrice(13, 1399);
        basePizza.addPrice(15, 1999);

        pizza.addPricingScheme(basePizza);

        pizza.setBasePizza(basePizza);

        Crust someCrust = new Crust(8, "regular", "regular", noExtraCharge);
        pizza.setRegularCrust(someCrust);
        pizza.getDefinedCrusts().add(someCrust);

        ToppingType someTopping = new ToppingType(9, "pepperoni","pep", noExtraCharge);
        pizza.addTopping(someTopping);

        Map<ToppingType, Integer> toppings = new HashMap<>();
        toppings.put(someTopping, 3);

        SpecialtyPizzaDescriptor specialty = new SpecialtyPizzaDescriptor(10,
                "XPEPPizza", basePizza, regular, someCrust, toppings, new ArrayList<>());

        pizza.getSpecialties().add(specialty);

        Menu.get.printMenu(System.out);
        pizza.print(System.out);



        File menu = new File("src/main/resources/menutest.json");
        if(!menu.exists()){
            menu.createNewFile();
        }

        Menu.get.saveMenu(menu);
    }
}
