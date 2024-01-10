package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.UserService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.view.UserController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(UserController.class)
@TestTransaction
public class UserControllerTests {
    @InjectMock
    UserService userService;
    static String userDTOJSONString = "{\"emailAddress\":\"email@test.com\",\"password\":\"password\",\"role\":\"Admin\",\"name\":\"membername\",\"dateOfBirth\":\"2020-1-1\",\"gender\":\"weiblich\"}";
    static String updateUserDTOJSONString = "{\"emailAddress\":\"email@test.com.new\",\"password\":\"passwordnew\",\"name\":\"membername\",\"dateOfBirth\":\"2020-1-1\",\"gender\":\"männlich\"}";
    private final String invalidJwtToken = "eyZraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAAOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpC3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJUdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjLzNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjUbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaGzsZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwian9pIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0NCYwNGZmZDFjIn0.XgV-PnqA_LB9OFFE4-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyCDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";

    @Test
    public void testValidateEmail() {
        String emailDTOJSONString = "{\"email\":\"test@example.com\"}";

        given()
                .contentType(ContentType.JSON)
                .body(emailDTOJSONString)
                .when().post("/validateEmail")
                .then()
                .statusCode(200);

        Mockito.verify(userService, times(1)).validateEmail(anyString());
    }

    @Test
    public void testCreateUser() throws HouseholdNotFoundException {
        given()
                .contentType(ContentType.JSON)
                .body(userDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/create")
                .then()
                .statusCode(200);

        Mockito.verify(userService, times(1)).createUser(any(), anyString());
    }

    @Test
    public void testCreateUser_CalledNotVerified() {
        given()
                .contentType(ContentType.JSON)
                .body(userDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/create")
                .then()
                .statusCode(401);
    }

    @Test
    public void testCreateUser_HouseholdNotFoundException() throws HouseholdNotFoundException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(userService).createUser(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(userDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/create")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateUser_InternalServerError() throws HouseholdNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(userService).createUser(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(userDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/create")
                .then()
                .statusCode(500);
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/delete")
                .then()
                .statusCode(200);

        Mockito.verify(userService, times(1)).deleteUser(anyString());
    }

    @Test
    public void testDeleteUser_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().delete("/delete")
                .then()
                .statusCode(401);
    }

    @Test
    public void testDeleteUser_NotFoundException() throws UserNotFoundException {
        Mockito.doThrow(UserNotFoundException.class).when(userService).deleteUser(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/delete/")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteUser_InternalServerError() throws UserNotFoundException  {
        Mockito.doThrow(NullPointerException.class).when(userService).deleteUser(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().delete("/delete")
                .then()
                .statusCode(500);
    }

    @Test
    public void testUpdateUser() throws HouseholdNotFoundException, UserNotFoundException {
        given()
                .contentType(ContentType.JSON)
                .body(updateUserDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(200);

        Mockito.verify(userService, times(1)).updateUser(any(), anyString(), anyString());
    }

    @Test
    public void testUpdateUser_CalledNotVerified() {
        given()
                .contentType(ContentType.JSON)
                .body(updateUserDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/update")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateUser_NotFoundException() throws HouseholdNotFoundException, UserNotFoundException {
        Mockito.doThrow(HouseholdNotFoundException.class).when(userService).updateUser(any(), anyString(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(updateUserDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateUser_InternalServerError() throws HouseholdNotFoundException, UserNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(userService).updateUser(any(), anyString(), anyString());
        given()
                .contentType(ContentType.JSON)
                .body(updateUserDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(500);
    }

    @Test
    public void testGetUserById() throws UserNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);

        Mockito.verify(userService, times(1)).getUserById(anyString());
    }

    @Test
    public void testGetUserById_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/get")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetUserById_UserNotFoundException() throws UserNotFoundException {
        Mockito.doThrow(UserNotFoundException.class).when(userService).getUserById(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetMemberById_InternalServerError() throws UserNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(userService).getUserById(anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(500);
    }

    @Test
    public void testGetAllUsers() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getAll")
                .then()
                .statusCode(200);

        Mockito.verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testGetAllUsers_CalledNotVerified() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/getAll")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetAllUsers_InternalServerError() {
        Mockito.doThrow(NullPointerException.class).when(userService).getAllUsers();

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getAll")
                .then()
                .statusCode(500);
    }
}
