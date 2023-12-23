package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.UserService;
import at.fhv.master.laendleenergy.application.authentication.AuthenticationService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.*;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import java.security.Principal;

@Path("/user")
@RequestScoped
public class UserController {

    @Inject
    UserService userService;
    @Inject
    JsonWebToken jwt;
    @Inject
    AuthenticationService authenticationService;


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Context SecurityContext ctx, CreateUserDTO createUserDTO)
    {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String householdId = jwt.getClaim("householdId");
            try {
                userService.createUser(createUserDTO, householdId);
                return Response.ok(true).build();
            } catch (HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response deleteUser(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("memberId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String userId = jwt.getClaim("memberId");
            try {
                userService.deleteUser(userId);
                return Response.ok(true).build();
            } catch (UserNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getAllUsers(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && authenticationService.verifiedCaller(name, jwt.getName())) {
            try {
                return Response.ok(userService.getAllUsers()).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getUserById(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("memberId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String userId = jwt.getClaim("memberId");
            try {
                return Response.ok(userService.getUserById(userId)).build();
            } catch (UserNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response updateUser(@Context SecurityContext ctx, UpdateUserDTO userDTO) {
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("memberId") && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String memberId = jwt.getClaim("memberId");
            String householdId = jwt.getClaim("householdId");
            try {
                userService.updateUser(userDTO, memberId, householdId);
                return Response.ok().build();
            } catch (UserNotFoundException | HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @PermitAll
    @POST
    @Path("/validateEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateEmail(EmailDTO email) {
        return Response.ok(userService.validateEmail(email.getEmail())).build();
    }
}
