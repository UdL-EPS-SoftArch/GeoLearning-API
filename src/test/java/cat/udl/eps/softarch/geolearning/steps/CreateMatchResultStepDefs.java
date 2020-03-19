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

    @Given("There is no registered matchResult for this match attached to the player")
    public void thereIsNoRegisteredMatchResultForThisMatchAttachedToThePlayer() {

    }

    @When("The match is finished with a matchResult with result {string} and time {string}")
    public void theMatchIsFinishedWithAMatchResultWithResultAndTime(String arg0, String arg1) {

    }

    @Then("There is a registered matchResult with result {string} and time {string} for this match attached to the player")
    public void thereIsARegisteredMatchResultWithResultAndTimeForThisMatchAttachedToThePlayer(String arg0, String arg1) {

    }

    @Given("There is a registered matchResult for this match attached to the player with result {string} and time {string}")
    public void thereIsARegisteredMatchResultForThisMatchAttachedToThePlayerWithResultAndTime(String arg0, String arg1) {

    }
}
