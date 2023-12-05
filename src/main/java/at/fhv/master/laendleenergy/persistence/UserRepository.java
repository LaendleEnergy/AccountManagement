package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import java.util.List;

public interface UserRepository {
    void addUser(User user);
    void updateUser(User newUser) throws UserNotFoundException;
    void deleteUser(String userId) throws UserNotFoundException;
    User getUserById(String userId) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    List<User> getAllUsers();
    boolean login(String email, String password);
}
