package main.java.lucia.client.content.menu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.lucia.client.content.menu.io.MenuLoader;
import main.java.lucia.client.content.menu.io.MenuSaver;
import main.java.lucia.client.content.menu.item.IDAble;
import main.java.lucia.client.content.menu.item.descriptor.*;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

import java.io.*;
import java.util.*;

/**
 * Singleton class that contains all of the menu information
 * for the loaded location.
 * @author Matt Kwiatkowski
 */
public class Menu {

    /**
     * The Static-store singleton instance of the menu.
     */
    public static Menu get = new Menu();


    public static PizzaMenu pizza = new PrintablePizzaMenu();


    /**
     * The loaded sections of this menu
     * NOTE: PIZZA IS NOT CONSIDERED A SECTION
     */
    private List<String> loadedSections;

    private Map<String, List<SimpleItemDescriptor>> sectionItems;

    /**
     * Maps integer ids to menu items.
     */
    private Map<Integer, IDAble> menuItemMap;

    public Menu(){
        loadedSections = new ArrayList<>();
        sectionItems = new HashMap<>();
        menuItemMap = new HashMap<>();
        //registerMenuJsonSerializables();
    }

//    /**
//     * Register any item/menu-based JsonSerializables
//     * into the JsonSeriilazable Factory
//     */
//    private void registerMenuJsonSerializables(){
//        JsonSerializableFactory.get.registerSerializable(IDAble.class, new IDAble(0));
//        JsonSerializableFactory.get.registerSerializable(Topping.class, new ToppingType());
//        JsonSerializableFactory.get.registerSerializable(SimpleItemDescriptor.class, new SimpleItemDescriptor());
//        JsonSerializableFactory.get.registerSerializable(SimplePizzaDescriptor.class, new SimplePizzaDescriptor());
//        JsonSerializableFactory.get.registerSerializable(SpecialtyPizzaDescriptor.class, new SpecialtyPizzaDescriptor());
//        JsonSerializableFactory.get.registerSerializable(ItemModifiableDescriptor.class, new ItemModifiableDescriptor());
//        JsonSerializableFactory.get.registerSerializable(AddonDescriptor.class, new AddonDescriptor());
//    }

    /**
     * Get all of the items of this given section
     * @param s the section to find
     * @return a list containing all of the loaded items of the given section
     */
    public List<SimpleItemDescriptor> getSection(String s){
        return sectionItems.get(s);
    }

    /**
     * Gets all the loaded sections of the menu, EXCLUDES PIZZA
     */
    public List<String> getLoadedSections(){
        return loadedSections;
    }


    /**
     * Add a section to the menu
     * @param sectionName the name of the section
     * @param sectionItems the items contained within the section
     */
    public void addSection(String sectionName, List<SimpleItemDescriptor> sectionItems){
        this.sectionItems.put(sectionName, sectionItems);
        if(!this.loadedSections.contains(sectionName)){
            this.loadedSections.add(sectionName);
        }
    }

    /**
     * Get a mapped menu item from its id.
     * @param id the id of the menu item
     * @return the associated {@link IDAble}
     */
    public IDAble getItemFromId(Integer id){
        return menuItemMap.get(id);
    }

    /**
     * registers an item to the integer id table
     */
    public void addMenuItem(IDAble t){
        menuItemMap.put(t.getId(), t);
    }

    /**
     * Adds an item to the given section.
     * @param item the item to add
     * @param section the section of the menu to add to.
     */
    private void addItem(SimpleItemDescriptor item, String section){
        if(sectionItems.containsKey(section)){
            sectionItems.get(section).add(item);
        }else {
            List<SimpleItemDescriptor> items = new ArrayList<SimpleItemDescriptor>();
            items.add(item);
            sectionItems.put(section, items);
        }
        addMenuItem(item);
    }

    /**
     * Clears the menu.
     */
    public void clearMenu(){
        sectionItems.clear();
        loadedSections.clear();
        menuItemMap.clear();
        pizza.clear();
    }

    /**
     * Load the menu from a given json
     * @param source the souce of the menu json
     */
    public void loadMenu(File source){
        Gson gson = GsonTypeFactory.MENU_ITEM_GSON;
        JsonParser parse = new JsonParser();
        FileReader r = null;
        try {
            r = new FileReader(source);
            JsonObject parser = parse.parse(r).getAsJsonObject();
            clearMenu();
            MenuLoader loader = new MenuLoader(parser);

            //load all the sections and their items
            loadedSections = loader.loadSections();
            List<SimpleItemDescriptor> items;
            for(String section: loadedSections){
                items = loader.loadSectionItems(section);
                for(SimpleItemDescriptor item: items){
                    addMenuItem(item);
                }
                sectionItems.put(section, items);
            }
            pizza.load(loader, this);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the menu to the given file
     * @param dest the file to write the menu to.
     */
    public void saveMenu(File dest){
        JsonObject menuJson = new JsonObject();
        JsonObject pizzaJson = new JsonObject();
        MenuSaver saver = new MenuSaver(menuJson, pizzaJson);
        saver.saveSections(this.loadedSections);
        for(Map.Entry<String, List<SimpleItemDescriptor>> ent: sectionItems.entrySet()){
            saver.saveSection(ent.getKey(), ent.getValue());
        }
        pizza.save(saver);

        Gson gson = GsonTypeFactory.MENU_ITEM_GSON;
        JsonObject overall = new JsonObject();
        overall.add("menu", menuJson);
        overall.add("pizza", pizzaJson);
        try {
            FileWriter r = new FileWriter(dest);
            gson.toJson(overall, r);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints out all relevant details about the menu
     * @param out the stream to write to
     */
    public void printMenu(PrintStream out){
        out.println("MENU INFO:");
        for(Map.Entry<String, List<SimpleItemDescriptor>> ent: sectionItems.entrySet()){
            out.printf("%s\n", ent.getKey());
            for(SimpleItemDescriptor a : ent.getValue()){
                printItem(a, out);
            }
        }
    }

    /**
     * Prints out the details of a single item
     * @param item the item to print details of.
     * @param out the output stream to write to
     */
    private void printItem(SimpleItemDescriptor item, PrintStream out){
        if(item instanceof AddonDescriptor){
            printAddon((AddonDescriptor) item, out);
            out.println();
        }else if(item instanceof ItemModifiableDescriptor){
            out.printf(" - Modifiable: %s, ID: %d Price: %d, Addons:\n",
                    item.getBaseName(), item.getId(), item.getBasePrice());
            out.print("            ");
            for(AddonDescriptor a: ((ItemModifiableDescriptor)item).getAppliableAddons()){
                out.print("{");
                printAddon(a, out);
                out.print("}");
            }
            out.println();
        }else{
            out.printf(" - Basic: %s, ID: %d Price: %d\n",
                    item.getBaseName(), item.getId(), item.getBasePrice());
        }
    }

    /**
     * Prints out the details of an addonDescriptor without a newline
     * @param a the addon to print out
     * @param out the output stream to print to.
     */
    private void printAddon(AddonDescriptor a, PrintStream out){
        out.printf(" - Addon: %s, ID: %d Price: %d, Discountable: %b",
                a.getBaseName(), a.getId(), a.getBasePrice(), a.isDiscountable() );
    }





}
