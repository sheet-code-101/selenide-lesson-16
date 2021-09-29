import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class RestAssuredTests {

    private final static String apiURL = "https://reqres.in/api/";
    private final static int invalidUserID = 27;
    private final static int validUserID = 3;

    @Test
    void checkThatUserCanNotBeFoundByNotValidID() {

        given()
                .when()
                .get(apiURL + "users/" + invalidUserID)
                .then()
                .statusCode(404);

    }

    @Test
    void checkThatUserCanBeFoundByValidID() {

        String name = "Emma";
        ExtractableResponse response = given()
                .when()
                .get(apiURL + "users/" + validUserID)
                .then()
                .statusCode(200)
                .extract();
        assertThat(response.path("data.first_name").toString()).isEqualTo(name);
    }

    @Test
    void checkThatResourcesAmountIsCorrect() {

        given()
                .when()
                .get(apiURL + "unknown/")
                .then()
                .body("total", is(12));
    }

    @Test
    void checkThatParticularResourceColorIsCorrect() {

        int resourceID = 3;
        String resourceName = "true red";
        given()
                .when()
                .get(apiURL + "unknown/" + resourceID)
                .then()
                .body("data.name", is(resourceName));
    }

    @Test
    void checkThatParticularResourceDoesntExist() {

        int resourceID = 13;
        given()
                .when()
                .get(apiURL + "unknown/" + resourceID)
                .then()
                .statusCode(404);
    }
}
