package ui.pages.gistGithub;

import core.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteExistingGist extends BasePage {
    public DeleteExistingGist(WebDriver driver) { super(driver); }

    @FindBy(xpath = "//a[contains(@href,'/FarrasQATestDummy/')]//strong[normalize-space()='TestQA']")
    private WebElement testQAHyperlink;

    @FindBy(xpath = "//form[contains(@action,'/FarrasQATestDummy/')]//button[@type='submit' and contains(@class,'Button--danger')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//div[@role='alert' and normalize-space()='Gist deleted successfully.']")
    private WebElement successDeleteGistNotification;

    public void deleteExistingGist(){
        testQAHyperlink.click();

        waitForElementToBeClickable(deleteButton);
        deleteButton.click();

        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public boolean isUserSuccessDeleteExistingGist(){
        try {
            waitForElementToBeVisible(successDeleteGistNotification);
            return successDeleteGistNotification.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
