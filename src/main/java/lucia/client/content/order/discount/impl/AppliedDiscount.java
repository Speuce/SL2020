package main.java.lucia.client.content.order.discount.impl;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.discount.Discount;

import java.util.Map;

/**
 * Represents a discount (with fields filled in) applied to an order
 * @author Matthew Kwiatkowski
 */
public class AppliedDiscount {

    /**
     * row num, for server saving.
     * Set by gson.
     */
    private final int rowNum;

    /**
     * The discount that was applied
     */
    private final Discount applied;

    /**
     * The amount saved with the discount
     */
    private int amtSaved;

    /**
     * The field name:value pairs for the discount's required fields.
     */
    private final Map<String, Object> filledFields;

    public AppliedDiscount(Discount applied, int amtSaved, Map<String, Object> filledFields) {
        this.applied = applied;
        this.amtSaved = amtSaved;
        rowNum = -1;
        this.filledFields = filledFields;
    }

    public AppliedDiscount(int rowNum, Discount applied, int amtSaved, Map<String, Object> filledFields) {
        this.rowNum = rowNum;
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
     * Adds the given amount to the total amount saved.
     */
    public void addAmtSaved(int add){
        amtSaved+=add;
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
    public void setAmtSaved(int amtSaved) {
        this.amtSaved = amtSaved;
    }

    /**
     * row num, for server saving.
     */
    public int getRowNum() {
        return rowNum;
    }

    /**
     * Removes this discount from the given order
     */
    public void remove(Order o){
        if(o.getDiscountList().remove(this)){

        }
    }
}
