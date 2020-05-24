package main.java.lucia.net.packet.event;

import main.java.lucia.net.packet.Packet;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

/**
 * Tracks a list of every {@link RegisteredPacketListener}
 * for an {@link main.java.lucia.net.packet.Packet}
 * @author Matthew Kwiatkowski
 */
public class PacketEventHandlerList {

    /**
     * The map of all priorities:registered listeners
     */
    private Map<ListenerPriority, ArrayList<RegisteredPacketListener>> listenerMap;

    public PacketEventHandlerList() {
        listenerMap = new EnumMap<>(ListenerPriority.class);
        for(ListenerPriority l: ListenerPriority.values()){
            listenerMap.put(l, new ArrayList<>());
        }
    }

    /**
     * Calls the event handlers, in order by priority
     * @param p the packet incoming
     * @return true if processing should continue, false otherwise
     */
    public boolean callInOrder(Packet p){
        for(ListenerPriority pri: ListenerPriority.values()){
            for(RegisteredPacketListener lis: listenerMap.get(pri)){
                lis.invoke(p);
            }
            if(p.isCancelled()){
                break;
            }
        }
        return !p.isCancelled();
    }

    /**
     * Adds the listener to the list
     * @param l the listener to add
     */
    public void addListener(RegisteredPacketListener l){
        if(listenerMap.get(l.getPriority()).contains(l)){
            throw new IllegalArgumentException("Attempted to register a listener twice!");
        }
        listenerMap.get(l.getPriority()).add(l);
    }


}
