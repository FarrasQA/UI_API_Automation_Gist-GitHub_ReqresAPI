package api.tests;

import api.assertions.ResponseTimeAssertions;
import api.assertions.StatusCodeAssertions;
import api.assertions.UserAssertions;
import api.endpoints.UserEndpoints;
import core.BaseApiTest;
import core.ConfigReader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetSingleUserTest extends BaseApiTest {

    @Test
    public void ts1_api_successGetSingleUser() {

        int userId =
                Integer.parseInt(
                        ConfigReader.getProperty(
                                "single.user.id"
                        )
                );

        String expectedFirstName =
                ConfigReader.getProperty(
                        "single.user.expected.firstname"
                );

        Response response =

                given()
                        .spec(request)
                        .pathParam("id", userId)

                        .when()
                        .get(UserEndpoints.SINGLE_USER);

        StatusCodeAssertions
                .validateStatusCode(
                        response,
                        200
                );

        UserAssertions
                .validateFirstName(
                        response,
                        expectedFirstName
                );

        ResponseTimeAssertions
                .validateResponseTimeUnder(
                        response,
                        5000
                );

        response.then()
                .assertThat()
                .body(
                        matchesJsonSchemaInClasspath(
                                "schemas/single-user-schema.json"
                        )
                );
    }
}