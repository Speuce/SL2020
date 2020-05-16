package main.java.lucia.net.packet.impl.incoming.codec.login;

import main.java.lucia.net.packet.IncomingPacket;

public class IncomingLoginAttemptPacket extends IncomingPacket {

    private final int loginResponseOpcode;

    public IncomingLoginAttemptPacket(final int echoCode, final int loginResponseCode) {
        super(echoCode);
        this.loginResponseOpcode = loginResponseCode;
    }

    public int getLoginResponseOpcode() {
        return loginResponseOpcode;
    }

}