package main.java.lucia.client.protocol.packet;

import main.java.lucia.net.packet.OutgoingPacket;

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

    public OutgoingAuthPacket(int opcode) {
        this.opcode = opcode;
    }

    /**
     * @return The code of the operation to perform
     */
    public int getOpcode() {
        return opcode;
    }

    /**
     * Used for specifying serialization behaviour of this packet
     * @return the entire packet, represented as a json string
     */
    public abstract String serialize();

    @Override
    public String toString() {
        return serialize();
    }
}
