package main.java.lucia.client.content.files;

import com.google.common.base.Charsets;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.java.lucia.Client;
import main.java.lucia.client.content.order.pricing.DiscountOthersCalculator;
import main.java.lucia.client.content.files.json.StoreInformationDefinition;
import main.java.lucia.consts.DirectoryConstants;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.menu.legacy.premade.PremadeFoodList;
import main.java.lucia.client.content.menu.legacy.toppings.Toppings;

/**
 * An enumerated type representing the different file types the server supports, with a fast way to
 * get the given FileType.
 *
 * @author Brett Downey
 */
public enum FileType {

  /**
   * The FileType for the premade food save files.
   */
  PREMADE_FOOD_LIST(DirectoryConstants.PREMADE_FOODS_DIRECTORY, new TypeToken<PremadeFoodList>(){}.getType()),

  /**
   * The FileType for the toppings save files.
   */
  TOPPINGS_LIST(DirectoryConstants.TOPPINGS_DIRECTORY, new TypeToken<Toppings>(){}.getType()),

  /**
   * The FileType for store hours information.
   */
  HOURS_INFORMATION(DirectoryConstants.HOURS_DIRECTORY, new TypeToken<ArrayList<StoreInformationDefinition>>() {}.getType()),

  /**
   * The discount definitions file type
   */
  DISCOUNT_DEFINITIONS(DirectoryConstants.DISCOUNT_DIRECTORY, new TypeToken<DiscountOthersCalculator>(){}.getType());

  /**
   * The file path to the directory for this FileType.
   */
  private String path;

  /**
   * The Class source file, used for JSON file types.
   */
  private Type source;

  /**
   * Constructor for a FileType.
   */
  FileType(String path, Type source) {
    this.path = path;
    this.source = source;
  }

  /**
   * Gets the file path for this FileType
   *
   * @return The the file path in String form.
   */
  public String getPath() {
    return path;
  }

  /**
   * Gets the source class for this FileType, used for JSON file types. Null is returned if the
   * given FileType is not of JSON type.
   */
  public Type getSource() {
    return source;
  }

  /**
   * Returns the file by the given path
   *
   * @return The File.
   */
  public File getFileByType() {
    return new File(this.getPath());
  }

  /**
   * Returns an array containing the lines of the file.
   *
   * @return An array containing the lines of the given file
   */
  public String[] pieces() {
    String[] toReturn = null;
    try {
      StringBuilder appender = new StringBuilder();
      Files.lines(Paths.get(this.getPath()), Charsets.UTF_8).forEach(appender::append);
      toReturn = appender.toString().split("\n");
    } catch (IOException e) {
      Client.logger.error("An error occurred while loading: " + this.name(), e);
    }
    return toReturn;
  }

  /**
   * Returns all instances of Key from this FileType, then rewrites the file when the operation is
   * complete.
   */
  public void deleteFromFile(String key) {
    AsynchronousTaskService.process(() -> {
      if (this.getSource() == null) {
        deleteFromText(key);
      }
    });
  }

  /**
   * Returns all instances of Key from this FileType if it is a text file.
   */
  private void deleteFromText(String key) {
    AsynchronousTaskService.process(() -> {
      synchronized (this) {
        try {
          final StringBuilder appender = new StringBuilder();
          Files.lines(Paths.get(this.getPath())).forEach(appender::append);
          Files.lines(Paths.get(this.getPath())).forEach(line -> {
            if(!line.equals(key)) {
              appender.append(line);
            }
          });

          Files.write(Paths.get(this.getPath()), appender.toString().getBytes(Charsets.UTF_8));
        } catch (IOException e) {
          Client.logger
              .error("An error occurred while deleting information from: " + this.name(), e);
        }
      }
    });
  }

  /**
   * Writes the given data to this FileType.
   */
  public void addToFile(String data) {
    AsynchronousTaskService.process(() -> {
      try {
        Stream<String> lines = Files.lines(Paths.get(this.getPath()));
        List<String> collect = lines.collect(Collectors.toList());
        collect.add(data);
        Files.write(Paths.get(this.getPath()), collect);
      } catch (IOException e) {
        Client.logger.error("An error has occurred while adding information to a file!", e);
      }
    });
  }
}