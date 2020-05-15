package main.java.lucia.net.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import main.java.lucia.Client;
import main.java.lucia.net.NetworkBuilder;
import main.java.lucia.net.packet.impl.incoming.MasterDecoder;
import main.java.lucia.net.protocol.ProtocolBuilder;

/**
 * The channel event handler, which handles all incoming
 * server packets.
 *
 * @author Brett Downey
 */
public class ChannelEventHandler extends SimpleChannelInboundHandler<String> {

    /**
     * The associated {@link NetworkBuilder}.
     */
    private NetworkBuilder network;

    /**
     * The associated {@link MasterDecoder}.
     */
    private MasterDecoder masterDecoder;

    /**
     * The {@link ProtocolBuilder} that handles all protocol related operations.
     */
    private static final ProtocolBuilder protocol = new ProtocolBuilder();

    /**
     * The ChannelEventHandler constructor.
     *
     * @param network The {@link NetworkBuilder} that is initializing this instance.
     */
    ChannelEventHandler(NetworkBuilder network) {
        this.network = network;
    }

    /**
     * Handles an incoming message from a channel.
     *
     * @param context The context the message is coming from
     * @param message The incoming message
     */
    @Override
    public void channelRead0(ChannelHandlerContext context, String message) throws Exception {
        masterDecoder.decode(message);
    }

    /**
     * Handles an exception from a channel.
     *
     * @param context The context the error is coming from
     * @param cause The cause of the error
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        Client.logger.info(cause);
        Client.logger.info("An error occurred on the server side, please wait for a reconnection attempt");
        context.close();
    }

    /**
     * Disconnect from the server automatically if the channel becomes inactive.
     *
     * @param context The context to shut down.
     */
    @Override
    public void channelInactive(ChannelHandlerContext context) {
        Client.logger.info("Disconnected from the server.");
        network.shutDownGracefully(-1);
        context.close();
    }

    /**
     * Send a logger message when you run to the server.
     *
     * @param context The context that joined.
     */
    @Override
    public void channelActive(ChannelHandlerContext context) {
        Client.logger.info("Connected to the server.");
        network.setClient(context.channel());
        masterDecoder = new MasterDecoder(network);
    }
}