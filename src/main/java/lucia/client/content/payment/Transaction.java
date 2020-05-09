package main.java.lucia.client.content.payment;


import main.java.lucia.client.content.payment.paymentmethods.SimplePayment;
import main.java.lucia.client.content.payment.paymentmethods.SplitPayment;

/**
 * Class for representing any movement of money
 *
 * @author Matt Kwiatkowski
 */
public interface Transaction {

  Payment getPayment();

  void setPayment(Payment payment);

  default PaymentType getPaymentType() {
    return getPayment().getPaymentType();
  }

  /**
   * if the order is paid, it calculates the total tips of the order
   *
   * @param cash true if this should return the cash tip portion, false if it should return the
   * non-cash tip portion (for SP only)
   * @return a long representing the number of cents in the tip
   */
  default long getTip(boolean cash) {
    if (this.getPayment() == null) {
      return 0L;
    } else if (this.getPayment() instanceof SimplePayment) {
      return ((SimplePayment) this.getPayment()).getTip();
    } else if (this.getPayment() instanceof SplitPayment) {
      SplitPayment r = (SplitPayment) this.getPayment();
      if (cash) {
        return r.getTotalCashTips();
      } else {
        return r.getTotalNonCashTips();
      }
    }
    return 0L;
  }
}
