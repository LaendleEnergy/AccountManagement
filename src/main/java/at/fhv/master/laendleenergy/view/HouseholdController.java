package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.application.authentication.AuthenticationService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.PricingPlanSerializer;
import at.fhv.master.laendleenergy.domain.serializer.SupplierSerializer;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import java.security.Principal;


@Path("/household")
public class HouseholdController {

    @Inject
    HouseholdService householdService;
    @Inject
    JsonWebToken jwt;
    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response createHousehold(CreateHouseholdDTO createHouseholdDTO)
    {
        try {
            String id = householdService.createHousehold(createHouseholdDTO);
            return Response.ok(id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response updateHousehold(@Context SecurityContext ctx, HouseholdDTO householdDTO)
    {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String householdId = jwt.getClaim("householdId");
            try {
                householdService.updateHousehold(householdId, householdDTO);
                return Response.ok(true).build();
            } catch (HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @DELETE
    @Path("/delete")
    @RolesAllowed("Admin")
    public Response deleteHousehold(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String householdId = jwt.getClaim("householdId");
            try {
                householdService.deleteHousehold(householdId);
                return Response.ok(true).build();
            } catch (HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/get")
    @RolesAllowed("Admin")
    public Response getHouseholdById(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(householdService.getHouseholdById(householdId)).build();
            } catch (HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/getSuppliers")
    @PermitAll
    public Response getSuppliers() {
        try {
            return Response.ok(SupplierSerializer.parse(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/getPricingPlans")
    @PermitAll
    public Response getPricingPlans() {
        try {
            return Response.ok(PricingPlanSerializer.parse(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/getUsersOfHousehold")
    @RolesAllowed("Admin")
    public Response getUsersOfHousehold(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && authenticationService.verifiedCaller(name, jwt.getName())) {
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(householdService.getUsersOfHousehold(householdId), MediaType.APPLICATION_JSON).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
