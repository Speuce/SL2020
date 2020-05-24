package main.java.lucia.client.content.payment;

import main.java.lucia.client.content.payment.util.TaxData;
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
     * The gst/pst paid on this item
     */
    private long gst, pst;

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
        this.gst = TaxData.getGstPaid(this.cost);
        this.pst = TaxData.getPstPaid(this.cost);
    }

    /**
     * Calculates the cost of this billable item,
     * most recent cost is stored in this object.
     * @return the calculated cost
     */
    protected abstract long calculateCost();

    /**
     * @return The grand total with tax
     */
    public BigDecimal getGrandTotalTax() {
        return CurrencyConverter.taxAndBuild(getCost());
    }

    /**
     * Returns the GST paid (verify this)
     */
    public BigDecimal getGrandTotalGST() {
        return CurrencyConverter.build(getGSTPaid());
    }

    /**
     * Returns the PST paid (verify this)
     */
    public BigDecimal getGrandTotalPST() {
        return CurrencyConverter.build(getPSTPaid());
    }

    /**
     * Calculates the grand total in long format plus tax
     *
     * @return the grand total with tax
     * @deprecated use {@link #getGrandTotal()}
     */
    @Deprecated()
    public long getGrandTotalLongWithTax(){
        return getGrandTotal();
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

    /**
     * Get the GST paid on this billable
     */
    public long getGSTPaid() {
        return gst;
    }

    /**
     * Get the PST paid on this billable
     */
    public long getPSTPaid() {
        return pst;
    }

    /**
     * Get the total amount of taxes paid
     */
    public long getTaxes(){
        return gst+pst;
    }

    /**
     * Get the grand total including all taxes
     *
     */
    public long getGrandTotal(){
        return getCost() + gst + pst;
    }

    /**
     * Sets the gst amount paid on this billable
     * Should only be done within a subclass.
     * @param gst the gst to be paid
     */
    protected void setGst(long gst) {
        this.gst = gst;
    }

    /**
     * Sets the pst amount paid on this billable
     * should only be done within a subclass
     * @param pst the pst to be paid
     */
    protected void setPst(long pst) {
        this.pst = pst;
    }
}
