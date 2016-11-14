package io.infectnet.server.engine.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * A queue that allows listeners to be registered. {@code ListenableQueue} is something like a
 * job queue in the sense that it does not allow elements to be removed. Instead elements get
 * processed by the registered listeners. Listeners can be registered in conjunction with a
 * {@link Class} they listen to. Whenever an element gets processed, the listener registered
 * with the appropriate {@code Class} will be called to process the element.
 * <p>
 *   <b>Note</b> that listeners are allowed to add elements as they are called, but this
 *   must be used with exceptional care to avoid feedback loops.
 * </p>
 * @param <E> the type of the elements to be stored in the queue
 */
public class ListenableQueue<E> {
  private final Map<Class<? extends E>, Consumer<E>> listenerMap;

  private final Queue<E> storage;

  /**
   * Constructs a new empty queue.
   */
  public ListenableQueue() {
    this.listenerMap = new HashMap<>();

    this.storage = new LinkedList<>();
  }

  /**
   * Adds a listener to the queue, that will be called if an element with the listened class
   * gets processed. If there's already a listener registered for the class, it'll be replaced
   * with the one specified in this call.
   * @param listenedClass the class the listener will listen to
   * @param listener the listener to register
   * @throws NullPointerException if any of the arguments is {@code null}
   */
  public void addListener(Class<? extends E> listenedClass, Consumer<E> listener) {
    listenerMap.put(Objects.requireNonNull(listenedClass),
                    Objects.requireNonNull(listener));
  }

  /**
   * Removes the class and its listener from the list of listeners. After this call, processing an
   * element with the specified class will not fire a listener.
   * @param listenedClass the class to remove
   * @throws NullPointerException if the class is {@code null}
   */
  public void removeListenedClass(Class<E> listenedClass) {
    listenerMap.remove(Objects.requireNonNull(listenedClass));
  }

  /**
   * Adds the specified element to the end of the queue.
   * @param element the element to add
   * @throws NullPointerException if the element is {@code null}
   */
  public void add(E element) {
    storage.add(Objects.requireNonNull(element));
  }

  /**
   * Adds all of the elements from the specified collection the end of the queue.
   * @param elements the elements to add
   * @throws NullPointerException if the collection is {@code null}
   */
  public void addAll(Collection<? extends E> elements) {
    storage.addAll(Objects.requireNonNull(elements));
  }

  /**
   * Instructs the queue to remove the element from the front of thr queue and call the listener
   * corresponding to the element's class.
   */
  public final void processOne() {
    processElement(storage.remove());
  }

  /**
   * Instructs the queue to process all queued elements. After this call the queue will be empty.
   */
  public void processAll() {
    while (!storage.isEmpty()) {
      processOne();
    }
  }

  /**
   * Returns the number of elements in the queue.
   * @return the number of elements
   */
  public int size() {
    return storage.size();
  }

  /**
   * Checks whether the queue is empty.
   * @return {@code true} if the queue is empty, {@code false} otherwise
   */
  public boolean isEmpty() {
    return storage.isEmpty();
  }

  private void processElement(E element) {
    Consumer<E> consumer = listenerMap.get(element.getClass());

    if (consumer != null) {
      consumer.accept(element);
    }
  }
}