package ui.tests;

import core.BaseTest;
import core.DriverManager;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import ui.pages.auth.Login;

public class GistBaseTest extends BaseTest {

    protected Login login;

    @BeforeMethod(alwaysRun = true)
    public void setupGistPage() {

        login = new Login(DriverManager.getDriver());

        login.login(
                config.getProperty("email"),
                config.getProperty("password")
        );

        Assert.assertTrue(
                login.isUserSuccessLogin(),
                "User should see create new gist button"
        );
    }
}