package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users;

    public UserRepositoryImpl() {
        users = new HashMap<>();
        User u = new User("alice@example.com", "79XRn7pTF6sf33S8GGhkwL7gbs5bIAhuUULKmpdEA7U=", Role.ADMIN, "Alice", Optional.of(LocalDate.of(1990, 5, 15)), Optional.of(Gender.FEMALE), "h1");
        users.put(u.getId(), u);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void updateUser(User u) throws UserNotFoundException {
        if (users.get(u.getId()) != null) {
            users.replace(u.getId(), u);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {
        if (users.get(userId) != null) {
            users.remove(userId);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        User user = users.get(userId);

        if (user != null) {
            return user;
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUserByEmail(String emailAddress) throws UserNotFoundException {
        for (User u : getAllUsers()) {
            if (emailAddress.equals(u.getEmailAddress())) {
                return u;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public List<User> getAllUsers() {
        return new LinkedList<>(users.values());
    }

    @Override
    public boolean validateEmail(String email) {
        for (User u : getAllUsers()) {
            if (u.getEmailAddress().equals(email)) {
                return false;
            }
        }
        return true;
    }
}
