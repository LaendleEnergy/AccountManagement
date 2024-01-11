package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Supplier;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.HouseholdController;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(HouseholdController.class)
public class HouseholdControllerTests {
    @InjectMock
    HouseholdService householdService;

    /*
    {
      "iss": "https://ard333.com",
      "sub": "alice@example.com",
      "iat": 1704711800,
      "exp": 36001704711800,
      "groups": [
        "Admin"
      ],
      "memberId": "1",
      "householdId": "h1",
      "deviceId": "D1",
      "jti": "c9a62faa-0b1e-4c7b-9704-9647f04ffd1c"
    }
     */
    static String createHouseholdDTOJSONString = "{\"deviceId\":\"d1\",\"emailAddress\":\"email\",\"password\":\"password\",\"name\":\"name\",\"pricingPlan\":\"Normal\"}";
    static String householdDTOJSONString = "{\"deviceId\":\"d1\",\"pricingPlan\":\"Tag/Nacht\", \"supplier\":\"VKW\"}";
    static HouseholdDTO householdDTO;
    private final String invalidJwtToken = "eyZraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAAOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpC3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJUdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjLzNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjUbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaGzsZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwian9pIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0NCYwNGZmZDFjIn0.XgV-PnqA_LB9OFFE4-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyCDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";

    @BeforeEach
    public void setup() {
        householdDTO = new HouseholdDTO("d1", ElectricityPricingPlan.DAYNIGHT.getName(), Supplier.VKW.getName());
    }

    @Test
    public void testCreateHousehold() throws JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(createHouseholdDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/create")
                .then()
                .statusCode(200);

        Mockito.verify(householdService, times(1)).createHousehold(any());
    }

    @Test
    public void testCreateHouseholdInternalServerError() throws JsonProcessingException {
        Mockito.when(householdService.createHousehold(any())).thenThrow(NullPointerException.class);

        given()
                .contentType(ContentType.JSON)
                .body(createHouseholdDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/create")
                .then()
                .statusCode(500);
    }

    @Test
    public void testUpdateHousehold() throws HouseholdNotFoundException, JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(householdDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(200);

        Mockito.verify(householdService, times(1)).updateHousehold(anyString(), any());
    }

    @Test
    public void testUpdateHousehold_CalledNotVerified() {
        given()
                .contentType(ContentType.JSON)
                .body(householdDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/update")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateHousehold_HouseholdNotFoundException() throws HouseholdNotFoundException, JsonProcessingException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(householdService).updateHousehold(anyString(), any());

        given()
                .contentType(ContentType.JSON)
                .body(householdDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteHousehold() throws HouseholdNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/delete")
                .then()
                .statusCode(200);

        Mockito.verify(householdService, times(1)).deleteHousehold(anyString());
    }

    @Test
    public void testDeleteHousehold_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().delete("/delete")
                .then()
                .statusCode(401);
    }

    @Test
    public void testDeleteHousehold_HouseholdNotFoundException() throws HouseholdNotFoundException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(householdService).deleteHousehold(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/delete")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetHouseholdById() throws HouseholdNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);

        Mockito.verify(householdService, times(1)).getHouseholdById(anyString());
    }

    @Test
    public void testGetHouseholdById_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/get")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetHouseholdById_HouseholdNotFoundException() throws HouseholdNotFoundException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(householdService).getHouseholdById(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetPricingPlans() {
        given()
                .when().get("/getPricingPlans")
                .then()
                .statusCode(200);
    }


    @Test
    public void testGetSuppliers() {
        given()
                .when().get("/getSuppliers")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetUsersOfHouseholdBy() throws HouseholdNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getUsersOfHousehold")
                .then()
                .statusCode(200);

        Mockito.verify(householdService, times(1)).getUsersOfHousehold(anyString());
    }

    @Test
    public void testGetUsersOfHousehold_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/getUsersOfHousehold")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetUsersOfHousehold_HouseholdNotFoundException() throws HouseholdNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(householdService).getUsersOfHousehold(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getUsersOfHousehold")
                .then()
                .statusCode(500);
    }
}
