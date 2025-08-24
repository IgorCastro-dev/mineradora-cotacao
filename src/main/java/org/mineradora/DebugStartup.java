package org.mineradora;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@Startup
@ApplicationScoped
public class DebugStartup {

    @Inject
    TestService testService;

    void onStart(@Observes StartupEvent ev) {
        testService.debugApiCall();
    }
}