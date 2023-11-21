package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.User;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

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
}
