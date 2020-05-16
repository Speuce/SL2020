package main.java.lucia.client.protocol.packet.in.order;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;

/**
 * Packet sent by server to indicate that the client should update
 * its' internal records of a particular order to the order given
 * @author Matthew Kwiatkowski
 */
public class PacketInSetOrder extends IncomingAuthPacket {

    /**
     * The order that is being dealt with
     */
    private Order o;

    public PacketInSetOrder(int echoCode, int opcode) {
        super(echoCode, opcode);
    }

    /**
     * The order that is being dealt with
     */
    public Order getOrder() {
        return o;
    }

    /**
     * The order that is being dealt with
     */
    public void setOrder(Order o) {
        this.o = o;
    }
}
