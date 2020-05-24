package main.java.lucia.net.packet.impl.incoming.codec;

import main.java.lucia.net.packet.IncomingPacket;

public class IncomingHandshakePacket extends IncomingPacket {

    private final String sessionToken;

    public IncomingHandshakePacket(final int echoCode, final String sessionToken) {
        super(echoCode);
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }


}
