package main.java.lucia.client.content.payment;

import main.java.lucia.util.currency.CurrencyConverter;

import java.math.BigDecimal;

/**
 * Abstract class for anything that should be accepted in a cashout
 * @author Matthew Kwiatkowksi
 */
public abstract class Billable {

    /**
     * Cost (in cents) (untaxed)
     */
    private long cost = -1;

    /**
     * Gets the cost of this billable
     * Calculates the cost if it hasn't been done yet.
     * @return the cost (in cents)
     */
    public long getCost() {
        if(cost == -1){
            recalculatePrice();
        }
        return cost;
    }

    /**
     * Recalculates the cost of this item.
     */
    public void recalculatePrice(){
        this.cost = calculateCost();
    }

    /**
     * Calculates the cost of this billable item,
     * most recent cost is stored in this object.
     * @return the calculated cost
     */
    protected abstract long calculateCost();

    /**
     *
     * @return The grand total with tax
     */
    public BigDecimal getGrandTotalTax() {
        return CurrencyConverter.taxAndBuild(getCost());
    }

    /**
     * Returns the GST paid (verify this)
     */
    public BigDecimal getGrandTotalGST() {
        return CurrencyConverter.taxAndBuildGST(getCost());
    }

    /**
     * Returns the PST paid (verify this)
     */
    public BigDecimal getGrandTotalPST() {
        return CurrencyConverter.taxAndBuildPST(getCost());
    }

    /**
     * Calculates the grand total in long format plus tax
     *
     * @return the grand total with tax
     */
    public long getGrandTotalLongWithTax(){
        return CurrencyConverter.withTax(getCost());
    }

    /**
     * Gets the actual total with tax
     *
     * @return a {@link BigDecimal} containing the actual total
     */
    public BigDecimal getAllTotal() {
        return getGrandTotalTax();
    }

    /**
     * @return The grand total in big decimal format
     */
    public BigDecimal getGrandTotalBD() {
        return CurrencyConverter.build(getCost());
    }
}
