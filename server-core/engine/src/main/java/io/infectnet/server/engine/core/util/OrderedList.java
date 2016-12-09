package io.infectnet.server.engine.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Wrapper class around a traditional list that accepts {@link Ordered} instances and maintain
 * the order between them based on the return value of {@link Ordered#getOrder()}.
 * @param <E> the type of elements to be stored in the list
 */
public class OrderedList<E extends Ordered> {
  private static final int FIRST = 0;

  private final List<E> backingList;

  /**
   * Produces a new {@code OrderedList} that contains the elements of the specified
   * collection.
   * @param elements the elements to be stored in the new {@code OrderedList}
   * @param <E> the type of elements to be stored in the list
   * @return a new {@code OrderedList}
   * @throws NullPointerException if the passed collection is {@code null}
   */
  public static <E extends Ordered> OrderedList<E> of(Collection<E> elements) {
    Collection<E> nonNullElements = Objects.requireNonNull(elements);

    OrderedList<E> list = new OrderedList<>();

    nonNullElements.forEach(list::add);

    return list;
  }

  /**
   * Constructs a new empty list.
   */
  public OrderedList() {
    this.backingList = new ArrayList<>();
  }

  /**
   * Adds an element to the list. The element's order will determine its position in the list.
   * When a tie occurs, the newly inserted element will precede the elements with the same order.
   * @param element the element to be added
   */
  public void add(E element) {
    E nonNullElement = Objects.requireNonNull(element);

    int place = getPlaceByOrder(nonNullElement.getOrder());

    backingList.add(place, nonNullElement);
  }

  /**
   * Gets an unmodifiable view of the backing list that stores the elements added with
   * {@link #add(Ordered)}.
   * @return an unmodifiable list of the elements
   */
  public List<E> getBackingList() {
    return Collections.unmodifiableList(backingList);
  }

  private int getPlaceByOrder(int order) {
    for (int i = 0; i < backingList.size(); ++i) {
      if (backingList.get(i).getOrder() >= order) {
        return i;
      }
    }

    return backingList.size();
  }
}