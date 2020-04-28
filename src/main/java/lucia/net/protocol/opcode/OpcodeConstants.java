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

    /**
     * The opcode which requests all information pertaining to a specific store.
     * This opcode will usually be ran once a client logs in to the server.
     */
    public static final int REQUEST_STORE_INFORMATION_OPCODE = 1;

    /**
     * The opcode which checks for an update, if the client sends this,
     * the server will reply with the update available status. If the
     * server sends this, then the client will notify the user that there
     * is an update pending.
     */
    public static final int CHECK_FOR_UPDATE_OPCODE = 2;

    /**
     * The system broadcast message opcode, which broadcasts a message to
     * all clients, depending on if {@link ConnectionType}
     * {@code MAIN} and/or {@code MOBILE} are selected for the operation.
     */
    public static final int SYSTEM_BROADCAST_MESSAGE = 3;

    /**
     * The message that the servers can report to the clients indicating that they are
     * required to shutdown. This will be sent during nightly broadcasts to all clients
     * in order to force a restart for the next morning.
     */
    public static final int CLIENT_SHUTDOWN_MESSAGE = 4;

    /**
     * The message that the server/client can report to eachother
     */
    public static final int STORE_OPEN_MESSAGE = 5;

    /**
     * The opcode for submitting an order
     */
    public static final int SUBMIT_ORDER_OPCODE = 14;

    /**
     * Opcode for setting the order in a client
     */
    public static final int SET_ORDER_OPCODE = 15;

    /**
     * The opcode for cancelling an order.
     */
    public static final int CANCEL_ORDER_OPCODE = 16;

    /**
     * The opcode for transferring an order to another store.
     */
    public static final int TRANSFER_ORDER_OPCODE = 17;

    /**
     * The opcode for the server sending the client updated current order number
     * for the current day
     */
    public static final int SET_CURRENT_ORDERNUM_OPCODE = 18;

    /**
     * The opcode for requesting the store's given day orders.
     */
    public static final int DAY_ORDERS_LIST_OPCODE = 19;

    /**
     * The opcode that is responsible for retrieving the server up time.
     */
    public static final int SERVER_UP_TIME_OPCODE = 30;

    //Employee Opcodes
    /**

     * The opcode for requested Employee object receiving
     */
    public static final int GET_EMPLOYEE_OPCODE = 32;

    /**
     * the opcode used for server sending an employee to client
     */
    public static final int SET_EMPLOYEE_OPCODE = 33;

    /**
     * The opcode for setting Employee data (or creating a new one) if the server does not recognize
     * the Employee id, it should automatically make a new one
     */
    public static final int NEW_EMPLOYEE_OPCODE = 35;
    /**
     * Opcode for requesting Employee ID:Name map
     */
    public static final int GET_EMPLOYEE_MAP_OPCODE = 36;
    /**
     * The opcode which signifies the request for the store report from a given store.
     */
    public static final int STORE_REPORT_OPCODE = 31;

    /**
     * For clients requesting preorders
     */
    public static final int GET_PREORDERS_OPCODE = 20;

    /**
     * For server sending a preorders as a part of a request
     */
    public static final int SEND_PREORDER_OPCODE = 21;

    /**
     * Opcode for client requesting the order number of a given day
     */
    public static final int GET_ORDERNUM_DAY_OPCODE = 22;

    /**
     * Opcode for server sending order number of a given day
     */
    public static final int FOUND_ORDERNUM_OPCODE = 23;

    //Customer details 40-50 please reserve

    /**
     * For the client sending requests to the server to
     * find an applicable customer
     */
    public static final int SEARCH_CUSTOMER_OPCODE = 40;

    /**
     * Sent as a later response to a clients Search Customer
     * request, indicating that a customer was found, and
     * containing the details of the customer
     */
    public static final int CUSTOMER_FOUND_OPCDOE = 41;

    public static final int SET_CUSTOMER_OPCODE = 42;


    // TODO Server report for every owner for the morning that reports
    // TODO All notable events that possibly shouldn't have happened.
    // TODO Create a ServerReportTracker
    // E.g. cancelled orders
    // Late orders
    // Edited orders
    // Store credit given
    //
}