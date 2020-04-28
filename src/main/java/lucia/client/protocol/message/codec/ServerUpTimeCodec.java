package main.java.lucia.client.protocol.message.codec;

import main.java.lucia.client.protocol.message.impl.ServerUpTimeMessage;

/**
 * The codec for {@link ServerUpTimeMessage}.
 *
 * @author Brett Downey
 */
public class ServerUpTimeCodec {

    /**
     * The current server up time in an automatically
     * formatted String.
     */
    private String formattedUptime;

    /**
     * The codec constructor for the {@link ServerUpTimeMessage}.
     *
     * @param formattedUptime The current server up time in a formatted String.
     */
    public ServerUpTimeCodec(String formattedUptime) {
        this.formattedUptime = formattedUptime;
    }

    /**
     * The setter class for the {@code formattedUptime}.
     *
     * @param formattedUptime The current server up time in a formatted String.
     */
    public void setFormattedUptime(String formattedUptime) {
        this.formattedUptime = formattedUptime;
    }

    /**
     * Gets current server up time in an automatically
     * formatted String.
     *
     * @return The current server up time in a formatted String.
     */
    public String getFormattedUptime() {
        return formattedUptime;
    }
}