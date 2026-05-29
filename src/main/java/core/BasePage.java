package core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        this.js = (JavascriptExecutor) driver;

        PageFactory.initElements(driver, this);
    }

    public void waitForElementToBeVisible(WebElement element) {

        wait.until(
                ExpectedConditions.visibilityOf(element)
        );
    }

    public void waitForElementToBeClickable(WebElement element) {

        wait.until(
                ExpectedConditions.elementToBeClickable(element)
        );
    }

    public void waitForInvisibility(By locator) {

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );
    }

    public void waitUntilInteractable(WebElement element) {

        wait.until(driver ->
                element.isDisplayed() &&
                        element.isEnabled()
        );
    }

    public void scrollToElement(WebElement element) {

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }

    public void jsClick(WebElement element) {

        js.executeScript(
                "arguments[0].click();",
                element
        );
    }

    public void safeClick(WebElement element) {

        waitForElementToBeClickable(element);

        scrollToElement(element);

        try {

            element.click();

        } catch (ElementClickInterceptedException e) {

            jsClick(element);

        } catch (StaleElementReferenceException e) {

            waitForElementToBeClickable(element);

            jsClick(element);
        }
    }
}
