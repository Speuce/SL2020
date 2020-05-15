package main.java.lucia.net.packet.impl.incoming.handshake;

import main.java.lucia.Client;
import main.java.lucia.consts.ClientConstants;
import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.incoming.MasterDecoder;
import main.java.lucia.net.packet.impl.incoming.DecoderInterface;
import main.java.lucia.net.packet.impl.incoming.codec.IncomingHandshakePacket;
import main.java.lucia.net.packet.impl.incoming.login.LoginDecoder;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingHandshakePacket;

/**
 * The decoder which handles the handshake message.
 *
 * @author Brett Downey
 */
public class HandshakeDecoder implements DecoderInterface {

    /**
     * The origin {@link MasterDecoder}
     * that created this.
     */
    private MasterDecoder origin;

    /**
     * The constructor for this HandshakeDecoder
     *
     * @param origin The decoder origin
     */
    public HandshakeDecoder(MasterDecoder origin) {
        this.origin = origin;
    }

    /**
     * Sends our handshake message
     *
     * @return This class
     */
    public HandshakeDecoder handshake() {
        System.out.println("TRYING THIS");
        OutgoingHandshakePacket outgoingHandshakePacket = new OutgoingHandshakePacket()
                .setClientPublicKey(origin.getDecrypt().getServerKey())
                .setVersion(ClientConstants.VERSION);
        origin.getNetwork().sendInitialMessage(outgoingHandshakePacket);
        return this;
    }

    @Override
    public IncomingHandshakePacket getPacket(String message) {
        return GsonTypeFactory.GENERIC_GSON.fromJson(message, IncomingHandshakePacket.class);
    }

    @Override
    public IncomingPacket process(String message) {
        IncomingHandshakePacket packet = (IncomingHandshakePacket) attemptToGetPacket(message);
        if(packet != null) {
            PacketSender.setSecureToken(packet.getSessionToken());
            origin.next();
            Client.logger.info("Handshake message complete.");
            Client.logger.info("This connection has been secured with " + origin.getDecrypt().getServerKey());
            return packet;
        } else {
            Client.logger.error("The handshake message has failed!");
            return null;
        }
    }

    @Override
    public DecoderInterface next() {
        return new LoginDecoder(origin);
    }
}
