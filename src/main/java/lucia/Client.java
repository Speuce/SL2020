package main.java.lucia;

import java.time.temporal.ChronoUnit;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.ClientBuilder;
import main.java.lucia.client.content.files.json.loader.JsonHandler;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;
import main.java.lucia.consts.ClientConstants;
import main.java.lucia.fxml.InterfaceBuilder;
import main.java.lucia.net.NetworkBuilder;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * The class which initializes the client.
 *
 * @author Brett Downey
 */
public class Client {

    /**
     * The {@link ClientBuilder} for the Client
     */
    private static ClientBuilder client;

    /**
     * The logger that will print important information.
     */
    public static final Logger logger = Logger.getLogger(Client.class.getSimpleName());
    static {
        BasicConfigurator.configure();
        logger.getLoggerRepository().getLogger("io.netty").setLevel(Level.INFO);
        Thread.currentThread().setName(ClientConstants.NAME);
    }

    /**
     * The main method that initializes the client protocol
     */
    public static void main(String[] args) {
        try {
            logger.info("Initializing " + ClientConstants.NAME);
            client = new ClientBuilder(new NetworkBuilder(), new InterfaceBuilder()).initialize();
        } catch (Exception e) {
            logger.error("An error occurred while initializing the server!", e);
        }
    }

    public synchronized static void shutdown(int errorCode) {
        logger.getLoggerRepository().getLogger("io.netty").setLevel(Level.OFF);
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