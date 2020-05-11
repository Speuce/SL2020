package main.java.lucia.client.content.payment.paymentmethods;

import main.java.lucia.client.content.payment.util.GiftCard;

import java.time.LocalDateTime;

/**
 * A gift certificate payment
 * @author Matt Kwiatkowski
 * For GSON usage see Payment.java
 */
public class GiftPayment extends SimplePayment {

    /**
     * The Number of the gift certificate
     */
    public Integer giftCertificateNumber;

    public GiftPayment(long price , GiftCard c) {
        super(PaymentType.GIFT, price);
        giftCertificateNumber = c.getCardNumber();
    }

    public GiftPayment(long price , Integer number) {
        super(PaymentType.GIFT, price);
        giftCertificateNumber = number;
    }

    public GiftPayment(long price , GiftCard c, LocalDateTime time) {
        super(PaymentType.GIFT, price, time);
        giftCertificateNumber = c.getCardNumber();
    }

    public GiftPayment(long price , Integer number, LocalDateTime time) {
        super(PaymentType.GIFT, price, time);
        giftCertificateNumber = number;
    }



    /**
     * @return an {@link Integer}
     */
    public Integer getGiftCertificateNumber(){
        return giftCertificateNumber;
    }


}
