package pw.stamina.minecraftapi.module;

import pw.stamina.minecraftapi.MinecraftApiAdapter;

import java.util.ServiceLoader;

public final class MinecraftApiModuleManager {
    private final ServiceLoader<MinecraftApiModule> modules;
    private final EventConsumer eventConsumer;

    private MinecraftApiModuleManager(ServiceLoader<MinecraftApiModule> modules,
                                      EventConsumer eventConsumer) {
        this.modules = modules;
        this.eventConsumer = eventConsumer;
    }

    public void bootstrap(MinecraftApiAdapter adapter) {
        modules.forEach(module -> module.bootstrap(adapter));
    }

    public <T> void consumeEvent(T event) {
        eventConsumer.consumeEvent(event);
    }

    public static MinecraftApiModuleManager loadModules(ClassLoader classLoader) {
        ServiceLoader<MinecraftApiModule> modules = loadModuleServiceLoader(classLoader);
        EventConsumer eventConsumer = getEventConsumerFromModules(modules);

        return new MinecraftApiModuleManager(modules, eventConsumer);
    }

    private static ServiceLoader<MinecraftApiModule> loadModuleServiceLoader(ClassLoader classLoader) {
        return ServiceLoader.load(MinecraftApiModule.class, classLoader);
    }

    private static EventConsumer getEventConsumerFromModules(ServiceLoader<MinecraftApiModule> modules) {
        return EventConsumer.empty(); //TODO: Get actual consumer
    }
}
