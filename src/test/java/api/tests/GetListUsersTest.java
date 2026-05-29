package api.tests;

import api.assertions.ResponseTimeAssertions;
import api.assertions.StatusCodeAssertions;
import api.endpoints.UserEndpoints;
import core.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetListUsersTest extends BaseApiTest {

    @Test
    public void ts1_api_successGetListUsers() {

        Response response =

                given()
                        .spec(request)
                        .queryParam("page", 2)

                        .when()
                        .get(UserEndpoints.USERS);

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
                        .getInt("page"),
                2
        );

        Assert.assertFalse(
                response.jsonPath()
                        .getList("data")
                        .isEmpty()
        );
    }
}