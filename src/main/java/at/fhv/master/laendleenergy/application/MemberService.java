package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface MemberService {
    void addHouseholdMember(MemberDTO memberDTO, String householdId) throws HouseholdNotFoundException, JsonProcessingException;
    void removeHouseholdMember(String memberId, String householdId) throws HouseholdNotFoundException, MemberNotFoundException, JsonProcessingException;
    List<MemberDTO> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException;
    MemberDTO getMemberById(String memberId) throws MemberNotFoundException, HouseholdNotFoundException;
    void updateMember(MemberDTO memberDTO, String householdId) throws MemberNotFoundException, HouseholdNotFoundException, JsonProcessingException;
}
