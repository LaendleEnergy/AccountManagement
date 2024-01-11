package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.MemberSerializer;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.streams.publisher.MemberAddedEventPublisher;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MemberServiceImpl implements MemberService {
    @Inject
    MemberRepository memberRepository;

    @Inject
    HouseholdRepository householdRepository;
    @Inject
    MemberAddedEventPublisher publisher;

    @Override
    @Transactional
    public void addHouseholdMember(MemberDTO memberDTO, String householdId) throws HouseholdNotFoundException, JsonProcessingException {
        Household household = householdRepository.getHouseholdById(householdId);
        memberRepository.addHouseholdMember(MemberDTO.create(memberDTO, household));

        MemberAddedEvent event = new MemberAddedEvent(UUID.randomUUID().toString(), memberDTO.getId(), memberDTO.getName(), householdId, LocalDateTime.now());
        publisher.publishMessage(MemberSerializer.parse(event));
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
