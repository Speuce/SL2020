package main.java.lucia.client.content.payment;

/**
 * An enum to track the various payment types available for orders
 * @author Matt Kwiatkowski
 */
public enum PaymentType {

    //The most common payment types
    MASTERCARD("MC"),
    VISA("Visa"),
    DEBIT("Debit"),
    CASH("Cash"),
    GIFT("Gift"),
    AMEX("AMEX"),
    DISCOVER("Discover"),
    CHEQUE("CHEQUE"),

    //Split Payment Type
    SPLIT("Split"),

    //A good C.Y.A. just incase
    OTHER("OTHER"),

    //not paid
    UNPAID("UNPAID");

    /**
     * The display text for this payment type
     */
    private String displayCode;

    private PaymentType(String displayCode){
        this.displayCode = displayCode;
    }

    /**
     * @return the {@link String} for displaying this paymentType
     */
    public String getDisplayCode(){
        return this.displayCode;
    }
}
