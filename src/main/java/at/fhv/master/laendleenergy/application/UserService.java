package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.view.DTOs.UpdateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import java.util.List;

public interface UserService {
    void createUser(UserDTO userDTO);
    void deleteUser(String userId);
    void addEmailAddress(String email);
    void changePassword(String userId);
    void editInformation(UpdateUserDTO userDTO, String email);
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    boolean login(String email, String password);
    UserDTO getUserByEmail(String email);
}
