package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.MemberService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/member")
public class MemberController {
    @Inject
    MemberService memberService;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addHouseholdMember(MemberDTO memberDTO)
    {
        try {
            memberService.addHouseholdMember(memberDTO);
            return Response.ok().build();
        } catch (HouseholdNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/remove/{householdId}")
    public Response removeHouseholdMember(String memberId, String householdId) {
        try {
            memberService.removeHouseholdMember(memberId, householdId);
            return Response.ok().build();
        } catch (HouseholdNotFoundException | MemberNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/{householdId}")
    public Response getAllMembersOfHousehold(String householdId) {
        try {
            return Response.ok(memberService.getAllMembersOfHousehold(householdId)).build();
        } catch (HouseholdNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
