package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class HouseholdRepositoryTests {

    @Inject
    HouseholdRepository householdRepository;
    @InjectMock
    EntityManager entityManager;

    static Household household;
    static final String householdId = "householdId1";

    @BeforeEach
    void setUp() {
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, new LinkedList<>());
    }

    @Test
    public void addHouseholdTest() {
        householdRepository.addHousehold(household);

        Mockito.verify(entityManager, times(1)).persist(any());
    }

    @Test
    public void deleteHouseholdTest() throws HouseholdNotFoundException {
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);

        householdRepository.deleteHousehold(householdId);

        Mockito.verify(entityManager, times(1)).remove(household);
    }

    @Test
    public void deleteHouseholdTestException() {
        assertThrows(HouseholdNotFoundException.class, () -> householdRepository.deleteHousehold(householdId));
        Mockito.verify(entityManager, times(0)).remove(household);
    }

    @Test
    public void updateHouseholdTest() throws HouseholdNotFoundException {
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);

        Household updatedHousehold = household;
        updatedHousehold.setDeviceId("newDeviceId");
        updatedHousehold.setMembers(List.of(new Member()));
        updatedHousehold.setPricingPlan(ElectricityPricingPlan.NORMAL);

        householdRepository.updateHousehold(updatedHousehold);

        Mockito.verify(entityManager, times(1)).merge(updatedHousehold);
    }

    @Test
    public void updateHouseholdTest_HouseholdDoesNotExist() {
        Household updatedHousehold = household;
        updatedHousehold.setDeviceId("newDeviceId");
        updatedHousehold.setMembers(List.of(new Member()));
        updatedHousehold.setPricingPlan(ElectricityPricingPlan.NORMAL);

        assertThrows(HouseholdNotFoundException.class, () -> householdRepository.updateHousehold(updatedHousehold));
        Mockito.verify(entityManager, times(0)).merge(updatedHousehold);
    }

    @Test
    public void getHouseholdById() throws HouseholdNotFoundException {
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);

        Household actualHousehold = householdRepository.getHouseholdById(householdId);
        assertEquals(actualHousehold, household);
    }

    @Test
    public void getHouseholdById_HouseholdDoesNotExist() {
        assertThrows(HouseholdNotFoundException.class, () -> householdRepository.getHouseholdById(householdId));
    }
}
