package main.java.lucia.client.content.menu;

import main.java.lucia.client.content.menu.item.descriptor.SimplePizzaDescriptor;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.client.content.menu.pizza.Topping;
import main.java.lucia.client.content.menu.size.PricingScheme;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * Extended PizzaMenu with print functionality!
 * @author Matthew Kwiatkowski
 */
public class PrintablePizzaMenu extends PizzaMenu{

    public PrintablePizzaMenu() {
        super();
    }

    /**
     * Prints out the pizza options to the given stream
     * @param out the stream to print to
     */
    public void print(PrintStream out){
        out.println("PIZZA OPTIONS INFO:");
        printSizes(out);
        printPricingSchemes(out);
        printCrusts(out);
        printSauces(out);
        printToppings(out);
        printSpecialties(out);
    }

    /**
     * Prints out the sizes
     */
    public void printSizes(PrintStream out){
        out.println("Defined Sizes:");
        for(Integer r: definedSizes){
            out.println("- " + r);
        }
    }

    /**
     * Prints the list of sauces
     */
    public void printSauces(PrintStream out){
        out.println("Defined Sauces");
        for(Sauce a: definedSauces){
            out.printf(" - Sauce: %s, ID: %d Pricing: ", a.getDisplayName(), a.getId());
            printPricingScheme(a.getPricingScheme(), out);
            out.println();
        }
    }

    /**
     * Prints the list of crustss
     */
    public void printCrusts(PrintStream out){
        out.println("Defined Crusts");
        for(Crust a: definedCrusts){
            out.printf(" - Crust: %s, ID: %d Pricing: ", a.getDisplayName(), a.getId());
            printPricingScheme(a.getPricingScheme(), out);
            out.println();
        }
    }

    /**
     * Prints the given {@link PricingScheme} to the given stream
     * @param s the pricingscheme to print
     * @param out the stream to print to
     */
    private void printPricingScheme(PricingScheme s, PrintStream out){
        out.print("[");
        for(Map.Entry<Integer, Long> ent: s.getPriceMap().entrySet()){
            out.printf("{%d:%d}", ent.getKey(), ent.getValue());
        }
        out.print("]");
    }

    /**
     * Prints a list of all defined pricing schemes
     * @param out the stream to print to.
     */
    public void printPricingSchemes(PrintStream out){
        out.println("Pricing Schemes:");
        pricingSchemeMap.forEach((s, pr) -> {out.print(" - " + s);
            printPricingScheme(pr, out);
            out.println();});
    }

    /**
     * Prints the list of all available toppings
     * @param out the stream to print to.
     */
    public void printToppings(PrintStream out){
        out.println("Toppings:");
        toppingTypeMap.forEach((s, type) -> {
            out.printf(" - Topping: %s, ID: %s, Pricing: ", s, type.getId());
            printPricingScheme(type.getPricingScheme(), out);
            out.println();
        });
    }


    public void printSpecialties(PrintStream out){
        out.println("Specialties: ");
        specialties.forEach( spec -> {
            out.printf(" - Special: %s, ID: %d, Sauce: %s, Crust: %s, Toppings: ",
                    spec.getBaseName(), spec.getId(), spec.getSauce().getDisplayName(), spec.getCrust().getDisplayName());
            printToppingList(spec.getToppings(), out);
            out.print(" Special Instructions: ");
            spec.getSpecialInstructions().forEach(s -> out.print(s + ","));
            out.println();
        });
    }

    private void printToppingList(List<Topping> toppingList, PrintStream out){
        out.print("[");
        toppingList.forEach(t -> out.printf("{%s:%d}", t.getName(), t.getAmount()));
        out.print("]");
    }

    /**
     * Adds a pricing scheme to the pizza menu
     * @param r the pricingScheme to add
     */
    public void addPricingScheme(PricingScheme r){
        this.pricingSchemeMap.put(r.getName(), r);
    }

    /**
     * Sets the regular sauce
     * @param r the "regular" sauce
     */
    public void setRegularSauce(Sauce r){
        this.regular = r;
    }

    /**
     * Sets the base (cheese) pizza pricing scheme
     * @param r the pricng scheme to set
     */
    public void setBasePizza(PricingScheme r){
        this.basePizza = new SimplePizzaDescriptor(1, "", r);
    }
}
