package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.MemberService;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/member")
public class MemberController {
    @Inject
    MemberService memberService;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addHouseholdMember(
            @FormParam("householdId") String householdId,
            @FormParam("name") String name,
            @FormParam("dateOfBirth") String dateOfBirth,
            @FormParam("gender") String gender)
    {
        try {
            MemberDTO memberDTO = new MemberDTO(name, dateOfBirth, gender);
            memberService.addHouseholdMember(householdId, memberDTO);
        } catch (HouseholdNotFoundException e) {

        }
    }

    @DELETE
    @Path("/remove/{householdId}")
    public void removeHouseholdMember(String memberId, String householdId) {
        try {
            memberService.removeHouseholdMember(memberId, householdId);
        } catch (HouseholdNotFoundException e) {

        }
    }

    @GET
    @Path("/get/{householdId}")
    public List<MemberDTO> getAllMembersOfHousehold(String householdId) {
        try {
            return memberService.getAllMembersOfHousehold(householdId);
        } catch (HouseholdNotFoundException e) {
            return null;
        }
    }
}
