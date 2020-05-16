package main.java.lucia.client.protocol.packet.outgoing.order;

import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Packet sent to the server when a new order is made
 * @author Matthew Kwiatkowski
 */
public class PacketOutSubmitOrder extends OutgoingAuthPacket {

    /**
     * The Order to submit
     */
    private Order o;

    public PacketOutSubmitOrder(Order o) {
        super(OpcodeConstants.SUBMIT_ORDER_OPCODE);
        this.o = o;
    }

    /**
     * Used for specifiying serialization behaviour of this packet
     *
     * @return the entire packet, represented as a json string
     */
    @Override
    public String serialize() {
        return ItemGson.ITEM_GSON.toJson(this, PacketOutSubmitOrder.class);
    }

    /**
     * The Order to submit
     */
    public Order getOrder() {
        return o;
    }

    /**
     * The Order to submit
     */
    public void setOrder(Order o) {
        this.o = o;
    }
}
