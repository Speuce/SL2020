package main.java.lucia.client.content.order.discount.impl;

import main.java.lucia.client.content.order.discount.Discount;

import java.util.Map;

/**
 * Represents a discount (with fields filled in) applied to an order
 * @author Matthew Kwiatkowski
 */
public class AppliedDiscount {

    /**
     * The discount that was applied
     */
    private final Discount applied;

    /**
     * The amount saved with the discount
     */
    private long amtSaved;

    /**
     * The field name:value pairs for the discount's required fields.
     */
    private final Map<String, Object> filledFields;

    public AppliedDiscount(Discount applied, long amtSaved, Map<String, Object> filledFields) {
        this.applied = applied;
        this.amtSaved = amtSaved;
        this.filledFields = filledFields;
    }

    /**
     * The discount that was applied
     */
    public Discount getApplied() {
        return applied;
    }

    /**
     * The amount saved with the discount
     */
    public long getAmtSaved() {
        return amtSaved;
    }

    /**
     * The field name:value pairs for the discount's required fields.
     */
    public Map<String, Object> getFilledFields() {
        return filledFields;
    }

    /**
     * The amount saved with the discount
     */
    public void setAmtSaved(long amtSaved) {
        this.amtSaved = amtSaved;
    }
}
