package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.EmailNotFoundException;

import java.util.List;

public interface UserRepository {
    void addUser(User user);
    void updateUser(User newUser);
    void deleteUser(String userId);
    User getUserById(String userId);
    User getUserByEmail(String email) throws EmailNotFoundException;
    List<User> getAllUsers();
    boolean login(String email, String password);
}
