package main.java.lucia.client.content.order.test;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.ItemBundle;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.item.type.SimpleItem;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;
import main.java.lucia.client.content.order.impl.ItemList;
import main.java.lucia.client.content.order.impl.OrderSorting;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * Prints out info of a given {@link main.java.lucia.client.content.order.impl.ItemList}
 * @author Matthew Kwiatkowski
 */
public class ItemListPrinter {

    /**
     * Prints this item list to the given printstream.
     * @param s the printstream to print to.
     */
    public void printList(ItemList itemlis, PrintStream s){
        List<Item> items = OrderSorting.sortItems(itemlis.getItems());
        for(Item i: items){
            printItem(i, s);
        }
    }

    private void printBundle(ItemBundle b, PrintStream p){
        p.print("BUNDLE: '" + b.getName() + "'");
        p.print(" Price: " + b.getPrice());
        p.print(" DisplayPrice: " + b.getBundlePrice());
        p.println(" Items:");
        for(Item i: b){
            p.print("     ");
            printItem(i, p);
            //p.println();
        }
    }

    private void printItem(Item i, PrintStream s){
        if(i instanceof Pizza){
            printPizzaItem((Pizza)i, s);
        } else if(i instanceof ItemModifiable){
            printModifiable((ItemModifiable)i, s);
        } else if(i instanceof SimpleItem) {
            printSimpleItem((SimpleItem) i, s);
        } else if (i instanceof ItemBundle){
            printBundle((ItemBundle)i, s);
        } else{
            throw new IllegalArgumentException("Item: " + i.getName() + "was added to an order but is neither pizza, modifiable, or simple!");
        }
    }

    /**
     * Prints a pizza item
     * @param s the pizza to print
     */
    private void printPizzaItem(Pizza s, PrintStream p){
        p.print("PIZZA: ");
        p.print(" Price: " + s.getPrice());
        p.print(" Discounted Price: " + s.getDiscountedPrice());
        p.print(" Size: ");
        p.print(s.getSize());
        if(s.isSpecialty()){
            p.print(" Specialty: ");
            p.print(s.getPizzaDescriptor().getBaseName());
        }
        p.print(" Toppings: ");
        for(Map.Entry<ToppingType, Integer> ent: s.getAllToppingsOnPizza().entrySet()){
            printTopping(ent.getKey(), ent.getValue(), p);
            p.print(",");
        }
        p.println();
    }

    /**
     * prints the given pizza topping
     */
    private void printTopping(ToppingType t, Integer amt, PrintStream p){
        p.print("{");
        p.print(t.getName());
        p.print(":");
        p.print(amt);
        p.print("}");
    }

    /**
     * prints a modifiable item
     */
    private void printModifiable(ItemModifiable s, PrintStream p){
        p.print("MODIFIABLE ITEM: ");
        p.print(s.getName());
        p.print(" Price: " + s.getPrice());
        p.print(" Discounted Price: " + s.getDiscountedPrice());
        p.print("Addons: ");
        for(Addon a: s.getAddons()){
            printAmountable(a, p);
            p.print(",");
        }
        p.println();
    }

    /**
     * Prints out a given addon
     */
    private void printAmountable(Addon t, PrintStream p) {
        p.print("{");
        p.print(t.getName());
        p.print(":");
        p.print(t.getAmount());
        p.print("}");
    }

    private void printSimpleItem(SimpleItem s, PrintStream p){
        p.print("Simple ITEM: ");
        p.print(s.getName());
        p.print(" Price: " + s.getPrice());
        p.print(" Discounted Price: " + s.getDiscountedPrice());
        p.println();
    }
}
