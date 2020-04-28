package main.java.lucia.client.protocol.message.handler.employee;

import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.MessageHandler;
import main.java.lucia.client.protocol.message.impl.employee.GetEmployeeMapMessage;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.protocol.ProtocolBuilder;

public class GetEmployeeMapHandler extends MessageHandler {
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
    public GetEmployeeMapMessage decodeInbound(PacketProcessor packetProcessor) {
        return new GetEmployeeMapMessage(packetProcessor);
    }
}
