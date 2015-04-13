package ca.ulaval.glo4002.rest.resources;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.models.RequestInformationModel;
import ca.ulaval.glo4002.services.RequestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response getRequestByEmail(@PathParam("id") String id, @PathParam("email") String email) {
        RequestInformationModel requestInformationModel = null;
        try {
            requestInformationModel = requestService.getRequestByEmailAndId(email, UUID.fromString(id));
        } catch (ObjectNotFoundException e) {
            return Response.status(404)
                    .entity(e.getMessage())
                    .build();
        }
        return Response.ok().entity(requestInformationModel).build();
    }
}
