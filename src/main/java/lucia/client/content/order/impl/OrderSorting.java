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
    private ArrayList<Item> sortItems(Collection<Item> items){
        //Set<Item> itemList = items;
        ArrayList<Item> sortedList = new ArrayList<Item>();

        /* Sort out item types and deal with them separately */
        Set<Pizza> pizzas = new HashSet<>();
        List<ItemModifiable> modifiables = new ArrayList<>();
        List<ItemBundle> bundles = new ArrayList<>();
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


        //TODO sort pizza
        Collections.sort(modifiables);
        Collections.sort(bundles);
        Collections.sort(remainder);

        //now that items is empty, we can start to add things back
        sortedList.addAll(bundles);
        sortedList.addAll(pizzas);
        sortedList.addAll(modifiables);
        sortedList.addAll(remainder);
        
        return sortedList;
    }

}
