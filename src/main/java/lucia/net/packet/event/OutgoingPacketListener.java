package main.java.lucia.net.packet.event;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;

/**
 * Listener for outgoing authenticated packets of a given type
 * @param <T> the type of the incoming authenticated packet to listen for.
 * @author Matthew Kwiatkowski
 */
public abstract class OutgoingPacketListener<T extends OutgoingAuthPacket> extends PacketListener<T> {

    public OutgoingPacketListener(ListenerPriority priority) {
        super(priority);
    }

    @Override
    public void handlePacket(T packet, Cancellable c){
        onPacketSend(packet, c);
    }

    /**
     * Called when a packet is about to be sent.
     * @param packet the packet that will be sent
     * @param cancel manages the cancellation of the sending.
     */
    public abstract void onPacketSend(T packet, Cancellable cancel);
}
