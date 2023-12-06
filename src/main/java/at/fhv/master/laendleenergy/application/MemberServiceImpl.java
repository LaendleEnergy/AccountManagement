package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
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
    public void addHouseholdMember(MemberDTO memberDTO) throws HouseholdNotFoundException {
        memberRepository.addHouseholdMember(MemberDTO.create(memberDTO));
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) throws HouseholdNotFoundException, MemberNotFoundException {
        memberRepository.removeHouseholdMember(memberId, householdId);
    }

    @Override
    public List<MemberDTO> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException {
        List<Member> members = new LinkedList<>(memberRepository.getAllMembersOfHousehold(householdId).values());
        List<MemberDTO> memberDTOS = new LinkedList<>();

        for (Member m : members) {
            memberDTOS.add(MemberDTO.create(m));
        }

        return memberDTOS;
    }

    @Override
    public MemberDTO getMemberById(String memberId, String householdId) throws MemberNotFoundException, HouseholdNotFoundException {
        return MemberDTO.create(memberRepository.getMemberById(memberId, householdId));
    }
}
