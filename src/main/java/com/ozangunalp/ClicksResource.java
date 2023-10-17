package com.ozangunalp;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.quarkus.logging.Log;

@Path("/clicks")
public class ClicksResource {

    @Channel("clicks-out")
    Emitter<PointerEvent> clicks;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postClick(PointerEvent click) {
        // TODO 1 Send click to clicks topic
        clicks.send(click);
        Log.infof("Click sent %s", click);
    }

}
