package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.MemberService;
import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import at.fhv.master.laendleenergy.view.MemberController;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(MemberController.class)
public class MemberControllerTests {
    @InjectMock
    MemberService memberService;

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
    static String memberDTOJSONString = "{\"id\":\"memberid\",\"name\":\"membername\",\"dateOfBirth\":\"2020-1-1\",\"gender\":\"weiblich\"}";
    static MemberDTO memberDTO;
    private final String invalidJwtToken = "eyZraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAAOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpC3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJUdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjLzNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjUbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaGzsZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwian9pIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0NCYwNGZmZDFjIn0.XgV-PnqA_LB9OFFE4-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyCDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";

    @BeforeEach
    public void setup() {
        memberDTO = new MemberDTO("memberid", "membername", LocalDate.of(2000, 1,1).toString(), Gender.FEMALE.getName());
    }

    @Test
    public void testAddHouseholdMember() throws HouseholdNotFoundException, JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/add")
                .then()
                .statusCode(200);

        Mockito.verify(memberService, times(1)).addHouseholdMember(any(), anyString());
    }

    @Test
    public void testAddHouseholdMember_CalledNotVerified() {
        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/add")
                .then()
                .statusCode(401);
    }

    @Test
    public void testAddHouseholdMember_HouseholdNotFoundException() throws HouseholdNotFoundException, JsonProcessingException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(memberService).addHouseholdMember(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/add")
                .then()
                .statusCode(404);
    }

    @Test
    public void testAddHouseholdMember_InternalServerError() throws HouseholdNotFoundException, JsonProcessingException {
        Mockito.doThrow(NullPointerException.class).when(memberService).addHouseholdMember(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/add")
                .then()
                .statusCode(500);
    }

    @Test
    public void testRemoveHouseholdMember() throws HouseholdNotFoundException, MemberNotFoundException {
        given()
                .pathParam("memberId", memberDTO.getId())
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/remove/{memberId}")
                .then()
                .statusCode(200);

        Mockito.verify(memberService, times(1)).removeHouseholdMember(anyString(), anyString());
    }

    @Test
    public void testRemoveHouseholdMember_CalledNotVerified() {
        given()
                .pathParam("memberId", memberDTO.getId())
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().delete("/remove/{memberId}")
                .then()
                .statusCode(401);
    }

    @Test
    public void testRemoveHouseholdMember_NotFoundException() throws HouseholdNotFoundException, MemberNotFoundException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(memberService).removeHouseholdMember(anyString(), anyString());

        given()
                .pathParam("memberId", memberDTO.getId())
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/remove/{memberId}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testRemoveHouseholdMember_InternalServerError() throws HouseholdNotFoundException, MemberNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(memberService).removeHouseholdMember(anyString(), anyString());

        given()
                .pathParam("memberId", memberDTO.getId())
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/remove/{memberId}")
                .then()
                .statusCode(500);
    }

    @Test
    public void testUpdateHouseholdMember() throws HouseholdNotFoundException, MemberNotFoundException {
        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(200);

        Mockito.verify(memberService, times(1)).updateMember(any(), anyString());
    }

    @Test
    public void testUpdateHouseholdMember_CalledNotVerified() {
        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/update")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateHouseholdMember_NotFoundException() throws HouseholdNotFoundException, MemberNotFoundException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(memberService).updateMember(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateHouseholdMember_InternalServerError() throws HouseholdNotFoundException, MemberNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(memberService).updateMember(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(500);
    }

    @Test
    public void testGetMemberById() throws MemberNotFoundException, HouseholdNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getMember")
                .then()
                .statusCode(200);

        Mockito.verify(memberService, times(1)).getMemberById(anyString());
    }

    @Test
    public void testGetMemberById_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/getMember")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetMemberById_MemberNotFoundException() throws HouseholdNotFoundException, MemberNotFoundException {
        Mockito.doThrow(MemberNotFoundException.class).when(memberService).getMemberById(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getMember")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetMemberById_InternalServerError() throws HouseholdNotFoundException, MemberNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(memberService).getMemberById(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getMember")
                .then()
                .statusCode(500);
    }

    @Test
    public void testGetAllMembersOfHousehold() throws HouseholdNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);

        Mockito.verify(memberService, times(1)).getAllMembersOfHousehold(anyString());
    }

    @Test
    public void testGetAllMembersOfHousehold_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/get")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetAllMembersOfHousehold_MemberNotFoundException() throws HouseholdNotFoundException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(memberService).getAllMembersOfHousehold(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetAllMembersOfHousehold_InternalServerError() throws HouseholdNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(memberService).getAllMembersOfHousehold(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(500);
    }
}
