package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import java.util.List;

public interface MemberService {
    void addHouseholdMember(MemberDTO memberDTO, String householdId) throws HouseholdNotFoundException;
    void removeHouseholdMember(String memberId, String householdId) throws HouseholdNotFoundException, MemberNotFoundException;
    List<MemberDTO> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException;
    MemberDTO getMemberById(String memberId, String householdId) throws MemberNotFoundException, HouseholdNotFoundException;
    void updateMember(MemberDTO memberDTO, String memberId, String householdId) throws MemberNotFoundException, HouseholdNotFoundException;
}
