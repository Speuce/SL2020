package main.java.lucia.client.task;


import main.java.lucia.client.AsynchronousTaskService;

/**
 * Represents a periodic task that can be scheduled with a {@link TaskManager}.
 *
 * @author Brett Downey
 */
public abstract class Task implements Runnable {


  /**
   * A flag which indicates if this task should be executed once immediately.
   */
  private final boolean immediate;

  /**
   * A flag which indicates if this task should be executed asynchronously.
   */
  private final boolean asynchronous;

  /**
   * The delay before the task should be executed.
   */
  private int delay;

  /**
   * A flag which indicates if this task is still running.
   */
  private boolean running = false;

  private long creationTime;

  /**
   * Creates a new task with the specified delay.
   *
   * @param delay The delay to wait in milliseconds
   * @throws IllegalArgumentException if the {@code delay} is not positive.
   */
  public Task(int delay, boolean immediate, boolean asynchronous) {
    this.delay = delay;
    this.immediate = immediate;
    this.asynchronous = asynchronous;
    this.creationTime = System.currentTimeMillis();
  }

  public void setEventRunning(boolean running) {
    this.running = running;
  }

  /**
   * Checks if this task is an immediate task.
   *
   * @return {@code true} if so, {@code false} if not.
   */
  public boolean isImmediate() {
    return immediate;
  }

  /**
   * Checks if this task is an asynchronous task.
   *
   * @return {@code true} if so, {@code false} if not.
   */
  public boolean isAsynchronous() {
    return asynchronous;
  }

  /**
   * Checks if the task is running.
   *
   * @return {@code true} if so, {@code false} if not.
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Checks if the task is stopped.
   *
   * @return {@code true} if so, {@code false} if not.
   */
  public boolean isStopped() {
    return !running;
  }

  public boolean executeIfReady(long systemTime) {
    if(systemTime - creationTime >= delay) {
      run();
      return true;
    } else return false;
  }

  public boolean executeIfReadyAsync(long systemTime) {
    if(systemTime - creationTime >= delay) {
      AsynchronousTaskService.process(this);
      return true;
    } else return false;
  }

  /**
   * Performs this task's action.
   */
   public abstract void run();

  /**
   * Gets the delay of this task.
   *
   * @return The delay of the task
   */
  public int getDelay() {
    return this.delay;
  }

  /**
   * Stops this task.
   */
  public void stop() {
    running = false;
  }
}