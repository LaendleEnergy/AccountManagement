package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.authentication.AuthenticationService;
import at.fhv.master.laendleenergy.domain.exceptions.UserNotFoundException;
import at.fhv.master.laendleenergy.view.AuthenticationController;
import at.fhv.master.laendleenergy.view.DTOs.AuthRequest;
import at.fhv.master.laendleenergy.view.DTOs.AuthResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(AuthenticationController.class)
public class AuthenticationControllerTests {
    @InjectMock
    AuthenticationService authenticationService;
    final String authRequestJSONString = "{\"emailAddress\":\"test@example.com\",\"password\":\"password\"}";
    final AuthResponse authResponse = new AuthResponse("token");
    final AuthRequest authRequest = new AuthRequest("test@email.com", "password");

    @Test
    public void testAuthenticate() throws Exception {
        Mockito.when(authenticationService.authenticate(authRequest)).thenReturn(authResponse);

        given()
                .contentType(ContentType.JSON)
                .body(authRequestJSONString)
                .when().post("/authenticate")
                .then()
                .statusCode(200);

        Mockito.verify(authenticationService, times(1)).authenticate(any());
    }

    @Test
    public void testAuthenticate_UserNotFound() throws Exception {
        Mockito.when(authenticationService.authenticate(any())).thenThrow(UserNotFoundException.class);

        given()
                .contentType(ContentType.JSON)
                .body(authRequestJSONString)
                .when().post("/authenticate")
                .then()
                .statusCode(404);

        Mockito.verify(authenticationService, times(1)).authenticate(any());
    }
}
