package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MemberServiceImpl implements MemberService {
    @Inject
    MemberRepository memberRepository;

    @Override
    public void addHouseholdMember(String householdId, MemberDTO memberDTO) {
        Member member = Member.create(memberDTO);
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
