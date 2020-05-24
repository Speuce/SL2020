package main.java.lucia.client.content.menu;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.*;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.item.type.pizza.Crust;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;
import main.java.lucia.client.content.menu.size.PricingScheme;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.content.utils.IDCaster;

import java.util.List;

/**
 * Example showing usage of the new item format
 * @author Matthew Kwiatkowski
 */
public class MenuExample {

    public static void main(String[] args){

        //---Menu, Sections ---

        //get the menu instance (this is for anything non-pizza)
        Menu m = Menu.get;

        //get the section in the menu (chicken, appetizers, drinks, etc).
        //this is just by name.
        List<String> sections = m.getLoadedSections();

        //get the 'first' section. Whatever that is
        String section = sections.get(0);

        //if you want to see the items in a section, use:
        List<SimpleItemDescriptor> items = m.getSection(section);




        //---Descriptors---

        //Now you can loop through the list or whatever you would like.
        //Lets get the first item.
        SimpleItemDescriptor item = items.get(0);

        //Notice this isn't an item, this is a descriptor.
        //A descriptor DESCRIBES the properties of an item.
        //It has a base name and base price property.
        //Base name is the name of the item before any modifications are added to it
        //Think lasagna. Just lasagna.
        //Base price is the price for JUST lasagna.

        //There are a few types of descriptors, and you have to be sure which one you're using before you cast
        //SimpleItemDescriptor - items with no modifications (eg coke, coleslaw, etc)
        //ItemModifiableDescriptor - items with modifications (lasagna, salad)
        //when i say modifications, I really mean addons. (+Mush,+CKN, etc)
        //AddonDescriptor - items that are addons

        //ItemModifiableDescriptor and AddonDescriptor are both subclasses of SimpleItemDescriptor
        //Use x instanceof y to see if you're dealing with an addon, a modifiable item, or a simple item.

        //Like I said, descriptors only describe items in their most basic form.
        //Descriptors CANNOT be added to orders. But items can
        Item i1 = item.getAsItem();

        //Now you can add to order as normal.
        //If you know that this item should have modifications,
        //first cast to ItemModifiable, then create the item,
        //THEN apply addons
        ItemModifiableDescriptor mod = (ItemModifiableDescriptor)item;
        ItemModifiable i2 = mod.getAsItem();
        mod.getAppliableAddons();
        Addon addonToAdd = null; /* obviously this wouldnt be null */
        i2.addAddon(addonToAdd);




        //---Pizza Basics---

        //Next lets discuss pizza.
        //Pizza stuff is done through PizzaOptionsManager.

        //get the instance.
        PizzaMenu o = Menu.pizza;

        //There lots of options here.
        o.getDefinedCrusts();
        o.getDefinedSauces();
        o.getDefinedSizes();
        //Notice size is no longer an enum. Its an int. Sizes will be loaded in with JSON

        //Notice crust and sauce are objects now. How does this work?
        //Example with crust (sauce is very similar)
        //Get whichever crust type is first.
        //You will never know what this is. This is random. Dont do this.
        Crust c = o.getDefinedCrusts().get(0);

        //How much does the crust cost? What sizes is it available for?
        //This is managed by a pricing scheme.
        PricingScheme s = c.getPricingScheme();
        //check if this crust is available for 13 inch
        if(s.hasPrice(13)){/*...*/}
        //get the price for a 13 inch
        s.getPrice(13);



        //---What dynamic menus should look like---

        //When youre loading in a menu for buttons and such, lets just say you're loading
        //in the pizza crusts from the menu. First get a list of all the crusts
        List<Crust> crusts = o.getDefinedCrusts();

        //now loop through the list of crusts and add a new button for every crust
        //set the "onClick" or whatever its called to call a method "selectCrust(id)"
        //where id is crust.getId();
        for(Crust crus: crusts){
            //addButton(crus.getId());
        }
        //What is an "id" you may ask?
        //Every item has an unique associated id that is defined in json.
        //You can use the id to get back the original menu object. (you gotta cast though)
        //so then your selectCrust method would look something like:

       /* public void selectCrust(int id){
            Crust selected = (Crust)Menu.get.getItemFromId(id);
            ...other code
            }
        */


        //---Item Building---

        //We'll start with building an Lasagna
        //The thing is with getting a specific item, we can't just search for
        //'lasagna' in a map or something. Since menu is dynamically loaded.
        //We know that lasagna is modifiable since it can have addons,
        //But we don't know its' id. And thats okay, we wont have to know its id!
        //Since the id will be provided by the clicking of the 'lasagna' button
        //as long as dynamic menu loading is properly implemented.
        ItemModifiableDescriptor maybeLasagna = new IDCaster<ItemModifiableDescriptor>().cast(6969);
        //We dont know that 6969 is lasagna. And you should NEVER hardcode values into
        //searches. USE THE VALUE PASSED FROM ONCLICK!!!!!
        //now create the item
        ItemModifiable probablyLasagna = maybeLasagna.getAsItem();
        //now add an addon to it. Again, same thing, you have to go by IDS.
        //NEVER HARDCODE ID VALUES.
        AddonDescriptor definitelyNotMushrooms = new IDCaster<AddonDescriptor>().cast(6969);
        //now that we got the addon, add it to the lasagna
        probablyLasagna.addAddon(definitelyNotMushrooms.getAsItem());

        //Now we have an item with an addon added!



        //---Pizza Building---

        //Lets start with Build-Your-Own pizza.
        int selectedSize = 10;
        //Once a size is selected, you can create a new pizza object by:
        //firstly, getting the base pizza (a base pizza is a cheese pizza)
        SimplePizzaDescriptor basePizza = Menu.pizza.getBasePizza();
        //secondly, creating the pizza object
        Pizza pizza = basePizza.getAsItem(selectedSize);
        //OR you can just do
        pizza = new Pizza(selectedSize);
        //But you should be aware that when doing the constructor way,
        //it will set it to a cheese pizza, and will call .getBasePizza for a pricing scheme.

        //Now add a topping
        //toppings are like items. You should have an id given to you by the button pressed
        //(onclick):
        ToppingType type = new IDCaster<ToppingType>().cast(6969);

        //now we cant add a topping type to a pizza. We gotta specify HOW MUCH topping to put
        //1-light 2-regular 3-Xtra 4-XXtra
        int amount = 3;
        pizza.addTopping(type, amount);

        //If the user changes the amount, no need to remove and re-add.
        //Pizza.java will take care of that, Just add the topping as usual.

        //To remove a topping:
        pizza.removeTopping(type); //amount is irrelevant when removing


        //Now what about specialty pizzas?
        //We'll have to get the SpecialtyPizzaDescriptor by id (no surprise there hopefully)
        SpecialtyPizzaDescriptor sls = new IDCaster<SpecialtyPizzaDescriptor>().cast(6969);
        Pizza special = sls.getAsItem(selectedSize);

        //Thats it. If you want to remove a topping from a specialty
        special.removeTopping(type);
        //Pizza.java takes care of the rest.



        //--- Order building ---

        //Alright now lets build an order
        Order order = new Order(OrderType.DELIVERY);

        //add some items to it (remember: Descriptors cannot be added! Items only!)
        order.addItem(pizza);
        order.addItem(probablyLasagna);

    }
}
