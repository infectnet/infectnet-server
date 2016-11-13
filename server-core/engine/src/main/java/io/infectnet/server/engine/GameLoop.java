package io.infectnet.server.engine;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import io.infectnet.server.engine.entity.wrapper.Action;
import io.infectnet.server.engine.script.Request;
import io.infectnet.server.engine.script.code.Code;
import io.infectnet.server.engine.script.code.CodeRepository;
import io.infectnet.server.engine.script.execution.ScriptExecutor;
import io.infectnet.server.engine.util.ListenableQueue;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameLoop {
  private static final long NO_DELAY = 0L;

  private final ListenableQueue<Action> actionQueue;

  private final ListenableQueue<Request> requestQueue;

  private final CodeRepository codeRepository;

  private final ScriptExecutor scriptExecutor;

  private ScheduledExecutorService gameLoopExecutorService;

  private Duration desiredTickDuration;

  private AtomicBoolean isLoopRunning;

  public GameLoop(ListenableQueue<Action> actionQueue, ListenableQueue<Request> requestQueue,
                  CodeRepository codeRepository, ScriptExecutor scriptExecutor) {
    this.actionQueue = actionQueue;

    this.requestQueue = requestQueue;

    this.codeRepository = codeRepository;

    this.scriptExecutor = scriptExecutor;
  }

  public void setDesiredTickDuration(Duration desiredTickDuration) {
    Duration duration = Objects.requireNonNull(desiredTickDuration);

    if (duration.isNegative()) {
      throw new IllegalArgumentException("The duration must not be negative!");
    }

    if (!gameLoopExecutorService.isShutdown()) {

    }

    this.desiredTickDuration = duration;
  }

  public void start() {
    if (isLoopRunning.get()) {
      return;
    }

    gameLoopExecutorService = Executors.newSingleThreadScheduledExecutor();

    isLoopRunning.set(true);

    gameLoopExecutorService.schedule(this::loop, NO_DELAY, MILLISECONDS);
  }

  public void stop() {
    if (!isLoopRunning.get()) {
      return;
    }

    gameLoopExecutorService.shutdown();

    isLoopRunning.set(false);
  }

  public boolean stopAndWait() {
    if (!isLoopRunning.get()) {
      return true;
    }

    isLoopRunning.set(false);

    /*
     * New tasks will not be accepted therefore we have to wait for 0 to 1 game ticks to complete
     * at maximum.
     */
    gameLoopExecutorService.shutdown();

    try {
      /*
       * Wait three game ticks. This
       */
      if (!gameLoopExecutorService.awaitTermination(3 * desiredTickDurationMillis(), MILLISECONDS)) {
        /*
         * Force shutdown by cancelling the currently executed task.
         */
        gameLoopExecutorService.shutdownNow();

        /*
         * Wait a game tick again, just to be sure.
         */
        return gameLoopExecutorService.awaitTermination(desiredTickDurationMillis(), MILLISECONDS);
      }

      return true;
    } catch (InterruptedException e) {
      /*
       *  Re-cancel, if interrupted while waiting.
       */
      gameLoopExecutorService.shutdownNow();

      /*
       * Preserve interrupt status, so callers can inspect it.
       */
      Thread.currentThread().interrupt();

      return false;
    }
  }

  public boolean isRunning() {
    return isLoopRunning.get();
  }

  private long desiredTickDurationMillis() {
    return desiredTickDuration.toMillis();
  }

  private void loop() {
    Instant startTime = Instant.now();

    /*
     * #1 Run Scripts
     *
     * Execute the DSL code written by the players. The action queue will be filled with Action
     * instances created by the executed Scripts.
     */
    for (Code code : codeRepository.getAllCodes()) {
      if (code.isRunnable()) {
        scriptExecutor.execute(code.getScript().get(), code.getOwner());
      }
    }

    /*
     * #2 Process Actions
     *
     * The Actions will be dispatched towards the various systems via the registered listeners. The
     * systems will create Requests and place them in the request queue.
     * Actions and Requests are separated because Actions depend on the previous state of the World
     * as well as they may operate on more than one Entity.
     */
    actionQueue.processAll();

    /*
     * #3 Process Requests
     *
     * Requests crafted from Actions have no dependencies on the previous state of the World or the
     * Entity System. The request queue dispatches the requests to the various systems which will
     * modify the World and the Entities.
     */
    requestQueue.processAll();

    /*
     * #4 Send results
     *
     * TODO
     */

    /*
     * #5 Reschedule Loop
     *
     * Once we're done with all our processing job, we will reschedule ourselves with respect to the
     * desired tick duration. If we've failed to deliver, the rescheduling will mean an instant
     * execution. This is exactly an infinite loop.
     */
    rescheduleLoop(startTime);
  }

  private void rescheduleLoop(Instant startTime) {
    Instant endTime = Instant.now();

    Duration actualTickDuration = Duration.between(startTime, endTime);

    Duration waitTime = desiredTickDuration.minus(actualTickDuration);

    if (waitTime.isNegative()) {
      gameLoopExecutorService.schedule(this::loop, NO_DELAY, MILLISECONDS);
    } else {
      gameLoopExecutorService.schedule(this::loop, waitTime.toMillis(), MILLISECONDS);
    }
  }
}
