package ui.tests;

import core.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.gistGithub.SeeListOfGists;

public class SeeListOfGistsTest extends GistBaseTest {

    private static final Logger log =
            LoggerFactory.getLogger(ui.tests.SeeListOfGistsTest.class);

    @Test(priority = 1, groups = {"smoke", "regression"})
    public void ts_ui_SeeListOfGists() {

        log.info("Create public gist");

        SeeListOfGists seeListOfGists = new SeeListOfGists(DriverManager.getDriver());
        seeListOfGists.SeeListOfGists();

        Assert.assertTrue(
                seeListOfGists.isUserSuccessSeeListOfGists(),
                "User should see all gists list"
        );
    }
}