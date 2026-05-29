package api.tests;

import api.assertions.ResponseTimeAssertions;
import api.assertions.StatusCodeAssertions;
import api.assertions.UserAssertions;
import api.endpoints.UserEndpoints;
import api.models.request.CreateUserRequest;
import core.BaseApiTest;
import core.ConfigReader;
import core.TestDataStore;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateUserTest extends BaseApiTest {

    @Test
    public void ts1_api_successCreateUser() {

        String name =
                ConfigReader.getProperty(
                        "create.user.name"
                );

        String job =
                ConfigReader.getProperty(
                        "create.user.job"
                );

        CreateUserRequest body =
                new CreateUserRequest(
                        name,
                        job
                );

        Response response =

                given()
                        .spec(request)
                        .body(body)

                        .when()
                        .post(UserEndpoints.USERS);

        response.prettyPrint();

        TestDataStore.userId =
                response.jsonPath()
                        .getString("id");

        System.out.println(
                "CREATED USER ID: "
                        + TestDataStore.userId
        );

        StatusCodeAssertions
                .validateStatusCode(
                        response,
                        201
                );

        UserAssertions
                .validateUserCreated(
                        response,
                        name,
                        job
                );

        ResponseTimeAssertions
                .validateResponseTimeUnder(
                        response,
                        3000
                );

        response.then()
                .assertThat()
                .body(
                        matchesJsonSchemaInClasspath(
                                "schemas/create-user-schema.json"
                        )
                );
    }
}