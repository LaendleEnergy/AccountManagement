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
import org.eclipse.microprofile.jwt.JsonWebToken;


@Path("/member")
public class MemberController {
    @Inject
    MemberService memberService;
    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response addHouseholdMember(MemberDTO memberDTO)
    {
        boolean hasJWT = jwt.getClaimNames() != null;
        System.out.println(jwt.containsClaim("householdId"));

        if (hasJWT && jwt.containsClaim("householdId")) {
            String householdId = jwt.getClaim("householdId");
            try {
                memberService.addHouseholdMember(memberDTO, householdId);
                return Response.ok().build();
            } catch (HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @DELETE
    @Path("/remove")
    @RolesAllowed("Admin")
    public Response removeHouseholdMember() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("memberId") && jwt.containsClaim("householdId")) {
            String memberId = jwt.getClaim("memberId");
            String householdId = jwt.getClaim("householdId");
            try {
                memberService.removeHouseholdMember(memberId, householdId);
                return Response.ok().build();
            } catch (HouseholdNotFoundException | MemberNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/get")
    @Authenticated
    public Response getAllMembersOfHousehold() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(memberService.getAllMembersOfHousehold(householdId)).build();
            } catch (HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/getMember")
    @Authenticated
    public Response getMemberById() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("memberId") && jwt.containsClaim("householdId")) {
            String memberId = jwt.getClaim("memberId");
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(memberService.getMemberById(memberId, householdId)).build();
            } catch (HouseholdNotFoundException | MemberNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response updateMember(MemberDTO memberDTO) {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("memberId") && jwt.containsClaim("householdId")) {
            String memberId = jwt.getClaim("memberId");
            String householdId = jwt.getClaim("householdId");
            try {
                memberService.updateMember(memberDTO, memberId, householdId);
                return Response.ok().build();
            } catch (MemberNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
