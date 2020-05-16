package main.java.lucia.net.test;

import main.java.lucia.client.protocol.packet.in.order.PacketInSetOrder;
import main.java.lucia.net.packet.event.PacketEventHandler;
import main.java.lucia.net.packet.event.PacketHandler;
import main.java.lucia.net.packet.event.PacketListenerManager;

/**
 * Example/Test of a packet listener
 * @author Matthew Kwiatkowski
 */
public class PacketListenerTest {

    public static void main(String[] args){
        PacketListenerManager.get.registerListener(new Listen());
        PacketInSetOrder inPacket = new PacketInSetOrder(10, 10);
        PacketListenerManager.get.callEvent(inPacket);
    }

}
class Listen implements PacketHandler {

    @PacketEventHandler
    public void gotPacket(PacketInSetOrder p){
        System.out.println("Got packet!");
    }

}
