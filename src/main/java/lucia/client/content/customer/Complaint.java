package main.java.lucia.client.content.customer;

import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;

import java.time.ZoneId;
import java.util.Date;

/**
 * A class to hold a complaint about a given order
 * @author Matt Kwiatkowski
 */
public class Complaint {

    /**
     * The specific id of this pizza on the server.
     * DO NOT SET. Let Gson do that.
     */
    private int rowNum = -1;

    /**
     * The Id of the order that this is about
     */
    private int orderId;

    /**
     * The day/time that the complaint was made
     */
    private ClientTime created;

    /**
     * The employee Id of the employee that created this complaint
     */
    private int employeeId;

    /**
     * The Row Num of the customer making the complaint
     */
    private int customerRowNum;

    /**
     * A short summary of the complaint
     */
    private String complaintTitle;

    /**
     * A detailed report of the complaint
     */
    private String complaintDetails;

    /**
     * Action taken based on the complaint
     */
    private ComplaintAction actionTaken;

    /**
     * Additional info about a complaint
     * could be:
     * - Free items list
     * - Partial Refund amount
     * - Store Credit Amount
     * - Store discount amount
     */
    private String complaintActionInfo;

    public Complaint(int orderId, int customerRowNum) {
        this.orderId = orderId;
        this.customerRowNum = customerRowNum;
        this.created = new ClientTime(TimeFormat.FORMATTER_ISO_STANDARD, ZoneId.systemDefault());
        this.employeeId = employeeId;
    }

    /**
     * Returns the id of the employee that initiated this complaint
     */
    public int getEmployeeId() {
        return employeeId;
    }


    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintDetails() {
        return complaintDetails;
    }

    public void setComplaintDetails(String complaintDetails) {
        this.complaintDetails = complaintDetails;
    }

    /**
     * The action taken
     * @return {@link ComplaintAction} relevant to this order
     */
    public ComplaintAction getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(ComplaintAction actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getComplaintActionInfo() {
        return complaintActionInfo;
    }

    public void setComplaintActionInfo(String complaintActionInfo) {
        this.complaintActionInfo = complaintActionInfo;
    }
}
