package main.java.lucia.client.protocol.message;

/**
 * An interface which is utilized by {@link Message}
 * in order to provide a uniform execution of different
 * Element return types via the JSON serialization and
 * deserialization methods provided within this interface.
 *
 * @param <M> The element which the JsonMap is mapped to.
 *
 * @author Brett Downey
 */
public interface JsonMap<M> {

    /**
     * Serialize this message into JSON-format.
     *
     * @return The JSON output.
     */
    String serialize();

    /**
     * Deserialize a given JSON String into it's origin object.
     *
     * @return An Element which is the object's input.
     */
    M deserialize();
}
