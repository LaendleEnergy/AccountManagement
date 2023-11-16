package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.User;

public interface UserRepository {
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(String userId);
}
