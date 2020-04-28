package main.java.lucia.client.protocol.message.impl;

import main.java.lucia.client.content.rights.Rights;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.net.packet.PacketProcessor;

/**
 * TO simplify messages that Matt is making without the need to
 * implement unecessary methods
 * @author Matt Kwiatkowski
 */
public abstract class MattMessage extends Message {
    /**
     * The constructor for a {@link Message} which provides the needed {@link PacketProcessor}.
     *
     * @param packetProcessor
     */
    public MattMessage(PacketProcessor packetProcessor) {
        super(packetProcessor);
    }

    //Redundant method required by superclass.
    @Override
    public boolean isCustom() {
        return false;
    }

    //Uneccesary
    @Override
    public String serialize() {
        return null;
    }

    //Unnecessary
    @Override
    public Object deserialize() {
        return null;
    }
}
