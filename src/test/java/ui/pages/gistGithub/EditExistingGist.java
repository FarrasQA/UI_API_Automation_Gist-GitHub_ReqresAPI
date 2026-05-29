package ui.pages.gistGithub;

import core.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditExistingGist extends BasePage {
    public EditExistingGist(WebDriver driver) { super(driver); }

    @FindBy(xpath = "//a[contains(@href,'/FarrasQATestDummy/')]//strong[normalize-space()='TestQA']")
    private WebElement testQAHyperlink;

    @FindBy(xpath = "//span[@class='Button-label' and normalize-space()='Edit']")
    private WebElement editButton;

    @FindBy(xpath = "//div[@id='gists']//input[@name='gist[description]']")
    private WebElement inputEditGistDescriptionTextField;

    @FindBy(id = "code-editor")
    private WebElement inputEditTextOrCodeTextField;

    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Update public gist']")
    private WebElement updatePublicGistButton;

    @FindBy(xpath = "//div[@itemprop='about']")
    private WebElement updatedGistDescription;

    public void editExistingGist(
            String editGistDescription,
            String editTextOrCode){

        testQAHyperlink.click();
        editButton.click();

        inputEditGistDescriptionTextField.sendKeys((Keys.CONTROL + "a"));
        inputEditGistDescriptionTextField.sendKeys(Keys.DELETE);
        inputEditGistDescriptionTextField.sendKeys(editGistDescription);

        inputEditTextOrCodeTextField.sendKeys((Keys.CONTROL + "a"));
        inputEditTextOrCodeTextField.sendKeys(Keys.DELETE);
        inputEditTextOrCodeTextField.sendKeys(editTextOrCode);

        updatePublicGistButton.click();
    }

    public boolean isUserSuccessEditExistingGist(){
        try {
            waitForElementToBeVisible(updatedGistDescription);
            return updatedGistDescription.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
