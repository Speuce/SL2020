package main.java.lucia.client.protocol.util;

import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

/**
 * Quick Utility object for sending packets to server
 * @author Matthew Kwiatkowski
 */
public class QuickPacket {

    /**
     * The content of the packet
     */
    private String content;

    /**
     * The opcode of the packet to send
     */
    private int opcode;

    public QuickPacket(int opcode, String content) {
        this.content = content;
        this.opcode = opcode;
    }

    /**
     * Send the packet to the server.
     */
    public void send(){
        OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(opcode);
        out.setJsonRequest(content);
        PacketSender.getCurrentPacketSender().sendMessage(out);
    }
}
