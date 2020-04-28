package main.java.lucia.client.protocol.message.impl;

import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

/**
 * Message that shouldn't respond to the server,
 * i.e when the server sends back an empty response
 * @author Matt Kwiatkowski
 */
public class DoNotReplyMessage extends Message {
    /**
     * The constructor for a {@link Message} which provides the needed
     * {@link PacketProcessor}.
     *
     * @param packetProcessor
     */
    public DoNotReplyMessage(PacketProcessor packetProcessor) {
        super(packetProcessor);
    }

    /**
     * Indicates whether or not this {@link Message} should be a
     * custom response which only the specific client which requested it
     * should receive back, or if result of this can be shared to other clients.
     * This improves efficiency, for example if two clients ask for the same
     * piece of information, it only needs to be processed once for both clients.
     *
     * @return {@code true} if the code should be custom, {@code false} otherwise.
     */
    @Override
    public boolean isCustom() {
        return true;
    }

    /**
     * Gets the reply opcode to send to the server, and if the message contains
     * an opcode other than -1, the client will send the opcode to the server.
     * Note that this does not support any information to also be sent, if a specific
     * packet must be sent, then see getReplyPacket, and leave this as {@code -1}.
     * <p>
     * Note that this function as well as {@code getReplyPacket} will be ran
     * when this packet is processed, and there is no need to set the outgoing
     * packet manually. If the outgoing packet has been manually set (via the
     * {@link PacketProcessor}, that packet
     * will also be checked and sent. Therefore at the maximum, 3 packets may
     * be returned from a single message.
     *
     * @return The reply opcode
     */
    @Override
    public int getReplyOpcode() {
        return -1;
    }

    /**
     * Gets the reply packet to send to the server, this method should be
     * filled in if the packet that is required needs to send information instead
     * of just simply an opcode to the server. If no information is to be sent,
     * leave this {@code null} and see getReplyOpcode.
     * <p>
     * Note that this function as well as {@code getReplyOpcode} will be ran
     * when this packet is processed, and there is no need to set the outgoing
     * packet manually. If the outgoing packet has been manually set (via the
     * {@link PacketProcessor}, that packet
     * will also be checked and sent. Therefore at the maximum, 3 packets may
     * be returned from a single message.
     *
     * @return The packet to reply with
     */
    @Override
    public OutgoingAuthenticatedPacket getReplyPacket() {
        return null;
    }

    /**
     * An abstract class which processes the given implemented {@link Message}.
     */
    @Override
    public void process() {
        getPacketProcessor().setOutgoingPacket(null);
    }

    /**
     * Serialize this message into JSON-format.
     *
     * @return The JSON output.
     */
    @Override
    public String serialize() {
        return null;
    }

    /**
     * Deserialize a given JSON String into it's origin object.
     *
     * @return An Element which is the object's input.
     */
    @Override
    public Object deserialize() {
        return null;
    }
}
