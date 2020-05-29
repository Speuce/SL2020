package main.java.lucia.client.protocol.packet.outgoing.customer;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Outgoing packet requesting the past 'x' orders by the given customer
 * @author Matthew Kwiatkowski
 */
public class PacketOutFindPastCustomerOrders extends OutgoingAuthPacket {

    /**
     * The number of orders to request
     */
    private int num;

    /**
     * The id (rownum) of the customer to search past orders of
     */
    private int customerId;

    public PacketOutFindPastCustomerOrders(int num, int customerId) {
        super(OpcodeConstants.FIND_PAST_ORDERS_OF_CUSTOMER_OPCODE);
        this.num = num;
        this.customerId = customerId;
    }

    /**
     * The number of orders to request
     */
    public int getNum() {
        return num;
    }

    /**
     * The number of orders to request
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * The id (rownum) of the customer to search past orders of
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * The id (rownum) of the customer to search past orders of
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Used for specifying serialization behaviour of this packet
     *
     * @return the entire packet, represented as a json string
     */
    @Override
    public String serialize() {
        return GsonTypeFactory.BASIC_GSON.toJson(this);
    }
}
