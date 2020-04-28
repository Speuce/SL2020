package main.java.lucia.client.content.order.pricing;


/**
 * The discount calculator class, which loads
 * the discount file and applies any known discounts
 * when required
 *
 * @author Brett Downey, zach
 */
public class DiscountOthersCalculator {

    private static DiscountOthersCalculator INSTANCE;

    private HalfOff halfOffDiscounts;

    private PickupSpecial pickupSpecials;

    private int deliveryFee;

    private StaffDiscount staffDiscount;

    public StaffDiscount getStaffDiscount() {
        return this.staffDiscount;
    }

    public HalfOff getHalfOffDiscounts() {
        return halfOffDiscounts;
    }

    public PickupSpecial getPickupSpecials() {
        return pickupSpecials;
    }

    public void prepare() {
        halfOffDiscounts.calculateNonDiscountedSizes();
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public static void setInstance(DiscountOthersCalculator calculator) {
        INSTANCE = calculator;
        INSTANCE.prepare();
    }

    public static DiscountOthersCalculator getInstance() {
        return INSTANCE;
    }
}