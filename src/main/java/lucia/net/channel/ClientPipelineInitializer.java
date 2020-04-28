package main.java.lucia.net.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import main.java.lucia.net.NetworkBuilder;

/**
 * Initiates an incoming channel to be suitable for the Santa
 * Lucia protocol.
 *
 * @author Brett Downey
 */
public class ClientPipelineInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * The {@link NetworkBuilder} associated to this class.
     */
    private final NetworkBuilder network;

    /**
     * The constructor for this class.
     *
     * @param network The {@link NetworkBuilder} associated to this class.
     */
    public ClientPipelineInitializer(NetworkBuilder network) {
        this.network = network;
    }

    /**
     * The handler that initiates the connection to the server.
     *
     * @param channel The channel that is being connected to.
     */
    @Override
    public void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(ChannelConstants.FRAME_DECODER, new LineBasedFrameDecoder(ChannelConstants.MAX_FRAME_LENGTH));
        pipeline.addLast(ChannelConstants.STRING_DECODER, new StringDecoder());
        pipeline.addLast(ChannelConstants.STRING_ENCODER, new StringEncoder());
        pipeline.addLast(ChannelConstants.CHANNEL_HANDLER, new ChannelEventHandler(network));
    }
}
