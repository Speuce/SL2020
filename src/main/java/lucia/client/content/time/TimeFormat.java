package main.java.lucia.client.content.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

/**
 * An enumerated class representing a format for different TimeFormat functions.
 *
 * @author Brett Downey
 */
public enum TimeFormat {

  /**
   * The formatter used to have neatly formatted time strings in 24 hour time.
   */
  FORMATTER_24_HOUR(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"), "yyyy/MM/dd HH:mm:ss"),

  /**cr
   * The formatter used to have neatly formatted time strings in 12 hour time.
   */
  FORMATTER_12_HOUR(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a"), "yyyy/MM/dd HH:mm:ss"),

  FORMATTER_ISO_STANDARD(DateTimeFormatter.ISO_DATE_TIME, "yyyy-MM-dd'T'HH:mm:ss");

  /**
   * The formatter for this enumerated type.
   */
  private final DateTimeFormatter FORMAT;

  /**
   * The pattern used to create the {@link DateTimeFormatter}
   */
  public final String PATTERN;

  /**
   * The TimeFormat constructor.
   */
  TimeFormat(DateTimeFormatter format, String pattern) {
    this.FORMAT = format;
    this.PATTERN = pattern;
  }

  /**
   * Gets a formatted String between two given time Strings, note that this does not
   * work if the given Strings are not within 24 hour time format.
   *
   * @param start The start time
   * @param end The end time
   * @param formatType The format time
   * @return The formatted String
   * @throws ParseException If the given String was unable to be parsed as a proper time
   */
  public static String getTimeBetween(String start, String end, TimeFormat formatType)
          throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat(formatType.PATTERN);

    Date first = format.parse(start);
    Date second = format.parse(end);
    DateTime dateTimeFirst = new DateTime(first);
    DateTime dateTimeSecond = new DateTime(second);

    StringBuilder builder = new StringBuilder();
    appendIfApplicable(builder, Days.daysBetween(dateTimeFirst, dateTimeSecond).getDays(), "days");
    appendIfApplicable(builder, Hours.hoursBetween(dateTimeFirst, dateTimeSecond).getHours(), "hours");
    appendIfApplicable(builder, Minutes.minutesBetween(dateTimeFirst, dateTimeSecond).getMinutes(), "minutes");
    appendIfApplicable(builder, Seconds.secondsBetween(dateTimeFirst, dateTimeSecond).getSeconds(), "seconds");

    return builder.toString();
  }

  /**
   * Returns a formatted string between two given times within
   * milliseconds
   *
   * @param start The start time
   * @param end The end time
   * @return The formatted String
   */
  public static String getFormattedStringMillis(long start, long end) {
    return DurationFormatUtils.formatDuration(end - start, "HH:mm:ss");
  }

  /**
   * Appends the item to the given StringBuilder if the long value to append is greater than zero.
   * If the given value is greater than zero, then no changes will be made to any objects.
   *
   * @param builder The {@link StringBuilder} to append on to
   * @param toAppend The long value to append to
   * @param type The name of the long value
   */
  private static void appendIfApplicable(StringBuilder builder, long toAppend, String type) {
    if (toAppend > 0) {
      builder.append(toAppend);
      builder.append(" ");
      builder.append(singlePlural(toAppend, type));
    }
  }

  /**
   * Returns the word if the word should be singular or plural.
   *
   * @param count The count of the value.
   * @param word The word which is in plural form.
   * @return The word if it should be plural or singular.
   */
  private static String singlePlural(long count, String word) {
    if (count == 1) {
      return word.substring(0, word.length() - 1);
    } else {
      return word;
    }
  }

  /**
   * Returns the formatter;
   *
   * @return The {@link DateTimeFormatter}
   */
  public DateTimeFormatter getFormat() {
    return FORMAT;
  }
}
