package ui.pages.gistGithub;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SeeListOfGists extends BasePage {
    public SeeListOfGists (WebDriver driver) { super(driver); }

    @FindBy(xpath = "//button[@aria-label='View profile and more']")
    private WebElement profileDropdownButton;

    @FindBy(xpath = "//a[@role='menuitem' and contains(.,'Your gists')]")
    private WebElement yourGistsOption;

    @FindBy(xpath = "//a[@href='/FarrasQATestDummy']//span[normalize-space()='All gists']")
    private WebElement allGistsList;

    public void SeeListOfGists(){
        profileDropdownButton.click();
        yourGistsOption.click();
    }

    public boolean isUserSuccessSeeListOfGists(){
        try {
            waitForElementToBeVisible(allGistsList);
            return allGistsList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
