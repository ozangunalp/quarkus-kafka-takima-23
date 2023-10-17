package com.ozangunalp;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ClicksPersistence {

    @Incoming("clicks")
    @Outgoing("clicks-persisted-out")
    Multi<PointerEventDTO> persistClicks(List<PointerEvent> clicks) {
        return Multi.createFrom().iterable(clicks)
                .map(PointerEventDTO::toDto)
                .call(d -> d.persist())
                .onCompletion().invoke(() -> Log.infof("Persisted clicks %s", clicks));
    }

}
