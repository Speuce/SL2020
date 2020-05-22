package main.java.lucia.client.protocol.packet.in.system;

import main.java.lucia.client.protocol.packet.IncomingAuthPacket;

/**
 * Incoming packet with ONLY an echo code and opcode, with no data attached.
 * Indicates the proper receiving of another packet.
 * @author Matthew Kwiatkowski
 */
public class PacketInEchoOnly extends IncomingAuthPacket {

    public PacketInEchoOnly(int echoCode, int opcode) {
        super(echoCode, opcode);
    }
}
