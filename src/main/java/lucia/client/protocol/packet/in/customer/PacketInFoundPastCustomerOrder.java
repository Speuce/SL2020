package main.java.lucia.client.protocol.packet.in.customer;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;

/**
 * Incoming packet when a customer's past orders are requested
 * @author Matthew Kwiatkowski
 */
public class PacketInFoundPastCustomerOrder extends IncomingAuthPacket {

    /**
     * Integer indicating how recent this order is,
     * where 1 is the most recent order
     */
    private int num;

    /**
     * The Order found
     */
    private Order o;

    /**
     * The id (rownum) of the customer requested
     */
    private int customerId;

    public PacketInFoundPastCustomerOrder(int echoCode, int opcode, int num, Order o, int customerId) {
        super(echoCode, opcode);
        this.num = num;
        this.o = o;
        this.customerId = customerId;
    }

    /**
     * Integer indicating how recent this order is,
     * where 1 is the most recent order
     */
    public int getNum() {
        return num;
    }

    /**
     * Integer indicating how recent this order is,
     * where 1 is the most recent order
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * The Order found
     */
    public Order getOrder() {
        return o;
    }

    /**
     * The Order found
     */
    public void setOrder(Order o) {
        this.o = o;
    }

    /**
     * The id (rownum) of the customer requested
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * The id (rownum) of the customer requested
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
