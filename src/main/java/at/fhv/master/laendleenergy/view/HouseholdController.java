package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Supplier;
import at.fhv.master.laendleenergy.domain.serializer.PricingPlanSerializer;
import at.fhv.master.laendleenergy.domain.serializer.SupplierSerializer;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/household")
public class HouseholdController {

    @Inject
    HouseholdService householdService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/create")
    public void createHousehold(
            @FormParam("email") String email,
            @FormParam("name") String name,
            @FormParam("password") String password,
            @FormParam("pricingPlan") String pricingPlan,
            @FormParam("deviceId") String deviceId)
    {
        UserDTO userDTO = new UserDTO(email, password, "Admin", name, null, null);
        HouseholdDTO householdDTO = new HouseholdDTO(UUID.randomUUID().toString(), pricingPlan, deviceId, "", "");
        householdService.createHousehold(householdDTO, userDTO);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/update")
    public void updateHousehold(
            @FormParam("id") String id,
            @FormParam("pricingPlan") String pricingPlan,
            @FormParam("deviceId") String deviceId)
    {
        HouseholdDTO householdDTO = new HouseholdDTO(id, pricingPlan, deviceId);
        householdService.updateHousehold(householdDTO, householdService.getHouseholdById(id).getMembers());
    }


    @DELETE
    @Path("/delete/{householdId}")
    public void deleteHousehold(String householdId) {
        this.householdService.deleteHousehold(householdId);
    }

    @GET
    @Path("/get/{householdId}")
    public Household getHouseholdById(String householdId) {
        return householdService.getHouseholdById(householdId);
    }

    @GET
    @Path("/get/suppliers")
    public RestResponse<List<String>> getSuppliers() throws JsonProcessingException {
        return RestResponse.ResponseBuilder
                .ok(SupplierSerializer.parse(), MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Path("/get/pricingPlans")
    public RestResponse<List<String>> getPricingPlans() throws JsonProcessingException {
        return RestResponse.ResponseBuilder
                .ok(PricingPlanSerializer.parse(), MediaType.APPLICATION_JSON)
                .build();
    }

}
