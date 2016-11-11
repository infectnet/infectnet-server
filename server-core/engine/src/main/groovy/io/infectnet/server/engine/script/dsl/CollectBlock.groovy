package io.infectnet.server.engine.script.dsl

import groovy.transform.CompileStatic

@CompileStatic
class CollectBlock {
  public static <T> Map collect(Collection<? extends T> elements) {
    return [
        that: { Closure<Boolean> filter ->
          List<? super T> result = new LinkedList<>();

          for (T element : elements) {
            def closureDelegate = [current: element];

            filter.delegate = closureDelegate;

            if (filter()) {
              result.add(element);
            }
          }

          return result;
        }
    ];
  }
}
