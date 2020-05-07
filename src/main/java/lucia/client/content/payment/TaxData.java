package main.java.lucia.client.content.payment;

/**
 * Contains all data about the tax rates.
 * @author Matthew Kwiatkowski
 * TODO make these data points set in json
 */
public class TaxData {

    public static final float GST_RATE = 0.05f;

    public static final float PST_RATE = 0.07f;

    /**
     * Calculates the rounded number of cents of gst to paid on a principle balance
     * @param principle the principle balance to calculate gst on
     * @return the gst to be paid
     */
    public static long getGstPaid(long principle){
        return Math.round(principle*GST_RATE);
    }

    /**
     * Calculates the rounded number of cents of pst to paid on a principle balance
     * @param principle the principle balance to calculate pst on
     * @return the pst to be paid
     */
    public static long getPstPaid(long principle){
        return Math.round(principle*PST_RATE);
    }
}
