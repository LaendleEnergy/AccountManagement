package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.UserService;
import at.fhv.master.laendleenergy.view.UserController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(UserController.class)
public class UserControllerTests {
    @InjectMock
    UserService userService;

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
}
