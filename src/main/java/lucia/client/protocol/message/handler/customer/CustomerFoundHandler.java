package main.java.lucia.client.protocol.message.handler.customer;

import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.MessageHandler;
import main.java.lucia.client.protocol.message.impl.customer.CustomerFoundMessage;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.protocol.ProtocolBuilder;

public class CustomerFoundHandler extends MessageHandler {
    /**
     * Decodes the given {@link MessageHandler} and in
     * turn creates a newly instanced {@link Message}
     * linked to the given {@link MessageHandler}
     * {@code opcode} from the {@link ProtocolBuilder}
     *
     * @param packetProcessor The {@link PacketProcessor } associated with this
     *                        {@link MessageHandler}}.
     * @param packetProcessor
     * @return The given Element
     */
    @Override
    public CustomerFoundMessage decodeInbound(PacketProcessor packetProcessor) {
        return new CustomerFoundMessage(packetProcessor);
    }
}
