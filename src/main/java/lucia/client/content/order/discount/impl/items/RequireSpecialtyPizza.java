package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.order.impl.ItemList;
import main.java.lucia.client.content.utils.IDCaster;

import java.io.PrintStream;
import java.util.*;

/**
 * Implemented Discount Attribute to include a specific
 * specialty pizza
 * @author Matthew Kwiatkowski
 */
public class RequireSpecialtyPizza extends DiscountApplicable{

    /**
     * The set of all specialty pizzas that must be contained
     * (by id), multiple occurences allowed
     */
    private int pizzaId;

    public RequireSpecialtyPizza(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    /**
     * Indicates whether or not the order can have
     * another application of this discount
     *
     * @param o the {@link ItemList} to apply it to
     * @return true if it can, false otherwise
     */
    @Override
    public Item canApply(Set<Item> o) {
        for(Item i: o){
            if(i instanceof Pizza){
                if(i.getItemDescriptor().getId() == pizzaId){
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * FIlters out which items from the set that this discount does NOT apply to.
     * @param o the set of items to check
     * @return a new set from elements of o which apply to this discount.
     */
    @Override
    public Set<Item> appliesTo(Set<Item> o) {
        Set<Item> ret = new HashSet<>();
        for(Item i: o){
            if(i instanceof Pizza && ((Pizza)i).getItemDescriptor().getId()==pizzaId){
                ret.add(i);
            }
        }
        return ret;
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print(" Specialty: " + new IDCaster<SpecialtyPizzaDescriptor>().cast(pizzaId).getBaseName());
    }
}
