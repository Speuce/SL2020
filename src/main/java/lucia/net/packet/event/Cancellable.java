package main.java.lucia.net.packet.event;

/**
 * Tracks a boolean, representing if a given event is cancelled or not.
 * @author Matthew Kwiatkowski
 */
public class Cancellable {

    /**
     * Flag indicating whether the event is cancelled or not.
     */
    private boolean cancelled;

    public Cancellable(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Flag indicating whether the event is cancelled or not.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Flag indicating whether the event is cancelled or not.
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
