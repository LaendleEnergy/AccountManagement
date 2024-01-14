package at.fhv.master.laendleenergy.application.authentication;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
@QuarkusTest
@Transactional
public class AuthenticationServiceTests {

    @Inject
    AuthenticationService service;
    @InjectMock
    UserRepository userRepository;
    @InjectMock
    PBKDF2Encoder passwordEncoder;
    static String email = "test@email.com";
    static User user;
    static final String userId = "userId1";
    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() {
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, new LinkedList<>());
        user = new User(userId, email, "password", Role.ADMIN, "Testname", Optional.of(LocalDate.of(1990, 1, 1)), Optional.of(Gender.FEMALE), household.getId(), household.getDeviceId());
        household.addMember(user);
    }

    @Test
    public void authenticateTest() throws Exception {
        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("password");

        AuthResponse response = service.authenticate(new AuthRequest(email, "password"));

        assertNotNull(response);
    }

    @Test
    public void authenticateTest_WrongPassword() throws Exception {
        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertThrows(UnauthorizedException.class, () -> service.authenticate(new AuthRequest(email, "password")));
    }

    @Test
    public void authenticateTest_UserNotFound() throws UserNotFoundException {
        Mockito.when(userRepository.getUserByEmail(email)).thenThrow(UserNotFoundException.class);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertThrows(UserNotFoundException.class, () -> service.authenticate(new AuthRequest(email, "password")));
    }

    @Test
    public void verifiedCallerTest() {
        assertTrue(service.verifiedCaller("name", "name"));
    }

    @Test
    public void verifiedCallerTest_NamesDoNotMatch() {
        assertFalse(service.verifiedCaller("name", "jwtname"));
    }
}
