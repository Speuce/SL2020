package main.java.lucia.consts;

public class ClientConstants {

    /**
     * true to enable networking, false otherwise.
     */
    public static final boolean ENABLE_NET = true;

    /**
     * The name of the game.
     */
    public static final String NAME = "Santa Lucia Main Client";

    /**
     * The current version of the client.
     */
    public static final String VERSION = "1.0A";

    /**
     * The server engine cycle rate in milliseconds.
     */
    public static final int CLIENT_ENGINE_PROCESSING_CYCLE_RATE = 150;

    /**
     * The thread pool allocated to the AsynchronousTaskService which is dedicated to handling
     * tasks that do not need to be executed instantly.
     */
    public static final int ASYNCHRONOUS_TASK_POOL = 2;

    /**
     * The host that this client is going to run on.
     */
    public static final String HOST = System.getProperty("host", "192.168.0.19");

    /**
     * The port that this client is going to run on.
     */
    public static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

    /**
     * The time of day that order nunbers reset
     */
    public static final int DAY_RESET = 5;
}