package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.*;
import cat.udl.eps.softarch.geolearning.repository.*;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.data.web.JsonPath;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EditMatchStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ContentCreatorRepository creatorRepository;

    @Autowired
    private ImageImageRepository imageRepository;

    @Autowired
    private ImageOptionRepository imageOptionRepository;

    private String newResourceUri;
    private Integer postedId;

    @When("^I edit match with name \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void iEditMatchWithDescription(String name, String description) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        JSONObject match = new JSONObject();
        match.put("description", description);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(match.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I edit match with name \"([^\"]*)\" and description \"([^\"]*)\" and match doesn't exist$")
    public void iEditMatchWithDescriptionNoExists(String name, String description) throws Throwable {
        Optional<Match> optMatch = matchRepository.findByName(name);
        try {
            Match noMatch = optMatch.get();
            JSONObject match = new JSONObject();
            match.put("description", description);
            stepDefs.result = stepDefs.mockMvc.perform(
                    patch("/matches/{id}", noMatch.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(match.toString())
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
        }catch (NoSuchElementException e){}
    }

    @And("^It has been edited a match with name \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void itHasBeenEditedAMatchWithNameDescription(String name, String description) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get(newResourceUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.description", is(description)));
    }

    @And("It has been edited a match with name {string} and rating {int}")
    public void itHasBeenEditedAMatchWithNameDescription(String name, Integer rating) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get(newResourceUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.rating", is(rating)));
    }

    @And("^It has not been edited a match with name \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void itHasNotBeenEditedAMatchWithNameDescription(String name, String description) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
        .andExpect(status().isUnauthorized());
    }

    @When("I edit match with name {string} and new rating {int}")
    public void iEditMatchWithNameAndNewRating(String name, Integer rating) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        JSONObject Match = new JSONObject();
        Match.put("rating", rating);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Match.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I edit match with name \"([^\"]*)\" and new name \"([^\"]*)\"$")
    public void iEditMatchWithNameAndNewName(String name, String name2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        JSONObject Match = new JSONObject();
        Match.put("name", name2);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Match.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("^No match with name \"([^\"]*)\"$")
    public void itDoesNotExistAMatchWithName(String name) throws Throwable {
        Optional<Match> optMatch = matchRepository.findByName(name);
        try {
            Match noMatch = optMatch.get();
            stepDefs.result = stepDefs.mockMvc.perform(
                    get("/match/{id}", noMatch.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }catch (NoSuchElementException e){}
    }
}
