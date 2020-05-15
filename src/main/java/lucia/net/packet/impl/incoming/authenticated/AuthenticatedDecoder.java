package main.java.lucia.net.packet.impl.incoming.authenticated;

import main.java.lucia.client.Engine;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.incoming.Decoder;
import main.java.lucia.net.packet.impl.incoming.codec.IncomingAuthenticatedPacket;
import main.java.lucia.net.protocol.Protocol;

/**
 * After handshake and login, this decoder will be used
 * @author Matthew Kwiatkowski
 */
public class AuthenticatedDecoder extends Decoder {

    private static final Protocol protocol = null;

    @Override
    public IncomingAuthenticatedPacket getPacket(String message) {
        return GsonTypeFactory.GENERIC_GSON.fromJson(message, IncomingAuthenticatedPacket.class);
    }

    @Override
    public IncomingPacket process(String message) {

        IncomingAuthenticatedPacket packet = (IncomingAuthenticatedPacket) decodePacket(message);

        if (packet != null && protocol.hasCode(packet.getOpcode())) {
            PacketProcessor packetProcessor = new PacketProcessor(packet.getJsonRequest());
            Message incoming = protocol.getMessage(packet.getOpcode(), packetProcessor);
            Engine.queuePacket(incoming);
            return packet;
        } else {
            return null;
        }
    }
}