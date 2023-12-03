package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.EmailNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.UpdateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import java.util.List;

public interface UserService {
    void createUser(UserDTO userDTO);
    void deleteUser(String userId);
    void addEmailAddress(String email);
    void changePassword(String userId);
    void updateUser(UpdateUserDTO userDTO, String email) throws EmailNotFoundException;
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    UserDTO getUserByEmail(String email) throws EmailNotFoundException;
}
