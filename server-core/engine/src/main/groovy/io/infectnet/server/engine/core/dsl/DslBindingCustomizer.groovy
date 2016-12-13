package io.infectnet.server.engine.core.dsl

import io.infectnet.server.engine.core.script.execution.BindingContext

interface DslBindingCustomizer {
  void customize(BindingContext bindingContext);
}