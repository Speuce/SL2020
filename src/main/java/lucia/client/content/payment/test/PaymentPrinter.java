package main.java.lucia.client.content.payment.test;

import main.java.lucia.client.content.payment.paymentmethods.GiftPayment;
import main.java.lucia.client.content.payment.paymentmethods.Payment;
import main.java.lucia.client.content.payment.paymentmethods.PaymentMethod;
import main.java.lucia.client.content.payment.paymentmethods.SimplePayment;
import main.java.lucia.client.content.time.TimeFormat;

import java.io.PrintStream;

/**
 * Prints out info of an {@link Payment}
 * @author Matthew Kwiatkowski
 */
public class PaymentPrinter {

    //the payment to print
    private Payment paymentPrint;

    public PaymentPrinter(Payment paymentPrint) {
        this.paymentPrint = paymentPrint;
    }

    /**
     * Prints the payment
     */
    public void print(PrintStream r){
        for(PaymentMethod m: paymentPrint){
            r.print("     ");
            printPaymentMethod(m, r);
            r.println();
        }
    }

    private void printPaymentMethod(PaymentMethod m, PrintStream r){
        if(m instanceof GiftPayment){
            GiftPayment g = (GiftPayment)m;
            r.printf("Gift Payment: %d, Card #: %d, Time: %s, Type: %s", g.getAmount(), g.getGiftCertificateNumber(),
                    TimeFormat.FORMATTER_ISO_STANDARD.getFormat().format(g.getTime()), g.getPaymentType().getDisplayCode());
        }else if(m instanceof SimplePayment){
            SimplePayment simp = (SimplePayment)m;
            r.printf("Simple Payment: %d, Time: %s, Type: %s", simp.getAmount(),
                    TimeFormat.FORMATTER_ISO_STANDARD.getFormat().format(simp.getTime()), simp.getPaymentType().getDisplayCode());
        }
    }
}
