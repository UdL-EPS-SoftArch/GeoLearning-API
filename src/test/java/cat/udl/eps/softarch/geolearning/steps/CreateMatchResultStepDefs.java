package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.Match;
import cat.udl.eps.softarch.geolearning.domain.MatchResult;
import cat.udl.eps.softarch.geolearning.domain.Player;
import cat.udl.eps.softarch.geolearning.repository.MatchRepository;
import cat.udl.eps.softarch.geolearning.repository.MatchResultRepository;
import cat.udl.eps.softarch.geolearning.repository.PlayerRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateMatchResultStepDefs {

    private Player player1;
    private Match match;
    private MatchResult matchResult;
    private MatchResult matchResultSaved;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Given("There is a player {string} with password {string} and {string}")
    public void thereIsAPlayerWithPasswordAnd(String username, String password, String email) {
        if(!playerRepository.existsById(username))
        {
            player1 = new Player();
            player1.setUsername(username);
            player1.setPassword(password);
            player1.setEmail(email);
            player1.encodePassword();
            playerRepository.save(player1);
        }
    }

    @And("There is a match with  id {int}")
    public void thereIsAMatchWithId(Integer matchId) {
        if(!matchRepository.existsById(matchId))
        {
            match = new Match();
            match.setId(matchId);
            matchRepository.save(match);
        }
    }

    @Given("There is no registered matchResult for this match attached to the player")
    public void thereIsNoRegisteredMatchResultForThisMatchAttachedToThePlayer() {
        Assert.assertNull(matchResultRepository.findByMatchAndPlayer(match, player1));
    }

    @When("The match is finished with a matchResult with result {int} and time {int}")
    public void theMatchIsFinishedWithAMatchResultWithResultAndTime(int result, int time) {

        if(matchResultRepository.findByMatchAndPlayer(match, player1) == null)
        {
            matchResult = new MatchResult();
            matchResult.setResult(result);
            matchResult.setTime(time);
            matchResult.setMatch(match);
            matchResult.setPlayer(player1);
            matchResultRepository.save(matchResult);
        }
        else
        {
            if(result > matchResultSaved.getResult() || (result == matchResultSaved.getResult() && time < matchResultSaved.getTime()))
            {
                matchResult = new MatchResult();
                matchResult.setResult(result);
                matchResult.setTime(time);
                matchResult.setMatch(match);
                matchResult.setPlayer(player1);
                matchResultRepository.delete(matchResultSaved);
                matchResultRepository.save(matchResult);
            }
        }
    }

    @Then("There is a registered matchResult with result {int} and time {int} for this match attached to the player")
    public void thereIsARegisteredMatchResultWithResultAndTimeForThisMatchAttachedToThePlayer(int result, int time) {
        MatchResult matchResultFinal = matchResultRepository.findByMatchAndPlayer(match, player1);
        Assert.assertEquals(matchResultFinal.getResult(), result);
        Assert.assertEquals(matchResultFinal.getTime(), time);
    }

    @Given("There is a registered matchResult for this match attached to the player with result {int} and time {int}")
    public void thereIsARegisteredMatchResultForThisMatchAttachedToThePlayerWithResultAndTime(int result, int time) {
        MatchResult matchResultSaved = matchResultRepository.findByMatchAndPlayer(match, player1);
        Assert.assertEquals(matchResultSaved.getResult(), result);
        Assert.assertEquals(matchResultSaved.getTime(), time);
    }


}
