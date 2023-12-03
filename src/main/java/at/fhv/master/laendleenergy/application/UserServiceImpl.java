package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.EmailNotFoundException;
import at.fhv.master.laendleenergy.persistence.UserRepository;
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

    @Override
    public void createUser(UserDTO userDTO) {
        userRepository.addUser(UserDTO.create(userDTO));
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public void addEmailAddress(String email) {

    }

    @Override
    public void changePassword(String userId) {

    }

    @Override
    public void updateUser(UpdateUserDTO userDTO, String emailAddress) throws EmailNotFoundException {
        User userData = userRepository.getUserByEmail(emailAddress);
        User user = UpdateUserDTO.create(userData.getId(), userDTO, userData.getRole().getName());
        userRepository.updateUser(user);
    }

    @Override
    public UserDTO getUserById(String id) {
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
    public boolean login(String emailAddress, String password) {
        return userRepository.login(emailAddress, password);
    }

    @Override
    public UserDTO getUserByEmail(String emailAddress) throws EmailNotFoundException {
        return UserDTO.create(userRepository.getUserByEmail(emailAddress));
    }
}
