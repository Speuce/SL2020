package main.java.lucia.client.protocol.message.handler;

import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.MessageHandler;
import main.java.lucia.client.protocol.message.impl.DoNotReplyMessage;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.protocol.ProtocolBuilder;

/**
 * Use this handler for opcodes that should j
 */
public class DoNotReplyHandler extends MessageHandler {

    /**
     * Decodes the given {@link MessageHandler} and in
     * turn creates a newly instanced {@link Message}
     * linked to the given {@link MessageHandler}
     * {@code opcode} from the {@link ProtocolBuilder
     *
     * @param packetProcessor The {@link PacketProcessor } associated with this
     *                        {@link MessageHandler}}.
     * @param packetProcessor
     * @return The given Element
     */
    @Override
    public DoNotReplyMessage decodeInbound(PacketProcessor packetProcessor) {
        return new DoNotReplyMessage(packetProcessor);
    }
}
