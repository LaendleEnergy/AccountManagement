package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.UserService;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.*;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/user")
@RequestScoped
public class UserController {

    @Inject
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response createUser(CreateUserDTO createUserDTO)
    {
        try {
            UserDTO userDTO = new UserDTO(createUserDTO.getEmailAddress(), createUserDTO.getPassword(), "User", createUserDTO.getName(), null, null, createUserDTO.getHouseholdId());
            userService.createUser(userDTO);
            return Response.ok(true).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/delete/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response deleteUser(String userId) {
        try {
            userService.deleteUser(userId);
            return Response.ok(true).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getAllUsers() {
        try {
            return Response.ok(userService.getAllUsers()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/get/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getUserById(String userId) {
        try {
            return Response.ok(userService.getUserById(userId)).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response updateUser(@Context SecurityContext ctx, UpdateUserDTO userDTO) {
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        try {
            userService.updateUser(userDTO, name);
            return Response.ok().build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
