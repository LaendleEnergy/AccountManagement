package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import java.util.List;

public interface UserService {
    void createUser(UserDTO userDTO);
    void deleteUser(String userId);
    void addEmailAddress(String email);
    void changePassword(String userId);
    void editInformation(UserDTO userDTO);
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
}
