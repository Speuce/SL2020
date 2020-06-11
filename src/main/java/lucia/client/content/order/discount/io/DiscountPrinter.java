package main.java.lucia.client.content.order.discount.io;

import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.impl.amount.DiscountAmount;
import main.java.lucia.client.content.order.discount.impl.fields.DiscountField;
import main.java.lucia.client.content.order.discount.impl.items.AmountRequirement;
import main.java.lucia.client.content.order.discount.impl.times.DiscountTime;

import java.io.PrintStream;
import java.util.List;
import java.util.Set;

/**
 * Object for printing out discount info
 * @author Matthew Kwiatkowski
 */
public class DiscountPrinter {

    /**
     * Prints the given custom discount
     * @param d the discount to print.
     */
    public void print(CustomDiscount d, PrintStream out){
        out.println("Discount name: " + d.getName() + " id: " + d.getId());
        printDiscountPricing(d.getAmount(), out);
        printAmountRequirements(d.getApplicables(), out);
        printTime(d.getTime(), out);
        printFields(d.getFields(), out);
    }

    private void printTime(Set<DiscountTime> times, PrintStream out){
        out.println("   Discount time:");
        for(DiscountTime t: times){
            out.print("     ");
            t.printInfo(out);
            out.println();
        }
    }

    private void printAmountRequirements(List<AmountRequirement> amt, PrintStream out){
        out.println("   Amount Requirements: ");
        for(AmountRequirement a: amt){
            out.print("     ");
            a.printInfo(out);
            out.println();
        }
    }

    private void printDiscountPricing(DiscountAmount amt, PrintStream out){
        out.println("   Discount Pricing: ");
        out.print("     ");
        amt.printInfo(out);
        out.println();
    }

    private void printFields(List<DiscountField> fields, PrintStream out){
        out.println("   Discount Fields:");
        for(DiscountField t: fields){
            out.print("     ");
            t.printInfo(out);
            out.println();
        }
    }
}
