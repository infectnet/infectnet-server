package io.infectnet.server.engine.configuration;

import io.infectnet.server.engine.dsl.script.ScriptModule;

import dagger.Module;

@Module(includes = { ScriptModule.class })
public class EngineModule {

}
