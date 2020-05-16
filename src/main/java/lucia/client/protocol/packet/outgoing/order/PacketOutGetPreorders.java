package main.java.lucia.client.protocol.packet.outgoing.order;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Outgoing packet requesting a list of all preorders.
 * @author Matthew Kwiatkowski
 */
public class PacketOutGetPreorders extends OutgoingAuthPacket {

    public PacketOutGetPreorders(){
        super(OpcodeConstants.GET_PREORDERS_OPCODE);
    }

    /**
     * Used for specifiying serialization behaviour of this packet
     *
     * @return the entire packet, represented as a json string
     */
    @Override
    public String serialize() {
        return GsonTypeFactory.BASIC_GSON.toJson(this, this.getClass());
    }
}
