package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.streams.publisher.HouseholdUpdatedEventPublisher;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@Transactional
public class HouseholdServiceTests {
    @Inject
    HouseholdService service;
    @InjectMock
    HouseholdRepository householdRepository;
    @InjectMock
    MemberRepository memberRepository;
    @InjectMock
    UserRepository userRepository;
    @InjectMock
    PBKDF2Encoder passwordEncoder;
    @InjectMock
    HouseholdUpdatedEventPublisher publisher;

    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() {
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, new LinkedList<>());
    }

    @Test
    public void createHouseholdTest() {
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        CreateHouseholdDTO householdDTO = new CreateHouseholdDTO();
        service.createHousehold(householdDTO);

        Mockito.verify(userRepository, times(1)).addUser(any());
        Mockito.verify(householdRepository, times(1)).addHousehold(any());
    }

    @Test
    public void deleteHouseholdTest() throws HouseholdNotFoundException {
        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);

        service.deleteHousehold(householdId);

        Mockito.verify(householdRepository, times(1)).deleteHousehold(householdId);
    }

    @Test
    public void updateHouseholdTest() throws HouseholdNotFoundException, JsonProcessingException {
        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);

        Household updatedHousehold = household;
        updatedHousehold.setDeviceId("newDeviceId");
        updatedHousehold.setMembers(List.of(new Member()));
        updatedHousehold.setPricingPlan(ElectricityPricingPlan.NORMAL);

        service.updateHousehold(householdId, HouseholdDTO.create(updatedHousehold));

        Mockito.verify(householdRepository, times(1)).updateHousehold(any());
    }

    @Test
    public void getHouseholdById() throws HouseholdNotFoundException {
        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);

        HouseholdDTO actualHousehold = service.getHouseholdById(householdId);
        HouseholdDTO expectedHousehold = HouseholdDTO.create(household);

        assertEquals(actualHousehold.getDeviceId(), expectedHousehold.getDeviceId());
    }

    @Test
    public void getUsersOfHousehold() throws HouseholdNotFoundException {
        User expectedUser = new User();
        List<Member> members = List.of(expectedUser, new Member(), new Member());
        Mockito.when(memberRepository.getAllMembersOfHousehold(householdId)).thenReturn(members);
        List<User> actualUsers = service.getUsersOfHousehold(householdId);

        assertEquals(1, actualUsers.size());
        assertEquals(expectedUser.getId(), actualUsers.get(0).getId());
        Mockito.verify(memberRepository, times(1)).getAllMembersOfHousehold(householdId);
    }
}
