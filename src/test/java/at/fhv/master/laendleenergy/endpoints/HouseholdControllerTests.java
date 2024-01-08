package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.application.authentication.AuthenticationService;
import at.fhv.master.laendleenergy.view.HouseholdController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.inject.Inject;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(HouseholdController.class)
public class HouseholdControllerTests {
    @InjectMock
    HouseholdService householdService;
    @InjectMock
    JsonWebToken jwt;

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
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";

    @Test
    public void testCreateHousehold() {
        String householdDTO = "{\"deviceId\":\"d1\",\"emailAddress\":\"email\",\"password\":\"password\",\"name\":\"name\",\"pricingPlan\":\"Normal\"}";

        given()
                .contentType(ContentType.JSON)
                .body(householdDTO)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/create")
                .then()
                .statusCode(200);

        Mockito.verify(householdService, times(1)).createHousehold(any());
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

}
