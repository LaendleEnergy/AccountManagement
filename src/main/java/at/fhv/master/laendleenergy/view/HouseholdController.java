package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/household")
public class HouseholdController {

    @Inject
    HouseholdService householdService;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createHousehold(
            @FormParam("email") String email,
            @FormParam("name") String name,
            @FormParam("password") String password,
            @FormParam("pricingPlan") String pricingPlan,
            @FormParam("deviceId") String deviceId)
    {
        UserDTO userDTO = new UserDTO(email, password, "Admin", name, "", "");
        HouseholdDTO householdDTO = new HouseholdDTO(UUID.randomUUID().toString(), pricingPlan, deviceId, "", "");
        householdService.createHousehold(householdDTO, userDTO);
    }

    @DELETE
    @Path("/deleteHousehold/{householdId}")
    public void deleteHousehold(String householdId) {
        this.householdService.deleteHousehold(householdId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addHouseholdMember(
            @FormParam("householdId") String householdId,
            @FormParam("name") String name,
            @FormParam("dateOfBirth") String dateOfBirth,
            @FormParam("gender") String gender)
    {
        MemberDTO memberDTO = new MemberDTO(name, dateOfBirth, gender);
        householdService.addHouseholdMember(householdId, memberDTO);
    }

    @DELETE
    @Path("/removeHouseholdMember/{householdId}")
    public void removeHouseholdMember(String memberId, String householdId) {
        this.householdService.removeHouseholdMember(memberId, householdId);
    }

    @GET
    @Path("/get/{householdId}")
    public Household getHouseholdById(String householdId) {
        return householdService.getHouseholdById(householdId);
    }
}
