package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/features",
				plugin = {"pretty", "json:target/cucumber-reports/Cucumber.json",
						  "junit:target/cucumber-reports/Cucumber.xml",
						  "html:target/cucumber-reports/Cucumber.html"}, 
				glue= {"stepDefinitions"})
public class TestRunner {
	//,tags= "@AddPlace" @DeletePlace @Regression
	//compile test verify 
}
