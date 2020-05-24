package main.java.lucia.net.packet.impl.incoming.authenticated;

import com.google.gson.JsonParser;
import main.java.lucia.Client;
import main.java.lucia.client.Engine;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;
import main.java.lucia.client.protocol.packet.in.system.PacketInEchoOnly;
import main.java.lucia.net.packet.impl.incoming.Decoder;
import main.java.lucia.net.protocol.PacketProtocol;
import main.java.lucia.net.protocol.Protocol;

/**
 * After handshake and login, this decoder will be used
 * @author Matthew Kwiatkowski
 */
public class AuthenticatedDecoder extends Decoder {

    private static final Protocol protocol = new PacketProtocol();

    public AuthenticatedDecoder() {

    }

    @Override
    public IncomingAuthPacket getPacket(String message) {
        return protocol.deserialize(new JsonParser().parse(message).getAsJsonObject());
    }

    @Override
    public IncomingAuthPacket process(String message) {
        IncomingAuthPacket packet = (IncomingAuthPacket) decodePacket(message);
        if (packet != null) {
            if (packet instanceof PacketInEchoOnly) {
                return packet;
            }
            Engine.queuePacket(packet);
            Client.logger.debug("Queued packet with opcode: " + packet.getOpcode());
            return packet;
        } else {
            Client.logger.info("Processed a null packet!");
            return null;
        }
    }
}