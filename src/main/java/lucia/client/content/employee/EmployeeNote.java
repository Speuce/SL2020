package main.java.lucia.client.content.employee;

import java.time.ZoneId;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;
import org.apache.commons.lang3.StringUtils;

/**
 * A class to hold data of a an employee's personal note
 *
 * @author Matt Kwiatkowski
 */
public class EmployeeNote implements Comparable<EmployeeNote>{

    /**
     * The content of the message
     */
    private String content;

    /**
     * The specific id of this pizza on the server.
     * DO NOT SET. Let Gson do that.
     */
    private int rowNum = -1;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ClientTime getClientTime() {
        return setTime;
    }

    public void setClientTime(ClientTime set) {
        this.setTime = set;
    }

    /**
     * The time and ClientTime that the message was sent
     */
    private ClientTime setTime;

    public EmployeeNote(String content, ClientTime set) {
        this.content = content;
        this.setTime = set;
    }
    public EmployeeNote(String content){
        this(content, new ClientTime(TimeFormat.FORMATTER_ISO_STANDARD, ZoneId.systemDefault()));
    }
    public EmployeeNote(){
        this(StringUtils.EMPTY);
    }

    @Override
    public int compareTo(EmployeeNote other) {
        return other.getClientTime().toLocalDate().compareTo(this.getClientTime().toLocalDate());
    }
}
