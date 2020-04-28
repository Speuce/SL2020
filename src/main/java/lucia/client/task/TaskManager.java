package main.java.lucia.client.task;

import java.util.LinkedList;
import java.util.Queue;
import main.java.lucia.Client;
import main.java.lucia.client.AsynchronousTaskService;

/**
 * The engine Task Manager
 *
 * @editor Brett Downey
 */
public final class TaskManager {

  /**
   * The collection of {@link Task}s that are pending
   */
  private final static Queue<Task> pendingTasks = new LinkedList<>();

  /**
   * A private constructor since this class cannot be instantiated.
   */
  private TaskManager() {
  }

  /**
   * Method which processes the pending tasks.
   */
  public static void process() {
    long systemTime = System.currentTimeMillis();
    boolean success;
    try {
      Task process;
      for(int pos = 0; pos < pendingTasks.size(); pos++) {
        process = pendingTasks.poll();
        if (process.isAsynchronous()) {
          success = process.executeIfReadyAsync(systemTime);
        } else {
          success = process.executeIfReady(systemTime);
        }
        if(!success) {
          pendingTasks.add(process);
        }
      }

    } catch (Throwable e) {
      Client.logger.error("An error has occurred while executing a task!", e);
    }
  }

  /**
   * Adds {@code task} to the {@code pendingTasks} list
   *
   * @param task The task that will be added to {@code pendingTasks}
   */
  public static void submit(Task task) {
    if (task.isAsynchronous() && task.isImmediate()) {
      AsynchronousTaskService.process(task);
    } else if (task.isImmediate()) {
      task.run();
    } else {
      pendingTasks.add(task);
    }
  }

  /**
   * Removes all pending tasks from {@code pendingTasks}
   *
   */
  public static void cancelTasks() {
      pendingTasks.clear();
  }

  /**
   * Gets the amount of tasks within the TaskManager
   *
   * @return {@code int} value representing how many tasks there are within the TaskManager
   */
  public static int getTaskAmount() {
    return pendingTasks.size();
  }
}
