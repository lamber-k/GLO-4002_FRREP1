package ca.ulaval.glo4002.rest.resources;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.services.RequestService;

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
    public Request getRequestByEmail(@PathParam("id") String id, @PathParam("email") String email) {
        return requestService.getRequestByEmailAndId(email, UUID.fromString(id));
    }
}
