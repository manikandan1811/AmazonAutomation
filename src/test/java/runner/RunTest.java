package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features="src/test/resources/feature",
    glue = "stepDefinition",
    tags ="@AmazonAddToCart"
    )
public class RunTest extends AbstractTestNGCucumberTests{
	
}
