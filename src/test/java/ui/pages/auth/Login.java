package ui.pages.auth;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Login extends BasePage {
    public Login(WebDriver driver) { super(driver); }

    @FindBy(xpath = "(//div[@aria-label='Sign in or sign up']//a[contains(@data-ga-click,'sign in')])[1]")
    private WebElement signInButton;

    @FindBy(id = "login_field")
    private WebElement inputUsernameOrEmailTextField;

    @FindBy(id = "password")
    private WebElement inputPasswordTextField;

    @FindBy(xpath = "//input[@type='submit' and @value='Sign in']")
    private WebElement signInSubmitButton;

    @FindBy(id = "gists-header-new-gist")
    private WebElement createNewGistButton;

    public void login(String email, String password){
        signInButton.click();
        inputUsernameOrEmailTextField.sendKeys(email);
        inputPasswordTextField.sendKeys(password);
        signInSubmitButton.click();
    }

    public boolean isUserSuccessLogin() {
        try {
            waitForElementToBeVisible(createNewGistButton);
            return createNewGistButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}