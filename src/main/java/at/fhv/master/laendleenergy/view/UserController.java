package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.UserService;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/user")
public class UserController {

    @Inject
    UserService userService;

    /*

    void addEmailAddress(String email);
    void changePassword(String userId);
    void editInformation(UserDTO userDTO);
     */

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createUser(
            @FormParam("email") String email,
            @FormParam("name") String name,
            @FormParam("password") String password)
    {
        UserDTO userDTO = new UserDTO(email, password, "Admin", name, "", "");
        userService.createUser(userDTO);
    }

    @DELETE
    @Path("/deleteUser/{userId}")
    public void deleteHUser(String userId) {
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
}
