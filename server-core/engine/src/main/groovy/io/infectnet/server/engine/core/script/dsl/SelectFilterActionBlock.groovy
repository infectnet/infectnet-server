package io.infectnet.server.engine.core.script.dsl

import groovy.transform.CompileStatic

@CompileStatic
class SelectFilterActionBlock implements DslBindingCustomizer {
  public static <T> Map all(Collection<? extends T> elements) {
    return [
        that: { Closure<Boolean> filter ->
          [execute: { Closure<Void> action ->
            doForAll(filter, action, elements);
          }]
        },
        execute  : { Closure<Void> action ->
          doForAll(SelectFilterActionBlock.&trueFilter, action, elements);
        }
    ];
  }

  public static <T> Map any(Collection<? extends T> elements) {
    return all(elements);
  }

  public static <T> Map only(Collection<? extends T> elements) {
    return [
        that: { Closure<Boolean> filter ->
          [execute: { Closure<Void> action ->
            doForOne(filter, action, elements);
          }]
        },
        execute: { Closure<Void> action ->
          doForOne(SelectFilterActionBlock.&trueFilter, action, elements);
        }
    ];
  }

  @Override
  void customize(Binding binding) {
    binding.setVariable("all", SelectFilterActionBlock.&all);

    binding.setVariable("any", SelectFilterActionBlock.&any);

    binding.setVariable("only", SelectFilterActionBlock.&only);
  }

  /**
   * Returns {@code true} on its all invocations. This method might look unnecessary but it's added
   * because it's much faster than creating a closure like this:
   * <pre>
   * <code>
   *   def trueFilter = { -> true };
   * </code>
   * </pre>
   * @return always {@code true}
   */
  private static boolean trueFilter() {
    return true;
  }

  private static <T> void doForOne(Closure<Boolean> filter, Closure<Void> action,
                                   Collection<? extends T> elements) {
    for (T element : elements) {
      def closureDelegate = [current: element];

      filter.delegate = closureDelegate;

      if (filter()) {
        action.delegate = closureDelegate;

        action();

        break;
      }
    }
  }

  private static <T> void doForAll(Closure<Boolean> filter, Closure<Void> action,
                                   Collection<? extends T> elements) {
    for (T element : elements) {
      def closureDelegate = [current: element];

      filter.delegate = closureDelegate;

      if (filter()) {
        action.delegate = closureDelegate;

        action();
      }
    }
  }
}
