package ui.tests;

import core.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.gistGithub.DeleteExistingGist;
import ui.pages.gistGithub.SeeListOfGists;

public class DeleteExistingGistTest extends GistBaseTest {

    private static final Logger log =
            LoggerFactory.getLogger(ui.tests.DeleteExistingGistTest.class);

    @Test(priority = 1, groups = {"smoke", "regression"})
    public void ts1_ui_deleteExistingGist() {

        log.info("Delete existing gist");

        SeeListOfGists seeListOfGists = new SeeListOfGists(DriverManager.getDriver());
        seeListOfGists.SeeListOfGists();

        Assert.assertTrue(
                seeListOfGists.isUserSuccessSeeListOfGists(),
                "User should see all gists list"
        );

        DeleteExistingGist deleteExistingGist = new DeleteExistingGist(DriverManager.getDriver());
        deleteExistingGist.deleteExistingGist();

        Assert.assertTrue(
                deleteExistingGist.isUserSuccessDeleteExistingGist(),
                "User should see success delete gist notification"
        );
    }
}