package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestTransaction
public class MemberServiceTests {
    @InjectMock
    MemberRepository memberRepository;
    @InjectMock
    HouseholdRepository householdRepository;
    @Inject
    MemberService service;

    static Household household;
    static final String householdId = "householdId1";
    static String memberId = "memberId";
    static Member member;

    @BeforeEach
    void setUp() {
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, new LinkedList<>());
        member = new Member(memberId, "name", Optional.empty(), Optional.empty(), household.getId(), household.getDeviceId());
    }


    @Test
    public void addHouseholdMember() throws HouseholdNotFoundException, JsonProcessingException {
        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);

        service.addHouseholdMember(new MemberDTO(), householdId);

        Mockito.verify(memberRepository, times(1)).addHouseholdMember(any());
    }

    @Test
    public void removeHouseholdMember() throws MemberNotFoundException, HouseholdNotFoundException, JsonProcessingException {
        Mockito.when(memberRepository.getMemberById(memberId)).thenReturn(member);

        service.removeHouseholdMember(memberId, householdId);

        Mockito.verify(memberRepository, times(1)).removeHouseholdMember(anyString());
    }

    @Test
    public void getMembersOfHousehold() throws HouseholdNotFoundException {
        List<Member> members = List.of(member, new Member(), new Member());
        Mockito.when(memberRepository.getAllMembersOfHousehold(householdId)).thenReturn(members);
        List<MemberDTO> actualMembers = service.getAllMembersOfHousehold(householdId);

        assertEquals(3, actualMembers.size());
        assertEquals(member.getId(), actualMembers.get(0).getId());
        Mockito.verify(memberRepository, times(1)).getAllMembersOfHousehold(householdId);
    }

    @Test
    public void getMemberById() throws HouseholdNotFoundException, MemberNotFoundException {
        Mockito.when(memberRepository.getMemberById(memberId)).thenReturn(member);

        MemberDTO actualMember = service.getMemberById(memberId);
        MemberDTO expectedMember= MemberDTO.create(member);

        Mockito.verify(memberRepository, times(1)).getMemberById(memberId);
        assertEquals(actualMember.getId(), expectedMember.getId());
    }

    @Test
    public void updateMemberTest() throws HouseholdNotFoundException, MemberNotFoundException {
        Mockito.when(householdRepository.getHouseholdById(householdId)).thenReturn(household);

        Member updatedMember = member;
        updatedMember.setDateOfBirth(LocalDate.of(1980, 2, 2));
        updatedMember.setName("testname");
        updatedMember.setGender(Gender.DIVERSE);

        service.updateMember(MemberDTO.create(updatedMember), householdId);

        Mockito.verify(memberRepository, times(1)).updateMember(any());
    }
}
