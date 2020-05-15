package main.java.lucia.net.packet.impl.incoming.codec;

import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

/**
 * Old incoming packet system.
 * See {@link main.java.lucia.client.protocol.packet.IncomingAuthPacket}
 */
@Deprecated
public class IncomingAuthenticatedPacket extends IncomingPacket {

    private final int opcode;

    private final String jsonRequest;

    public IncomingAuthenticatedPacket(final int echoCode, final int opcode, final String jsonRequest) {
        super(echoCode);
        this.opcode = opcode;
        this.jsonRequest = jsonRequest;
    }

    public int getOpcode() {
        return opcode;
    }

    public String getJsonRequest() {
        return jsonRequest;
    }

    @Override
    public OutgoingAuthenticatedPacket createOutgoingPacket() {
        return new OutgoingAuthenticatedPacket(opcode);
    }
}