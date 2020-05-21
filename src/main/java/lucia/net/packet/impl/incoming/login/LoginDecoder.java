package main.java.lucia.net.packet.impl.incoming.login;

import main.java.lucia.client.ClientBuilder;
import main.java.lucia.client.task.TaskManager;
import main.java.lucia.client.task.impl.LoginResponseTask;
import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.event.PacketListenerManager;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.incoming.Decoder;
import main.java.lucia.net.packet.impl.incoming.MasterDecoder;
import main.java.lucia.net.packet.impl.incoming.codec.login.IncomingLoginAttemptPacket;

public class LoginDecoder extends Decoder {

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
        IncomingLoginAttemptPacket packet = (IncomingLoginAttemptPacket) decodePacket(message);
        PacketListenerManager.get.callEvent(packet);
        if(ClientBuilder.runGUI){
            TaskManager.submit(new LoginResponseTask(packet));
        }
        if (packet.getLoginResponseOpcode() == 2) {
            origin.next();
        }
        return packet;
    }
}