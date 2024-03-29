package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.wildfly.common.Assert.assertFalse;

@QuarkusTest
@TestTransaction
public class PersistenceFunctionTests {

    @Inject
    HouseholdService householdService;

    @Test
    public void testInsertNewHouseholdShouldWork() throws JsonProcessingException {
        CreateHouseholdDTO dto = new CreateHouseholdDTO(
                "deviceId",
                "emailAddress",
                "password",
                "name",
                ElectricityPricingPlan.NORMAL.getName()
        );

        String result = householdService.createHousehold(dto);

        assertFalse(result.isEmpty());
    }
}
