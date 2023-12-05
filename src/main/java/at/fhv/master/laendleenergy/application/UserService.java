package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.UpdateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import java.util.List;

public interface UserService {
    void createUser(UserDTO userDTO);
    void deleteUser(String userId) throws UserNotFoundException;
    void addEmailAddress(String email);
    void changePassword(String userId);
    void updateUser(UpdateUserDTO userDTO, String email) throws UserNotFoundException;
    UserDTO getUserById(String id) throws UserNotFoundException;
    List<UserDTO> getAllUsers();
    UserDTO getUserByEmail(String email) throws UserNotFoundException;
}
