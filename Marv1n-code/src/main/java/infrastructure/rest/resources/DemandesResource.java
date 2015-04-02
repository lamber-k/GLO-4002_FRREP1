package infrastructure.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
public class DemandesResource {
    public DemandesResource() {
    }

    @GET
    @Path("{email}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String GetRequestByEmail(@PathParam("id") String id, @PathParam("email") String email) {
        return "YOLO";
    }
}
