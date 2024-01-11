package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.events.HouseholdUpdatedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class DomainTests {

    @Test
    public void electricityPricingPlanTest() {
        assertEquals(ElectricityPricingPlan.get("Tag/Nacht"), ElectricityPricingPlan.DAYNIGHT);
        assertEquals(ElectricityPricingPlan.get("Normal"), ElectricityPricingPlan.NORMAL);
    }

    @Test
    public void genderTest() {
        assertEquals(Gender.get("weiblich"), Gender.FEMALE);
        assertEquals(Gender.get("männlich"), Gender.MALE);
        assertEquals(Gender.get("divers"), Gender.DIVERSE);
    }

    @Test
    public void roleTest() {
        assertEquals(Role.get("Admin"), Role.ADMIN);
        assertEquals(Role.get("User"), Role.USER);
    }

    @Test
    public void supplierTest() {
        assertEquals(Supplier.get("VKW"), Supplier.VKW);
    }

    @Test
    public void householdTest() {
        Household household = new Household("h1", "d1", ElectricityPricingPlan.NORMAL, new LinkedList<>());
        household.setPricingPlan(ElectricityPricingPlan.DAYNIGHT);
        household.setDeviceId("d2");
        household.setMembers(List.of(new Member()));

        assertEquals("d2", household.getDeviceId());
        assertNotNull(household.getMembers());
        assertEquals(ElectricityPricingPlan.DAYNIGHT, household.getPricingPlan());
        assertEquals("h1", household.getId());
    }

    @Test
    public void memberTest() {
        Member member = new Member("memberName", Optional.of(LocalDate.of(2000, 2, 2)), Optional.of(Gender.FEMALE), new Household());
        member.setGender(Gender.DIVERSE);
        member.setHousehold(null);
        member.setName("newTestName");
        member.setDateOfBirth(LocalDate.of(2000, 4, 4));

        assertEquals("newTestName", member.getName());
        assertNull(member.getHousehold());
        assertEquals(Gender.DIVERSE, member.getGender());
        assertEquals(LocalDate.of(2000, 4,4), member.getDateOfBirth());
    }

    @Test
    public void userTest() {
        User user = new User("email@test.com", "password", Role.USER, "memberName", Optional.of(LocalDate.of(2000, 2, 2)), Optional.of(Gender.FEMALE), new Household());
        user.setEmailAddress("newemail@test.com");
        user.setPassword("newpassword");
        user.setRole(Role.ADMIN);

        assertEquals("newemail@test.com", user.getEmailAddress());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void memberAddedEventTest() {
        MemberAddedEvent event = new MemberAddedEvent("event1", "m1", "name", "h1", LocalDateTime.now());
        event.setEventId("new");
        event.setMemberId("memberid");
        event.setHouseholdId("householdid");
        event.setTimestamp(LocalDateTime.of(2000, 2,2,2,2));

        assertEquals("new", event.getEventId());
        assertEquals("memberid", event.getMemberId());
        assertEquals("householdid", event.getHouseholdId());
        assertEquals(LocalDateTime.of(2000, 2,2,2,2), event.getTimestamp());
    }

    @Test
    public void householdUpdatedEventTest() {
        Household household = new Household("123", ElectricityPricingPlan.NORMAL, new LinkedList<>());
        HouseholdUpdatedEvent event = new HouseholdUpdatedEvent("event1", household, LocalDateTime.now());
        event.setEventId("new");
        event.setHousehold(null);
        event.setTimestamp(LocalDateTime.of(2000, 2,2,2,2));

        assertEquals("new", event.getEventId());
        assertNull(event.getHousehold());
        assertEquals(LocalDateTime.of(2000, 2,2,2,2), event.getTimestamp());
    }
}