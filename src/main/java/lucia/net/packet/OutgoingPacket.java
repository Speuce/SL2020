package main.java.lucia.net.packet;

import main.java.lucia.net.NetworkConstants;

/**
 * A {@link OutgoingPacket} which allows the creation of
 * outgoing packets to be seamless.
 *
 * @author Brett Downey
 */
public abstract class OutgoingPacket extends Packet{


    private String sessionToken;

    private String cId = NetworkConstants.CID;

    public String getcId() {
        return cId;
    }

    @Override
    public OutgoingPacket setEchoCode(int echoCode) {
        super.setEchoCode(echoCode);
        return this;
    }

    public OutgoingPacket setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    @Override
    public abstract String toString();
}