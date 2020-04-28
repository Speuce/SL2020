package main.java.lucia.net.packet.impl.incoming.codec;

import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingHandshakePacket;

public class IncomingHandshakePacket extends IncomingPacket {

    private final String sessionToken;

    public IncomingHandshakePacket(final int echoCode, final String sessionToken) {
        super(echoCode);
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    @Override
    public OutgoingHandshakePacket createOutgoingPacket() {
        return new OutgoingHandshakePacket();
    }
}
