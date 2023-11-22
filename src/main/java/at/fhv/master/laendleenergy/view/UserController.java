package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.UserService;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/user")
public class UserController {

    @Inject
    UserService userService;

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
    @Path("/deleteUser/{userId}")
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
    public void updateUser(UserDTO userDTO) {
        userService.editInformation(userDTO);
    }

    @POST
    @Path("/login")
    public RestResponse<Boolean> login(@FormParam("email") String email, @FormParam("password") String password) {
        return RestResponse.ResponseBuilder
                .ok(userService.login(email, password), MediaType.TEXT_PLAIN)
                .build();
    }
}
