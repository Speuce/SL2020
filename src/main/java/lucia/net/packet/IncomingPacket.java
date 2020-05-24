package main.java.lucia.net.packet;

/**
 * Represents any packet incoming from the server.
 */
public abstract class IncomingPacket extends Packet{

    public IncomingPacket(final int echoCode) {
        setEchoCode(echoCode);
    }


}