package ui.pages.gistGithub;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreatePublicGist extends BasePage {
    public CreatePublicGist(WebDriver driver) { super(driver); }

    @FindBy(id = "gists-header-new-gist")
    private WebElement createNewGistButton;

    @FindBy(xpath = "//div[@id='gists']//input[@name='gist[description]']")
    private WebElement inputGistDescriptionTextField;

    @FindBy(xpath = "//div[contains(@class,'gist-filename-input')]//input[@name='gist[contents][][name]']")
    private WebElement inputFilenameIncludingExtensionTextField;

    @FindBy(id = "code-editor")
    private WebElement inputTextOrCodeTextField;

    @FindBy(xpath = "//details[contains(@class,'select-menu')]//summary[@aria-label='Select a type of Gist']")
    private WebElement dropdownButton;

    @FindBy(xpath = "//span[normalize-space()='Create public gist']")
    private WebElement createPublicGistOption;

    @FindBy(xpath = "//div[contains(@class,'BtnGroup')]//button[@type='submit' and normalize-space()='Create public gist']")
    private WebElement createPublicGistButton;

    @FindBy(xpath = "//div[contains(@class,'note') and contains(.,'Created')]")
    private WebElement CreationStatusMessage;

    @FindBy(xpath = "//div[@role='alert' and contains(.,\"Contents can't be empty\")]")
    private WebElement failAlert;

    public void createPublicGist(
            String gistDescription,
            String filenameIncludingExtension,
            String textOrCode){

        createNewGistButton.click();

        inputGistDescriptionTextField.sendKeys(gistDescription);
        inputFilenameIncludingExtensionTextField.sendKeys(filenameIncludingExtension);
        inputTextOrCodeTextField.sendKeys(textOrCode);

        dropdownButton.click();
        createPublicGistOption.click();
        createPublicGistButton.click();
    }

    public boolean isUserSuccessCreatePublicGist(){
        try {
            waitForElementToBeVisible(CreationStatusMessage);
            return CreationStatusMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void createPublicGistWithEmptyData(){
        createNewGistButton.click();
        dropdownButton.click();
        createPublicGistOption.click();
        createPublicGistButton.click();
    }

    public boolean isUserFailCreatePublicGist(){
        try {
            waitForElementToBeVisible(failAlert);
            return failAlert.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
