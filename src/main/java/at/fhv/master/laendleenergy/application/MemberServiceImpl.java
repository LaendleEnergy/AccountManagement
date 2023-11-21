package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MemberServiceImpl implements MemberService {
    @Inject
    MemberRepository memberRepository;

    @Override
    public void addHouseholdMember(String householdId, MemberDTO memberDTO) {
        Member member = new Member(memberDTO.getName(), Optional.of(LocalDate.parse(memberDTO.getDateOfBirth())), Optional.of(Gender.valueOf(memberDTO.getGender())));
        memberRepository.addHouseholdMember(member, householdId);
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) {
        memberRepository.removeHouseholdMember(memberId, householdId);
    }

    @Override
    public List<Member> getAllMembersOfHousehold(String householdId) {
        return memberRepository.getAllMembersOfHousehold(householdId);
    }
}
