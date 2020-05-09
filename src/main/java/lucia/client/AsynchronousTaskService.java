package main.java.lucia.client;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import main.java.lucia.Client;
import main.java.lucia.client.content.utils.TaskComplete;
import main.java.lucia.consts.ClientConstants;

import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/**
 * A class used for handling methods that can be done asynchronously,
 * which improves the performance of the server.
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public class AsynchronousTaskService {

    /**
     * The amount of threads (therefore tasks that can
     * be executed asynchronously using this service)
     * allotted to this service.
     */
    private static final int POOL_SIZE = ClientConstants.ASYNCHRONOUS_TASK_POOL;

    /**
     * The ScheduleExecutorService which handles the tasks.
     */
    private static ScheduledExecutorService SERVICE = service();

    /**
     * The flag that determines if this service has been shutdown.
     */
    private static boolean shutdown;

    /**
     * Awaits the completion of the execution of the tasks by the executor. This
     * will block the thread for an infinite amount of time or until the tasks
     * are completed. Once the tasks complete, this service will be
     * shutdown and cannot be used again.
     *
     * @return {@code true} if the tasks complete and this loader is shutdown
     * normally, {@code false} otherwise.
     * @throws IllegalStateException if this background loader has been shutdown.
     */
    public static void safeShutdown() {
        Preconditions.checkState(!shutdown, "This background loader has been shutdown!");
        try {
            SERVICE.shutdown();
            SERVICE.awaitTermination(1, TimeUnit.HOURS);
            Client.logger.info("The Asynchronous Task Service has successfully shutdown!");
        } catch (InterruptedException e) {
            Client.logger.error("The Asynchronous Task Service shutdown process was interrupted", e);
        }
        shutdown = true;
    }

    /**
     * Gets the opposite status of the shutdown flag.
     *
     * @return {@code true} if shutdown, {@code false} otherwise.
     */
    public static boolean isActive() {
        return !shutdown;
    }

    /**
     * Takes a Runnable {@code t} and submits it to the
     * executor service to be handled when available.
     * This should be used to execute functions that do not
     * mainly direct the main thread of the server.
     * An example of this is writing something to a file.
     *
     * @param t The code to execute
     */
    public static void process(Runnable t) {
        Preconditions.checkState(isActive(), "The Asynchronous Task Service has been shutdown! Failed to execute runnable.");
        try {
            SERVICE.execute(t);
        } catch (Exception e) {
            Client.logger.error("An error has occurred while executing an asynchronous task!", e);
        }
    }

    /**
     * Takes a Runnable {@code t} and submits it to the
     * executor service to be handled when available.
     * This should be used to execute functions that do not
     * mainly direct the main thread of the server. A delay is
     * given before the service should execute.
     *
     * @param t       The code to execute
     * @param seconds The delay in seconds
     */
    public static void scheduleProcess(Runnable t, long seconds) {
        try {
            SERVICE.schedule(t, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            Client.logger.error("An error has occurred while executing an asynchronous task!", e);
        }
    }

    /**
     * Takes a Runnable {@code t} and submits it to the
     * executor service to be handled when available.
     * This should be used to execute functions that do not
     * mainly direct the main thread of the server. A delay is
     * given before the service should execute.
     *
     * @param t       The code to execute
     * @param milliseconds The delay in milliseconds
     */
    public static void scheduleProcessMils(Runnable t, long milliseconds) {
        try {
            SERVICE.schedule(t, milliseconds, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Client.logger.error("An error has occurred while executing an asynchronous task!", e);
        }
    }

    /**
     * Takes a Runnable {@code t} and submits it to the
     * executor service to be handled when available.
     * This should be used to execute functions that do not
     * mainly direct the main thread of the server.
     * This code will set the runnable to run at a fixed given
     * delay of {@code seconds}
     *
     * @param t       The code to execute
     * @param seconds The delay in seconds
     */
    public static void fixedDelaySchedule(Runnable t, long seconds) {
        try {
            SERVICE.schedule(t, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            Client.logger.error("An error has occurred while executing an asynchronous task!", e);
        }
    }

    /**
     * Takes a Runnable {@code t} and submits it to the
     * executor service to be handled when available.
     * This should be used to execute functions that do not
     * mainly direct the main thread of the server.
     * This code will set the runnable to run at a fixed given
     * delay of {@code seconds}
     * The code's delay depends on the start on when the execution
     * is finished, then the delay begins again. See
     * fixedRateScheduleRepeating for the rate to be the same each time.
     *
     * @param t       The code to execute
     * @param seconds The delay in seconds
     */
    public static void fixedDelayScheduleRepeating(Runnable t, long seconds, long initialDelay) {
        try {
            SERVICE.scheduleWithFixedDelay(t, initialDelay, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            Client.logger.error("An error has occurred while executing an asynchronous task!", e);
        }
    }

    /**
     * Takes a Runnable {@code t} and submits it to the
     * executor service to be handled when available.
     * This should be used to execute functions that do not
     * mainly direct the main thread of the server.
     * This code will set the runnable to run at a fixed given
     * rate of {@code seconds}
     *
     * @param t       The code to execute
     * @param seconds The delay in seconds
     */
    public static void fixedRateScheduleRepeating(Runnable t, long seconds, long initialDelay) {
        try {
            SERVICE.scheduleAtFixedRate(t, initialDelay, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            Client.logger.error("An error has occurred while executing an asynchronous task!", e);
        }
    }

    /**
     * Takes a Runnable {@code t} and submits it to the
     * executor service to be handled when available.
     * This should be used to execute functions that do not
     * mainly direct the main thread of the server.
     * This code will set the runnable to run at a fixed given
     * rate of {@code seconds}
     *
     * @param t       The code to execute
     * @param mseconds The delay in ms
     *
     * @return a future representing the running task
     */
    public static Future<?> scheduleRepeating(Runnable t, long mseconds) {
        try {
            return SERVICE.scheduleAtFixedRate(t, 0L, mseconds, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            //Client.logger.error("An error has occurred while executing an asynchronous task!", e);
            //ignored. Likely interruption exception
        }
        return null;
    }


    /**
     * for use wth tasks that throw an error to exit. Whoops.
     * Parameters are in miliseconds.
     * Made for matt. BY matt.
     */
    public static void scheduleEndingTask(Runnable t, long ms, long initialDelay) {
        try {
            SERVICE.scheduleAtFixedRate(t, initialDelay, ms, TimeUnit.MILLISECONDS);
        } catch (TaskComplete e) {
            //ignored
            //Client.logger.error("An error has occurred while executing an asynchronous task!", e);
            return;
        }catch(Exception e){
            Client.logger.error("An error has occurred while executing an asynchronous task!", e);
        }
    }

    /**
     * Creates the service used for asynchronous tasks.
     *
     * @return {@code ScheduledExecutorService} the newly created service.
     */
    private static ScheduledExecutorService service() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
            AsynchronousTaskService.POOL_SIZE);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("AsynchronousTaskService").build());
        executor.setKeepAliveTime(45, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
        executor.prestartCoreThread();
        return Executors.unconfigurableScheduledExecutorService(executor);
    }
}