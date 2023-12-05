package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.serializer.PricingPlanSerializer;
import at.fhv.master.laendleenergy.domain.serializer.SupplierSerializer;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import java.util.*;


@Path("/household")
public class HouseholdController {

    @Inject
    HouseholdService householdService;

    @PermitAll
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHousehold(CreateHouseholdDTO createHouseholdDTO)
    {
        try {
            householdService.createHousehold(createHouseholdDTO);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHousehold(HouseholdDTO householdDTO)
    {
        try {
            householdService.updateHousehold(householdDTO);
            return Response.ok(true).build();
        } catch (HouseholdNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/delete/{deviceId}")
    public Response deleteHousehold(String deviceId) {
        try {
            householdService.deleteHousehold(deviceId);
            return Response.ok(true).build();
        } catch (HouseholdNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/{deviceId}")
    public Response getHouseholdById(String deviceId) {
        try {
            return Response.ok(householdService.getHouseholdById(deviceId)).build();
        } catch (HouseholdNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/suppliers")
    public RestResponse<List<String>> getSuppliers() {
        try {
            return RestResponse.ResponseBuilder
                    .ok(SupplierSerializer.parse(), MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/get/pricingPlans")
    public RestResponse<List<String>> getPricingPlans() {
        try {
            return RestResponse.ResponseBuilder
                    .ok(PricingPlanSerializer.parse(), MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
