package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static final Logger log =
            LogManager.getLogger(TestListener.class);

    private static ExtentReports extent;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    private static final String REPORT_DIR =
            System.getProperty("user.dir") + "/reports/";

    private static final String SCREENSHOT_DIR =
            REPORT_DIR + "screenshots/";

    private static int passedCount = 0;
    private static int failedCount = 0;
    private static int skippedCount = 0;

    private static String latestReportPath;

    @Override
    public void onStart(ITestContext context) {

        log.info(
                "========== Test Suite Started: {} ==========",
                context.getName()
        );

        String timestamp =
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                        .format(new Date());

        String reportPath =
                REPORT_DIR + "extent-report_" + timestamp + ".html";

        latestReportPath = reportPath;

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(reportPath);

        sparkReporter.config().setReportName(
                "Automation Test Report"
        );

        sparkReporter.config().setDocumentTitle(
                "Test Execution Report"
        );

        sparkReporter.config().setTheme(Theme.STANDARD);

        sparkReporter.config().setEncoding("utf-8");

        sparkReporter.config().setTimeStampFormat(
                "EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'"
        );

        extent = new ExtentReports();

        extent.attachReporter(sparkReporter);

        extent.setSystemInfo(
                "OS",
                System.getProperty("os.name")
        );

        extent.setSystemInfo(
                "Java Version",
                System.getProperty("java.version")
        );

        extent.setSystemInfo(
                "User",
                System.getProperty("user.name")
        );

        extent.setSystemInfo(
                "Environment",
                System.getProperty("env", "staging")
        );

        new File(SCREENSHOT_DIR).mkdirs();

        log.info(
                "ExtentReports initialized. Report path: {}",
                reportPath
        );
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName =
                result.getMethod().getMethodName();

        String testDescription =
                result.getMethod().getDescription();

        log.info(
                "---------- Test Started: {} ----------",
                testName
        );

        ExtentTest test =
                extent.createTest(
                        testName,
                        testDescription
                );

        test.assignCategory(
                result.getMethod().getGroups()
        );

        extentTest.set(test);

        extentTest.get().log(
                Status.INFO,
                "Test execution started"
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        passedCount++;

        String testName =
                result.getMethod().getMethodName();

        long duration =
                result.getEndMillis()
                        - result.getStartMillis();

        log.info(
                "Test PASSED: {} ({} ms)",
                testName,
                duration
        );

        extentTest.get().log(
                Status.PASS,
                "Test executed successfully"
        );

        extentTest.get().log(
                Status.INFO,
                "Execution time: " + duration + " ms"
        );

        attachScreenshot(testName, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        failedCount++;

        String testName =
                result.getMethod().getMethodName();

        Throwable throwable =
                result.getThrowable();

        log.error("Test FAILED: {}", testName);

        log.error(
                "Failure reason: {}",
                throwable != null
                        ? throwable.getMessage()
                        : "Unknown"
        );

        extentTest.get().log(
                Status.FAIL,
                throwable != null
                        ? throwable.getMessage()
                        : "Unknown Error"
        );

        attachScreenshot(testName, "FAILED");

        if (throwable != null) {

            extentTest.get().log(
                    Status.FAIL,
                    throwable
            );
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        skippedCount++;

        String testName =
                result.getMethod().getMethodName();

        log.warn("Test SKIPPED: {}", testName);

        extentTest.get().log(
                Status.SKIP,
                "Test skipped"
        );
    }

    @Override
    public void onFinish(ITestContext context) {

        log.info(
                "========== Test Suite Finished: {} ==========",
                context.getName()
        );

        log.info(
                "Total: {}, Passed: {}, Failed: {}, Skipped: {}",
                context.getAllTestMethods().length,
                passedCount,
                failedCount,
                skippedCount
        );

        if (extent != null) {
            extent.flush();
        }

        generateSummaryFile();
    }

    private void attachScreenshot(
            String testName,
            String status
    ) {

        WebDriver driver =
                DriverManager.getDriverOrNull();

        // HANDLE API TEST
        if (driver == null) {

            log.warn(
                    "WebDriver is NULL. Screenshot skipped for API test."
            );

            if (extentTest.get() != null) {

                extentTest.get().log(
                        Status.INFO,
                        "Screenshot skipped because no UI driver initialized"
                );
            }

            return;
        }

        try {

            String timestamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(new Date());

            String fileName =
                    status + "_" + testName + "_"
                            + timestamp + ".png";

            String filePath =
                    SCREENSHOT_DIR + fileName;

            String relativePath =
                    "screenshots/" + fileName;

            File srcFile =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            File destFile =
                    new File(filePath);

            FileUtils.copyFile(srcFile, destFile);

            extentTest.get()
                    .addScreenCaptureFromPath(
                            relativePath
                    );

            log.info(
                    "Screenshot saved: {}",
                    filePath
            );

        } catch (Exception e) {

            log.error(
                    "Failed capture screenshot: {}",
                    e.getMessage()
            );
        }
    }

    private void generateSummaryFile() {

        try {

            FileWriter writer =
                    new FileWriter(
                            REPORT_DIR + "summary.txt"
                    );

            writer.write(
                    "PASSED=" + passedCount + "\n"
            );

            writer.write(
                    "FAILED=" + failedCount + "\n"
            );

            writer.write(
                    "SKIPPED=" + skippedCount + "\n"
            );

            writer.write(
                    "REPORT_PATH=" + latestReportPath
            );

            writer.close();

            log.info("summary.txt generated");

        } catch (IOException e) {

            log.error(
                    "Failed generate summary file: {}",
                    e.getMessage()
            );
        }
    }
}