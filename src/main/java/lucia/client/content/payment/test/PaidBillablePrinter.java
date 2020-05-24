package main.java.lucia.client.content.payment.test;

import main.java.lucia.client.content.payment.PaidBillable;

import java.io.PrintStream;

/**
 * printer for test/debug info for {@link main.java.lucia.client.content.payment.PaidBillable}
 */
public class PaidBillablePrinter {

    //the paid billable to print
    PaidBillable toPrint;

    public PaidBillablePrinter(PaidBillable toPrint) {
        this.toPrint = toPrint;
    }

    /**
     * Prints out information of this paid billable
     * @param o the printstream to print to.
     */
    public void print(PrintStream o){
        o.printf("PAID BILLABLE: amount: %d, GST: %d, PST: %d, Grand Total %d\n  Server: %d, Total Paid: %d\n",
                toPrint.getCost(), toPrint.getGSTPaid(), toPrint.getPSTPaid(), toPrint.getGrandTotal(),
                toPrint.getServer(), toPrint.getTotalPaid());
        o.print("Payment: \n");
        new PaymentPrinter(toPrint.getPayment()).print(o);
        o.print("Tips: \n");
        new PaymentPrinter(toPrint.getTips()).print(o);
    }
}
