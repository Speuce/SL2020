package main.java.lucia.net.protocol;

import main.java.lucia.client.protocol.message.handler.DoNotReplyHandler;
import main.java.lucia.client.protocol.message.handler.customer.CustomerFoundHandler;
import main.java.lucia.client.protocol.message.handler.employee.GetEmployeeMapHandler;;
import main.java.lucia.client.protocol.message.handler.employee.SetEmployeeHandler;
import main.java.lucia.client.protocol.message.handler.ServerUpTimeHandler;
import main.java.lucia.client.protocol.message.handler.order.FoundOrdernumHandler;
import main.java.lucia.client.protocol.message.handler.order.SetCurrentOrdernumHandler;
import main.java.lucia.client.protocol.message.handler.order.SetOrderMessageHandler;
import main.java.lucia.client.protocol.message.handler.order.SetPreorderHandler;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;
import main.java.lucia.net.protocol.opcode.OpcodeMap;


public class ProtocolBuilder extends OpcodeMap {

    public ProtocolBuilder() {
        super();
        initiate();
    }

    /**
     * Initialises the protocol by registering the encoders and decoders
     * linked to their respective opcodes.
     */
    private void initiate() {
        register(OpcodeConstants.SERVER_UP_TIME_OPCODE, new ServerUpTimeHandler());

        register(OpcodeConstants.NEW_EMPLOYEE_OPCODE, new DoNotReplyHandler());
        register(OpcodeConstants.GET_EMPLOYEE_OPCODE, new DoNotReplyHandler());
        register(OpcodeConstants.GET_EMPLOYEE_MAP_OPCODE, new GetEmployeeMapHandler());
        register(OpcodeConstants.SET_EMPLOYEE_OPCODE, new SetEmployeeHandler());

        register(OpcodeConstants.DAY_ORDERS_LIST_OPCODE, new DoNotReplyHandler());
        register(OpcodeConstants.SUBMIT_ORDER_OPCODE, new DoNotReplyHandler());
        register(OpcodeConstants.SET_ORDER_OPCODE, new SetOrderMessageHandler());
        register(OpcodeConstants.SET_CURRENT_ORDERNUM_OPCODE, new SetCurrentOrdernumHandler());
        register(OpcodeConstants.SEND_PREORDER_OPCODE, new SetPreorderHandler());
        register(OpcodeConstants.GET_PREORDERS_OPCODE, new DoNotReplyHandler());
        //register(OpcodeConstants.SET_CURRENT_ORDERNUM_OPCODE, new DoNotReplyHandler());
        register(OpcodeConstants.FOUND_ORDERNUM_OPCODE, new FoundOrdernumHandler());

        register(OpcodeConstants.SEARCH_CUSTOMER_OPCODE, new DoNotReplyHandler());
        register(OpcodeConstants.CUSTOMER_FOUND_OPCDOE, new CustomerFoundHandler());

    }
}