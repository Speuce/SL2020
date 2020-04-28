package main.java.lucia.net.channel;

import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChannelConstants {

    /**
     * The identifier for the the channel {@link LineBasedFrameDecoder}.
     */
    public static final String FRAME_DECODER = "frameDecoder";

    /**
     * The identifier for the the channel {@link StringDecoder}
     */
    public static final String STRING_DECODER = "stringDecoder";

    /**
     * The identifier for the the channel {@link StringEncoder}.
     */
    public static final String STRING_ENCODER = "stringEncoder";

    /**
     * The identifier for the the channel {@link ChannelEventHandler}.
     */
    public static final String CHANNEL_HANDLER = "channelHandler";

    /**
     * The identifier for the max length the {@link LineBasedFrameDecoder}
     * can support.
     */
    public static final int MAX_FRAME_LENGTH = 5000000;
}
