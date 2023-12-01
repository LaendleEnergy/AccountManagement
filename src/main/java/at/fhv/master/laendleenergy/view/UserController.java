package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.UserService;
import at.fhv.master.laendleenergy.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.authentication.TokenUtils;
import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.exceptions.EmailNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.*;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import java.security.Principal;

@Path("/user")
@RequestScoped
public class UserController {

    @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration") public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;

    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    UserService userService;
    @Inject
    JsonWebToken jwt;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUserDTO createUserDTO)
    {
        try {
            UserDTO userDTO = new UserDTO(createUserDTO.getEmailAddress(), createUserDTO.getPassword(), "User", createUserDTO.getName(), null, null);
            userService.createUser(userDTO);
            return Response.ok(true).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/delete/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(String userId) {
        try {
            userService.deleteUser(userId);
            return Response.ok(true).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        try {
            return Response.ok(userService.getAllUsers()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/{emailAddress}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(String emailAddress) {
        try {
            return Response.ok(userService.getUserByEmail(emailAddress)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@Context SecurityContext ctx, UpdateUserDTO userDTO) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        try {
            userService.editInformation(userDTO, name);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest) {
        try {
            UserDTO u = userService.getUserByEmail(authRequest.getEmailAddress());
            if (u != null && u.getPassword().equals(passwordEncoder.encode(authRequest.getPassword()))) {
                try {
                    return Response.ok(new AuthResponse(TokenUtils.generateToken(u.getEmailAddress(), Role.get(u.getRole()), duration, issuer))).build();
                } catch (Exception e) {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
