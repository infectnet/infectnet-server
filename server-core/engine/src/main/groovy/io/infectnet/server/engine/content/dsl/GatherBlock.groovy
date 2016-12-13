package io.infectnet.server.engine.content.dsl

import groovy.transform.CompileStatic
import io.infectnet.server.engine.core.dsl.DslBindingCustomizer
import io.infectnet.server.engine.core.script.execution.BindingContext

@CompileStatic
class GatherBlock implements DslBindingCustomizer {
  private static final String BINDING_VARIABLE_NAME = "gather";

  public static <T> Map gather(Collection<? extends T> elements) {
    return [
        that: { Closure<Boolean> filter ->
          List<? super T> result = new LinkedList<>();

          for (T element : elements) {
            def closureDelegate = [current: element];

            closureDelegate.putAll(
                (Map) filter.thisObject.properties["binding"].properties["variables"]);

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
    bindingContext.getBinding().setVariable(BINDING_VARIABLE_NAME, GatherBlock.&gather);
  }
}
