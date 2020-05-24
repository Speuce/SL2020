package test.lucia.server;

import main.java.lucia.Client;
import main.java.lucia.client.ClientBuilder;
import main.java.lucia.net.NetworkBuilder;
import main.java.lucia.net.packet.event.PacketListenerManager;

/**
 * Tests connection to the server and packet sending.
 * @author Matthew Kwiatkowski
 */
public class ServerConnectTest {

    public static ClientBuilder cl;

    public static void main(String[] args){
        Client.logger.info("Starting Server Connection Test...");
        PacketListenerManager.get.registerListener(new ServerConnectTester());
        //System.setProperty("io.netty.tryReflectionSetAccessible", "true");
        cl = new ClientBuilder(new NetworkBuilder(), null).setRunGUI(false).initialize();
    }
}
