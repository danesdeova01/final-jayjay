package testrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/api/features",
        glue = "api.stepdefinitions",
        plugin = {
                "pretty",
                "json:build/reports/api-cucumber-report.json",
                "html:build/reports/api-cucumber-html-report"
        },
        monochrome = true,
        tags = "@api"
)
public class ApiTestRunner {}
