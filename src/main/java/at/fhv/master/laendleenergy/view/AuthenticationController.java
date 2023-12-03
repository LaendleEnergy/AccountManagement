package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.AuthenticationService;
import at.fhv.master.laendleenergy.domain.exceptions.EmailNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import at.fhv.master.laendleenergy.view.DTOs.LoginDTO;
import io.quarkus.security.UnauthorizedException;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
        } catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest) {
        try {
            LoginDTO loginDTO = authenticationService.login(authRequest);
            return Response.ok(loginDTO).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
