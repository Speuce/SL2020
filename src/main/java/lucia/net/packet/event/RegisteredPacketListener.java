package main.java.lucia.net.packet.event;

import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.net.packet.Packet;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * An object tracking a method and object for execution of a
 * listener method.
 * @author Matthew Kwiatkowski
 */
public class RegisteredPacketListener implements Comparable<RegisteredPacketListener>{

    /**
     * The method that will be executed
     */
    private Method m;

    /**
     * The object which will have the method executed on.
     */
    private PacketHandler r;

    /**
     * The priority of the listener
     */
    private ListenerPriority priority;

    /**
     * The type of packets that this handles.
     */
    private Class<? extends Packet> type;

    public RegisteredPacketListener(@NotNull Method m,@NotNull PacketHandler r,@NotNull ListenerPriority priority,@NotNull Class<? extends Packet> type) {
        this.m = m;
        this.r = r;
        this.priority = priority;
        this.type = type;
    }

    /**
     * Calls this listener to do its things.
     * @param p the packet to invoke this listener with.
     */
    public void invoke(@NotNull Packet p){
        if(!type.isAssignableFrom(p.getClass())){
            //not the right packet
            MLogger.logError("Attemped to invoke this listener with the WRONG packet");
            return;
        }
        try{
            getMethod().invoke(getPacketHandler(), p);
        }catch(Exception|Error e){
            e.printStackTrace(MLogger.getErrorStream());
        }
    }

    @Override
    public int compareTo(@NotNull RegisteredPacketListener packetListener) {
        return getPriority().getSlot() - packetListener.getPriority().getSlot();
    }

    public Method getMethod() {
        return m;
    }

    public PacketHandler getPacketHandler() {
        return r;
    }

    public ListenerPriority getPriority() {
        return priority;
    }
}
