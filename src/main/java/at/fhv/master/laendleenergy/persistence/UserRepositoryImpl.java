package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.*;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public UserRepositoryImpl() {
        String[] userNames = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack"};
        String[] emailAddresses = {"alice@example.com", "bob@example.com", "charlie@example.com", "david@example.com", "emma@example.com", "frank@example.com", "grace@example.com", "henry@example.com", "ivy@example.com", "jack@example.com"};
        String[] passwords = {"password1", "password2", "password3", "password4", "password5", "password6", "password7", "password8", "password9", "password10"};
        Role[] roles = {Role.USER, Role.ADMIN, Role.USER, Role.USER, Role.ADMIN, Role.USER, Role.ADMIN, Role.USER, Role.USER, Role.ADMIN};

        // Create and add users to the map
        for (int i = 0; i < 10; i++) {
            User user = new User(
                    emailAddresses[i],
                    passwords[i],
                    roles[i],
                    userNames[i],
                    Optional.of(LocalDate.of(1990 + i, 1, 1)), // Example: Incrementing birth year
                    Optional.of(i % 2 == 0 ? Gender.MALE : Gender.FEMALE) // Example: Alternating gender
            );
            users.put(user.getId(), user);
        }
    }

    @Override
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void updateUser(User newUser) {
        users.replace(newUser.getId(), newUser);
    }

    @Override
    public void deleteUser(String userId) {
        users.remove(userId);
    }

    @Override
    public User getUserById(String userId) {
        return users.get(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new LinkedList<>(users.values());
    }

    @Override
    public boolean login(String email, String password) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getEmailAddress().equals(email) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
