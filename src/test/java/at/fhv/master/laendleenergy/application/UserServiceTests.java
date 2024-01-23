package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.CreateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UpdateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.wildfly.common.Assert.*;

@QuarkusTest
@TestTransaction
public class UserServiceTests {
    @Inject
    UserService service;
    @InjectMock
    UserRepository userRepository;
    @InjectMock
    PBKDF2Encoder passwordEncoder;
    @InjectMock
    HouseholdRepository householdRepository;

    static User user;
    static final String userId = "userId1";
    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() {
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, new LinkedList<>());
        user = new User(userId, "test@email.com", "password", Role.ADMIN, "Testname", Optional.of(LocalDate.of(1990, 1, 1)), Optional.of(Gender.FEMALE), household.getId(), household.getDeviceId());
        household.addMember(user);
    }

    @Test
    public void createUserTest() throws HouseholdNotFoundException {
        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("password");

        CreateUserDTO createUserDTO = new CreateUserDTO("email", "name", "password");
        service.createUser(createUserDTO, householdId);

        Mockito.verify(userRepository, times(1)).addUser(any());
    }

    @Test
    public void updateUserTest() throws UserNotFoundException, HouseholdNotFoundException, JsonProcessingException {
        Mockito.when(userRepository.getUserById(userId)).thenReturn(user);
        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO(user.getEmailAddress(), user.getPassword(), "newName", user.getDateOfBirth().toString(), user.getGender().getName());
        service.updateUser(updateUserDTO, userId, householdId);

        Mockito.verify(userRepository, times(1)).updateUser(any());
    }

    @Test
    public void deleteUserTest() throws UserNotFoundException {
        service.deleteUser(userId);

        Mockito.verify(userRepository, times(1)).deleteUser(userId);
    }

    @Test
    public void getUserById() throws UserNotFoundException {
        Mockito.when(userRepository.getUserById(userId)).thenReturn(user);

        UserDTO expectedUser = UserDTO.create(user);
        UserDTO actualUser = service.getUserById(userId);

        assertEquals(actualUser.getEmailAddress(), expectedUser.getEmailAddress());
    }

    @Test
    public void getAllUsers() {
        List<User> expectedUsers = List.of(user);
        Mockito.when(userRepository.getAllUsers()).thenReturn(expectedUsers);

        List<UserDTO> actualUsersDTOs = service.getAllUsers();

        assertEquals(actualUsersDTOs.size(), 1);
    }

    @Test
    public void validateEmail() {
        Mockito.when(userRepository.validateEmail(user.getEmailAddress())).thenReturn(true);

        assertTrue(service.validateEmail(user.getEmailAddress()));
    }
}
