package main.java.lucia.client.protocol.packet.in.system;

import main.java.lucia.client.protocol.packet.IncomingAuthPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Incoming packet representing an error with an previous operation
 * @author Matthew Kwiatkowski
 */
public class PacketInError extends IncomingAuthPacket {

    /**
     * The error message associated with the error
     */
    private String message;

    public PacketInError(int echoCode) {
        super(echoCode, OpcodeConstants.ERROR_OPCODE);
    }

    /**
     * The error message associated with the error
     */
    public String getMessage() {
        return message;
    }

    /**
     * The error message associated with the error
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
