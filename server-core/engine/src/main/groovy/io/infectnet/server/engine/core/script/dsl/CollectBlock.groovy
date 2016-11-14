package io.infectnet.server.engine.core.script.dsl

import groovy.transform.CompileStatic

@CompileStatic
class CollectBlock implements DslBindingCustomizer {
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

  @Override
  public void customize(Binding binding) {
    binding.setVariable("collect", CollectBlock.&collect);
  }
}
