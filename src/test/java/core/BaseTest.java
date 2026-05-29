package core;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

@Listeners(TestListener.class)
public class BaseTest {

    protected static Properties config;

    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() {

        try {

            config = new Properties();

            String env =
                    System.getProperty(
                            "env",
                            "staging"
                    );

            String configFile =
                    "config/" + env + ".properties";

            InputStream input =
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(configFile);

            if (input == null) {

                throw new RuntimeException(
                        configFile + " NOT FOUND"
                );
            }

            config.load(input);

            System.out.println(
                    "LOAD CONFIG: " + configFile
            );

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(
            @Optional("chrome") String browser
    ) {

        // PRIORITIZE SYSTEM PROPERTY
        browser =
                System.getProperty(
                        "browser",
                        browser
                );

        DriverManager.initializeDriver(browser);

        driver = DriverManager.getDriver();

        driver.manage().window().maximize();

        driver.manage()
                .timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));

        driver.get(
                config.getProperty("baseUrl")
        );

        System.out.println(
                "OPEN URL: "
                        + config.getProperty("baseUrl")
        );

        System.out.println(
                "RUN BROWSER: " + browser
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        DriverManager.quitDriver();
    }
}