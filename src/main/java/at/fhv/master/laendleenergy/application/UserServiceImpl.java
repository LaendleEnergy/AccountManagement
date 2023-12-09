package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.CreateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UpdateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;
    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    HouseholdRepository householdRepository;

    @Override
    public void createUser(CreateUserDTO createUserDTO, String householdId) throws HouseholdNotFoundException {
        UserDTO userDTO = new UserDTO(createUserDTO.getEmailAddress(), passwordEncoder.encode(createUserDTO.getPassword()), "User", createUserDTO.getName(), null, null);
        Household household = householdRepository.getHouseholdById(householdId);
        userRepository.addUser(UserDTO.create(userDTO, household));
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {
        userRepository.deleteUser(userId);
    }

    @Override
    public void addEmailAddress(String email) {

    }

    @Override
    public void updateUser(UpdateUserDTO userDTO, String emailAddress, String memberId, String householdId) throws UserNotFoundException, HouseholdNotFoundException {
        User userData = userRepository.getUserByEmail(emailAddress);
        Household household = householdRepository.getHouseholdById(householdId);
        User user = UpdateUserDTO.create(memberId, userDTO, userData.getRole().getName(), household);
        userRepository.updateUser(user);
    }

    @Override
    public UserDTO getUserById(String id) throws UserNotFoundException {
        return UserDTO.create(userRepository.getUserById(id));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new LinkedList<>();

        for (User u : userRepository.getAllUsers()) {
            users.add(UserDTO.create(u));
        }

        return users;
    }

    @Override
    public UserDTO getUserByEmail(String emailAddress) throws UserNotFoundException {
        return UserDTO.create(userRepository.getUserByEmail(emailAddress));
    }

    @Override
    public boolean validateEmail(String email) {
        return userRepository.validateEmail(email);
    }
}
