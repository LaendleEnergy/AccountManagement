package at.fhv.master.laendleenergy.integration;

import at.fhv.master.laendleenergy.view.AuthenticationController;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(AuthenticationController.class)
public class AuthenticationIntegrationTests {
    @Test
    public void testAuthenticate() {
        String authRequestJSONString = "{\"emailAddress\":\"alice@example.com\",\"password\":\"password1\"}";

        given()
                .contentType(ContentType.JSON)
                .body(authRequestJSONString)
                .when().post("/authenticate")
                .then()
                .statusCode(200);
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        String authRequestJSONString = "{\"emailAddress\":\"test@example.com\",\"password\":\"password\"}";

        given()
                .contentType(ContentType.JSON)
                .body(authRequestJSONString)
                .when().post("/authenticate")
                .then()
                .statusCode(404);
    }
}
