package main.java.lucia.net.packet;

import main.java.lucia.client.content.structures.Exclude;

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

    /**
     * Flag tracking whether sending/receving of this packet is cancelled or not.
     */
    @Exclude
    private boolean cancelled;

    public int getEchoCode() {
        return echoCode;
    }

    public Packet setEchoCode(int echoCode){
        this.echoCode = echoCode;
        return this;
    }

    /**
     * Flag tracking whether sending/receving of this packet is cancelled or not.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Flag tracking whether sending/receving of this packet is cancelled or not.
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
