package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.Match;
import cat.udl.eps.softarch.geolearning.domain.Player;
import cat.udl.eps.softarch.geolearning.repository.MatchRepository;
import cat.udl.eps.softarch.geolearning.repository.PlayerRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateMatchResultStepDefs {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Given("There is a player {string} with password {string}")
    public void thereIsAPlayerWithPassword(String username, String password) {
        if(!playerRepository.existsById(username))
        {
            Player player1 = new Player();
            player1.setUsername(username);
            player1.setPassword(password);
            player1.encodePassword();
            playerRepository.save(player1);
        }
    }

    @And("There is a match with  id {int}")
    public void thereIsAMatchWithId(Integer matchId) {
        if(!matchRepository.existsById(matchId))
        {
            Match match = new Match();
            match.setId(matchId);
            matchRepository.save(match);
        }
    }

    @Given("There is no registered matchResult for match {int} attached to {string}")
    public void thereIsNoRegisteredMatchResultForMatchAttachedTo(Integer arg0, String arg1) {
        
    }

    @When("The match {string} is finished with a matchResult with result {string} and time {string}")
    public void theMatchIsFinishedWithAMatchResultWithResultAndTime(String arg0, String arg1, String arg2) {
        
    }

    @Then("There is a registered matchResult with result {string} and time {string} for {string} attached to {string}")
    public void thereIsARegisteredMatchResultWithResultAndTimeForAttachedTo(String arg0, String arg1, String arg2, String arg3) {
        
    }

    @Given("There is a registered matchResult for match {string} attached to {string} with result {string} and time {string}")
    public void thereIsARegisteredMatchResultForMatchAttachedToWithResultAndTime(String arg0, String arg1, String arg2, String arg3) {
    }
}
