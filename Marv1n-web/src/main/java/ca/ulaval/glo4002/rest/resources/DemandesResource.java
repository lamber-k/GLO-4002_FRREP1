package ca.ulaval.glo4002.rest.resources;

import ca.ulaval.glo4002.applicationServices.models.RequestInformationModel;
import ca.ulaval.glo4002.applicationServices.models.RequestModel;
import ca.ulaval.glo4002.applicationServices.models.RequestsInformationModel;
import ca.ulaval.glo4002.applicationServices.services.RequestService;
import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.request.Request;

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
    public Response getRequestByEmailAndId(@PathParam("id") String id, @PathParam("email") String email) {
        RequestInformationModel requestInformationModel = null;
        try {
            requestInformationModel = requestService.getRequestByEmailAndId(email, UUID.fromString(id));
        } catch (ObjectNotFoundException e) {
            return Response.status(404)
                    .entity(e.getMessage())
                    .header("Content-Type", "text/plain")
                    .build();
        }
        return Response.ok()
                .entity(requestInformationModel)
                .build();
    }

    @GET
    @Path("{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRequestByEmail(@PathParam("email") String email) {
        RequestsInformationModel requestsInformationModel = null;
        try {
            requestsInformationModel = requestService.getRequestByEmail(email);
        } catch (ObjectNotFoundException exception) {
            return Response.status(404)
                    .entity(exception.getMessage())
                    .build();
        }
        return Response.ok().entity(requestsInformationModel).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRequest(RequestModel model) {
        Request request;
        try {
            request = requestService.addRequest(model);
        } catch (InvalidFormatException exception) {
            return Response.status(400)
                    .entity(exception.getMessage())
                    .header("Content-Type", "text/plain")
                    .build();
        }
        return Response.status(201)
                .header("Location", String.format("/demandes/%s/%s", request.getResponsible().getMailAddress(), request.getRequestID()))
                .build();
    }
}
