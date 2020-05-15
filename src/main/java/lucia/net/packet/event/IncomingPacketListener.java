package main.java.lucia.net.packet.event;

import main.java.lucia.client.protocol.packet.IncomingAuthPacket;

/**
 * Listener for incoming authenticated packets of a given type
 * @param <T> the type of the incoming authenticated packet to listen for.
 * @author Matthew Kwiatkowski
 */
public abstract class IncomingPacketListener<T extends IncomingAuthPacket> extends PacketListener<T> {

    public IncomingPacketListener(ListenerPriority priority) {
        super(priority);
    }

    @Override
    public void handlePacket(T packet, Cancellable c){
        onPacketReceive(packet, c);
    }

    /**
     * Called when a packet is received.
     * @param packet the packet received
     * @param cancel the {@link Cancellable} managing the event.
     */
    public abstract void onPacketReceive(T packet, Cancellable cancel);

}
