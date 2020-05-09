package main.java.lucia.client.content.payment;


import main.java.lucia.client.content.payment.paymentmethods.PaymentMethod;
import main.java.lucia.client.content.payment.paymentmethods.SimplePayment;
import main.java.lucia.client.content.payment.paymentmethods.SplitPayment;

/**
 * Class for representing any movement of money
 *
 * @author Matt Kwiatkowski
 */
public interface Transaction {

  PaymentMethod getPaymentMethod();

  void setPaymentMethod(PaymentMethod paymentMethod);

  default PaymentType getPaymentType() {
    return getPaymentMethod().getPaymentType();
  }

  /**
   * if the order is paid, it calculates the total tips of the order
   *
   * @param cash true if this should return the cash tip portion, false if it should return the
   * non-cash tip portion (for SP only)
   * @return a long representing the number of cents in the tip
   */
  default long getTip(boolean cash) {
    if (this.getPaymentMethod() == null) {
      return 0L;
    } else if (this.getPaymentMethod() instanceof SimplePayment) {
      return ((SimplePayment) this.getPaymentMethod()).getTip();
    } else if (this.getPaymentMethod() instanceof SplitPayment) {
      SplitPayment r = (SplitPayment) this.getPaymentMethod();
      if (cash) {
        return r.getTotalCashTips();
      } else {
        return r.getTotalNonCashTips();
      }
    }
    return 0L;
  }
}
