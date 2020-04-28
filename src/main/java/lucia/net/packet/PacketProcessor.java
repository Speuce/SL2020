package main.java.lucia.net.packet;

import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

/**
 * A packet that holds all the information that is
 * received from the {@link lucia.net.packet.impl.incoming.Decoder}
 */
public class PacketProcessor {

    /**
     * The contents that will need to be processed by the server.
     */
    private final String jsonRequest;

    /**
     * The start of our outgoingPacket, which may or may not be sent depending on the
     * information retrieved
     */
    private OutgoingAuthenticatedPacket outgoingPacket;

    /**
     * The PacketProcessor constructor.
     *
     * @param jsonRequest The content that needs to be processed by the server.
     */
    public PacketProcessor(final String jsonRequest) {
        this.jsonRequest = jsonRequest;
    }

    /**
     * Retrieves the message contents that needs to be processed by the server.
     *
     * @return The message contents.
     */
    public String getJsonRequest() {
        return jsonRequest;
    }

    /**
     * Gets the current response.
     *
     * @return The current response {@link OutgoingPacket}.
     */
    public OutgoingAuthenticatedPacket getOutgoingPacket() {
        return outgoingPacket;
    }

    /**
     * Sets the outgoing packet, note that these packets should only
     * be sent under specific circumstances. If the packet that will be
     * placed within here will be sent every time, then see the {@link Message}
     * classes which support constant responses.
     *
     * @param outgoingPacket The new outgoing packet
     */
    public void setOutgoingPacket(OutgoingAuthenticatedPacket outgoingPacket) {
        this.outgoingPacket = outgoingPacket;
    }

    /**
     * Sets the outgoing packet, note that these packets should only
     * be sent under specific circumstances. If the packet that will be
     * placed within here will be sent every time, then see the {@link Message}
     * classes which support constant responses.
     *
     * @param opcode The opcode for the new outgoing packet
     */
    public void setOutgoingPacket(int opcode) {
        this.outgoingPacket = new OutgoingAuthenticatedPacket(opcode);
    }
}