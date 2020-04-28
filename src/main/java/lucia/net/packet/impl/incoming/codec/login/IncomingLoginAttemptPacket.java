package main.java.lucia.net.packet.impl.incoming.codec.login;

import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingLoginAttemptPacket;

public class IncomingLoginAttemptPacket extends IncomingPacket {

    private final int loginResponseOpcode;

    public IncomingLoginAttemptPacket(final int echoCode, final int loginResponseCode) {
        super(echoCode);
        this.loginResponseOpcode = loginResponseCode;
    }

    public int getLoginResponseOpcode() {
        return loginResponseOpcode;
    }

    @Override
    public OutgoingLoginAttemptPacket createOutgoingPacket() {
        return null;
    }
}