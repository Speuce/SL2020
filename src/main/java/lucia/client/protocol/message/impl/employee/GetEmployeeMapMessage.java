package main.java.lucia.client.protocol.message.impl.employee;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.impl.MattMessage;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

import java.lang.reflect.Type;
import java.util.Map;

public class GetEmployeeMapMessage extends MattMessage {
    /**
     * The constructor for a {@link Message} which provides the needed {@link PacketProcessor}.
     *
     * @param packetProcessor
     */
    public GetEmployeeMapMessage(PacketProcessor packetProcessor) {
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
        return -1;
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
        BiMap<Integer, String> putMap = HashBiMap.create(deserialize());
        EmployeePane.instance.setEmployeeMap(putMap);
    }

    /**
     * Sends an employee map request to the server
     */
    public static void sendMapRequest(){
        OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(OpcodeConstants.GET_EMPLOYEE_MAP_OPCODE);
        out.setJsonRequest("hey");
        PacketSender.getCurrentPacketSender().sendMessage(out);
    }

    @Override
    public Map<Integer, String> deserialize(){
        TypeToken<Map<Integer, String>> typeToken = new TypeToken<Map<Integer, String>>(){};
        Type empMapType = typeToken.getType();
        return new Gson().fromJson(getPacketProcessor().getJsonRequest(), empMapType);
    }
}
