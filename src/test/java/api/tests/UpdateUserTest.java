package api.tests;

import api.assertions.ResponseTimeAssertions;
import api.assertions.StatusCodeAssertions;
import api.endpoints.UserEndpoints;
import api.models.request.UpdateUserRequest;
import core.BaseApiTest;
import core.ConfigReader;
import core.TestDataStore;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UpdateUserTest extends BaseApiTest {

    @Test(
            priority = 2,
            dependsOnMethods =
                    "api.tests.CreateUserTest.ts1_api_successCreateUser"
    )
    public void ts1_api_successUpdateUser() {

        String updatedJob =
                ConfigReader.getProperty(
                        "update.user.job"
                );

        UpdateUserRequest body =
                new UpdateUserRequest(
                        updatedJob
                );

        Response response =

                given()
                        .spec(request)
                        .pathParam(
                                "id",
                                TestDataStore.userId
                        )
                        .body(body)

                        .when()
                        .put(UserEndpoints.SINGLE_USER);

        response.prettyPrint();

        StatusCodeAssertions
                .validateStatusCode(
                        response,
                        200
                );

        ResponseTimeAssertions
                .validateResponseTimeUnder(
                        response,
                        3000
                );

        Assert.assertEquals(
                response.jsonPath()
                        .getString("job"),
                updatedJob
        );

        response.then()
                .assertThat()
                .body(
                        matchesJsonSchemaInClasspath(
                                "schemas/update-user-schema.json"
                        )
                );
    }
}