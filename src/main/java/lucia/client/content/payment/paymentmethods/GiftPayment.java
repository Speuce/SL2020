package main.java.lucia.client.content.payment.paymentmethods;

import main.java.lucia.client.content.payment.GiftCard;
import main.java.lucia.client.content.payment.PaymentType;

/**
 * A gift certifcate payment
 * @author Matt Kwiatkowski
 * For GSON usage see Payment.java
 */
public class GiftPayment extends SimplePayment {

    public Integer giftCertificateNumber;

    public GiftPayment(long price , GiftCard c) {
        super(PaymentType.SPLIT, price);
        giftCertificateNumber = c.getCardNumber();
    }

    /**
     * @return an {@link Integer}
     */
    public Integer getGiftCertificateNumber(){
        return giftCertificateNumber;
    }


}
