package com.ozangunalp;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.quarkus.logging.Log;
import io.smallrye.common.annotation.RunOnVirtualThread;

@ApplicationScoped
public class ClicksPersistence {

    @Incoming("clicks")
    @Transactional
    @RunOnVirtualThread
    void persistClicks(List<PointerEvent> clicks) {
        for (PointerEvent click : clicks) {
            PointerEventDTO.toDto(click).persist();
        }
        Log.infof("Persisted clicks %s", clicks);
    }

}
