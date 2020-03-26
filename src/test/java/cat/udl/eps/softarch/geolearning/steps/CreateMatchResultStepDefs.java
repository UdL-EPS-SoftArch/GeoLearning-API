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
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CreateMatchResultStepDefs {

    private Player player1;
    private Match match;
    private MatchResult matchResult;
    public String newResourceUri;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private StepDefs stepDefs;

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

    @And("There is a match with id {int}")
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

    @Given("I played the match and is created MatchResult with the result {int} and time {int}")
    public void iPlayedTheMatchAndIsCreatedMatchResultWithTheResultAndTime(int result, int time) throws Throwable {
        MatchResult matchResult = new MatchResult();
        matchResult.setResult(result);
        matchResult.setTime(time);
        matchResult.setMatch(match);
        matchResult.setPlayer(player1);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matchResults")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                stepDefs.mapper.writeValueAsString(matchResult))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print()).andExpect(status().isOk());
        
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("The match is finished with a matchResult with result {int} and time {int}")
    public void theMatchIsFinishedWithAMatchResultWithResultAndTime(int result, int time) throws  Throwable{

        if(matchResultRepository.findByMatchAndPlayer(match, player1) == null)
        {
            matchResult = new MatchResult();
            matchResult.setResult(result);
            matchResult.setTime(time);
            matchResult.setMatch(match);
            matchResult.setPlayer(player1);
            stepDefs.result = stepDefs.mockMvc.perform(
                    post("/matchResults")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    stepDefs.mapper.writeValueAsString(matchResult))
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
            newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        }
        else
        {
            if(result > matchResult.getResult() || (result == matchResult.getResult() && time < matchResult.getTime()))
            {
                matchResult.setResult(result);
                matchResult.setTime(time);
                stepDefs.result = stepDefs.mockMvc.perform(
                        post("/matchResults")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        stepDefs.mapper.writeValueAsString(matchResult))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                        .andDo(print());
            }
        }
    }

    @Then("There is a registered matchResult with result {int} and time {int} for this match attached to the player")
    public void thereIsARegisteredMatchResultWithResultAndTimeForThisMatchAttachedToThePlayer(int result, int time) throws Exception {
        /*stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.result", is(result)));*/
        MatchResult matchResultFinal = matchResultRepository.findByMatchAndPlayer(match, player1);
        Assert.assertEquals(matchResultFinal.getResult(), result);
        Assert.assertEquals(matchResultFinal.getTime(), time);
    }

    @Given("There is a registered matchResult for this match attached to the player with result {int} and time {int}")
    public void thereIsARegisteredMatchResultForThisMatchAttachedToThePlayerWithResultAndTime(int result, int time) {
        matchResult = matchResultRepository.findByMatchAndPlayer(match, player1);
        Assert.assertEquals(matchResult.getResult(), result);
        Assert.assertEquals(matchResult.getTime(), time);
    }
}
