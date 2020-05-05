package main.java.lucia.client.content.menu;

import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.io.MenuLoader;
import main.java.lucia.client.content.menu.io.MenuSaver;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimplePizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.client.content.menu.size.PricingScheme;
import main.java.lucia.client.content.menu.size.Size;

import java.io.PrintStream;
import java.util.*;

/**
 * Stores and manages all options relating to pizza builing.
 * @author Matt Kwiatkowski
 */
public class PizzaMenu {

    /**
     * The defined sizes of pizzas for this menu
     */
    protected TreeSet<Integer> definedSizes;

    /**
     * The list of all pizza sauces defined for this menu.
     */
    protected List<Sauce> definedSauces;

    /**
     * The list of all pizza crusts defined for this menu.
     */
    protected List<Crust> definedCrusts;

    /**
     * The map for searching integer sizes --> Size objects
     */
   // protected Map<Integer, Size> sizeMap;

    /**
     * The list of defined toppings for this menu
     */
    protected List<ToppingType> definedToppings;

    /**
     * The list of specialty pizzas loaded for this menu
     */
    protected List<SpecialtyPizzaDescriptor> specialties;

    /**
     * Maps PricingScheme name to PricingScheme object
     */
    protected Map<String, PricingScheme> pricingSchemeMap;

    /**
     * Maps topping names to ToppingType objects
     */
    protected Map<String, ToppingType> toppingTypeMap;

    /**
     * Regular sauce, for comparasion.
     */
    protected Sauce regular;

    /**
     * Regular crust
     */
    protected Crust regularCrust;

    /**
     * The loaded base prices of pizza. Represents a cheese pizza of any size.
     */
    protected SimplePizzaDescriptor basePizza;

    public PizzaMenu() {
        definedSizes = new TreeSet<>();
        //sizeMap = new HashMap<>();
        this.definedToppings = new ArrayList<>();
        this.specialties = new ArrayList<>();
        this.pricingSchemeMap = new HashMap<>();
        this.definedCrusts = new ArrayList<>();
        this.definedSauces = new ArrayList<>();
        this.toppingTypeMap = new HashMap<>();
    }

    /**
     * Get the list of loaded toppings on the menu
     * @return
     */
    public List<ToppingType> getDefinedToppings(){
        return definedToppings;
    }

    /**
     * Get all defined sizes as an integer
     */
    public TreeSet<Integer> getDefinedSizes() {
        return definedSizes;
    }

    /**
     * Get all defined sauces
     */
    public List<Sauce> getDefinedSauces() {
        return definedSauces;
    }

    /**
     * Get all defined crusts
     */
    public List<Crust> getDefinedCrusts() {
        return definedCrusts;
    }

    /**
     * Maps PricingScheme name to PricingScheme object
     */
    public Map<String, PricingScheme> getPricingSchemeMap() {
        return pricingSchemeMap;
    }


    /**
     * Does a case-insensitive search of Pricing Scheme, by name\
     * Reports an error if the pricing scheme is not found.
     * Ideally this search should only be called on menu loading,
     * So the additional if-statement shouldn't affect performance.
     * @param name the search parameter
     * @return the PricingScheme object, if found, null otherwise.
     */
    public PricingScheme getPricingScheme(String name){
        PricingScheme ret = pricingSchemeMap.get(name);
        if(ret == null){
            MLogger.logError("Pricing scheme search: " + name + " failed.");
            Thread.dumpStack();
        }
        return ret;
    }

    /**
     * Does a case-insensitive search of topping type, by name
     * Reports an error if the pricing scheme is not found.
     * Ideally this search should only be called on menu loading,
     * So the additional if-statement shouldn't affect performance.
     * @param name the search parameter
     * @return the PricingScheme object, if found, null otherwise.
     */
    public ToppingType getToppingType(String name){
        ToppingType ret = toppingTypeMap.get(name.toLowerCase());
        if(ret == null){
            MLogger.logError("Topping type search: " + name + " failed.");
        }
        return ret;
    }

    /**
     * Register/add a new pricing scheme to the menu.
     * Warning: Names, regardless of case, should NOT be duplicated.
     * @param l the pricing scheme to add
     */
    public void addPricingScheme(PricingScheme l){
        pricingSchemeMap.put(l.getName().toLowerCase(), l);
    }

    /**
     * Get a list of all of the specialty pizzas
     * @return
     */
    public List<SpecialtyPizzaDescriptor> getSpecialties() {
        return specialties;
    }

    /**
     * Get the regular sauce object
     */
    public Sauce getRegularSauce() {
        return regular;
    }

    public SimplePizzaDescriptor getBasePizza(){
        return this.basePizza;
    }

    /**
     * Sets the base pizza price
     * @param chz the base pizza, representing the pricing scheme for any size cheese pizza
     */
    public void setBasePizza(SimplePizzaDescriptor chz){
        this.basePizza = chz;
    }

    /**
     * Clears all available pizza options
     */
    public void clear(){
        definedSizes.clear();
        definedToppings.clear();
        specialties.clear();
    }

    /**
     * Performs the pizza menu loading procedures
     */
    void load(MenuLoader loader, Menu m){
        //load the defined sizes
        definedSizes = loader.loadDefinedSizes();

        //load the pricing schemes
        loader.loadPricingSchemes().forEach(l -> pricingSchemeMap.put(l.getName(), l));

        //load the sauces, crusts, toppings
        definedSauces = loader.loadDefinedSauceList();
        definedCrusts = loader.loadDefinedCrustList();
        definedToppings = loader.loadDefinedToppingList();

        //register the id mappings of all of these
        definedSauces.forEach(m::addMenuItem);
        definedCrusts.forEach(m::addMenuItem);
        definedToppings.forEach(m::addMenuItem);

        //make the mappings for toppings
        definedToppings.forEach(t -> toppingTypeMap.put(t.getName(), t));

        //load the default sauce/pizza
        regular = loader.loadRegularSauce();
        regularCrust = loader.loadRegularCrust();
        basePizza = new SimplePizzaDescriptor(1, "", Objects.requireNonNull(loader.loadBasePizzaPricing()));

        specialties = loader.loadSpecialtyList();
    }



    /**
     * Executes all pizza-related menu-saving procedures
     */
    void save(MenuSaver saver){
        saver.savePizzaSizes(this.getDefinedSizes());
        saver.savePricingSchemes(this.pricingSchemeMap.values());

        saver.saveDefinedCrustList(this.getDefinedCrusts());
        saver.saveDefinedSauceList(this.getDefinedSauces());
        saver.saveDefinedToppingList(this.definedToppings);

        saver.saveRegularPizza(this.basePizza.getPricingScheme());
        saver.saveRegularSauce(this.regular);
        saver.saveRegularCrust(this.regularCrust);

        saver.saveSpecialtyList(specialties);
    }

    public void addTopping(ToppingType type){
        this.definedToppings.add(type);
        this.toppingTypeMap.put(type.getName(), type);
    }

    public Crust getRegularCrust() {
        return regularCrust;
    }

    public void setRegularCrust(Crust regularCrust) {
        this.regularCrust = regularCrust;
    }
}
