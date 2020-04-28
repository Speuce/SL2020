package main.java.lucia.client.content.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * A class representing the server time.
 *
 * @author Brett Downey
 */
public class ServerTime {
  // 	America/Regina ZoneId saskatoonZoneId = ZoneId.of("America/Regina");
  /**
   * The current time zone this server represents.
   */
  private static final ZoneId SERVER_TIME_ZONE = ZoneId.systemDefault();
  /**
   * The INSTANCE of the LocalDateTime from when the constructor was called.
   */
  private final LocalDateTime INSTANCE;
  /**
   * A String which represents the formatted version of this LocalDateTime.
   */
  private final String INSTANCE_STRING;

  /**
   * A constructor for the ServerTime whenever this is called. This time does not change and are
   * final variables.
   */
  public ServerTime(TimeFormat format) {
    this.INSTANCE = ServerTime.getCurrentLocalTime();
    this.INSTANCE_STRING = ServerTime.string(this, format);
  }

  /**
   * Returns the current server time.
   */
  public static LocalDateTime getCurrentLocalTime() {
    return LocalDateTime.now();
  }

  /**
   * Returns the current server time in a formatted string.
   *
   * @return A formatted String
   */
  public static String string(TimeFormat format) {
    return format.getFormat().format(getCurrentLocalTime());
  }

  /**
   * Returns the server time in a formatted string.
   *
   * @return A formatted String
   */
  private static String string(ServerTime time, TimeFormat format) {
    return format.getFormat().format(time.INSTANCE);
  }

  /**
   * Returns the time this INSTANCE was made.
   *
   * @return The time.
   */
  public LocalDateTime getThisTime() {
    return INSTANCE;
  }

  /**
   * Returns the time this INSTANCE was made in a neatly formatted string.
   *
   * @return The String representing the ServerTime when this ServerTime was created.
   */
  public String toString() {
    return INSTANCE_STRING;
  }

  /**
   * Returns the amount of time between this ServerTime and a given ServerTime.
   *
   * @param val The ChronoUnit which represents the time unit.
   * @return The time between this time and the given {@code time}
   * @oaram time The time to compare it to.
   */
  public long getTimeBetween(ServerTime time, ChronoUnit val) {
    return Math.abs(val.between(INSTANCE, time.getThisTime()));
  }

  /**
   * Returns the amount of time between this and the current local server time.
   *
   * @param val The ChronoUnit which represents the time unit.
   * @return The time between this time and the local server time.
   */
  public long timeFromNow(ChronoUnit val) {
    return val.between(INSTANCE, ServerTime.getCurrentLocalTime());
  }
}