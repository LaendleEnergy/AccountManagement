package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.CreateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UpdateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import java.util.List;

public interface UserService {
    void createUser(CreateUserDTO createUserDTO, String householdId) throws HouseholdNotFoundException;
    void deleteUser(String userId) throws UserNotFoundException;
    void updateUser(UpdateUserDTO userDTO, String email, String memberId, String householdId) throws UserNotFoundException, HouseholdNotFoundException;
    UserDTO getUserById(String id) throws UserNotFoundException;
    List<UserDTO> getAllUsers();
    boolean validateEmail(String email);
}
