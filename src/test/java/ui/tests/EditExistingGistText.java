package ui.tests;

import core.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.gistGithub.EditExistingGist;
import ui.pages.gistGithub.SeeListOfGists;

public class EditExistingGistText extends GistBaseTest {

    private static final Logger log =
            LoggerFactory.getLogger(EditExistingGistText.class);

    @Test(priority = 1, groups = {"smoke", "regression"})
    public void ts1_ui_editExistingGist() {

        log.info("Edit existing gist");

        SeeListOfGists seeListOfGists = new SeeListOfGists(DriverManager.getDriver());
        seeListOfGists.SeeListOfGists();

        Assert.assertTrue(
                seeListOfGists.isUserSuccessSeeListOfGists(),
                "User should see all gists list"
        );

        EditExistingGist editExistingGist = new EditExistingGist(DriverManager.getDriver());
        editExistingGist.editExistingGist(
                config.getProperty("editGistDescription"),
                config.getProperty("editTextOrCode")
        );

        Assert.assertTrue(
                editExistingGist.isUserSuccessEditExistingGist(),
                "User should see updated gist description"
        );
    }
}