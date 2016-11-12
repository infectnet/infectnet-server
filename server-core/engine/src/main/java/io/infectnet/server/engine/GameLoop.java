package io.infectnet.server.engine;

import io.infectnet.server.engine.entity.wrapper.Action;
import io.infectnet.server.engine.script.Request;
import io.infectnet.server.engine.script.code.Code;
import io.infectnet.server.engine.script.code.CodeRepository;
import io.infectnet.server.engine.script.execution.ScriptExecutor;
import io.infectnet.server.engine.util.ListenableQueue;

public class GameLoop {
  private final ListenableQueue<Action> actionQueue;

  private final ListenableQueue<Request> requestQueue;

  private final CodeRepository codeRepository;

  private final ScriptExecutor scriptExecutor;

  public GameLoop(ListenableQueue<Action> actionQueue, ListenableQueue<Request> requestQueue,
                  CodeRepository codeRepository, ScriptExecutor scriptExecutor) {
    this.actionQueue = actionQueue;

    this.requestQueue = requestQueue;

    this.codeRepository = codeRepository;

    this.scriptExecutor = scriptExecutor;
  }

  public void loop() {
    /*
     * #1 Run Scripts
     *
     * The action queue will be filled with Action instances made by the Scripts.
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
  }
}
