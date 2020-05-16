package main.java.lucia.client.protocol.packet.outgoing.order;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Outgoing packet requesting the orders for the current day
 * @author Matthew Kwiatkowski
 */
public class PacketOutDayOrdersList extends OutgoingAuthPacket {


    public PacketOutDayOrdersList() {
        super(OpcodeConstants.DAY_ORDERS_LIST_OPCODE);
    }

    /**
     * Used for specifiying serialization behaviour of this packet
     *
     * @return the entire packet, represented as a json string
     */
    @Override
    public String serialize() {
        return GsonTypeFactory.BASIC_GSON.toJson(this, PacketOutDayOrdersList.class);
    }
}
