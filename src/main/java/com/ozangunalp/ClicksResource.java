package com.ozangunalp;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/clicks")
public class ClicksResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postClick(PointerEvent click) {
        // TODO 1 Send click to clicks topic
    }

}
