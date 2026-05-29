package api.assertions;

import io.restassured.response.Response;
import org.testng.Assert;

public class UserAssertions {

    public static void validateFirstName(
            Response response,
            String expectedFirstName
    ) {

        Assert.assertEquals(
                response.jsonPath()
                        .getString("data.first_name"),
                expectedFirstName
        );
    }

    public static void validateUserCreated(
            Response response,
            String expectedName,
            String expectedJob
    ) {

        Assert.assertEquals(
                response.jsonPath()
                        .getString("name"),
                expectedName
        );

        Assert.assertEquals(
                response.jsonPath()
                        .getString("job"),
                expectedJob
        );
    }
}