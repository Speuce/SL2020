package main.java.lucia;

import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.ClientBuilder;
import main.java.lucia.client.content.files.json.loader.JsonHandler;
import main.java.lucia.consts.ClientConstants;
import main.java.lucia.fxml.InterfaceBuilder;
import main.java.lucia.net.NetworkBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * The class which initializes the client.
 *
 * @author Brett Downey
 * @author Matthew Kwiatkowski
 */
public class Client {

    /**
     * The {@link ClientBuilder} for the Client
     */
    private static ClientBuilder client;

//    static {
//        System.out.println("static block");
//        Configurator.initialize(LogConfigBuilder.buildDefaultConfig().build());
//        Thread.currentThread().setName(ClientConstants.NAME);
//        System.out.println("static block2");
//
//        //Configurator.setLevel("io.netty", Level.ALL);
//    }

    /**
     * The logger that will print important information.
     */
    public static final Logger logger = LogManager.getLogger(Client.class);

    static{
        //LogManager.getLogger("io.netty").atInfo()
    }

    /**
     * The main method that initializes the client protocol
     */
    public static void main(String[] args) {
        try {

            logger.info("Initializing " + ClientConstants.NAME);
            client = new ClientBuilder(new NetworkBuilder(), new InterfaceBuilder()).initialize();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("An error occurred while initializing the server!", e);
        }
    }

    public synchronized static void shutdown(int errorCode) {
        Configurator.setLevel("io.netty", Level.OFF);
        synchronized (Client.class) {
            Client.logger.info("The client is shutting down with an error code of : " + errorCode + "\n");
            Client.logger.info("Shutdown Trace:");
            client.shutdown(errorCode);
            Client.logger.info("All client related processes have been successfully shutdown.");

            JsonHandler.saveAndShutdown();
            Client.logger.info("All previously loaded files have been saved!");

            AsynchronousTaskService.safeShutdown();

            if(NetworkBuilder.isDead) {
                System.exit(errorCode);
            }
        }
    }

    /**
     * Finalizes the shutdown
     * @param errorCode
     */
    public synchronized static void finalizeShutdown(int errorCode) {
        synchronized (Client.class) {
            Client.logger.info("All processes have been shutdown");
            System.exit(errorCode);
        }
    }
}