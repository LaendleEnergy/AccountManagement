package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class MemberServiceImpl implements MemberService {
    @Inject
    MemberRepository memberRepository;

    @Override
    public void addHouseholdMember(String householdId, MemberDTO memberDTO) {
        memberRepository.addHouseholdMember(Member.create(memberDTO), householdId);
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) {
        memberRepository.removeHouseholdMember(memberId, householdId);
    }

    @Override
    public List<MemberDTO> getAllMembersOfHousehold(String householdId) {
        List<Member> members = new LinkedList<>(memberRepository.getAllMembersOfHousehold(householdId).values());
        List<MemberDTO> memberDTOS = new LinkedList<>();

        for (Member m : members) {
            memberDTOS.add(MemberDTO.create(m));
        }

        return memberDTOS;
    }
}
