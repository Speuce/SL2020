package main.java.lucia.net.packet.event;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;

/**
 * Listener for outgoing authenticated packets of a given type
 * @param <T> the type of the incoming authenticated packet to listen for.
 * @author Matthew Kwiatkowski
 */
public interface OutgoingPacketListener<T extends OutgoingAuthPacket> {

    /**
     * Called when a packet is about to be sent.
     * @param packet
     * @param cancel
     */
    void onPacketSend(T packet, Cancellable cancel);
}
