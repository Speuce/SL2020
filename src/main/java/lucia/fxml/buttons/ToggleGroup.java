package main.java.lucia.fxml.buttons;

import java.util.HashMap;
import javafx.scene.Node;

/**
 * A toggle which represents a selection of
 * nodes and different toggle attributes that
 * can be applied to said nodes
 *
 * @author Brett Downey
 */
public class ToggleGroup {

  private HashMap<Node, ToggleOptions> nodes;

  public HashMap<Node, ToggleOptions> getNodes() {
    return nodes;
  }

  public void registerNode(Node node, ToggleOptions options) {
    nodes.put(node, options);
  }

  public void reregisteNode(Node node) {
    nodes.remove(node);
  }
}