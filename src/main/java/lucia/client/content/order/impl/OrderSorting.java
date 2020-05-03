package main.java.lucia.client.content.order.impl;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.ItemBundle;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.pizza.Pizza;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides the default sorting functionality
 * for order items
 * @author Matthew Kwiatkowski
 */
public class OrderSorting {

    /**
     * Sorts the orders items by the defined ordering
     * @return the ordered list
     */
    private List<Item> sortItems(Collection<Item> items){
        //Set<Item> itemList = items;

        /* Sort out item types and deal with them separately */
        List<Item> pizzas = new ArrayList<>();
        List<Item> modifiables = new ArrayList<>();
        List<Item> bundles = new ArrayList<>();
        List<Item> remainder = new ArrayList<>();
        Iterator<Item> iter = items.iterator();
        Item item;
        while(iter.hasNext()){
            item = iter.next();
            if(item instanceof Pizza){
                pizzas.add((Pizza) item);
            }else if(item instanceof ItemModifiable){
                modifiables.add((ItemModifiable) item);
            }else if(item instanceof ItemBundle){
                bundles.add((ItemBundle) item);
            }else{
                remainder.add(item);
            }
        }
        //sort required collections


        Collections.sort(pizzas);
        Collections.sort(modifiables);
        Collections.sort(bundles);
        Collections.sort(remainder);

        //now that items is empty, we can start to add things back
        bundles.addAll(pizzas);
        bundles.addAll(modifiables);
        bundles.addAll(remainder);

        return bundles;
    }

}
