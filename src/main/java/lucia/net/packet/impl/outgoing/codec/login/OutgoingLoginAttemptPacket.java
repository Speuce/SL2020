package main.java.lucia.net.packet.impl.outgoing.codec.login;

import main.java.lucia.net.packet.impl.GsonTypeFactory;

public class OutgoingLoginAttemptPacket extends OutgoingLoginPacket {

    private String type = GsonTypeFactory.CLIENT_TO_SERVER_LOGIN_ATTEMPT;

    public OutgoingLoginAttemptPacket() {
    }

    public OutgoingLoginAttemptPacket(String username, String hashedPassword) {
        super(username, hashedPassword);
    }

    @Override
    public String toString() {
        return GsonTypeFactory.LOGIN_GSON_OUTGOING.toJson(this, this.getClass());
    }
}