package api.tests;

import api.assertions.ResponseTimeAssertions;
import api.assertions.StatusCodeAssertions;
import api.endpoints.UserEndpoints;
import core.BaseApiTest;
import core.TestDataStore;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteUserTest extends BaseApiTest {

    @Test(
            priority = 3,
            dependsOnMethods =
                    "api.tests.CreateUserTest.ts1_api_successCreateUser"
    )
    public void ts1_api_deleteUser() {

        Response response =

                given()
                        .spec(request)
                        .pathParam(
                                "id",
                                TestDataStore.userId
                        )

                        .when()
                        .delete(UserEndpoints.SINGLE_USER);

        StatusCodeAssertions
                .validateStatusCode(
                        response,
                        204
                );

        ResponseTimeAssertions
                .validateResponseTimeUnder(
                        response,
                        3000
                );
    }
}