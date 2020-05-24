package main.java.lucia.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import main.java.lucia.Client;
import main.java.lucia.client.Engine;
import main.java.lucia.consts.ClientConstants;
import main.java.lucia.net.channel.ClientPipelineInitializer;
import main.java.lucia.net.packet.OutgoingPacket;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingHandshakePacket;

import java.util.concurrent.TimeUnit;

/**
 * The network builder
 *
 * @author Brett Downey
 */
public class NetworkBuilder implements Runnable {

  /**
   * A flag representing if this NetworkBuilder is dead, since the network may shutdown in two
   * different states, this is here to keep track of which state it is shutting down from.
   */
  public static boolean isDead;

  /**
   * The reconnect delay for this Network.
   */
  private static final int RECONNECT_DELAY = 3000;

  /**
   * The timer associated to this network
   */
  private static Timer timer = new HashedWheelTimer();

  /**
   * The flag which indicates if this network has been shut down.
   */
  private boolean shutdown;

  /**
   * The EventLoopGroup associated with this NetworkBuilder.
   */
  private static EventLoopGroup group;

  /**
   * The Bootstrap associated with this NetworkBuilder.
   */
  private Bootstrap bootstrap;

  /**
   * The connected status of the network.
   */
  private boolean connected;

  /**
   * The status of if the network builder is currently attempting to connect.
   */
  private boolean connecting;

  /**
   * The message sender service associated with this network connection.
   */
  private PacketSender senderService;

  /**
   * The channel associated with this NetworkBuilder.
   */
  private Channel client;

  /**
   * Returns the client to server connection status
   *
   * @return True if connection, false otherwise
   */
  public boolean isConnected() {
    return connected;
  }

  /**
   * Connects this Network builder to the server using Netty, and if a successful connection occurs,
   * then establish the PacketSender.
   */
  @Override
  public void run() {
    if (shutdown || connected || connecting || !ClientConstants.ENABLE_NET) {
      return;
    }

    if (group != null && !group.isShutdown()) {
      group.shutdownGracefully();
    }
    initializeValues();
    connecting = true;

    try {
      ChannelFuture connection = bootstrap.connect();
      connection.awaitUninterruptibly();

      assert connection.isDone();

      if (connection.isSuccess()) {
        connected = true;
      } else {
        Client.logger.info(
            "Failed to connect, re-attempting connection in " + RECONNECT_DELAY + " milliseconds.");
        TimerTask timerTask = timeout -> {
          connecting = false;
          run();
        };
        timer.newTimeout(timerTask, RECONNECT_DELAY, TimeUnit.MILLISECONDS);
      }
    } catch (Exception e) {
      Client.logger.error("Failed to run to the server!", e);
    }
  }

  public void buildPacketSender() {
    senderService = new PacketSender(client);
    PacketSender.setCurrentPacketSender(senderService);
  }

  /**
   * initialize the values associated with this NetworkBuilder.
   */
  private void initializeValues() {
    connected = false;
    senderService = null;
    group = new NioEventLoopGroup();
    bootstrap = new Bootstrap()
        .group(group)
        .channel(NioSocketChannel.class)
        .remoteAddress(ClientConstants.HOST, ClientConstants.PORT)
        .handler(new ClientPipelineInitializer(this));
  }

  /**
   * Sends an outgoing packet.
   *
   * @param packet The outgoing packet
   */
  public void sendMessage(OutgoingPacket packet) {
    senderService.sendMessage(packet);
  }

  public void setClient(Channel client) {
    this.client = client;
  }

  /**
   * Sends the initial message, which confirms the senderService has been activated and a {@link
   * NullPointerException} doesn't occur.
   *
   * @param outgoingHandshakePacket The outgoing packet
   */
  public void sendInitialMessage(OutgoingHandshakePacket outgoingHandshakePacket) {
    try {
      Client.logger.info("Sending handshake packet");
      sendMessage(outgoingHandshakePacket);
    } catch (Exception e) {
      Client.logger.error("A fatal exception occurred while sending the initial message", e);
    }
  }

  /**
   * Checks a given echo ID with the engine.
   *
   * @param codeId The codeID
   */
  public void checkId(int codeId) {
    Engine.getEchoedCodes().add(codeId);
  }

  /**
   * Attempts to resend messages which have not been echoed back by the server.
   */
  public void attemptResend() {
    if (senderService != null) {
      senderService.reattemptFailedMessages();
    }
  }

  /**
   * Processes the echo code ID returned by the server.
   *
   * @param codeId The echo code ID
   */
  public void processCodeId(int codeId) {
    senderService.echoedCodeProcess(codeId);
  }

  /**
   * Shuts down the network
   */
  public void shutDownGracefully(int errorCode) {
    if (errorCode != -1) {
      shutdown = true;
    }

    PacketSender.setCurrentPacketSender(null);
    connected = false;
    if (client != null) {
      ChannelFuture channelFuture = client.closeFuture();
      channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
        Client.logger.info("The network has successfully shutdown");
        if (errorCode != -1) {
          Client.finalizeShutdown(errorCode);
        }
      });
      client.close();
      group.shutdownGracefully();
    } else if (errorCode != -1) {
      group.shutdownGracefully();
      isDead = true;
    }
    Client.logger.info("The network has successfully finished it's cleanup protocol");

    if (!shutdown) {
      TimerTask timerTask = timeout -> {
        connecting = false;
        run();
      };
      timer.newTimeout(timerTask, RECONNECT_DELAY, TimeUnit.MILLISECONDS);
    } else {
      timer.stop();
    }
  }
}