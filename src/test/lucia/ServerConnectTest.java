package test.lucia;

import main.java.lucia.Client;
import main.java.lucia.client.ClientBuilder;
import main.java.lucia.net.NetworkBuilder;

/**
 * Tests connection to the server and packet sending.
 * @author Matthew Kwiatkowski
 */
public class ServerConnectTest {

    public static void main(String[] args){
        Client.logger.info("Starting Server Connection Test...");
        //System.setProperty("io.netty.tryReflectionSetAccessible", "true");
        new ClientBuilder(new NetworkBuilder(), null).setRunGUI(false).initialize();
    }
}
