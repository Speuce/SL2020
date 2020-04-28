package main.java.lucia.client.protocol.message.handler;

import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.MessageHandler;
import main.java.lucia.client.protocol.message.impl.ServerUpTimeMessage;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.protocol.ProtocolBuilder;

/**
 * The {@link MessageHandler} for the {@link ServerUpTimeMessage}
 *
 * @author Brett Downey
 */
public class ServerUpTimeHandler extends MessageHandler {

    /**
     * Decodes the given {@link ServerUpTimeHandler} and in
     * turn creates a newly instanced {@link ServerUpTimeMessage}
     * linked to the given {@link ServerUpTimeHandler}
     * {@code opcode} from the {@link ProtocolBuilder
     *
     * @param packetProcessor The {@link PacketProcessor} associated with this
     *                        {@link ServerUpTimeHandler}}.
     * @return The newly instanced {@link ServerUpTimeMessage}
     */
    @Override
    public ServerUpTimeMessage decodeInbound(PacketProcessor packetProcessor) {
        return new ServerUpTimeMessage(packetProcessor);
    }
}