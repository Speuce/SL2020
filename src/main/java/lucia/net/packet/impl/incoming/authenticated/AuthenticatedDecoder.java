package main.java.lucia.net.packet.impl.incoming.authenticated;

import com.google.gson.JsonParser;
import main.java.lucia.client.Engine;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;
import main.java.lucia.net.packet.impl.incoming.Decoder;
import main.java.lucia.net.protocol.PacketProtocol;
import main.java.lucia.net.protocol.Protocol;

/**
 * After handshake and login, this decoder will be used
 * @author Matthew Kwiatkowski
 */
public class AuthenticatedDecoder extends Decoder {

    private static final Protocol protocol = new PacketProtocol();

    @Override
    public IncomingAuthPacket getPacket(String message) {
        return protocol.deserialize(new JsonParser().parse(message).getAsJsonObject());
    }

    @Override
    public IncomingAuthPacket process(String message) {
        IncomingAuthPacket packet = (IncomingAuthPacket) decodePacket(message);
        if (packet != null) {
            Engine.queuePacket(packet);
            return packet;
        } else {
            return null;
        }
    }
}