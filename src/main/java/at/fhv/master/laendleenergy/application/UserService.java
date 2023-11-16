package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;

public interface UserService {
    void createUser(String email, String password, String name, Role role);
    void deleteUser(String userId);
    void addEmailAddress(String email);
    void changePassword(String userId);
    void editInformation(User user);
}
