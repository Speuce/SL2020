package main.java.lucia.client.protocol.message;

import main.java.lucia.client.protocol.message.impl.ServerUpTimeMessage;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.protocol.ProtocolBuilder;

/**
 * The abstract {@link MessageHandler} which is used for the
 * abstract {@link Message} clas subtypes.
 *
 * @author Brett Downey
 */
public abstract class MessageHandler<M extends Message> {

    /**
     * Decodes the given {@link MessageHandler} and in
     * turn creates a newly instanced {@link Message}
     * linked to the given {@link MessageHandler}
     * {@code opcode} from the {@link ProtocolBuilder
     *
     * @param packetProcessor The {@link PacketProcessor} associated with this
     *                        {@link MessageHandler}}.
     * @return The given Element
     */
    public abstract M decodeInbound(PacketProcessor packetProcessor);
}