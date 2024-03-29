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
    private Order order;

    public PacketOutSubmitOrder(Order order) {
        super(OpcodeConstants.SUBMIT_ORDER_OPCODE);
        this.order = order;
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
        return order;
    }

    /**
     * The Order to submit
     */
    public void setOrder(Order o) {
        this.order = o;
    }
}
