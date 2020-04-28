package main.java.lucia.client.content.employee;

import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;

import java.time.ZoneId;
import java.util.Date;

/**
 * Object representing a note with a sender (manager note)
 *
 * @author Matt Kwiatkowski
 */
public class ManagerNote extends EmployeeNote{

//    /**
//     * The specific id of this pizza on the server.
//     * DO NOT SET. Let Gson do that.
//     */
//    private int rowNum = -1;

    private String sender;
    private boolean read;

    private String type = "managerNote";

    public ManagerNote(String content, ClientTime set, String sender) {
        super(content, set);
        this.sender = sender;
        this.read = false;
    }

    public ManagerNote(String content, String sender) {
        this(content, new ClientTime(TimeFormat.FORMATTER_ISO_STANDARD, ZoneId.systemDefault()), sender);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
