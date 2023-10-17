package com.ozangunalp;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestStreamElementType;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.tuples.Tuple2;

@Path("/clicks-count")
public class ClicksView {

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<Tuple2<String, Long>> stream() {
        // TODO 4 send counts as SSE event
        return null;
    }

}
