package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import java.util.List;

public interface MemberService {
    void addHouseholdMember(String householdId, MemberDTO memberDTO);
    void removeHouseholdMember(String memberId, String householdId);
    List<MemberDTO> getAllMembersOfHousehold(String householdId);
}
