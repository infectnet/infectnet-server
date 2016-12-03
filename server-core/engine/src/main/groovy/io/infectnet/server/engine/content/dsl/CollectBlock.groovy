package io.infectnet.server.engine.content.dsl

import groovy.transform.CompileStatic
import io.infectnet.server.engine.core.script.execution.BindingContext

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
  public void customize(BindingContext bindingContext) {
    bindingContext.getBinding().setVariable("collect", CollectBlock.&collect);
  }
}
