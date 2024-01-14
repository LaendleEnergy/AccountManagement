package at.fhv.master.laendleenergy.integration;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import at.fhv.master.laendleenergy.view.HouseholdController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(HouseholdController.class)
@TestTransaction
public class HouseholdIntegrationTests {

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
    @InjectMock
    EntityManager entityManager;

    static String createHouseholdDTOJSONString = "{\"deviceId\":\"D1\",\"emailAddress\":\"email\",\"password\":\"password\",\"name\":\"name\",\"pricingPlan\":\"Normal\"}";
    static String householdDTOJSONString = "{\"deviceId\":\"D1\",\"pricingPlan\":\"Normal\", \"supplier\":\"VKW\"}";
    static HouseholdDTO householdDTO;
    private final String invalidJwtToken = "eyZraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAAOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpC3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJUdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjLzNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjUbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaGzsZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwian9pIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0NCYwNGZmZDFjIn0.XgV-PnqA_LB9OFFE4-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyCDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";
    static String householdId = "h1";
    static Household household;

    @BeforeEach
    public void setup() {
        household = new Household(householdId, "D1", ElectricityPricingPlan.DAYNIGHT, new LinkedList<>());
        household.setMembers(List.of(UserDTO.create(new UserDTO("email", "password", Role.USER.getName(), "name", LocalDate.of(2000, 2,2).toString(), Gender.FEMALE.getName()), household)));
        householdDTO = HouseholdDTO.create(household);
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);
    }

    @Test
    public void testGetUsersOfHousehold() {
        Query<Member> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Member.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(household.getMembers());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getUsersOfHousehold")
                .then()
                .statusCode(200);
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
    public void testCreateHousehold() {
        given()
                .contentType(ContentType.JSON)
                .body(createHouseholdDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/create")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateHousehold_CallerNotVerified() {
        given()
                .contentType(ContentType.JSON)
                .body(householdDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/update")
                .then()
                .statusCode(401);
    }

    @Test
    public void testDeleteHousehold() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/delete")
                .then()
                .statusCode(200);

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
    public void testGetHouseholdById() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);
        }

    @Test
    public void testGetHouseholdById_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/get")
                .then()
                .statusCode(401);
    }
}
