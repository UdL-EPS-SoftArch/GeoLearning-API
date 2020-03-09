package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.repository.MatchRepository;
import cat.udl.eps.softarch.geolearning.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchStepDefs {
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private MatchRepository matchRepository;

    @And("There is no registered match with name \"([^\"]*)\"")
    public void noMatchExisting(){

    }
}
