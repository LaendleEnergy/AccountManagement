package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.*;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User u) throws UserNotFoundException {
        User toUpdate = entityManager.find(User.class, u.getId());
        if (toUpdate == null) throw new UserNotFoundException();

        entityManager.merge(u);
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {
        User toDelete = entityManager.find(User.class, userId);
        if (toDelete == null) throw new UserNotFoundException();

        entityManager.remove(toDelete);
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        User user = entityManager.find(User.class, userId);
        if (user == null) throw new UserNotFoundException();

        return user;
    }

    @Override
    public User getUserByEmail(String emailAddress) throws UserNotFoundException {
        String jpqlQuery = "SELECT u FROM User u WHERE u.emailAddress =: emailAddress";

        try {
            User user = entityManager.createQuery(jpqlQuery, User.class)
                    .setParameter("emailAddress", emailAddress)
                    .getResultList().stream().findFirst().orElse(null);

            if (user == null) throw new UserNotFoundException();

            return user;
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String jpqlQuery = "SELECT u FROM User u";

        return entityManager.createQuery(jpqlQuery, User.class)
                .getResultList();
    }

    @Override
    public boolean validateEmail(String email) {
        String hql = "SELECT u FROM User u WHERE u.emailAddress  = :email";

        List<User> found = entityManager.createQuery(hql, User.class).setParameter("email", email).getResultList();
        return found.isEmpty();
    }
}
