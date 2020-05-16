package main.java.lucia.net.packet.event;

import main.java.lucia.net.packet.Packet;

import java.util.*;

/**
 * Manages All packet listeners
 * @author Matthew Kwiatkowski
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class PacketListenerManager {

    /**
     * The map of all packet type to Listener Lists
     */
    private Map<Class<? extends Packet>, List<PacketListener>> listenerMap;

    public PacketListenerManager() {
        this.listenerMap = new HashMap<>();
    }

    /**
     * Register a listener for a given packettype
     * @param type the type of packet that the listener works with
     * @param listener the listener to register.
     */
    public void registerListener( Class<? extends Packet> type, PacketListener listener){
        List<PacketListener> listeners;
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
    public void unRegisterListener( Class<? extends Packet> type, PacketListener listener){
        List<PacketListener> listeners;
        if(listenerMap.containsKey(type)){
            listeners = listenerMap.get(type);
            listeners.remove(listener);
            Collections.sort(listeners);
            listenerMap.put(type, listeners);
        }
    }

    /**
     * Calls any listeners listening for this specific packet.
     * @param p the packet to call events for
     * @return true if the packet processing should continue, false otherwise
     */
    public boolean callEvent(Packet p){
        if(listenerMap.containsKey(p.getClass())){
            Cancellable c = new Cancellable(false);
            for(PacketListener lis: listenerMap.get(p.getClass())){
                lis.handlePacket(p, c);
                //stop higher up listeners if one of the lower ones cancels.
                if(c.isCancelled()) break;
            }
            return !c.isCancelled();
        }else{
            return true;
        }
    }

}
