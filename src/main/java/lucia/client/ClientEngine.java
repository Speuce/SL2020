package main.java.lucia.client;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import main.java.lucia.Client;
import main.java.lucia.consts.ClientConstants;
import main.java.lucia.consts.ErrorCodeConstants;

import java.util.concurrent.*;

/**
 * The engine which processes the server
 *
 ** @editor Brett Downey
 */
public class ClientEngine implements Runnable {

    /**
     * The {@link ScheduledExecutorService} which will be used for
     * this engine.
     */
    private final ScheduledExecutorService EXECUTOR_SERVICE = service();

    /**
     * The close future associated with this ClientEngine to shut down
     * the engine cycle process.
     */
    private ScheduledFuture<?> closeFuture;

    /**
     * The flag that determines if this service has been shutdown.
     */
    private static boolean shutdown;

    /**
     * Initializes this {@link ClientEngine}.
     */
    ClientEngine init() {
        closeFuture = EXECUTOR_SERVICE
            .scheduleAtFixedRate(this, 0, ClientConstants.CLIENT_ENGINE_PROCESSING_CYCLE_RATE,
                TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * The engine run cycle which is called every {@code ServerConstants.SERVER_ENGINE_PROCESSING_CYCLE_RATE}
     */
    @Override
    public void run() {
        try {
            Engine.process();
        } catch (Throwable e) {
            Client.logger.error("An error occurred while processing the engine!", e);
            Client.shutdown(ErrorCodeConstants.ENGINE_FAILURE.getCode());
        }
    }

    /**
     * Creates the service used for asynchronous tasks.
     *
     * @return {@code ScheduledExecutorService} the newly created service.
     */
    private static ScheduledExecutorService service() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.setRemoveOnCancelPolicy(true);
        executor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("EngineThread").build());
        return Executors.unconfigurableScheduledExecutorService(executor);
    }

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
    void safeShutdown() {
        Preconditions.checkState(!shutdown, "The engine has been shutdown previously!");
        try {
            closeFuture.cancel(false);
            EXECUTOR_SERVICE.shutdown();
            EXECUTOR_SERVICE.awaitTermination(1, TimeUnit.HOURS);
            Engine.unload();
            Client.logger.info("The processing engine has successfully shutdown.");
        } catch (InterruptedException e) {
            Client.logger.error("The engine shutdown has failed!", e);
        }
        shutdown = true;
    }
}