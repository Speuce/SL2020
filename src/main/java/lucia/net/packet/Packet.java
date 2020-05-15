package main.java.lucia.net.packet;

/**
 * High-level abstraction of a packet.
 * Only contains information about the echocode
 * @author Matthew Kwiatkowski
 */
public abstract class Packet {

    /**
     * The code that the server either echod back to the client,
     * or the code that should be echod back to the server.
     */
    private int echoCode;

    public int getEchoCode() {
        return echoCode;
    }

    public Packet setEchoCode(int echoCode){
        this.echoCode = echoCode;
        return this;
    }

}
