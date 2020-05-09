package main.java.lucia.client.content.time;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * The client time
 *
 * @author Brett Downey
 */
public class ClientTime {

  /**
   * This time zone which is gotten off of the system,
   * if the system's time zone is incorrect then this timezone
   * may be inaccurate.
   */
  private static final ZoneId THIS_TIME_ZONE = ZoneId.systemDefault();

  /**
   * The current time zone of Winnipeg.
   */
  private static final ZoneId WINNIPEG_TIME_ZONE = ZoneId.of("America/Winnipeg");

  /**
   * The current time zone of Saskatoon.
   */
  private static final ZoneId SASKATOON_TIME_ZONE = ZoneId.of("America/Regina");
  /**
   * The instance of the LocalDateTime from when the constructor was called.
   */
  private final LocalDateTime INSTANCE;
  /**
   * A String which represents the formatted version of this LocalDateTime.
   */
  private final String INSTANCE_STRING;
  /**
   * A ZoneId which represents this ClientTime's time zone.
   */
  private final ZoneId TIME_ZONE;

  /**
   * A constructor for the ClientTime whenever this is called. This time does not change and are
   * final variables.
   */
  public ClientTime(TimeFormat format, ZoneId timeZone) {
    this.INSTANCE = LocalDateTime.now(timeZone);
    this.INSTANCE_STRING = ClientTime.string(this, format);
    this.TIME_ZONE = timeZone;
  }

  public ClientTime(TimeFormat format) {
    this.INSTANCE = LocalDateTime.now(ZoneId.systemDefault());
    this.INSTANCE_STRING = ClientTime.string(this, format);
    this.TIME_ZONE = ZoneId.systemDefault();
  }
  public ClientTime(LocalDateTime instance){
    this.INSTANCE = instance;
    this.INSTANCE_STRING = ClientTime.string(this, TimeFormat.FORMATTER_12_HOUR);
    this.TIME_ZONE = getWinnipegTimeZone(); //todo saskatoon
  }

  /**
   * Gets the current Winnipeg timezone.
   *
   * @return ZoneId Returns the Winnipeg timezone.
   */
  public static ZoneId getWinnipegTimeZone() {
    return WINNIPEG_TIME_ZONE;
  }

  /**
   * Gets the current Winnipeg timezone.
   *
   * @return ZoneId Returns the Saskatoon timezone.
   */
  public static ZoneId getSaskatoonTimeZone() {
    return SASKATOON_TIME_ZONE;
  }

  /**
   * Gets the current time in Saskatoon
   *
   * @return Returns the Saskatoon time.
   */
  public static LocalDateTime getSaskatoonTime() {
    return LocalDateTime.now(SASKATOON_TIME_ZONE);
  }

  public static LocalDate getWinnipegLocalDate() {
    return LocalDate.now(WINNIPEG_TIME_ZONE);
  }

  /**
   * Gets the current time in Winnipeg
   *
   * @return Returns the Winnipeg time.
   */
  public static LocalDateTime getWinnipegTime() {
    return LocalDateTime.now(WINNIPEG_TIME_ZONE);
  }

  /**
   * Returns the current saskatoon time in a formatted string.
   *
   * @return A formatted String
   */
  public static String string(TimeFormat format) {
    return format.getFormat().format(getSaskatoonTime());
  }

  /**
   * Returns the client time in a formatted string.
   *
   * @return A formatted String
   */
  private static String string(ClientTime time, TimeFormat format) {
    return format.getFormat().format(time.INSTANCE);
  }

  /**
   * Returns the time this instance was made.
   *
   * @return The time
   */
  public LocalDateTime getThisTime() {
    return INSTANCE;
  }

  /**
   * Returns the time this instance was made in a neatly formatted string.
   *
   * @return The String representing the ClientTime when this ClientTime was created.
   */
  @Override
  public String toString() {
    return INSTANCE_STRING;
  }

  /**
   * Returns the time this instance was made in a neatly formatted String
   * in the given format.
   *
   * @param format The given format
   * @return The neatly formatted String
   */
  public String toString(TimeFormat format) {
    return ClientTime.string(this, format);
  }

  /**
   * Returns this instance time zone.
   *
   * @return {@link ZoneId} the time zone.
   */
  public ZoneId getTimeZone() {
    return TIME_ZONE;
  }

  /**
   * Returns the current client time.
   */
  public static LocalDateTime getCurrentLocalTime() {
    return LocalDateTime.now();
  }

  /**
   * Returns the amount of time between this ClientTime and a given ClientTime.
   *
   * @param val The ChronoUnit which represents the time unit.
   * @param time The time to compare it to.
   * @return The time between this time and the given {@code time}
   */
  public long getTimeBetween(ClientTime time, ChronoUnit val) {
    return Math.abs(val.between(INSTANCE, time.getThisTime()));
  }

  /**
   * Returns the amount of time between this and the current local client time.
   *
   * @param val The ChronoUnit which represents the time unit.
   * @return The time between this time and the local client time.
   */
  public long timeFromNow(ChronoUnit val) {
    return Math.abs(val.between(INSTANCE, ClientTime.getWinnipegTime()));
  }

  /**
   * Creates the serializer for ClientTime objects
   * @return the {@link JsonSerializer} for ClientTime objects
   */
  public static JsonSerializer<ClientTime> getJsonSerializer(){
    return new JsonSerializer<ClientTime>() {

    };
  }

  /**
   * Creates the deserializer for ClientTime objects
   * @return the {@link JsonDeserializer} for ClientTime objects
   */
  public static JsonDeserializer<ClientTime> getJsonDeserializer(){
    return new JsonDeserializer<ClientTime>() {

    };
  }
}