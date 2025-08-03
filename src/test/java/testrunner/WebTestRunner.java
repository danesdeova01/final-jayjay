package testrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/web/features",
        glue = "web.stepdefinitions",
        plugin = {
                "pretty",
                "json:build/reports/web-cucumber-report.json",
                "html:build/reports/web-cucumber-html-report"
        },
        monochrome = true,
        tags = "@web"
)
public class WebTestRunner {}