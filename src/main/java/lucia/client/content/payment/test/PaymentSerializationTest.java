package main.java.lucia.client.content.payment.test;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.content.menu.test.ItemTester;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.content.payment.paymentmethods.GiftPayment;
import main.java.lucia.client.content.payment.paymentmethods.PaymentType;
import main.java.lucia.client.content.payment.paymentmethods.SimplePayment;

/**
 * Tests payments and Serialization of such payments
 * @author Matthew Kwiatkowski
 */
public class PaymentSerializationTest {

    public static void main(String[] args){
        Menu.loadFromTestMenu();

        //build an example order
        Order o = new Order(OrderType.PICKUP);
        o.addItem(ItemTester.build10Chz());

        //pay full on debit
        o.payinFullSimple(PaymentType.DEBIT, 100);

        System.out.print("is the order paid?: ");
        System.out.println(o.isPaid());

        PaidBillablePrinter pr = new PaidBillablePrinter(o);
        pr.print(System.out);

        System.out.println(ItemGson.ITEM_GSON.toJson(o));

        o.clearPayments();

        //now add a split pay
        o.addPayment(new SimplePayment(PaymentType.DEBIT, 500));
        o.addTip(new SimplePayment(PaymentType.DEBIT, 100));
        o.addPayment(new GiftPayment(500, 123454321));

        System.out.print("is the order paid?: ");
        System.out.println(o.isPaid());

        pr.print(System.out);

        System.out.println(ItemGson.ITEM_GSON.toJson(o));

    }
}
