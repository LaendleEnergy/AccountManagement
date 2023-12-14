package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.authentication.AuthenticationService;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@RequestScoped
public class AuthenticationController {

    @Inject
    AuthenticationService authenticationService;

    @PermitAll
    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(AuthRequest authRequest) {
        try {
            AuthResponse authResponse = authenticationService.authenticate(authRequest);
            return Response.ok(authResponse).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @PermitAll
    @POST
    @Path("/invalidateToken")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response invalidateToken(AuthResponse authResponse) {
        try {
            authenticationService.invalidateToken(authResponse);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
