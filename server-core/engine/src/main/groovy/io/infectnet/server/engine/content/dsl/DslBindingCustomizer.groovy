package io.infectnet.server.engine.content.dsl

import io.infectnet.server.engine.core.script.execution.BindingContext

interface DslBindingCustomizer {
  void customize(BindingContext bindingContext);
}