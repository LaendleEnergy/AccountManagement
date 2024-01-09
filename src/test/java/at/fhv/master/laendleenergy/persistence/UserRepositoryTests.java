package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.wildfly.common.Assert.*;

@QuarkusTest
@TestTransaction
public class UserRepositoryTests {

    @Inject
    UserRepository userRepository;
    @InjectMock
    EntityManager entityManager;

    static User user;
    static final String userId = "userId1";
    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() {
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, new LinkedList<>());
        user = new User(userId, "test@email.com", "password", Role.ADMIN, "Testname", Optional.of(LocalDate.of(1990, 1, 1)), Optional.of(Gender.FEMALE), household);
        household.addMember(user);
    }

    @Test
    public void addUserTest() {
        userRepository.addUser(user);

        Mockito.verify(entityManager, times(1)).persist(any());
    }

    @Test
    public void deleteUserTest() throws UserNotFoundException {
        Mockito.when(entityManager.find(User.class, userId)).thenReturn(user);

        userRepository.deleteUser(userId);

        Mockito.verify(entityManager, times(1)).remove(user);
    }

    @Test
    public void deleteUserTestException() {
        assertThrows(UserNotFoundException.class, () -> userRepository.deleteUser(userId));
        Mockito.verify(entityManager, times(0)).remove(user);
    }

    @Test
    public void updateUserTest() throws UserNotFoundException {
        Mockito.when(entityManager.find(User.class, userId)).thenReturn(user);

        User updatedUser = user;
        updatedUser.setEmailAddress("newtest@email.com");
        updatedUser.setPassword("newpassword");
        updatedUser.setRole(Role.USER);
        updatedUser.setGender(Gender.DIVERSE);
        updatedUser.setDateOfBirth(LocalDate.of(1990, 2, 2));
        updatedUser.setName("newname");
        updatedUser.setHousehold(new Household());

        userRepository.updateUser(updatedUser);

        Mockito.verify(entityManager, times(1)).merge(updatedUser);
    }

    @Test
    public void updateUserTest_UserDoesNotExist() {
        User updatedUser = user;
        updatedUser.setEmailAddress("newtest@email.com");

        assertThrows(UserNotFoundException.class, () -> userRepository.updateUser(updatedUser));
        Mockito.verify(entityManager, times(0)).merge(updatedUser);
    }

    @Test
    public void getUserById() throws UserNotFoundException {
        Mockito.when(entityManager.find(User.class, userId)).thenReturn(user);

        User actualUser = userRepository.getUserById(userId);
        assertEquals(actualUser, user);
    }

    @Test
    public void getUserById_UserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> userRepository.getUserById(userId));
    }

    @Test
    public void getUserByEmail() throws UserNotFoundException {
        Query<User> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(List.of(user));

        User actualUser = userRepository.getUserByEmail(user.getEmailAddress());
        assertEquals(actualUser, user);
    }

    @Test
    public void getUserByEmail_UserDoesNotExist() {
        Query<User> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userRepository.getUserByEmail(user.getEmailAddress()));
    }

    @Test
    public void getAllUsers() {
        Query<User> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(List.of(user));

        List<User> actualUsers = userRepository.getAllUsers();

        assertNotNull(actualUsers);
    }

    @Test
    public void validateEmailTest() {
        Query<User> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(List.of(user));

        assertFalse(userRepository.validateEmail(user.getEmailAddress()));
    }
}
