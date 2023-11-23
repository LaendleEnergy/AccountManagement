package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.MemberService;
import at.fhv.master.laendleenergy.domain.Member;
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
        MemberDTO memberDTO = new MemberDTO(name, dateOfBirth, gender);
        memberService.addHouseholdMember(householdId, memberDTO);
    }

    @DELETE
    @Path("/remove/{householdId}")
    public void removeHouseholdMember(String memberId, String householdId) {
        memberService.removeHouseholdMember(memberId, householdId);
    }

    @GET
    @Path("/get/{householdId}")
    public List<Member> getAllMembersOfHousehold(String householdId) {
        return memberService.getAllMembersOfHousehold(householdId);
    }
}
