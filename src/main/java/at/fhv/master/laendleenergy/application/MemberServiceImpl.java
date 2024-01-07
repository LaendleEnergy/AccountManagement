package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class MemberServiceImpl implements MemberService {
    @Inject
    MemberRepository memberRepository;

    @Inject
    HouseholdRepository householdRepository;

    @Override
    @Transactional
    public void addHouseholdMember(MemberDTO memberDTO, String householdId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);
        memberRepository.addHouseholdMember(MemberDTO.create(memberDTO, household));
    }

    @Override
    @Transactional
    public void removeHouseholdMember(String memberId, String householdId) throws MemberNotFoundException {
        memberRepository.removeHouseholdMember(memberId);
    }

    @Override
    public List<MemberDTO> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException {
        List<Member> members = memberRepository.getAllMembersOfHousehold(householdId);
        List<MemberDTO> memberDTOS = new LinkedList<>();

        for (Member m : members) {
            memberDTOS.add(MemberDTO.create(m));
        }

        return memberDTOS;
    }

    @Override
    public MemberDTO getMemberById(String memberId) throws MemberNotFoundException {
        return MemberDTO.create(memberRepository.getMemberById(memberId));
    }

    @Override
    @Transactional
    public void updateMember(MemberDTO memberDTO, String householdId) throws MemberNotFoundException, HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);
        memberRepository.updateMember(MemberDTO.create(memberDTO, household));
    }
}
