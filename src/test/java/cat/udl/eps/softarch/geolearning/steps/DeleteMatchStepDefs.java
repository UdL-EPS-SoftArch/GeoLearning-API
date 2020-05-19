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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteMatchStepDefs{
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


    @When("I create a new match with name {string} and description {string} with rating {int}")
        public void registerMatch(String name, String description, Integer rating) throws Exception {
            Match match = new Match();
            match.setName(name);
            match.setDescription(description);
            match.setRating(rating);

            stepDefs.result = stepDefs.mockMvc.perform(
                    post("/matches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(stepDefs.mapper.writeValueAsString(match))
//                .content(matchJSON.toString())
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate())
            ).andDo(print());
            newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        }

    @When("I delete match with name {string} and description {string} with rating {int}")
    public void iDeleteAMatchtWithNameDescriptionAndRate(String name, String description , Integer rating) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I delete match with name {string} and description {string} with rating {int}, match not exists")
    public void iDeleteAMatchtWithNameDescriptionAndRateNoExists(String name, String description , Integer rating) throws Throwable {
        Optional<Match> optMatch = matchRepository.findByName(name);
        try {
            Match noMatch = optMatch.get();
            stepDefs.result = stepDefs.mockMvc.perform(
                    delete("/matches/{id}", noMatch.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print())
            .andExpect(status().isNotFound());
        }catch (NoSuchElementException e){}
    }


    @And("^It does not exist a match with name \"([^\"]*)\"$")
    public void itDoesNotExistAMatchWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
        .andExpect(status().isNotFound());
    }

    @Then("The match with name {string} does not exists")
    public void itDoesNotExistAMatchWithName2(String name) throws Throwable {
        Optional<Match> optMatch = matchRepository.findByName(name);
        try {
            Match noMatch = optMatch.get();
            stepDefs.result = stepDefs.mockMvc.perform(
                    get("/matches/{id}", noMatch.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }catch (NoSuchElementException e) {}
    }
}



