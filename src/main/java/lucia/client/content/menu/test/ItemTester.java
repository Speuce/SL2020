package main.java.lucia.client.content.menu.test;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.item.type.SimpleItem;
import main.java.lucia.client.content.utils.IDCaster;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;

/**
 * class for creating certain menu items
 * @author Matthew Kwiatkowski
 */
public class ItemTester {

    /**
     * @return a 10-inch specialty pizza
     */
    public static Pizza build10Special(){
        Pizza special = Menu.pizza.getSpecialties().get(0).getAsItem(10);
        return special;
    }

    /**
     * @return a 13-inch specialty pizza
     */
    public static Pizza build13Special(){
        Pizza special = Menu.pizza.getSpecialties().get(0).getAsItem(13);
        return special;
    }

    /**
     * @return a 15-inch specialty pizza
     */
    public static Pizza build15Special(){
        Pizza special = Menu.pizza.getSpecialties().get(0).getAsItem(15);
        return special;
    }

    /**
     * @return a 15-inch specialty pizza with an added topping
     */
    public static Pizza build15SpecialWithTop(){
        Pizza special = Menu.pizza.getSpecialties().get(0).getAsItem(15);
        special.addTopping(new IDCaster<ToppingType>().cast(11), 2);
        return special;
    }

    /**
     * build a 10-inch chz pizza
     */
    public static Pizza build10Chz(){
        Pizza pizza = Menu.pizza.getBasePizza().getAsItem(10);
        return pizza;
    }

    /**
     * build a 13-inch chz pizza
     */
    public static Pizza build13Chz(){
        Pizza pizza = Menu.pizza.getBasePizza().getAsItem(13);
        return pizza;
    }

    /**
     * build a 15-inch chz pizza
     */
    public static Pizza build15Chz(){
        Pizza pizza = Menu.pizza.getBasePizza().getAsItem(15);
        return pizza;
    }

    /**
     * Finds any {@link ItemModifiable} in the menu
     * @return an {@link ItemModifiable} if found, null otherwise
     */
    public static ItemModifiable getSomeModifiableItem(){
        for(String sec : Menu.get.getLoadedSections()){
            for(SimpleItemDescriptor desc: Menu.get.getSection(sec)){
                if(desc instanceof ItemModifiableDescriptor){
                    return ((ItemModifiableDescriptor)desc).getAsItem();
                }
            }
        }
        return null;
    }

    /**
     * Finds any {@link SimpleItem} on the menu that is NOT modifiable and returns it
     * @return the {@link SimpleItem} if found, null otherwise
     */
    public static SimpleItem getSomeSimpleItem(){
        for(String sec : Menu.get.getLoadedSections()){
            for(SimpleItemDescriptor desc: Menu.get.getSection(sec)){
                if(!(desc instanceof ItemModifiableDescriptor)){
                    return (SimpleItem) ((SimpleItemDescriptor)desc).getAsItem();
                }
            }
        }
        return null;
    }
}
