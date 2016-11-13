package io.infectnet.server.engine.configuration.content;

import dagger.Module;

@Module(includes = {SelectorModule.class, DslModule.class, SystemModule.class})
public class ContentModule {
}
