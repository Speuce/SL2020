package main.java.lucia.client.content.menu.legacy;

/**
 * A data class to store the name of an item along with it's price
 *
 * @author Brett Downey
 */
public class NamePrice {

  private String name;

  private long price;

  public NamePrice(String name, long price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public long getPrice() {
    return price;
  }
}
