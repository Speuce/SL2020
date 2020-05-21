package main.java.lucia.client;

import main.java.lucia.Client;
import main.java.lucia.consts.ClientConstants;
import main.java.lucia.fxml.InterfaceBuilder;
import main.java.lucia.net.NetworkBuilder;
import main.java.lucia.net.packet.event.PacketListenerManager;
import main.java.lucia.util.BackgroundLoader;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Loads all required information and starts processes required for the server
 *
 * @author Brett Downey
 */
public class ClientBuilder {

    /**
     * The background loader that will load various utilities in the background while the bootstrap is
     * preparing the server.
     */
    private final BackgroundLoader backgroundLoader = new BackgroundLoader();

    /**
     * The network dedicated to this client
     */
    private NetworkBuilder network;

    /**
     * The interface dedicated to this client
     */
    private InterfaceBuilder fxml;

    /**
     * The engine associated with this ClientBuilder.
     */
    private ClientEngine engine;

    /**
     * Whether or not to run the gui part of the application, or to run it headless.
     */
    private boolean runGUI = true;

    /**
     * The constructor for this Client.
     */
    public ClientBuilder(NetworkBuilder network, InterfaceBuilder fxml) {
        Engine.network = network;
        Engine.listenerManager = new PacketListenerManager();
        this.network = network;
        this.fxml = fxml;
    }

    /**
     * Initializes this game builder effectively preparing the background startup tasks and game
     * processing.
     *
     * @throws Exception if any issues occur while starting the network.
     */
    public ClientBuilder initialize() {
        AsynchronousTaskService.process(() -> {
            engine = new ClientEngine().init();
            //backgroundLoader.init(createBackgroundTasks());

            /* Make sure background tasks loaded properly before proceeding */
//            if (!backgroundLoader.awaitCompletion()) {
//                throw new IllegalStateException("Background load did not complete normally!");
//            }
            //MenuTest.test(); // TODO Remove this when done w/ menu testing
            Client.logger.info(ClientConstants.NAME + " has successfully loaded\n");
        });

        AsynchronousTaskService.process(() -> network.run());
        if(runGUI){
            fxml.initialize();
        }

        return this;
    }

    /**
     * Returns a queue containing all of the background tasks that will be executed by the background
     * loader. Please note that the loader may use multiple threads to load the utilities
     * concurrently, so utilities that depend on each other <b>must</b> be executed in the same task
     * to ensure thread safety.
     *
     * @return the queue of background tasks.
     */
    public Queue<Runnable> createBackgroundTasks() {
        Queue<Runnable> tasks = new ArrayDeque<>();
//        tasks.add(new PremadeFoodDefinitionLoader());
//        tasks.add(new ToppingsDefinitionLoader());
//        tasks.add(new StoreInformationDefinitionLoader());
//        tasks.add(new DiscountDefinitionLoader());
        return tasks;
    }

    /**
     * Performs a safe shutdown of the client.
     */
    public void shutdown(int errorCode) {
        if(runGUI){
            fxml.stop();
        }
        engine.safeShutdown();

        AsynchronousTaskService.process(() -> {
            try {
                backgroundLoader.awaitCompletion();
                Client.logger.info("The background loader has successfully shutdown");
            } catch (IllegalStateException ignored) {
            }
        });

        network.shutDownGracefully(errorCode);
    }

    /**
     * Set whether or not to run with the gui.
     */
    public ClientBuilder setRunGUI(boolean runGUI) {
        this.runGUI = runGUI;
        return this;
    }
}