package main.java.lucia.net.packet.impl.incoming;

import main.java.lucia.Client;
import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.net.packet.IncomingPacket;

/**
 * Interface for all decoder implementations
 * @author Matthew Kwiatkowski
 * @author Brett Downey
 */
public abstract class Decoder {

    /**
     * Provides Deserialization functionality for converting messages
     * to {@link IncomingPacket}
     * @param message the message to decode
     * @return the respective {@link IncomingPacket}
     */
    protected abstract IncomingPacket getPacket(String message);

    /**
     * Processes the incoming message
     * @param message the message to process
     * @return the packet, after processing.
     */
    public abstract IncomingPacket process(String message);

    /**
     * Safely decodes a message
     * @param message the message to decode
     * @return an {@link IncomingPacket} with the given message
     */
    public IncomingPacket decodePacket(String message){
        Client.logger.debug("Processed incoming packet: " + message);
        IncomingPacket ret = null;
        try{
            ret = getPacket(message);
        }catch(Exception|Error e){
            e.printStackTrace(MLogger.getErrorStream());
        }
        return ret;
    }


}
