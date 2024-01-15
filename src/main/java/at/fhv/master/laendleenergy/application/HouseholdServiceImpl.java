package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
import at.fhv.master.laendleenergy.domain.events.HouseholdUpdatedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.HouseholdCreatedSerializer;
import at.fhv.master.laendleenergy.domain.serializer.HouseholdUpdatedSerializer;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.streams.publisher.HouseholdCreatedEventPublisher;
import at.fhv.master.laendleenergy.streams.publisher.HouseholdUpdatedEventPublisher;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@ApplicationScoped
public class HouseholdServiceImpl implements HouseholdService {

    @Inject
    HouseholdRepository householdRepository;
    @Inject
    MemberRepository memberRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    HouseholdUpdatedEventPublisher householdUpdatedEventPublisher;
    @Inject
    HouseholdCreatedEventPublisher householdCreatedEventPublisher;

    @Override
    @Transactional
    public String createHousehold(CreateHouseholdDTO householdDTO) throws JsonProcessingException {
        User user = new User(householdDTO.getEmailAddress(), passwordEncoder.encode(householdDTO.getPassword()), Role.ADMIN, householdDTO.getName(), Optional.empty(), Optional.empty(), null, null);

        List<Member> members = List.of(user);

        Household household = new Household(householdDTO.getDeviceId(), ElectricityPricingPlan.get(householdDTO.getPricingPlan()), members);
        user.setHouseholdId(household.getId());
        user.setDeviceId(household.getDeviceId());

        userRepository.addUser(user);

        HouseholdCreatedEvent event = new HouseholdCreatedEvent(UUID.randomUUID().toString(), user.getId(), user.getName(), household.getId(), LocalDateTime.now());
        householdCreatedEventPublisher.publishMessage(HouseholdCreatedSerializer.parse(event));

        return householdRepository.addHousehold(household);
    }

    @Override
    @Transactional
    public void deleteHousehold(String householdId) throws HouseholdNotFoundException {
        householdRepository.deleteHousehold(householdId);
    }

    @Override
    @Transactional
    public void updateHousehold(String householdId, HouseholdDTO householdDTO) throws HouseholdNotFoundException, JsonProcessingException {
        Household household = HouseholdDTO.create(householdId, householdDTO, memberRepository.getAllMembersOfHousehold(householdId));
        householdRepository.updateHousehold(household);

        HouseholdUpdatedEvent event = new HouseholdUpdatedEvent(UUID.randomUUID().toString(), household.getId(), LocalDateTime.now());
        householdUpdatedEventPublisher.publishMessage(HouseholdUpdatedSerializer.parse(event));
    }

    @Override
    public HouseholdDTO getHouseholdById(String householdId) throws HouseholdNotFoundException {
        return HouseholdDTO.create(householdRepository.getHouseholdById(householdId));
    }

    @Override
    public List<UserDTO> getUsersOfHousehold(String householdId) throws HouseholdNotFoundException {
        List<UserDTO> users = new LinkedList<>();

        for (Member m : memberRepository.getAllMembersOfHousehold(householdId)) {
            if (m instanceof User u) {
                users.add(UserDTO.create(u));
            }
        }

        return users;
    }
}
