package main.java.lucia.net;

public abstract class NetworkConstants {

    /**
     * The client identifier which identifies if this is a mobile or main application.
     * It is a security risk for this value to be given out as this is the main application.
     * Values are changed every update.
     */
    public static final String CID = "MA4li9gGA26EmvDJyuEl";

    /**
     * The maximum amount of messages the client will attempt to
     * resend if an echo code hasn't been received for that message
     * within a given time frame.
     */
    public static final int MAX_RESEND_ATTEMPT = 10;

    /**
     * The key location used for encryption
     */
    public static final String PUBLIC_KEY_DIRECTORY = "security/public.key";

    /**
     * The maximum amount of messages that can be processed in one sequence.
     */
    public static final int PACKET_PROCESS_LIMIT = 150;
}
