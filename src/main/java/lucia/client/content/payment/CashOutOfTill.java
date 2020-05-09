package main.java.lucia.client.content.payment;

public class CashOutOfTill implements Transaction {

    private PaymentMethod paymentMethod;

    private String reason;

    public CashOutOfTill(String reason, PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.reason = reason;
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReason() {
        return reason;
    }
}
