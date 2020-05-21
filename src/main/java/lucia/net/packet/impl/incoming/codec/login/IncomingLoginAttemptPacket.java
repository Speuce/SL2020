package main.java.lucia.net.packet.impl.incoming.codec.login;

import main.java.lucia.net.packet.IncomingPacket;

/**
 * @author Matthew Kwiatkowski
 * @author Brett Downey
 */
public class IncomingLoginAttemptPacket extends IncomingPacket {

    /**
     * The response operation code from the server
     * 1 = new acct
     * 2 = successfull login
     * 3 = invalid credentials
     * 4 = server full
     * 5 = error/account already logged into
     * 6 = ip restricted
     * 7 = banned
     * 8 = ip banned
     */
    private final int loginResponseOpcode;

    public IncomingLoginAttemptPacket(final int echoCode, final int loginResponseCode) {
        super(echoCode);
        this.loginResponseOpcode = loginResponseCode;
    }

    /**
     * The response operation code from the server
     * 1 = new acct
     * 2 = successfull login
     * 3 = invalid credentials
     * 4 = server full
     * 5 = error/account already logged into
     * 6 = ip restricted
     * 7 = banned
     * 8 = ip banned
     */
    public int getLoginResponseOpcode() {
        return loginResponseOpcode;
    }

}