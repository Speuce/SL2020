package main.java.lucia.client.protocol.packet;

import main.java.lucia.net.packet.OutgoingPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

/**
 * Represents a packet to be sent out to the server
 * after authentication has been performed
 * @author Matthew Kwiatkowski
 */
public abstract class OutgoingAuthPacket extends OutgoingPacket {

    /**
     * The code of the operation to perform
     */
    private int opcode;

    protected OutgoingAuthPacket(int opcode) {
        this.opcode = opcode;
    }


    /**
     * Used for specifying serialization behaviour of this packet
     * @return the entire packet, represented as a json string
     */
    public abstract String serialize();

    @Override
    public String toString() {
        return GsonTypeFactory.BASIC_GSON.toJson(this, this.getClass());
    }
}
