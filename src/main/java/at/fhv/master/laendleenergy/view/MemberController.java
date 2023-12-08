package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.MemberService;
import at.fhv.master.laendleenergy.application.authentication.AuthenticationService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.security.Principal;


@Path("/member")
public class MemberController {
    @Inject
    MemberService memberService;
    @Inject
    JsonWebToken jwt;
    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response addHouseholdMember(@Context SecurityContext ctx, MemberDTO memberDTO)
    {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
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
    public Response removeHouseholdMember(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("memberId") && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
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
    public Response getAllMembersOfHousehold(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
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
    public Response getMemberById(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("memberId") && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
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
    public Response updateMember(@Context SecurityContext ctx, MemberDTO memberDTO) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("memberId") && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String memberId = jwt.getClaim("memberId");
            String householdId = jwt.getClaim("householdId");
            try {
                memberService.updateMember(memberDTO, memberId, householdId);
                return Response.ok().build();
            } catch (MemberNotFoundException | HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
