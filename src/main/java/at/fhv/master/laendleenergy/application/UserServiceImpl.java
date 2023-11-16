package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.persistence.UserRepository;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public void createUser(String email, String password, String name, Role role) {
        User user = new User(email, password, role, name, Optional.empty(), Optional.empty());
        userRepository.createUser(user);
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
    public void editInformation(User user) {
    userRepository.updateUser(user);
    }
}
