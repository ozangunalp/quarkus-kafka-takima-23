package com.ozangunalp;

import jakarta.enterprise.context.ApplicationScoped;

import io.smallrye.mutiny.tuples.Tuple2;

@ApplicationScoped
public class ClicksAggregation {

    Tuple2<String, Long> aggregate(PointerEvent event) {
        // TODO 3 count clicks per path
        return Tuple2.of(null, null);
    }

}
