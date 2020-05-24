package main.java.lucia.net.packet.event;

import main.java.lucia.net.packet.Packet;
import org.jetbrains.annotations.NotNull;

/**
 * High-level abstraction of any object that listens for
 * packet events.
 * @author Matthew Kwiatkowski
 */
@SuppressWarnings("rawtypes")
public abstract class PacketListener<T extends Packet> implements Comparable<PacketListener>{

    /**
     * The priority of this Listener
     */
    private ListenerPriority priority;

    public PacketListener(ListenerPriority priority) {
        this.priority = priority;
    }

    /**
     * The priority of this Listener
     */
    public ListenerPriority getPriority() {
        return priority;
    }

    /**
     * Handles a packet
     * @param packet the packet to handle
     * @param c handles cancelling of the packet processing.
     */
    public abstract void handlePacket(T packet, Cancellable c);


    @Override
    public int compareTo(@NotNull PacketListener packetListener) {
        return getPriority().getSlot() - packetListener.getPriority().getSlot();
    }
}
