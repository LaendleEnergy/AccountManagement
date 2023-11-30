package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.UserService;
import at.fhv.master.laendleenergy.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.authentication.TokenUtils;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import at.fhv.master.laendleenergy.view.DTOs.UpdateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
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
import java.util.List;

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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createUser(
            @FormParam("email") String email,
            @FormParam("name") String name,
            @FormParam("password") String password)
    {
        UserDTO userDTO = new UserDTO(email, password, "Admin", name, null, null);
        userService.createUser(userDTO);
    }

    @DELETE
    @Path("/delete/{userId}")
    public void deleteUser(String userId) {
        this.userService.deleteUser(userId);
    }

    @GET
    @Path("/get/{id}")
    public UserDTO getUserById(String id) {
        return userService.getUserById(id);
    }

    @GET
    @Path("/get/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(@Context SecurityContext ctx, UpdateUserDTO userDTO) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        userService.editInformation(userDTO, name);
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest) {
        User u = userService.findUserByEmail(authRequest.getEmail());

        if (u != null && u.getPassword().equals(passwordEncoder.encode(authRequest.password))) {
            try {
                return Response.ok(new AuthResponse(TokenUtils.generateToken(u.getEmailAddress(), u.getRole(), duration, issuer))).build();
            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
