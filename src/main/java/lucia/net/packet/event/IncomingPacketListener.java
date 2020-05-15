package main.java.lucia.net.packet.event;

import main.java.lucia.client.protocol.packet.IncomingAuthPacket;

/**
 * Listener for incoming authenticated packets of a given type
 * @param <T> the type of the incoming authenticated packet to listen for.
 * @author Matthew Kwiatkowski
 */
public interface IncomingPacketListener<T extends IncomingAuthPacket> {

    /**
     * Called when a packet is received.
     * @param packet the packet received
     * @param cancel the {@link Cancellable} managing the event.
     */
    void onPacketReceive(T packet, Cancellable cancel);

}
