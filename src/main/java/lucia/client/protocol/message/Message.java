package main.java.lucia.client.protocol.message;

import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

/**
 * A message sent by the client which can be terminated
 * by the server if deemed fit.
 *
 * @author Brett Downey
 */
public abstract class Message implements JsonMap {

    /**
     * The {@link PacketProcessor} which holds important
     * information given from the client to the server which is
     * used to process different events.
     */
    private PacketProcessor packetProcessor;

    /**
     * Indicates whether or not the {@link Message} has been terminated.
     */
    private boolean terminated;

    /**
     * The constructor for a {@link Message} which provides the needed
     * {@link PacketProcessor}.
     */
    public Message(PacketProcessor packetProcessor) {
        this.packetProcessor = packetProcessor;
    }

    /**
     * Returns the {@link PacketProcessor}
     *
     * @return Returns the {@link PacketProcessor} linked to this {@link Message}.
     */
    public PacketProcessor getPacketProcessor() {
        return packetProcessor;
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
    public abstract boolean isCustom();

    /**
     * Gets the reply opcode to send to the server, and if the message contains
     * an opcode other than -1, the client will send the opcode to the server.
     * Note that this does not support any information to also be sent, if a specific
     * packet must be sent, then see getReplyPacket, and leave this as {@code -1}.
     *
     * Note that this function as well as {@code getReplyPacket} will be ran
     * when this packet is processed, and there is no need to set the outgoing
     * packet manually. If the outgoing packet has been manually set (via the
     * {@link PacketProcessor}, that packet
     * will also be checked and sent. Therefore at the maximum, 3 packets may
     * be returned from a single message.
     *
     * @return The reply opcode
     */
    public abstract int getReplyOpcode();

    /**
     * Gets the reply packet to send to the server, this method should be
     * filled in if the packet that is required needs to send information instead
     * of just simply an opcode to the server. If no information is to be sent,
     * leave this {@code null} and see getReplyOpcode.
     *
     * Note that this function as well as {@code getReplyOpcode} will be ran
     * when this packet is processed, and there is no need to set the outgoing
     * packet manually. If the outgoing packet has been manually set (via the
     * {@link PacketProcessor}, that packet
     * will also be checked and sent. Therefore at the maximum, 3 packets may
     * be returned from a single message.
     *
     * @return The packet to reply with
     */
    public abstract OutgoingAuthenticatedPacket getReplyPacket();

    /**
     * An abstract class which processes the given implemented {@link Message}.
     */
    public abstract void process();

    /**
     * Terminates the {@link Message}.
     */
    public final void terminate() {
        terminated = true;
    }

    /**
     * Returns whether or not the {@link Message} chain has been terminated.
     *
     * @return {@code true} if the {@link Message} has been terminated, otherwise {@code false}.
     */
    public final boolean terminated() {
        return terminated;
    }
}