package main.java.lucia.client.content.payment;

public class CashOutOfTill implements Transaction {

    private Payment payment;

    private String reason;

    public CashOutOfTill(String reason,Payment payment) {
        this.payment = payment;
        this.reason = reason;
    }

    @Override
    public Payment getPayment() {
        return payment;
    }

    @Override
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getReason() {
        return reason;
    }
}
