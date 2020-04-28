package main.java.lucia.fxml.buttons;

import java.util.HashMap;

/**
 * A class representing the different
 * toggle options a button can have
 *
 * @author Brett Downey
 */
public class ToggleOptions {

  private HashMap<String, Boolean> toggleOptions;

  public HashMap<String, Boolean> getToggleOptions() {
    return toggleOptions;
  }

  public void removeToggleOption(String option) {
    toggleOptions.remove(option);
  }

  public void addToggleOption(String option, boolean state) {
    toggleOptions.put(option, state);
  }
}