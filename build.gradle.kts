plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {

    toolchain {

        languageVersion.set(
            JavaLanguageVersion.of(17)
        )
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // =====================================================
    // TEST FRAMEWORK
    // =====================================================

    testImplementation(
        "org.testng:testng:7.11.0"
    )

    testImplementation(
        "org.assertj:assertj-core:3.27.3"
    )

    // =====================================================
    // UI AUTOMATION
    // =====================================================

    implementation(
        "org.seleniumhq.selenium:selenium-java:4.35.0"
    )

    implementation(
        "org.seleniumhq.selenium:selenium-devtools-v139:4.35.0"
    )

    implementation(
        "io.github.bonigarcia:webdrivermanager:6.3.2"
    )

    // =====================================================
    // API AUTOMATION
    // =====================================================

    testImplementation(
        "io.rest-assured:rest-assured:5.5.6"
    )

    testImplementation(
        "io.rest-assured:json-path:5.5.6"
    )

    testImplementation(
        "io.rest-assured:xml-path:5.5.6"
    )

    // =====================================================
    // JSON
    // =====================================================

    implementation(
        "com.fasterxml.jackson.core:jackson-databind:2.17.1"
    )

    implementation(
        "org.json:json:20250517"
    )

    implementation(
        "com.googlecode.json-simple:json-simple:1.1.1"
    )

    testImplementation(
        "io.rest-assured:json-schema-validator:5.5.6"
    )

    // =====================================================
    // REPORTING
    // =====================================================

    implementation(
        "com.aventstack:extentreports:5.1.2"
    )

    testImplementation(
        "io.qameta.allure:allure-testng:2.29.0"
    )

    // =====================================================
    // FILE / EXCEL
    // =====================================================

    implementation(
        "org.apache.poi:poi:5.4.1"
    )

    implementation(
        "org.apache.poi:poi-ooxml:5.4.1"
    )

    implementation(
        "commons-io:commons-io:2.15.1"
    )

    // =====================================================
    // LOGGING
    // =====================================================

    implementation(
        "org.apache.logging.log4j:log4j-api:2.25.1"
    )

    implementation(
        "org.apache.logging.log4j:log4j-core:2.25.1"
    )

    implementation(
        "org.slf4j:slf4j-api:2.0.13"
    )

    implementation(
        "org.slf4j:slf4j-simple:2.0.13"
    )

    // =====================================================
    // ENVIRONMENT VARIABLE
    // =====================================================

    implementation(
        "io.github.cdimascio:dotenv-java:3.2.0"
    )

    // =====================================================
    // HTTP CLIENT
    // =====================================================

    implementation(
        "org.apache.httpcomponents.client5:httpclient5:5.4"
    )

    // =====================================================
    // UTILITIES
    // =====================================================

    implementation(
        "com.github.javafaker:javafaker:1.0.2"
    )

    implementation(
        "org.projectlombok:lombok:1.18.38"
    )

    annotationProcessor(
        "org.projectlombok:lombok:1.18.38"
    )
}

tasks.test {

    // =====================================================
    // TESTNG
    // =====================================================

    useTestNG()

    // =====================================================
    // DYNAMIC SUITE SUPPORT
    // =====================================================

    val suite = System.getProperty("suite")

    if (suite != null) {

        println("Run test suite: $suite")

        useTestNG {
            suites(suite)
        }
    }

    // =====================================================
    // ENVIRONMENT
    // =====================================================

    systemProperty(
        "env",
        System.getProperty(
            "env",
            "staging"
        )
    )

    // =====================================================
    // BROWSER
    // =====================================================

    systemProperty(
        "browser",
        System.getProperty(
            "browser",
            "chrome"
        )
    )

    // =====================================================
    // API BASE URL
    // =====================================================

    systemProperty(
        "base_url",
        System.getProperty(
            "base_url",
            "https://reqres.in"
        )
    )

    // =====================================================
    // TEST LOGGING
    // =====================================================

    testLogging {

        events(
            "passed",
            "skipped",
            "failed",
            "standardOut",
            "standardError"
        )

        showExceptions = true
        showCauses = true
        showStackTraces = true

        exceptionFormat =
            org.gradle.api.tasks.testing.logging
                .TestExceptionFormat.FULL
    }

    // =====================================================
    // JVM CONFIG
    // =====================================================

    systemProperty(
        "file.encoding",
        "UTF-8"
    )

    minHeapSize = "512m"
    maxHeapSize = "2048m"

    // =====================================================
    // PARALLEL EXECUTION
    // =====================================================

    systemProperty(
        "dataproviderthreadcount",
        "3"
    )

    // ALWAYS RERUN TEST
    outputs.upToDateWhen { false }
}