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

    private Player player;
    private Match match;
    private MatchResult matchResult;
    public String newResourceUri, oldResourceUri;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private StepDefs stepDefs;

    @And("There is a match with name {string} and rating {int}")
    public void thereIsAMatcWithNameAndRating(String name, int rating) {
        if(!matchRepository.existsByName(name))
        {
            match = new Match();
            match.setName(name);
            match.setRating(rating);
            matchRepository.save(match);
        }
    }

    @Given("There is no registered matchResult for this match attached to my user")
    public void thereIsNoRegisteredMatchResultForThisMatchAttachedToMyUser() throws Exception {
        player = playerRepository.findByEmail("demo@sample.app");
        Assert.assertNull(matchResultRepository.findByMatchAndPlayer(match, player));
    }

    @Given("I played the match and It has been created a MatchResult with the result {int} and time {int}")
    public void iPlayedTheMatchAndItHasBeenCreatedMatchResultWithTheResultAndTime(int result, int time) throws Throwable {

        player = playerRepository.findByEmail("demo@sample.app");
        matchResult = new MatchResult();
        matchResult.setResult(result);
        matchResult.setTime(time);
        matchResult.setMatch(match);
        matchResult.setPlayer(player);

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

    @When("The match is finished with a matchResult with result {int} and time {int}")
    public void theMatchIsFinishedWithAMatchResultWithResultAndTime(int result, int time) throws  Throwable{

        if(result > matchResult.getResult() || (result == matchResult.getResult() && time < matchResult.getTime()))
        {
            //We insert another match Result, but we save the direction of the old one to delete it later.
            oldResourceUri = newResourceUri;
            matchResult.setResult(result);
            matchResult.setTime(time);
            matchResult.setMatch(match);
            matchResult.setPlayer(player);
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
    }

    @Given("There is a registered matchResult for this match attached to my user with result {int} and time {int}")
    public void thereIsARegisteredMatchResultForThisMatchAttachedToMyUserWithResultAndTime(int result, int time) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.result", is(result)))
                .andExpect(jsonPath("$.time",is(time)));
    }

    @Then("It has been created a matchResult with result {int} and time {int} for this match attached to my user")
    public void itHasBeenCreatedAMatchResultWithResultAndTimeForThisMatchAttachedToMyUser(int result, int time) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.result", is(result)))
                .andExpect(jsonPath("$.time",is(time)));
    }

    @And("It has been deleted the old MatchResult")
    public void itHasBeenDeletedTheOldMatchResult() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(oldResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
