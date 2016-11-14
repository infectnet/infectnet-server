package io.infectnet.server.engine.content.print;

import io.infectnet.server.engine.core.entity.wrapper.Action;
import io.infectnet.server.engine.core.script.Request;
import io.infectnet.server.engine.core.system.ProcessorSystem;
import io.infectnet.server.engine.core.util.ListenableQueue;

public class PrintSystem implements ProcessorSystem {
  private final ListenableQueue<Request> requestQueue;

  public PrintSystem(ListenableQueue<Request> requestQueue) {
    this.requestQueue = requestQueue;
  }

  @Override
  public void registerActionListeners(ListenableQueue<Action> actionQueue) {
    actionQueue.addListener(PrintAction.class, this::consumePrintAction);
  }

  @Override
  public void registerRequestListeners(ListenableQueue<Request> requestQueue) {
    requestQueue.addListener(PrintRequest.class, this::consumePrintRequest);
  }

  private void consumePrintAction(Action action) {
    PrintAction printAction = (PrintAction) action;

    requestQueue.add(new PrintRequest(printAction.getSource(), action, printAction.getMessage()));
  }

  private void consumePrintRequest(Request request) {
    PrintRequest printRequest = (PrintRequest) request;

    System.err.println(printRequest.getMessage());
  }
}
