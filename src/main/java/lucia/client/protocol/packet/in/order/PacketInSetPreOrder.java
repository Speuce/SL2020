package main.java.lucia.client.protocol.packet.in.order;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Packet sent by server to indicate that the client should update
 * its' internal records of a particular preorder to the preorder given
 * @author Matthew Kwiatkowski
 */
public class PacketInSetPreOrder extends IncomingAuthPacket {

    /**
     * The order that is being dealt with
     */
    private Order order;

    public PacketInSetPreOrder(int echoCode, Order order) {
        super(echoCode, OpcodeConstants.SET_PREORDER_OPCODE);
        this.order = order;
    }

    /**
     * The order that is being dealt with
     */
    public Order getOrder() {
        return order;
    }

    /**
     * The order that is being dealt with
     */
    public void setOrder(Order o) {
        this.order = o;
    }
}
