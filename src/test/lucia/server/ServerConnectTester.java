package test.lucia.server;

import main.java.lucia.Client;
import main.java.lucia.net.packet.event.PacketEventHandler;
import main.java.lucia.net.packet.event.PacketHandler;
import main.java.lucia.net.packet.impl.incoming.codec.IncomingHandshakePacket;
import main.java.lucia.net.packet.impl.incoming.codec.login.IncomingLoginAttemptPacket;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingLoginAttemptPacket;
import main.java.lucia.net.security.passwords.CryptographicHash;

/**
 * For use with {@link ServerConnectTest}
 */
public class ServerConnectTester implements PacketHandler {

    @PacketEventHandler
    public void onHandShakeComplete(IncomingHandshakePacket hand){
        Client.logger.info("Completed Handshake! Sending login");
        PacketSender.getCurrentPacketSender().sendMessage(new OutgoingLoginAttemptPacket("123", CryptographicHash.hashPassword("123")));
    }

    @PacketEventHandler
    public void onLoginReceive(IncomingLoginAttemptPacket e){
        Client.logger.info("Got incoming login packet as response with code: " + e.getLoginResponseOpcode());
        if(e.getLoginResponseOpcode() == 2){
            Client.logger.info("Successfully logged in!");
            Client.logger.info("Test completed.");
            ServerConnectTest.cl.shutdown(0);
        }
    }
}
