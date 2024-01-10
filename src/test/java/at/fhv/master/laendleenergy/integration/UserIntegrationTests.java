package at.fhv.master.laendleenergy.integration;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import at.fhv.master.laendleenergy.view.UserController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(UserController.class)
@TestTransaction
public class UserIntegrationTests {
    @InjectMock
    EntityManager entityManager;
    static String userDTOJSONString = "{\"id\":\"1\",\"emailAddress\":\"email@test.com\",\"password\":\"password\",\"role\":\"Admin\",\"name\":\"membername\",\"dateOfBirth\":\"2020-1-1\",\"gender\":\"weiblich\"}";
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";

    static UserDTO userDTO;
    static User user;
    static String userId = "1";
    static String householdId = "h1";
    static Household household;

    @BeforeEach
    public void setup() {
        user = new User(userId, "email", "pw", Role.ADMIN, "Alice", Optional.of(LocalDate.of(2000, 10, 10)), Optional.of(Gender.FEMALE), null);
        userDTO = UserDTO.create(user);
        household = new Household(householdId, "d1", ElectricityPricingPlan.DAYNIGHT, List.of(UserDTO.create(userDTO, household)));
        user.setHousehold(household);

        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);
        Mockito.when(entityManager.find(User.class, userId)).thenReturn(user);
    }

    @Test
    public void testValidateEmail() {
        String emailDTOJSONString = "{\"email\":\"alice@example.com\"}";

        List<User> users = List.of(user);
        Query<User> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(users);


        given()
                .contentType(ContentType.JSON)
                .body(emailDTOJSONString)
                .when().post("/validateEmail")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateUser() {
        given()
                .contentType(ContentType.JSON)
                .body(userDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/create")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/delete")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateUser() {
        String updateUserDTOJSONString = "{\"emailAddress\":\"email@test.com.new\",\"password\":\"passwordnew\",\"name\":\"membername\",\"dateOfBirth\":\"2020-10-10\",\"gender\":\"männlich\"}";

        given()
                .contentType(ContentType.JSON)
                .body(updateUserDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetUserById() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = List.of(user);
        Query<User> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(users);

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getAll")
                .then()
                .statusCode(200);
    }
}
