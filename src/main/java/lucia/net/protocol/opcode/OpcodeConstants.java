package main.java.lucia.net.protocol.opcode;

import main.java.lucia.net.protocol.ProtocolBuilder;

/**
 * The constant opcodes used for our {@link ProtocolBuilder}
 *
 * @author Brett Downey
 * @author Zachery Unrau
 * @author Matt Kwiatkowski
 */
public class OpcodeConstants {
    //System Error/Info/Request opcodes 0-50

    /**
     * This opcode will be for errors, sent to the client from the server.
     * Its up to the client how to deal with these.
     */
    public static final int ERROR_OPCODE = 0;

    /**
     * The opcode which requests all information pertaining to a specific store.
     * This opcode will usually be ran once a client logs in to the server.
     */
    public static final int REQUEST_STORE_INFORMATION_OPCODE = 11;

    /**
     * The opcode which checks for an update, if the client sends this,
     * the server will reply with the update available status. If the
     * server sends this, then the client will notify the user that there
     * is an update pending.
     */
    public static final int CHECK_FOR_UPDATE_OPCODE = 12;

    /**
     * The system broadcast message opcode, which broadcasts a message to
     * all clients, depending on if ConnectionType
     * {@code MAIN} and/or {@code MOBILE} are selected for the operation.
     */
    public static final int SYSTEM_BROADCAST_MESSAGE = 13;

    /**
     * The message that the servers can report to the clients indicating that they are
     * required to shutdown. This will be sent during nightly broadcasts to all clients
     * in order to force a restart for the next morning.
     */
    public static final int CLIENT_SHUTDOWN_MESSAGE = 14;

    /**
     * The message that the server/client can report to eachother
     */
    public static final int STORE_OPEN_MESSAGE = 15;

    /**
     * The opcode that is responsible for retrieving the server up time.
     */
    public static final int SERVER_UP_TIME_OPCODE = 30;

    //Order Opcodes 100-199

    /**
     * The operation code for submitting an order to the server
     */
    public static final int SUBMIT_ORDER_OPCODE = 100;

    /**
     * Opcode for setting the order in a client
     */
    public static final int SET_ORDER_OPCODE = 101;

    /**
     * The opcode for cancelling an order.
     */
    public static final int VOID_ORDER_OPCODE = 199;

    /**
     * The opcode for transferring an order to another store.
     */
    public static final int TRANSFER_ORDER_OPCODE = 120;

    /**
     * The opcode for requesting the store's given day orders.
     */
    public static final int DAY_ORDERS_LIST_OPCODE = 103;

    /**
     * For clients requesting preorders
     */
    public static final int GET_PREORDERS_OPCODE = 104;

    /**
     * For server sending a preorder as a part of a request
     */
    public static final int SET_PREORDER_OPCODE = 105;

    /**
     * Client requesting ordernumber of a specific day
     */
    public static final int GET_ORDERNUM_DAY_OPCODE = 106;

    /**
     * Rsponse packet to get ordernum day
     * Server telling client
     */
    public static final int FOUND_ORDERNUM_DAY_OPCODE = 107;


    //Employee Opcodes 200-299
    /**
     * The opcode for requested Employee object receiving
     */
    public static final int GET_EMPLOYEE_OPCODE = 200;

    /**
     * the opcode used for server sending an employee to client
     */
    public static final int SET_EMPLOYEE_OPCODE = 201;

    /**
     * The opcode for setting Employee data (or creating a new one) if the server does not recognize
     * the Employee id, it should automatically make a new one
     */
    public static final int NEW_EMPLOYEE_OPCODE = 202;
    /**
     * Opcode for requesting Employee ID:Name map
     */
    public static final int GET_EMPLOYEE_MAP_OPCODE = 203;

    /**
     * Opcode for when an employee/manager attempts to do something without
     * a proper session
     */
    public static final int EMPLOYEE_PERMISSION_DENIED= 204;

    //Customer details 300-399

    /**
     * For the client sending requests to the server to
     * find an applicable customer
     */
    public static final int SEARCH_CUSTOMER_PHONE_OPCODE = 300;

    /**
     * Sent as a later response to a clients Search Customer
     * request, indicating that a customer was found, and
     * containing the details of the customer
     */
    public static final int CUSTOMER_FOUND_OPCDOE = 301;

    /**
     * Opcode for saving a customer into the database.
     */
    public static final int SAVE_CUSTOMER_OPCODE = 302;


    // TODO Server report for every owner for the morning that reports
    // TODO All notable events that possibly shouldn't have happened.
    // TODO Create a ServerReportTracker
    // E.g. cancelled orders
    // Late orders
    // Edited orders
    // Store credit given
    //
}