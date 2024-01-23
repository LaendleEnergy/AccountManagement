package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.application.streams.publisher.MemberUpdatedEventPublisher;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.events.MemberUpdatedEvent;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.MemberUpdatedSerializer;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.CreateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UpdateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;
    @Inject
    PBKDF2Encoder passwordEncoder;
    @Inject
    HouseholdRepository householdRepository;
    @Inject
    MemberUpdatedEventPublisher memberUpdatedEventPublisher;

    @Override
    @Transactional
    public void createUser(CreateUserDTO createUserDTO, String householdId) throws HouseholdNotFoundException {
        UserDTO userDTO = new UserDTO(createUserDTO.getEmailAddress(), passwordEncoder.encode(createUserDTO.getPassword()), "User", createUserDTO.getName(), "", "");
        Household household = householdRepository.getHouseholdById(householdId);
        userRepository.addUser(userDTO.toUser(household));
    }

    @Override
    @Transactional
    public void deleteUser(String userId) throws UserNotFoundException {
        userRepository.deleteUser(userId);
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserDTO userDTO, String memberId, String householdId) throws UserNotFoundException, HouseholdNotFoundException, JsonProcessingException {
        User userData = userRepository.getUserById(memberId);
        Household household = householdRepository.getHouseholdById(householdId);
        User user = userDTO.toUser(memberId, userData.getRole(), household);
        userRepository.updateUser(user);

        MemberUpdatedEvent event = new MemberUpdatedEvent(UUID.randomUUID().toString(), memberId, userDTO.getName(), householdId, LocalDateTime.now());
        memberUpdatedEventPublisher.publishMessage(MemberUpdatedSerializer.parse(event));
    }

    @Override
    public UserDTO getUserById(String id) throws UserNotFoundException {
        return UserDTO.create(userRepository.getUserById(id));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new LinkedList<>();

        for (User u : userRepository.getAllUsers()) {
            users.add(UserDTO.create(u));
        }

        return users;
    }

    @Override
    public boolean validateEmail(String email) {
        return userRepository.validateEmail(email);
    }
}
