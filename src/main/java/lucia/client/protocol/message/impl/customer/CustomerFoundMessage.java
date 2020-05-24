package main.java.lucia.client.protocol.message.impl.customer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.impl.MattMessage;
import main.java.lucia.client.protocol.message.impl.W;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class CustomerFoundMessage extends MattMessage {
    /**
     * The constructor for a {@link Message} which provides the needed {@link PacketProcessor}.
     *
     * @param packetProcessor
     */
    private static Map<Integer, Set<Consumer<CustomerDetails>>> callbackMap = new HashMap<>();

    public CustomerFoundMessage(PacketProcessor packetProcessor) {
        super(packetProcessor);
    }

    /**
     * Sends a request to the server to find all
     * customers where their phone number is LIKE
     * the one passed here
     */
    public static void findCustomerByPhone(String phone, Consumer<CustomerDetails> callback){
        if(callbackMap.containsKey(phone)){
            callbackMap.get(phone).add(callback);
        }else{
            JsonObject put = new JsonObject();
            put.addProperty("phoneNumber", phone);
            //OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(OpcodeConstants.SEARCH_CUSTOMER_OPCODE);
            //out.setJsonRequest(GsonTypeFactory.BASIC_GSON.toJson(put));
            //PacketSender.getCurrentPacketSender().sendMessage(out);
            Set<Consumer<CustomerDetails>> callbackSet = new HashSet<>();
            callbackSet.add(callback);
            callbackMap.put(Integer.valueOf(phone), callbackSet);
        }

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
        W.p("receiving an customer response: " + getPacketProcessor().getJsonRequest());
        getPacketProcessor().setOutgoingPacket(null);
        JsonObject got = new JsonParser().parse(getPacketProcessor().getJsonRequest()).getAsJsonObject();
        Integer phone = got.get("phone").getAsInt();
        if(got.has("got")){
            //got a customer details file, send it to callback
            if(callbackMap.containsKey(phone)){
                CustomerDetails foundCustomer = GsonTypeFactory.BASIC_GSON.fromJson(got.get("got"), CustomerDetails.class);
                callbackMap.get(phone).forEach(call -> call.accept(foundCustomer));
                callbackMap.remove(phone);
            }
        }else{
            //customer not found. Also send to callback
            if(callbackMap.containsKey(phone)){
                callbackMap.get(phone).forEach(call -> call.accept(null));
                callbackMap.remove(phone);
            }
        }

    }
}
