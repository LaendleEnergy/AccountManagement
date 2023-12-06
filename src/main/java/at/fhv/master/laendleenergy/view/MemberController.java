package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.MemberService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed("Admin")
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
    @Path("/remove/{memberId}/{householdId}")
    @RolesAllowed("Admin")
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
    @Authenticated
    public Response getAllMembersOfHousehold(String householdId) {
        try {
            return Response.ok(memberService.getAllMembersOfHousehold(householdId)).build();
        } catch (HouseholdNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/{memberId}/{householdId}")
    @Authenticated
    public Response getMemberById(String memberId, String householdId) {
        try {
            return Response.ok(memberService.getMemberById(memberId, householdId)).build();
        } catch (HouseholdNotFoundException | MemberNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/update")
    @RolesAllowed("Admin")
    public Response updateMember(MemberDTO memberDTO) {
        try {
            memberService.updateMember(memberDTO);
            return Response.ok().build();
        } catch (MemberNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
