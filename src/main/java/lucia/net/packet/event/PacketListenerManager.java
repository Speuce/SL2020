package main.java.lucia.net.packet.event;

import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.net.packet.Packet;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Manages All packet listeners
 * @author Matthew Kwiatkowski
 */
public class PacketListenerManager {

    /**
     * The singleton instance of {@link PacketListenerManager}
     */
    public static final PacketListenerManager get = new PacketListenerManager();

    /**
     * The map of all packet type to Listener Lists
     */
    private Map<Class<? extends Packet>, PacketEventHandlerList> listenerMap;

    public PacketListenerManager() {
        this.listenerMap = new HashMap<>();
    }


    /**
     * Calls any listeners listening for this specific packet.
     * @param p the packet to call events for
     * @return true if the packet processing should continue, false otherwise
     */
    public boolean callEvent(Packet p){
        if(listenerMap.containsKey(p.getClass())){
            return listenerMap.get(p.getClass()).callInOrder(p);
        }else{
            return true;
        }
    }

    /**
     * Registers the given listener class
     * @param l the object to register
     */
    public void registerListener(@NotNull PacketHandler l){
        PacketEventHandlerList curr;
        for(Map.Entry<Class<? extends Packet>, Set<RegisteredPacketListener>> ent : getListeners(l).entrySet()){
            curr = listenerMap.computeIfAbsent(ent.getKey(), k -> new PacketEventHandlerList());
            for(RegisteredPacketListener lis: ent.getValue()){
                curr.addListener(lis);
            }
        }
    }


    /**
     * Looks through any listener class,
     * and attempted to extract any properly-paramterized
     * methods annotated with {@link PacketEventHandler}
     * Inspired by code from: https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/browse/src/main/java/org/bukkit/plugin/java/JavaPluginLoader.java#237
     * @param listener the listener class to get listener methods from.
     */
    private Map<Class<? extends Packet>, Set<RegisteredPacketListener>> getListeners(@NotNull PacketHandler listener){

        Map<Class<? extends Packet>, Set<RegisteredPacketListener>> ret = new HashMap<>();

        Set<Method> methods;
        //first get ALL of the methods declared.
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<>(publicMethods.length + privateMethods.length, 1.0f);
            Collections.addAll(methods, publicMethods);
            Collections.addAll(methods, privateMethods);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace(MLogger.getErrorStream());
            return ret;
        }
        //now loop through every method
        Class<?> checkClass;
        Class<? extends Packet> eventClass;
        for (Method method : methods) {
            PacketEventHandler eh = method.getAnnotation(PacketEventHandler.class);
            if (eh == null) continue;
            if (method.isBridge() || method.isSynthetic()) {
                continue;
            }
            if (method.getParameterTypes().length != 1) {
                MLogger.logError("Error registering event! Param length is bad!");
                continue;
            }
            checkClass = method.getParameterTypes()[0];
            if(!Packet.class.isAssignableFrom(checkClass)){
                MLogger.logError("Error registering event! Param type is bad!");
            }
            method.setAccessible(true);

            eventClass = checkClass.asSubclass(Packet.class);
            Set<RegisteredPacketListener> eventSet = ret.computeIfAbsent(eventClass, k -> new HashSet<>());
            eventSet.add(new RegisteredPacketListener(method, listener, eh.priority(), eventClass));


        }
        return ret;
    }

}
