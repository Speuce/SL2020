package main.java.lucia.net.packet.impl.incoming.login;

import main.java.lucia.client.task.TaskManager;
import main.java.lucia.client.task.impl.LoginResponseTask;
import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.incoming.MasterDecoder;
import main.java.lucia.net.packet.impl.incoming.DecoderInterface;
import main.java.lucia.net.packet.impl.incoming.authenticated.AuthenticatedDecoder;
import main.java.lucia.net.packet.impl.incoming.codec.login.IncomingLoginAttemptPacket;

public class LoginDecoder implements DecoderInterface {

    /**
     * The origin {@link MasterDecoder}
     * that created this.
     */
    private MasterDecoder origin;

    public LoginDecoder(MasterDecoder origin) {
        this.origin = origin;
    }

    @Override
    public IncomingLoginAttemptPacket getPacket(String message) {
        return GsonTypeFactory.GENERIC_GSON.fromJson(message, IncomingLoginAttemptPacket.class);
    }

    @Override
    public IncomingPacket process(String message) {
        // TODO Finish this stuff
        IncomingLoginAttemptPacket packet = (IncomingLoginAttemptPacket) attemptToGetPacket(message);
        TaskManager.submit(new LoginResponseTask(packet));

        if (packet.getLoginResponseOpcode() == 2) {
            origin.next();
        }
        return packet;
    }

    @Override
    public DecoderInterface next() {
        return new AuthenticatedDecoder();
    }
}