package main.java.lucia.client.protocol.packet;

import main.java.lucia.net.packet.IncomingPacket;

/**
 * Represents any inbound packet from the server after the authentication process has completed
 * @author Matthew Kwiatkowski
 */
public abstract class IncomingAuthPacket extends IncomingPacket {

    /**
     * The operation being performed with this packet.
     */
    private int opcode;

    public IncomingAuthPacket(int echoCode, int opcode) {
        super(echoCode);
        this.opcode = opcode;
    }

    /**
     * @return The operation being performed with this packet.
     */
    public int getOpcode() {
        return opcode;
    }

    /**
     * @param opcode The operation being performed with this packet.
     */
    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }
}
