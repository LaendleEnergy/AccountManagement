package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.domain.serializer.PricingPlanSerializer;
import at.fhv.master.laendleenergy.domain.serializer.SupplierSerializer;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.CreateUserDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
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

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createHousehold(CreateHouseholdDTO createHouseholdDTO)
    {
        householdService.createHousehold(createHouseholdDTO);
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void updateHousehold(
            @FormParam("id") String id,
            @FormParam("pricingPlan") String pricingPlan,
            @FormParam("deviceId") String deviceId)
    {
        HouseholdDTO householdDTO = new HouseholdDTO(pricingPlan, deviceId);
        householdService.updateHousehold(id, householdDTO);
    }


    @DELETE
    @Path("/delete/{householdId}")
    public Response deleteHousehold(String householdId) {
        try {
            householdService.deleteHousehold(householdId);
            return Response.ok(true).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/{householdId}")
    public Response getHouseholdById(String householdId) {
        try {
            return Response.ok(householdService.getHouseholdById(householdId)).build();
        } catch (Exception e) {
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
