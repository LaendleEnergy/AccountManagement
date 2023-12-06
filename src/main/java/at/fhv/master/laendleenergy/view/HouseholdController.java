package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.PricingPlanSerializer;
import at.fhv.master.laendleenergy.domain.serializer.SupplierSerializer;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;
import java.util.*;


@Path("/household")
public class HouseholdController {

    @Inject
    HouseholdService householdService;
    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response createHousehold(CreateHouseholdDTO createHouseholdDTO)
    {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT) {
            try {
                String id = householdService.createHousehold(createHouseholdDTO);
                return Response.ok(id).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response updateHousehold(HouseholdDTO householdDTO)
    {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT) {
            try {
                householdService.updateHousehold(householdDTO);
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
    public Response deleteHousehold() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
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
    public Response getHouseholdById() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(householdService.getHouseholdById(householdId)).build();
            } catch (HouseholdNotFoundException e) {
                e.printStackTrace();
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/getSuppliers")
    @Authenticated
    public Response getSuppliers() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT) {
            try {
                return Response.ok(SupplierSerializer.parse(), MediaType.APPLICATION_JSON).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/getPricingPlans")
    @Authenticated
    public Response getPricingPlans() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT) {
            try {
                return Response.ok(PricingPlanSerializer.parse(), MediaType.APPLICATION_JSON).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
