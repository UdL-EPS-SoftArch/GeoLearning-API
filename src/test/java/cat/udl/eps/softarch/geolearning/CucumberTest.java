package cat.udl.eps.softarch.geolearning;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty"}, features="src/test/resources/features/CreateImageNameGame.feature", strict = true)
public class CucumberTest {

}
