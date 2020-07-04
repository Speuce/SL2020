package main.java.lucia.client.content.payment.cashout;

import main.java.lucia.client.content.payment.paymentmethods.PaymentMethod;

public class CashOutOfTill {

    private PaymentMethod paymentMethod;

    private String reason;

    public CashOutOfTill(String reason, PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.reason = reason;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReason() {
        return reason;
    }
}
