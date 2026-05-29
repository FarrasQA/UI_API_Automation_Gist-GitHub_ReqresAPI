package ui.tests;

import core.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.gistGithub.CreatePublicGist;

public class CreatePublicGistTest extends GistBaseTest {

    private static final Logger log =
            LoggerFactory.getLogger(CreatePublicGistTest.class);

    @Test(priority = 1, groups = {"smoke", "regression"})
    public void ts1_ui_successCreatePublicGist() {

        log.info("Create public gist");

        CreatePublicGist createPublicGist = new CreatePublicGist(DriverManager.getDriver());
        createPublicGist.createPublicGist(
                config.getProperty("gistDescription"),
                config.getProperty("filenameIncludingExtension"),
                config.getProperty("textOrCode"));

        Assert.assertTrue(
                createPublicGist.isUserSuccessCreatePublicGist(),
                "User should see creation status message"
        );
    }

    @Test(priority = 2, groups = {"smoke", "regression"})
    public void ts2_ui_failCreatePublicGistWithEmptyData() {

        log.info("Create public gist with empty data");

        CreatePublicGist createPublicGist = new CreatePublicGist(DriverManager.getDriver());
        createPublicGist.createPublicGistWithEmptyData();

        Assert.assertTrue(
                createPublicGist.isUserFailCreatePublicGist(),
                "User should see fail alert"
        );
    }
}