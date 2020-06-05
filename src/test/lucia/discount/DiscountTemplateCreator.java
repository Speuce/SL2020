package test.lucia.discount;

import main.java.lucia.client.content.order.discount.DiscountManager;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.impl.amount.PercentageOff;
import main.java.lucia.client.content.order.discount.impl.fields.DiscountField;
import main.java.lucia.client.content.order.discount.impl.fields.FieldType;
import main.java.lucia.client.content.order.discount.impl.items.AmountRequirement;
import main.java.lucia.client.content.order.discount.impl.items.RequireANY;
import main.java.lucia.client.content.order.discount.impl.stacking.DiscountStacking;
import main.java.lucia.client.content.order.discount.impl.times.DiscountTime;
import main.java.lucia.client.content.order.discount.impl.times.TimeEveryDay;

import java.io.File;
import java.util.*;

/**
 * Test to create a json template for discounts.
 */
public class DiscountTemplateCreator {

    public static void main(String[] args){
        DiscountManager.getLoadedCustomDiscounts().add(buildCAADiscount());
        File discountFile = new File("src/main/resources/discounts.json");
        DiscountManager.save(discountFile);
    }

    private static CustomDiscount buildCAADiscount(){
        LinkedList<AmountRequirement> lis = new LinkedList<>();
        lis.add(new AmountRequirement(new RequireANY(), 1));
        Set<DiscountTime> time = new HashSet<>();
        time.add(new TimeEveryDay());
        List<DiscountField> field = new ArrayList<>();
        field.add(new DiscountField("Name", FieldType.TEXT));
        field.add(new DiscountField("CAA Number", FieldType.NUMBER));
        field.add(new DiscountField("Expiry Date", FieldType.TIME));
        return new CustomDiscount("caa", 101, lis,
                time, field, new DiscountStacking(),new PercentageOff(0.10f), true);
    }
}
