package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public void createUser(UserDTO userDTO) {
        User user = new User(userDTO.getEmailAddress(), userDTO.getPassword(), Role.valueOf(userDTO.getRole()), userDTO.getName(), Optional.of(LocalDate.parse(userDTO.getDateOfBirth())), Optional.of(Gender.valueOf(userDTO.getGender())));
        userRepository.addUser(user);
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
    public void editInformation(UserDTO userDTO) {
        User user = new User(userDTO.getEmailAddress(), userDTO.getPassword(), Role.valueOf(userDTO.getRole()), userDTO.getName(), Optional.of(LocalDate.parse(userDTO.getDateOfBirth())), Optional.of(Gender.valueOf(userDTO.getGender())));
        userRepository.updateUser(user);
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.getUserById(id);
        return new UserDTO(user.getEmailAddress(),user.getPassword(), user.getRole().getName(), user.getName(), user.getDateOfBirth().toString(), user.getGender().getName());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new LinkedList<>();

        for (User u : userRepository.getAllUsers()) {
            UserDTO userDTO = new UserDTO(u.getEmailAddress(), u.getPassword(), u.getRole().getName(), u.getName(), u.getDateOfBirth().toString(), u.getGender().getName());
            users.add(userDTO);
        }

        return users;
    }
}
