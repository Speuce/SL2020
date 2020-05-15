package main.java.lucia.net.packet.event;

import main.java.lucia.net.packet.Packet;

import java.util.*;

/**
 * Manages All packet listeners
 * @author Matthew Kwiatkowski
 */
public class PacketListenerManager {

    /**
     * The map of all packet type to Listener Lists
     */
    private Map<Class<? extends Packet>, List<PacketListener<? extends Packet>>> listenerMap;

    public PacketListenerManager() {
        this.listenerMap = new HashMap<>();
    }

    /**
     * Register a listener for a given packettype
     * @param type the type of packet that the listener works with
     * @param listener the listener to register.
     */
    public void registerListener( Class<? extends Packet> type, PacketListener<? extends Packet> listener){
        List<PacketListener<? extends Packet>> listeners;
        if(listenerMap.containsKey(type)){
            listeners = listenerMap.get(type);
        }else{
            listeners = new LinkedList<>();
        }
        listeners.add(listener);
        Collections.sort(listeners);
        listenerMap.put(type, listeners);
    }

    /**
     * un-register a listener for a given packettype
     * @param type the type of packet that the listener works with
     * @param listener the listener to register.
     */
    public void unRegisterListener( Class<? extends Packet> type, PacketListener<? extends Packet> listener){
        List<PacketListener<? extends Packet>> listeners;
        if(listenerMap.containsKey(type)){
            listeners = listenerMap.get(type);
            listeners.remove(listener);
            Collections.sort(listeners);
            listenerMap.put(type, listeners);
        }
    }

}
