package main.java.lucia.client.protocol.message.impl.employee;

import com.google.gson.Gson;
import main.java.lucia.client.content.employee.Employee;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.impl.MattMessage;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

public class SetEmployeeMessage extends MattMessage {
    /**
     * The constructor for a {@link Message} which provides the needed {@link PacketProcessor}.
     *
     * @param packetProcessor
     */
    public SetEmployeeMessage(PacketProcessor packetProcessor) {
        super(packetProcessor);
    }

    /**
     * Gets the reply opcode to send to the server, and if the message contains
     * an opcode other than -1, the client will send the opcode to the server.
     * Note that this does not support any information to also be sent, if a specific
     * packet must be sent, then see getReplyPacket, and leave this as {@code -1}.
     * <p>
     * Note that this function as well as {@code getReplyPacket} will be ran
     * when this packet is processed, and there is no need to set the outgoing
     * packet manually. If the outgoing packet has been manually set (via the
     * {@link PacketProcessor}, that packet
     * will also be checked and sent. Therefore at the maximum, 3 packets may
     * be returned from a single message.
     *
     * @return The reply opcode
     */
    @Override
    public int getReplyOpcode() {
        return 0;
    }

    /**
     * Gets the reply packet to send to the server, this method should be
     * filled in if the packet that is required needs to send information instead
     * of just simply an opcode to the server. If no information is to be sent,
     * leave this {@code null} and see getReplyOpcode.
     * <p>
     * Note that this function as well as {@code getReplyOpcode} will be ran
     * when this packet is processed, and there is no need to set the outgoing
     * packet manually. If the outgoing packet has been manually set (via the
     * {@link PacketProcessor}, that packet
     * will also be checked and sent. Therefore at the maximum, 3 packets may
     * be returned from a single message.
     *
     * @return The packet to reply with
     */
    @Override
    public OutgoingAuthenticatedPacket getReplyPacket() {
        return null;
    }

    /**
     * An abstract class which processes the given implemented {@link Message}.
     */
    @Override
    public void process() {
        getPacketProcessor().setOutgoingPacket(null);
        EmployeePane.gotEmployee(deserialize());
    }

    /**
     * Sends a request to get an employee with id
     * @param id the id to serach for
     */
    public static void sendEmployeeRequestMessage(Integer id){
        OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(OpcodeConstants.GET_EMPLOYEE_OPCODE);
        out.setJsonRequest(new Gson().toJson(id));
        PacketSender.getCurrentPacketSender().sendMessage(out);
    }

    @Override
    public Employee deserialize(){
        return GsonTypeFactory.EMPLOYEE_GSON.fromJson(getPacketProcessor().getJsonRequest(), Employee.class);
    }
}
