package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.view.DTOs.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Link;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DTOTests {

    @Test
    public void authRequestDTOTest() {
        AuthRequest authRequest = new AuthRequest("email", "password");
        authRequest.setEmailAddress("newemail");
        authRequest.setPassword("newpassword");

        assertEquals("newemail", authRequest.getEmailAddress());
        assertEquals("newpassword", authRequest.getPassword());
    }

    @Test
    public void authResponseDTOTest() {
        AuthResponse authResponse = new AuthResponse("token");
        authResponse.setToken("newtoken");

        assertEquals("newtoken", authResponse.getToken());
    }

    @Test
    public void createHouseholdDTOTest() {
        CreateHouseholdDTO householdDTO = new CreateHouseholdDTO("d1", "email", "password", "name", "Normal");
        householdDTO.setDeviceId("d2");
        householdDTO.setName("name1");
        householdDTO.setPassword("password1");
        householdDTO.setEmailAddress("email1");
        householdDTO.setPricingPlan("Tag/Nacht");

        assertEquals("d2", householdDTO.getDeviceId());
        assertEquals("name1", householdDTO.getName());
        assertEquals("password1", householdDTO.getPassword());
        assertEquals("email1", householdDTO.getEmailAddress());
        assertEquals("Tag/Nacht", householdDTO.getPricingPlan());
    }

    @Test
    public void createUserDTOTest() {
        CreateUserDTO userDTO = new CreateUserDTO("email", "name", "password");
        userDTO.setEmailAddress("email1");
        userDTO.setName("name1");
        userDTO.setPassword("password1");

        assertEquals("email1", userDTO.getEmailAddress());
        assertEquals("name1", userDTO.getName());
        assertEquals("password1", userDTO.getPassword());
    }

    @Test
    public void emailDTOTest() {
        EmailDTO emailDTO = new EmailDTO("email");
        emailDTO.setEmail("email1");

        assertEquals("email1", emailDTO.getEmail());
    }

    @Test
    public void householdDTOTest() {
        HouseholdDTO householdDTO = new HouseholdDTO("d1", "Normal", "VKW");
        householdDTO.setDeviceId("d2");
        householdDTO.setSupplier("VWK");
        householdDTO.setPricingPlan("Tag/Nacht");

        assertEquals("d2", householdDTO.getDeviceId());
        assertEquals("VWK", householdDTO.getSupplier());
        assertEquals("Tag/Nacht", householdDTO.getPricingPlan());
    }

    @Test
    public void householdDTOCreateTest() {
        HouseholdDTO householdDTO = new HouseholdDTO("d1", "Normal", "VKW");
        Household household = HouseholdDTO.create(householdDTO, new LinkedList<>());

        assertEquals(household.getDeviceId(), householdDTO.getDeviceId());
        assertEquals(household.getPricingPlan(), ElectricityPricingPlan.NORMAL);
        assertEquals("Normal", householdDTO.getPricingPlan());
    }

    @Test
    public void householdDTOCreateTest2() {
        HouseholdDTO householdDTO = new HouseholdDTO("d1", "Normal", "VKW");
        Household household = HouseholdDTO.create("id", householdDTO, new LinkedList<>());

        assertEquals(household.getDeviceId(), householdDTO.getDeviceId());
        assertEquals(household.getPricingPlan(), ElectricityPricingPlan.NORMAL);
        assertEquals("Normal", householdDTO.getPricingPlan());
        assertEquals("id", household.getId());
    }

    @Test
    public void householdDTOCreateTest3() {
        List<Member> members = List.of(new Member());
        Household household = new Household("d1", ElectricityPricingPlan.DAYNIGHT, members);
        HouseholdDTO householdDTO = HouseholdDTO.create(household);

        assertEquals(householdDTO.getDeviceId(), household.getDeviceId());
        assertEquals("Tag/Nacht", household.getPricingPlan().getName());
        assertEquals(members, household.getMembers());
    }

    @Test
    public void memberDTOTest() {
        MemberDTO memberDTO = new MemberDTO("id", "name", "2000-10-10", "weiblich");
        memberDTO.setGender("divers");
        memberDTO.setName("name1");
        memberDTO.setDateOfBirth("2001-4-4");
        memberDTO.setId("id1");

        assertEquals("id1", memberDTO.getId());
        assertEquals("name1", memberDTO.getName());
        assertEquals("2001-4-4", memberDTO.getDateOfBirth());
        assertEquals("divers", memberDTO.getGender());
    }

    @Test
    public void memberDTOCreateTest() {
        MemberDTO memberDTO = new MemberDTO("id", "name", "2000-10-10", "weiblich");
        Member member = MemberDTO.create(memberDTO, new Household());

        assertEquals(member.getId(), memberDTO.getId());
        assertEquals(member.getName(), memberDTO.getName());
        assertEquals(member.getDateOfBirth().toString(), memberDTO.getDateOfBirth());
        assertEquals(member.getGender().getName(), memberDTO.getGender());
    }

    @Test
    public void memberDTOCreateTest2() {
        Member member = new Member("id", "name", Optional.of(LocalDate.of(2000, 1, 1)), Optional.of(Gender.DIVERSE), new Household());
        MemberDTO memberDTO = MemberDTO.create(member);

        assertEquals(member.getId(), memberDTO.getId());
        assertEquals(member.getName(), memberDTO.getName());
        assertEquals(member.getDateOfBirth().toString(), memberDTO.getDateOfBirth());
        assertEquals(member.getGender().getName(), memberDTO.getGender());
    }

    @Test
    public void memberDTOCreateTest3() {
        MemberDTO memberDTO = new MemberDTO("id", "name", "2000-10-10", "weiblich");
        Member member = MemberDTO.create("id", memberDTO, new Household());

        assertEquals(member.getId(), memberDTO.getId());
        assertEquals(member.getName(), memberDTO.getName());
        assertEquals(member.getDateOfBirth().toString(), memberDTO.getDateOfBirth());
        assertEquals(member.getGender().getName(), memberDTO.getGender());
    }

    @Test
    public void memberDTOCreateTest4() {
        Member member = new Member("id", "name", Optional.empty(), Optional.empty(), new Household());
        MemberDTO memberDTO = MemberDTO.create(member);

        assertEquals(member.getId(), memberDTO.getId());
        assertEquals(member.getName(), memberDTO.getName());
        assertEquals("", memberDTO.getDateOfBirth());
        assertEquals("", memberDTO.getGender());
    }

    @Test
    public void memberDTOCreateTest5() {
        MemberDTO memberDTO = new MemberDTO("id", "name", null, null);
        Member member = MemberDTO.create(memberDTO, new Household());

        assertEquals(member.getId(), memberDTO.getId());
        assertEquals(member.getName(), memberDTO.getName());
        assertEquals(null, member.getDateOfBirth());
        assertEquals(null, member.getGender());
    }

    @Test
    public void memberDTOCreateTest6() {
        MemberDTO memberDTO = new MemberDTO("id", "name", null, null);
        Member member = MemberDTO.create("id", memberDTO, new Household());

        assertEquals(member.getId(), memberDTO.getId());
        assertEquals(member.getName(), memberDTO.getName());
        assertEquals(null, member.getDateOfBirth());
        assertEquals(null, member.getGender());
    }

    @Test
    public void updateUserDTOTest() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("email", "password", "name", "2000-5-5", "männlich");
        updateUserDTO.setDateOfBirth("1990-1-1");
        updateUserDTO.setGender("divers");
        updateUserDTO.setName("name1");
        updateUserDTO.setEmailAddress("email1");
        updateUserDTO.setPassword("password1");

        assertEquals("1990-1-1", updateUserDTO.getDateOfBirth());
        assertEquals("divers", updateUserDTO.getGender());
        assertEquals("name1", updateUserDTO.getName());
        assertEquals("email1", updateUserDTO.getEmailAddress());
        assertEquals("password1", updateUserDTO.getPassword());
    }

    @Test
    public void updateUserDTOCreateTest() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("email", "password", "name", LocalDate.of(2000, 4, 4).toString(), "männlich");
        User user = UpdateUserDTO.create("id", updateUserDTO, Role.USER, new Household());

        assertEquals(user.getDateOfBirth().toString(), updateUserDTO.getDateOfBirth());
        assertEquals(user.getGender().getName(), updateUserDTO.getGender());
        assertEquals(user.getName(), updateUserDTO.getName());
        assertEquals(user.getEmailAddress(), updateUserDTO.getEmailAddress());
        assertEquals(user.getPassword(), updateUserDTO.getPassword());
        assertEquals(user.getRole(), Role.USER);
        assertEquals(user.getId(), "id");
    }

    @Test
    public void userDTOTest() {
        UserDTO userDTO = new UserDTO("email", "password", "Admin", "name", "2000-1-1", "männlich");
        userDTO.setDateOfBirth("1990-1-1");
        userDTO.setGender("weiblich");
        userDTO.setName("name1");
        userDTO.setRole("User");
        userDTO.setPassword("password1");
        userDTO.setEmailAddress("email1");

        assertEquals("1990-1-1", userDTO.getDateOfBirth());
        assertEquals("weiblich", userDTO.getGender());
        assertEquals("name1", userDTO.getName());
        assertEquals("email1", userDTO.getEmailAddress());
        assertEquals("password1", userDTO.getPassword());
        assertEquals("User", userDTO.getRole());
    }

    @Test
    public void userDTOCreateTest() {
        UserDTO userDTO = new UserDTO("email", "password", "Admin", "name", LocalDate.of(1990, 4, 4).toString(), "männlich");
        User user = UserDTO.create(userDTO, new Household());

        assertEquals(user.getDateOfBirth().toString(), userDTO.getDateOfBirth());
        assertEquals(user.getGender().getName(), userDTO.getGender());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmailAddress(), userDTO.getEmailAddress());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getRole().getName(), userDTO.getRole());
    }

    @Test
    public void userDTOCreateTest2() {
        User user = new User("id", "password", Role.ADMIN, "name", Optional.of(LocalDate.of(2000, 1, 1)), Optional.of(Gender.DIVERSE), new Household());
        UserDTO userDTO = UserDTO.create(user);

        assertEquals(user.getDateOfBirth().toString(), userDTO.getDateOfBirth());
        assertEquals(user.getGender().getName(), userDTO.getGender());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmailAddress(), userDTO.getEmailAddress());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getRole().getName(), userDTO.getRole());
    }
}
