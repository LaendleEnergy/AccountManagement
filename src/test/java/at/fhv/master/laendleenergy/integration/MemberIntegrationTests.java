package at.fhv.master.laendleenergy.integration;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import at.fhv.master.laendleenergy.view.MemberController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static io.restassured.RestAssured.given;
import org.mockito.Mockito;

@QuarkusTest
@TestHTTPEndpoint(MemberController.class)
@TestTransaction
public class MemberIntegrationTests {

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
    static String memberDTOJSONString = "{\"id\":\"1\",\"name\":\"Alice\",\"dateOfBirth\":\"2000-10-10\",\"gender\":\"weiblich\"}";
    static MemberDTO memberDTO;
    static Member member;
    static String memberId = "1";
    static String householdId = "h1";
    static Household household;
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";

    @InjectMock
    EntityManager entityManager;

    @BeforeEach
    public void setup() {
        member = new Member(memberId, "Alice", Optional.of(LocalDate.of(2000, 10, 10)), Optional.of(Gender.FEMALE), "", "");
        memberDTO = MemberDTO.create(member);
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, new LinkedList<>());
        household.setMembers(List.of(MemberDTO.create(memberDTO, household)));
        member.setHouseholdId(household.getId());
        member.setDeviceId(household.getDeviceId());

        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);
        Mockito.when(entityManager.find(Member.class, memberId)).thenReturn(member);
    }

    @Test
    public void testAddHouseholdMember() {
        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/add")
                .then()
                .statusCode(200);
   }

    @Test
    public void testRemoveHouseholdMember() {
        given()
                .pathParam("memberId", memberDTO.getId())
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/remove/{memberId}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateHouseholdMember() {
        given()
                .contentType(ContentType.JSON)
                .body(memberDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(200);


    }

    @Test
    public void testGetMemberById() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getMember")
                .then()
                .statusCode(200);
    }


    @Test
    public void testGetAllMembersOfHousehold() {
        Query<Member> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Member.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(household.getMembers());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);
    }
}
