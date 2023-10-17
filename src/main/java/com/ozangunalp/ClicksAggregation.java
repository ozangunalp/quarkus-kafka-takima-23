package com.ozangunalp;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.reactive.messaging.kafka.commit.DefaultCheckpointMetadata;

@ApplicationScoped
public class ClicksAggregation {

    @Incoming("clicks-persisted")
    @Outgoing("clicks-per-element")
    Tuple2<String, Long> aggregate(PointerEventDTO event, DefaultCheckpointMetadata<CountState> checkpoint) {
        String key = event.xpath;
        long count = checkpoint.transform(CountState::new, s -> s.increment(key)).get(key);
        return Tuple2.of(key, count);
    }

}
