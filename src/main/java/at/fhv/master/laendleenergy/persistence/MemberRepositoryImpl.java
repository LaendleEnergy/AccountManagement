package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.*;

@ApplicationScoped
public class MemberRepositoryImpl implements MemberRepository {

    @Inject
    HouseholdRepository householdRepository;
    Map<String, Member> members;

    public MemberRepositoryImpl() {
        members = new HashMap<>();
        members.put("m1", new User("alice@example.com", "79XRn7pTF6sf33S8GGhkwL7gbs5bIAhuUULKmpdEA7U=", Role.ADMIN, "Alice", Optional.of(LocalDate.of(1990, 5, 15)), Optional.of(Gender.FEMALE), "h1"));
        members.put("m2", new Member("Bob",  Optional.of(LocalDate.of(1985, 8, 22)),  Optional.of(Gender.MALE), "h2"));
        members.put("m3", new Member("Charlie", Optional.of(LocalDate.of(1992, 2, 10)),  Optional.of(Gender.MALE), "h3"));
        members.put("m4", new Member("David", Optional.of(LocalDate.of(1988, 11, 30)),  Optional.of(Gender.MALE), "h4"));
        members.put("m5", new Member("Emma", Optional.of(LocalDate.of(1995, 4, 5)),  Optional.of(Gender.FEMALE), "h5"));
        members.put("m6", new Member("Frank", Optional.of(LocalDate.of(1983, 7, 18)),  Optional.of(Gender.MALE), "h6"));
        members.put("m7", new Member("Grace", Optional.of(LocalDate.of(1998, 9, 12)),  Optional.of(Gender.FEMALE), "h7"));
        members.put("m8", new Member("Henry", Optional.of(LocalDate.of(1982, 12, 8)),  Optional.of(Gender.MALE), "h8"));
        members.put("m9", new Member("Ivy", Optional.of(LocalDate.of(1993, 6, 25)),  Optional.of(Gender.FEMALE), "h9"));
        members.put("m10", new Member("Jack", Optional.of(LocalDate.of(1987, 3, 14)),  Optional.of(Gender.MALE), "h10"));
    }

    @Override
    public void addHouseholdMember(Member member) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(member.getHouseholdId());

        if (household != null) {
            household.addMember(member);
        } else {
            throw new HouseholdNotFoundException();
        }
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) throws HouseholdNotFoundException, MemberNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);

        if (household == null) {
            throw new HouseholdNotFoundException();
        } else if (!household.getMembers().containsKey(memberId)) {
            throw new MemberNotFoundException();
        } else {
            household.removeMember(memberId);
        }
    }

    @Override
    public Map<String,Member> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);

        if (household != null) {
            return householdRepository.getHouseholdById(householdId).getMembers();
        } else {
            throw new HouseholdNotFoundException();
        }
    }

    @Override
    public Member getMemberById(String memberId, String householdId) throws MemberNotFoundException, HouseholdNotFoundException {
        Member member = getAllMembersOfHousehold(householdId).get(memberId);

        if (member != null) {
            return member;
        }
        throw new MemberNotFoundException();
    }

    @Override
    public void updateMember(Member member) throws MemberNotFoundException {
        Member m = members.get(member.getId());

        if (m != null) {
            members.replace(member.getId(), member);
        } else {
            throw new MemberNotFoundException();
        }
    }

    @Override
    public List<Member> getAllMembers() {
        return new LinkedList<>(members.values());
    }
}
