package main.java.lucia.client.content.files.json.loader;

import java.io.IOException;
import java.util.HashMap;
import main.java.lucia.Client;
import main.java.lucia.client.content.files.FileType;

/**
 * The file which loads specific FileTypes for JSON files. Any FileType that is attempted to be
 * loaded here which does not have a source is automatically rejected.
 *
 * @author Brett Downey
 */
public abstract class JsonHandler implements Runnable {

  /**
   * A boolean that indicates if the JsonHandler
   * that loaded a file needs to rewrite the file to the
   * size disk since a new modification has occured to
   * it.
   */
  protected boolean modified;

  /**
   * Sets the {@code modified} indicator
   * field to be true.
   */
  public void setModified() {
    modified = true;
  }

  /**
   * The initializer for the JsonHandler.
   */
  protected abstract void load();

  /**
   * Applies the file to the save list, which is periodically saved to ensure no information is
   * lost.
   */
  private void applySaveList() {
    ALL_LOADED_FILES.put(type(), this);
  }

  /**
   * The file type for the JsonHandler.
   */
  protected abstract FileType type();

  /**
   * Saves the given file to the disk.
   */
  protected abstract void save() throws IOException;

  /**
   * The list of all loaders that have been used
   */
  private static final HashMap<FileType, JsonHandler> ALL_LOADED_FILES = new HashMap<>();

  /**
   * The runner for the specific type of Json loader.
   */
  public void run() {
    try {
      long start = System.currentTimeMillis();
      load();
      applySaveList();
      Client.logger.info(
          "Loaded definitions for: " + type().name() + " in " + (System.currentTimeMillis() - start)
              + " ms");
    } catch (Exception e) {
      Client.logger.error("Error loading definitions for: " + type().name(), e);
    }
  }

  /**
   * Saves all previously loaded files
   */
  public static void saveAllLoadedFiles() {
    ALL_LOADED_FILES.forEach((type, file) -> saveFile(file));
  }

  /**
   * Saves a specific file
   *
   * @param file The file to save
   */
  public static void saveSpecificFile(FileType file) {
    saveFile(ALL_LOADED_FILES.get(file));
  }

  /**
   * Sets the given file to the modified state, and then on the next
   * mass save session, or when the client shuts down, the file will therefore
   * be saved automatically to the hard disk.
   *
   * @param file The file to save
   */
  public static void setFileModified(FileType file) {
    ALL_LOADED_FILES.get(file).setModified();
  }

  /**
   * Saves the file
   *
   * @param file The file being saved
   */
  private static void saveFile(JsonHandler file) {
      try {
        long start = System.currentTimeMillis();
        file.save();
        Client.logger.info(
            "Saved definitions for: " + file.type().name() + " in " + (System.currentTimeMillis()
                - start)
                + " ms");
      } catch (IOException e) {
        Client.logger.error("An error occurred while saving a file!", e);
      }
  }

  /**
   * Saves all files, then shuts down the JsonHandler service,
   * so files will not be attempted to be saved again.
   */
  public static void saveAndShutdown() {
    saveAllLoadedFiles();
    ALL_LOADED_FILES.clear();
  }
}