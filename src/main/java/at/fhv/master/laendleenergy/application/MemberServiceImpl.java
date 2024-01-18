package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberRemovedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.MemberAddedSerializer;
import at.fhv.master.laendleenergy.domain.serializer.MemberRemovedSerializer;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.application.streams.publisher.MemberAddedEventPublisher;
import at.fhv.master.laendleenergy.application.streams.publisher.MemberRemovedEventPublisher;
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
    MemberAddedEventPublisher memberAddedEventPublisher;
    @Inject
    MemberRemovedEventPublisher memberRemovedEventPublisher;

    @Override
    @Transactional
    public void addHouseholdMember(MemberDTO memberDTO, String householdId) throws HouseholdNotFoundException, JsonProcessingException {
        Household household = householdRepository.getHouseholdById(householdId);
        String id = UUID.randomUUID().toString();
        memberRepository.addHouseholdMember(memberDTO.toMember(id, household));

        MemberAddedEvent event = new MemberAddedEvent(UUID.randomUUID().toString(), id, memberDTO.getName(), householdId, LocalDateTime.now());
        memberAddedEventPublisher.publishMessage(MemberAddedSerializer.parse(event));
    }

    @Override
    @Transactional
    public void removeHouseholdMember(String memberId, String householdId) throws MemberNotFoundException, JsonProcessingException {
        memberRepository.removeHouseholdMember(memberId);

        MemberRemovedEvent event = new MemberRemovedEvent(UUID.randomUUID().toString(), memberId, householdId, LocalDateTime.now());
        memberRemovedEventPublisher.publishMessage(MemberRemovedSerializer.parse(event));
    }

    @Override
    public List<MemberDTO> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException {
        List<Member> members = memberRepository.getAllMembersOfHousehold(householdId);
        List<MemberDTO> memberDTOS = new LinkedList<>();

        for (Member m : members) {
            if (! (m instanceof User)) {
                memberDTOS.add(MemberDTO.create(m));
            }
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
        memberRepository.updateMember(memberDTO.toMember(household));
    }
}
