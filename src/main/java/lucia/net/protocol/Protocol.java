package main.java.lucia.net.protocol;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;

import java.util.HashMap;
import java.util.Map;

/**
 * Specific Protocol for how to handle certain incoming packets
 * @author Matthew Kwiatkowski
 */
public abstract class Protocol {

    /**
     * Mappings of opcode values to respective Packet Class representations.
     */
    private Map<Integer, Class<? extends IncomingAuthPacket>> opcodeMap;

    /**
     * Mappings of opcode values to Gson objects,
     * specifying which {@link com.google.gson.Gson} to use for packet deserialization
     */
    private Map<Integer, Gson> deserializerMap;

    public Protocol() {
        opcodeMap = new HashMap<>();
        deserializerMap = new HashMap<>();
    }

    /**
     * Registers all Packets as necessary
     */
    protected abstract void registerAll();

    /**
     * Register a packet
     * @param opcode
     * @param clazz
     * @param gson
     */
    public void register(Integer opcode, Class<? extends IncomingAuthPacket> clazz, Gson gson){
        opcodeMap.put(opcode,clazz);
        deserializerMap.put(opcode, gson);
    }

    public IncomingAuthPacket deserialize(JsonObject o){
        assert(o.has("opcode"));
        int opcode = o.get("opcode").getAsInt();
        if(deserializerMap.containsKey(opcode)){
            return deserializerMap.get(opcode).fromJson(o, opcodeMap.get(opcode));
        }
        return null;
    }

    public Class<? extends IncomingAuthPacket> getType(int opcode){
        return opcodeMap.get(opcode);
    }

}
