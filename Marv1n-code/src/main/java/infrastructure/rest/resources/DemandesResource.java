package infrastructure.rest.resources;

import core.request.Request;
import core.services.RequestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
public class DemandesResource {

    RequestService requestService;

    public DemandesResource() {
        this.requestService = new RequestService();
    }

    @GET
    @Path("{email}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Request GetRequestByEmail(@PathParam("id") String id, @PathParam("email") String email) {
        return requestService.GetRequestByEmailAndId(email, UUID.fromString(id));
    }
}
