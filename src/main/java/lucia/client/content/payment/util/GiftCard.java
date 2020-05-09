package main.java.lucia.client.content.payment.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Class representing a giftcard
 * @author Matt Kwiatkowski
 */
public class GiftCard {

    private Integer cardNumber;
    private BigDecimal balance;
    private Date activated;

    public GiftCard(Integer cardNumber, BigDecimal balance, Date activated) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.activated = activated;
    }
    public GiftCard(Integer cardNumber, BigDecimal balance){
        this(cardNumber, balance, new Date());
    }

    /**
     * @return the gift card's number (unique for every card)
     */
    public Integer getCardNumber() {
        return cardNumber;
    }

    /**
     * Gets the balance remaining on this gift card
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void addBalance(BigDecimal add){
        balance.add(add);
    }

    /**
     * subtract the given balance from the total balance
     * @param sub
     * @return {@code true} if the operation was successful and {@code false} otherwise
     */
    public boolean subtractBalance(BigDecimal sub){
        if(this.balance.doubleValue() >= sub.doubleValue()){
            this.balance.subtract(sub);
            return true;
        }
        return false;
    }

    /**
     * @return an {@link Date} representing the day that the card was activated
     */
    public Date getActivated() {
        return activated;
    }

    public void setActivated(Date activated) {
        this.activated = activated;
    }
}
